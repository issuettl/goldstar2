/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.life.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.lge.goldstar.core.pagination.PageRequestSupport;
import kr.co.lge.goldstar.mvc.c.encrypt.service.EncryptService;
import kr.co.lge.goldstar.mvc.m.life.service.LifeService;
import kr.co.lge.goldstar.mvc.m.manager.service.ManagerLogExecution;
import kr.co.lge.goldstar.orm.jpa.entity.LifeStatus;
import kr.co.lge.goldstar.orm.jpa.entity.LifeType;
import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.life.LifeDateEntity;
import kr.co.lge.goldstar.orm.jpa.entity.life.LifePartEntity;
import kr.co.lge.goldstar.orm.jpa.repository.dsl.LifePartDslRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.LifeDateRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.LifePartRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author NEOFLOW
 *
 */
@Service("m.LifeService")
public class LifeServiceImpl implements LifeService {

	@Autowired
	private LifePartRepository lifePartRepository;

	@Autowired
	private LifeDateRepository lifeDateRepository;

	@Autowired
	private LifePartDslRepository lifePartDslRepository;
	
	@Autowired
	private EncryptService encryptService;

	@Override
	public List<LifePartEntity> getLifePart(int memberSn, String created) {
		return this.lifePartRepository.findByMemberSnAndDate(memberSn, created);
	}

	@Override
	@ManagerLogExecution(process = "getPageWeekday(DataMap params)", menu="예약 확정 리스트", button="검색")
	public Page<LifePartEntity> getPageWeekday(DataMap params) {
		
		Page<LifePartEntity> page = this.lifePartDslRepository.findPageByWeekday(
                params, PageRequestSupport.getPageRequest(params));
        
        if(ObjectUtils.isEmpty(page) || ObjectUtils.isEmpty(page.getContent())){
            return page;
        }
        
        int today = Integer.parseInt(DateUtils.getToday("yyyyMMdd"));
        //if 콘텐츠 변경
        page.getContent().forEach(item -> {
        	try {
				item.getMember().setNameDec(this.encryptService.decryptAES256(item.getMember().getName()));
			} catch (Exception e) {
			}
        	try {
				item.getMember().setPhoneDec(this.encryptService.decryptAES256(item.getMember().getPhone()));
			} catch (Exception e) {
			}
        	
        	item.setStatusName(item.getStatus().getWeekday());
        	if(LifeStatus.status2.equals(item.getStatus())) {
        		if(today > Integer.parseInt(item.getDate())) {
        			item.setStatusName("기간만료");
        		}
        		
        		else if(LifeType.weekend.equals(item.getType())){
        			item.setStatusName("당첨");
        		}
        	}
        });
        
        return page;
	}

	@Override
	@ManagerLogExecution(process = "getPageWeekend(DataMap params)", menu="주말 응모 리스트", button="검색")
	public Page<LifePartEntity> getPageWeekend(DataMap params) {
		
		Page<LifePartEntity> page = this.lifePartDslRepository.findPageByWeekend(
                params, PageRequestSupport.getPageRequest(params));
        
        if(ObjectUtils.isEmpty(page) || ObjectUtils.isEmpty(page.getContent())){
            return page;
        }

        //if 콘텐츠 변경
        page.getContent().forEach(item -> {
        	try {
				item.getMember().setNameDec(this.encryptService.decryptAES256(item.getMember().getName()));
				item.getMember().setPhoneDec(this.encryptService.decryptAES256(item.getMember().getPhone()));
			} catch (Exception e) {
			}
        	
        	item.setStatusName(item.getStatus().getWeekend());
        });
        
        return page;
	}

	@Override
	@ManagerLogExecution(process = "adminCancel(DataMap params)", menu="예약 확정 리스트", button="삭제")
	public DataMap adminCancel(DataMap dataMap) {
		
		Optional<LifePartEntity> lifePartEntity = this.lifePartRepository.findById(dataMap.getAsInt("sn"));
		if(lifePartEntity.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "예약정보를 찾을 수 없습니다.");
			return result;
		}
		
		LifePartEntity lifePart = lifePartEntity.get();
		
		if(LifeType.weekend.equals(lifePart.getType())) {
			lifePart.setStatus(LifeStatus.status3);
		}
		else {
			lifePart.setStatus(LifeStatus.status6);
		}
		lifePart.setCanceledAdmin(DateUtils.getToday("yyyyMMddHHmmss"));
		lifePart.setMemo(dataMap.getAsString("memo"));
		
		this.lifePartRepository.save(lifePart);
		
		DataMap result = new DataMap(true);
		return result;
	}
	

	@Override
	@ManagerLogExecution(process = "weekdayStaff(DataMap params)", menu="예약 확정 리스트", button="스탭체크")
	public DataMap weekdayStaff(DataMap dataMap) {
		switch (dataMap.getAsEnum("staffCheck", StaffCheck.class)) {
		case notyet:
			return weekdayStaffCancel(dataMap);
		case present:
			return weekdayStaffCheck(dataMap);

		default:
			break;
		}
		
		DataMap result = new DataMap(false);
		result.put("reason", "스탭체크를 찾을 수 없습니다.");
		return result;
	}

	private DataMap weekdayStaffCancel(DataMap dataMap) {
		
		Optional<LifePartEntity> lifePartEntity = this.lifePartRepository.findById(dataMap.getAsInt("sn"));
		if(lifePartEntity.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "예약정보를 찾을 수 없습니다.");
			return result;
		}
		
		LifePartEntity lifePart = lifePartEntity.get();
		
		if(!LifeStatus.status4.equals(lifePart.getStatus())) {
			DataMap result = new DataMap(false);
			result.put("reason", "체험완료 상태인 경우에만 체크할 수 있습니다.");
			return result;
		}
		
		//응모의 경우 다른 당첨자가 있으면 안된다.
		if(LifeType.weekend.equals(lifePart.getType())) {
			List<LifePartEntity> list = this.lifePartRepository.findByTypeAndDateAndTimeAndStatus(lifePart.getType(), lifePart.getDate(), lifePart.getTime(), LifeStatus.status2);
			if(list.size() > 0) {
				DataMap result = new DataMap(false);
				result.put("reason", "이미 다른 당첨자가 존재합니다 체험상태를 변경할 수 없습니다.");
				return result;
			}
		}

		lifePart.setStatus(LifeStatus.status2);
		lifePart.setStaffCreated(DateUtils.getToday("yyyyMMddHHmmss"));
		lifePart.setStaffCheck(StaffCheck.notyet);
		
		this.lifePartRepository.save(lifePart);
		
		DataMap result = new DataMap(true);
		return result;
	}

	private DataMap weekdayStaffCheck(DataMap dataMap) {
		
		Optional<LifePartEntity> lifePartEntity = this.lifePartRepository.findById(dataMap.getAsInt("sn"));
		if(lifePartEntity.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "예약정보를 찾을 수 없습니다.");
			return result;
		}
		
		LifePartEntity lifePart = lifePartEntity.get();
		
		if(!LifeStatus.status2.equals(lifePart.getStatus())) {
			DataMap result = new DataMap(false);
			result.put("reason", "예약 상태인 경우에만 체크할 수 있습니다.");
			return result;
		}
		
		lifePart.setStatus(LifeStatus.status4);
		lifePart.setStaffCreated(DateUtils.getToday("yyyyMMddHHmmss"));
		lifePart.setStaffCheck(StaffCheck.present);
		
		this.lifePartRepository.save(lifePart);
		
		DataMap result = new DataMap(true);
		return result;
	}

	@Override
	@ManagerLogExecution(process = "adminConfirm(DataMap dataMap)", menu="주말 응모 리스트", button="당첨")
	public DataMap adminConfirm(DataMap dataMap) {
		
		Optional<LifePartEntity> lifePartEntity = this.lifePartRepository.findById(dataMap.getAsInt("sn"));
		if(lifePartEntity.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "예약정보를 찾을 수 없습니다.");
			return result;
		}
		
		LifePartEntity lifePart = lifePartEntity.get();
		/*
		if(!LifeStatus.status1.equals(lifePart.getStatus())) {
			DataMap result = new DataMap(false);
			result.put("reason", "응모 중 상태인 경우에만 당첨시킬 수 있습니다.");
			return result;
		}*/
		
		String today = DateUtils.getToday("yyyyMMddHHmmss");
		
		//미당첨자
		List<LifePartEntity> parts = this.lifePartRepository.findByTypeAndDateAndTimeAndSnNot(lifePart.getType(), lifePart.getDate(), lifePart.getTime(), lifePart.getSn());

		//당첨처리
		lifePart.setStatus(LifeStatus.status2);
		lifePart.setReservatedAdmin(today);
		this.lifePartRepository.save(lifePart);
		
		//미당첨처리
		if(CollectionUtils.isNotEmpty(parts)) {
			parts.forEach(item -> {
				item.setStatus(LifeStatus.status3);
				item.setReservatedAdmin(today);
			});
			
			this.lifePartRepository.saveAll(parts);
		}
		
		DataMap result = new DataMap(true);
		return result;
	}

	@Override
	public DataMap adminConfirms(DataMap dataMap) {
		
		List<LifePartEntity> parts = this.lifePartRepository.findBySnIn(dataMap.getListAsInteger("sn[]"));
		
		if(CollectionUtils.isEmpty(parts)) {
			DataMap result = new DataMap(false);
			result.put("reason", "예약정보를 찾을 수 없습니다.");
			return result;
		}

		String today = DateUtils.getToday("yyyyMMddHHmmss");
		boolean isNotStatus3 = false;
		
		for(LifePartEntity item : parts) {
			if(!LifeStatus.status3.equals(item.getStatus())) {
				isNotStatus3 = true;
			}
			
			item.setStatus(LifeStatus.status2);
			item.setReservatedAdmin(today);
		}
		
		if(isNotStatus3) {
			DataMap result = new DataMap(false);
			result.put("reason", "미당첨이 아닌 상태가 있습니다.");
			return result;
		}

		this.lifePartRepository.saveAll(parts);
		
		DataMap result = new DataMap(true);
		return result;
	}

	@Override
	@ManagerLogExecution(process = "weekdayDateAdd(DataMap dataMap)", menu="예약 확정 리스트", button="예약 불가일 설정")
	public DataMap weekdayDateAdd(DataMap dataMap) {

		String date = dataMap.getAsString("date");
		LifeDateEntity entity = this.lifeDateRepository.findByDate(date);
		
		if(ObjectUtils.isEmpty(entity)) {
			entity = new LifeDateEntity();
			entity.setDate(date);
			
			this.lifeDateRepository.save(entity);
			
			DataMap result = new DataMap(true);
			result.put("date", entity);
			return result;
		}
		
		DataMap result = new DataMap(false);
		result.put("reason", "이미 등록되어 있습니다.");
		return result;
	}

	@Override
	@ManagerLogExecution(process = "weekdayDateRemove(DataMap dataMap)", menu="예약 확정 리스트", button="예약 불가일 삭제")
	public DataMap weekdayDateRemove(DataMap dataMap) {

		Optional<LifeDateEntity> entity = this.lifeDateRepository.findById(dataMap.getAsInt("sn"));
		
		if(entity.isPresent()) {
			this.lifeDateRepository.delete(entity.get());
			return new DataMap(true);
		}
		
		DataMap result = new DataMap(false);
		result.put("reason", "해당 날짜를 찾을 수 없습니다.");
		return result;
	}

	@Override
	public List<LifeDateEntity> getNotDates() {
		return this.lifeDateRepository.findAllByOrderByDateAsc();
	}

}
