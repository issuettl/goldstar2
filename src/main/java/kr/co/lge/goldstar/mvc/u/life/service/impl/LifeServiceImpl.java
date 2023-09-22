/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.life.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import kr.co.lge.goldstar.mvc.u.life.service.LifeService;
import kr.co.lge.goldstar.mvc.u.sign.service.SignService;
import kr.co.lge.goldstar.orm.jpa.entity.LifeStatus;
import kr.co.lge.goldstar.orm.jpa.entity.LifeTime;
import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.life.LifeDateEntity;
import kr.co.lge.goldstar.orm.jpa.entity.life.LifePartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;
import kr.co.lge.goldstar.orm.jpa.repository.dsl.LifePartDslRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.LifeDateRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.LifePartRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Service("u.LifeService")
public class LifeServiceImpl implements LifeService {
	
	@Autowired
	private LifePartRepository lifePartRepository;
	
	@Autowired
	private LifePartDslRepository lifePartDslRepository;
	
	@Autowired
	private LifeDateRepository lifeDateRepository;
	
	@Autowired
	private SignService signService;

	@Override
	public DataMap bookingTimes(LifePartEntity lifePartEntity) {
		
		List<LifePartEntity> entities = this.lifePartRepository.findByDate(lifePartEntity.getDate());
		
		List<LifeTime> times = new ArrayList<>();
		for(LifeTime lifeTime : LifeTime.values()) {
			
			boolean isBookinged = false;
			if(CollectionUtils.isNotEmpty(entities)) {
				for(LifePartEntity part : entities) {
					
					if(lifeTime.equals(part.getTime())) {
						
						switch (part.getStatus()) {
						case status2:
						case status4:
							isBookinged = true;
							break;
						default:
							break;
						}
					}
				}
			}
			
			if(!isBookinged) {
				times.add(lifeTime);
			}
		}
		
		DataMap results = new DataMap(true);
		results.put("times", times);
		
		return results;
	}

	@Override
	public synchronized DataMap bookingAction(LifePartEntity lifePartEntity) {

		if(!this.signService.existMemberIn()) {
			DataMap result = new DataMap(false);
			result.put("reason", "통합회원 로그인 상태가 아닙니다.");
			result.put("code", 1000);
			return result;
		}
		
		if(lifePartEntity == null) {
			DataMap result = new DataMap(false);
			result.put("reason", "잘못 된 데이터 입니다.");
			result.put("code", 1001);
			return result;
		}
		
		if(!StringUtils.hasText(lifePartEntity.getDate())) {
			DataMap result = new DataMap(false);
			result.put("reason", "잘못 된 데이터 입니다.");
			result.put("code", 1002);
			return result;
		}
		
		if(!StringUtils.hasText(lifePartEntity.getTime().getName())) {
			DataMap result = new DataMap(false);
			result.put("reason", "잘못 된 데이터 입니다.");
			result.put("code", 1003);
			return result;
		}
		
		if(DateUtils.getDiffDayCount(lifePartEntity.getDate(), DateUtils.getToday("yyyyMMdd")) >= 0) {
			DataMap result = new DataMap(false);
			result.put("reason", "신청일은 오늘 날짜보다 작거나 같을수 없습니다.");
			result.put("code", 2001);
			return result;
		}
		
		MemberEntity member = this.signService.getMemberIn();
		
		List<LifePartEntity> parts = this.lifePartRepository.findByMemberSnAndDate(member.getSn(), lifePartEntity.getDate());
		if(!CollectionUtils.isEmpty(parts)) {
			for(LifePartEntity part : parts) {
				if(LifeStatus.status1.equals(part.getStatus()) || LifeStatus.status2.equals(part.getStatus()) || LifeStatus.status3.equals(part.getStatus()) || LifeStatus.status4.equals(part.getStatus())) {
					DataMap result = new DataMap(false);
					result.put("reason", "해당 날짜는 이미 고객님의 예약정보가 있습니다.");
					result.put("code", 3001);
					return result;
				}
			}
		}
		
		parts = this.lifePartRepository.findByDateAndMemberPhone(lifePartEntity.getDate(), member.getPhone());
		if(!CollectionUtils.isEmpty(parts)) {
			for(LifePartEntity part : parts) {
				if(LifeStatus.status1.equals(part.getStatus()) || LifeStatus.status2.equals(part.getStatus()) || LifeStatus.status3.equals(part.getStatus()) || LifeStatus.status4.equals(part.getStatus())) {
					DataMap result = new DataMap(false);
					result.put("reason", "해당 날짜는 이미 고객님의 예약정보가 있습니다.");
					result.put("code", 3001);
					return result;
					
				}
			}
		}
		
		parts = this.lifePartRepository.findByDateAndTime(lifePartEntity.getDate(), lifePartEntity.getTime());
		if(CollectionUtils.isEmpty(parts)) {
			savePart(lifePartEntity);
			
			DataMap result = new DataMap(true);
			result.put("part", lifePartEntity);
			
			return result;
		}
		
		boolean isBookinged = false;
		for(LifePartEntity part : parts) {
			
			switch (part.getType()) {
			case weekday:
				
				switch (part.getStatus()) {
				case status2:
				case status4:
					isBookinged = true;
					break;
				default:
					break;
				}
				
				break;
			case weekend:
				
				switch (part.getStatus()) {
				case status2:
				case status4:
					isBookinged = true;
					break;
				default:
					break;
				}
				
				break;

			default:
				break;
			}
		}
		
		if(isBookinged) {
			DataMap result = new DataMap(false);
			result.put("reason", "이미 신청완료된 시간입니다.");
			result.put("code", 3001);
			return result;
		}
		
		//저장
		savePart(lifePartEntity);
		
		DataMap result = new DataMap(true);
		result.put("part", lifePartEntity);
		
		return result;
	}

	/**
	 * @param lifePartEntity
	 */
	private void savePart(LifePartEntity lifePartEntity) {
		
		MemberEntity member = this.signService.getMemberIn();
		lifePartEntity.setMemberSn(member.getSn());
		
		switch (lifePartEntity.getType()) {
		case weekday:
			lifePartEntity.setStatus(LifeStatus.status2);
			break;
		case weekend:
			lifePartEntity.setStatus(LifeStatus.status1);
			break;

		default:
			break;
		}
		
		lifePartEntity.setReservated(DateUtils.getToday("yyyyMMddHHmmss"));
		lifePartEntity.setStaffCheck(StaffCheck.notyet);
		
		this.lifePartRepository.save(lifePartEntity);
	}

	@Override
	public LifePartEntity getLifePart() {
		
		MemberEntity member = this.signService.getMemberIn();
		
		String today = DateUtils.getToday("yyyyMMdd");
		List<LifePartEntity> parts = this.lifePartRepository.findByMemberSnAndDate(member.getSn(), today);
		if(CollectionUtils.isEmpty(parts)) {
			parts = this.lifePartRepository.findByDateAndMemberPhone(today, member.getPhone());
		}
		
		for(LifePartEntity part : parts) {
			switch (part.getType()) {
			case weekday:
				switch (part.getStatus()) {
				case status2:
				case status4:
					return part;
				default:
					break;
				}
				break;
			case weekend:
				switch (part.getStatus()) {
				case status1:
				case status2:
				case status3:
					return part;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}
		
		return null;
	}
	
	private String getFriday(String yyyyMMdd) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(yyyyMMdd.substring(0, 4)), Integer.parseInt(yyyyMMdd.substring(4, 6)) - 1, Integer.parseInt(yyyyMMdd.substring(6)));
 		calendar.set(Calendar.WEEK_OF_MONTH, calendar.get(Calendar.WEEK_OF_MONTH)); //선택 날짜의 주 차
 		calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
 		
 		return formatter.format(calendar.getTime());
	}
	
	private int getWeekday(String yyyyMMdd) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(yyyyMMdd.substring(0, 4)), Integer.parseInt(yyyyMMdd.substring(4, 6)) - 1, Integer.parseInt(yyyyMMdd.substring(6)));
 		
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	private String[] getWeekday(String[] weekdayDates) {
		
		List<String> dates = new ArrayList<>();
		
		for(String d : weekdayDates) {
			int weekday = getWeekday(d);
			if(weekday != 1 && weekday != 7) {
				dates.add(d);
			}
		}
		
		String[] result = new String[dates.size()];
		dates.toArray(result);
        return result;
	}
	
	private String[] getWeekend(String[] weekdayDates) {
		
		List<String> dates = new ArrayList<>();
		
		for(String d : weekdayDates) {
			int weekday = getWeekday(d);
			if(weekday == 1 || weekday == 7) {
				dates.add(d);
			}
		}
		
		String[] result = new String[dates.size()];
		dates.toArray(result);
        return result;
	}

	@Override
	public List<String> getWeekdayDates() {
		
		List<String> dates = new ArrayList<>();
		/*
		 * 차주 월요일~금요일 선택가능
		 * 12일부터 18일까지는 차주 월~금
		 * 
		 * 나머지는 내일부터 차주 월~금
		 */
		String today = DateUtils.getToday("yyyyMMdd");
		
		//내일부터 다음주 금요일까지.
		//단, 일요일이면 그 주 금요일까지.
		
		int weekday = getWeekday(today);
		
		//월요일 오전 9시 이전엔 일요일로 계산함
		if(weekday == 2) {
			int currentTime = Integer.parseInt(DateUtils.getToday("HH"));
			if(currentTime < 9) {
				today = DateUtils.getAddDate(today, Calendar.DATE, -1, "yyyyMMdd");
				weekday = getWeekday(today);
			}
		}
		
		String start = DateUtils.getAddDate(today, Calendar.DATE, 1, "yyyyMMdd");
		String nextWeek = DateUtils.getAddDate(today, Calendar.DATE, 7, "yyyyMMdd");
		String end = weekday == 1 ? getFriday(today) : getFriday(nextWeek);

		String[] weekdayDates = getWeekday(DateUtils.getDiffDays(start, end));
		
		List<LifeDateEntity> notDates = this.lifeDateRepository.findAll();
		for(String dd : weekdayDates) {
			if(!hasDate(notDates, dd)) {
				dates.add(dd);
			}
		}
		
		return dates;
	}

	@Override
	public List<String> getWeekendDates() {
		
		List<String> dates = new ArrayList<>();
		/*
		 * 차주 월요일~금요일 선택가능
		 * 나머지는 내일부터 차주 월~금
		 */
		String today = DateUtils.getToday("yyyyMMdd");
		
		//내일부터 다음주 금요일까지.
		//단, 일요일이면 그 주 금요일까지.
		int weekday = getWeekday(today);
		
		//월요일 오전 9시 이전엔 일요일로 계산함
		if(weekday == 2) {
			int currentTime = Integer.parseInt(DateUtils.getToday("HH"));
			if(currentTime < 9) {
				today = DateUtils.getAddDate(today, Calendar.DATE, -1, "yyyyMMdd");
				weekday = getWeekday(today);
			}
		}
		
		//내일부터 다음주 금요일까지.
		//단, 일요일이면 그 주 금요일까지.
		String start = DateUtils.getAddDate(today, Calendar.DATE, 1, "yyyyMMdd");
		String nextWeek = DateUtils.getAddDate(today, Calendar.DATE, 7, "yyyyMMdd");
		String end = weekday == 1 ? getFriday(today) : getFriday(nextWeek);
		
		//다음주 주말
		start = DateUtils.getAddDate(start, Calendar.DATE, 7, "yyyyMMdd");
		end = DateUtils.getAddDate(end, Calendar.DATE, 7, "yyyyMMdd");
		
		String[] weekdayDates = getWeekend(DateUtils.getDiffDays(start, end));
		
		List<LifeDateEntity> notDates = this.lifeDateRepository.findAll();
		for(String dd : weekdayDates) {
			if(!hasDate(notDates, dd)) {
				dates.add(dd);
			}
		}
		
		return dates;
	}
	
	private boolean hasDate(List<LifeDateEntity> entities, String date) {
		for(LifeDateEntity entity : entities) {
			if(entity.getDate().equals(date)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<LifePartEntity> myList() {

		MemberEntity member = this.signService.getMemberIn();
		
		List<LifePartEntity> parts = this.lifePartDslRepository.findAllByMemberSnOrderByDateDescTimeDesc(member.getSn(), member.getName(), member.getPhone());
		
		if(CollectionUtils.isEmpty(parts)) {
			return parts;
		}
		
		int today = Integer.parseInt(DateUtils.getToday("yyyyMMdd"));
		parts.forEach(item -> {
			switch (item.getType()) {
			case weekday:
				item.setStatusName(item.getStatus().getWeekday());
	        	if(LifeStatus.status2.equals(item.getStatus())) {
	        		
	        		item.setStatusName("확정");
	        		
	        		if(today > Integer.parseInt(item.getDate())) {
	        			item.setStatusName("기간만료");
	        		}
	        	}
				break;
			case weekend:
				item.setStatusName(item.getStatus().getWeekend());
				break;
			default:
				break;
			}
		});
		
		return parts;
	}

	@Override
	public DataMap bookingCancel(LifePartEntity params) {
		
		Optional<LifePartEntity> lifePartEntity = this.lifePartRepository.findById(params.getSn());
		if(lifePartEntity.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "예약정보를 찾을 수 없습니다.");
			return result;
		}

		LifePartEntity lifePart = lifePartEntity.get();
		
		List<LifePartEntity> myList = this.myList();
		boolean isMyList = false;
		for(LifePartEntity part : myList) {
			if(part.getSn() == lifePart.getSn()) {
				isMyList = true;
			}
		}
		
		if(!isMyList) {
			DataMap result = new DataMap(false);
			result.put("reason", "본인의 예약정보가 아닙니다.");
			return result;
		}
		
		switch (lifePart.getType()) {
		case weekday:
			if(!LifeStatus.status2.equals(lifePart.getStatus())) {
				DataMap result = new DataMap(false);
				result.put("reason", "취소할 수 없는 상태입니다.");
				return result;
			}
			break;
		case weekend:
			if(!LifeStatus.status1.equals(lifePart.getStatus())) {
				DataMap result = new DataMap(false);
				result.put("reason", "취소할 수 없는 상태입니다.");
				return result;
			}
			break;

		default:
			break;
		}
		
		if(lifePart.getDate().equals(DateUtils.getToday("yyyyMMdd"))) {
			DataMap result = new DataMap(false);
			result.put("reason", "당일 예약 취소는 불가합니다.");
			return result;
		}
		
		lifePart.setStatus(LifeStatus.status5);
		lifePart.setCanceled(DateUtils.getToday("yyyyMMddHHmmss"));
		
		this.lifePartRepository.save(lifePart);
		
		DataMap result = new DataMap(true);
		return result;
	}

}
