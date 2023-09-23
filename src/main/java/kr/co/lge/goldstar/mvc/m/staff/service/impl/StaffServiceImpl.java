/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.staff.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.lge.goldstar.mvc.c.encrypt.service.EncryptService;
import kr.co.lge.goldstar.mvc.m.indiv.service.IndivService;
import kr.co.lge.goldstar.mvc.m.life.service.LifeService;
import kr.co.lge.goldstar.mvc.m.mind.service.MindService;
import kr.co.lge.goldstar.mvc.m.mood.service.MoodService;
import kr.co.lge.goldstar.mvc.m.refresh.service.RefreshService;
import kr.co.lge.goldstar.mvc.m.staff.service.StaffService;
import kr.co.lge.goldstar.mvc.m.style.service.StyleService;
import kr.co.lge.goldstar.orm.jpa.entity.CornerType;
import kr.co.lge.goldstar.orm.jpa.entity.LifeStatus;
import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.indiv.IndivPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.life.LifePartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.JammyEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignId;
import kr.co.lge.goldstar.orm.jpa.entity.mind.MindPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.refresh.RefreshPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.style.StylePartEntity;
import kr.co.lge.goldstar.orm.jpa.repository.dsl.SignDslRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.JammyRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.LifePartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MemberRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.SignRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author NEOFLOW
 *
 */
@Service("m.StaffService")
public class StaffServiceImpl implements StaffService {
	
	@Autowired
	private SignRepository signRepository;
	
	@Autowired
	private SignDslRepository signDslRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private JammyRepository jammyRepository;
	
	@Autowired
	private EncryptService encryptService;
	
	@Resource(name = "m.MindService")
	private MindService mindService;

	@Resource(name = "m.StyleService")
	private StyleService styleService;

	@Resource(name = "m.IndivService")
	private IndivService indivService;

	@Resource(name = "m.MoodService")
	private MoodService moodService;

	@Resource(name = "m.RefreshService")
	private RefreshService refreshService;

	@Resource(name = "m.LifeService")
	private LifeService lifeService;

	@Autowired
	private LifePartRepository lifePartRepository;

	@Override
	public DataMap getSign(SignId signId) {

		String today = DateUtils.getToday("yyyyMMdd");
		
		if(!today.equals(signId.getCreated())) {
			DataMap result = new DataMap(false);
			result.put("reason", "유효한 날짜가 아닙니다.");
			result.put("code", 403);
			return result;
		}
		
		Optional<SignEntity> signEntity = this.signRepository.findById(signId);
		
		if(signEntity.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "회원을 찾을수 없습니다.");
			result.put("code", 404);
			return result;
		}
		
		DataMap result = new DataMap(true);
		
		SignEntity sign = signEntity.get();
		MemberEntity member = sign.getMember();

		try {
			result.put("nick", this.encryptService.decryptAES256(sign.getNick()));
		} catch (Exception e) {
			result.put("nick", sign.getNick());
		}
		result.put("worry", sign.getWorryType() != null ? sign.getWorryType().getDesc() : null);
		result.put("jammy", sign.getMember().getJammy());
		
		DataMap mind = new DataMap();
		MindPartEntity mindPart = this.mindService.getMindPart(signId.getMemberSn(), signId.getCreated());
		result.put("mind", mind);
		mind.put("corner", CornerType.mind.getTitle());																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
		if(ObjectUtils.isEmpty(mindPart)) {
			mind.put("questions", "미체험");
			mind.put("recommend", "-");
			mind.put("check", "-");
			mind.put("checkValue", null);
			mind.put("status", "미체험");
		} else {
			mind.put("questions", "종료");
			mind.put("recommend", mindPart.getType() == null ? "-" : mindPart.getType().getTitle());
			mind.put("check", mindPart.getStaffCheck().getTitle());
			mind.put("checkValue", mindPart.getStaffCheck().getName());
			
			switch (mindPart.getStaffCheck()) {
			case present:
				mind.put("status", "완료");
				break;
			case notyet:
				mind.put("status", "사전 문답");
				break;
			default:
				break;
			}
		}
		
		DataMap indiv = new DataMap();
		IndivPartEntity indivPart = this.indivService.getIndivPart(signId.getMemberSn(), signId.getCreated());
		result.put("indiv", indiv);
		indiv.put("corner", CornerType.indiv.getTitle());
		if(ObjectUtils.isEmpty(indivPart)) {
			indiv.put("questions", "미체험");
			indiv.put("recommend", "-");
			indiv.put("check", "-");
			indiv.put("checkValue", null);
			indiv.put("status", "미체험");
		} else {
			indiv.put("questions", "종료");
			indiv.put("recommend", indivPart.getType() == null ? "-" : indivPart.getType().getTitle());
			indiv.put("check", indivPart.getStaffCheck().getTitle());
			indiv.put("checkValue", indivPart.getStaffCheck().getName());
			
			switch (indivPart.getStaffCheck()) {
			case present:
				indiv.put("status", "완료");
				break;
			case notyet:
				indiv.put("status", "사전 문답");
				break;
			default:
				break;
			}
		}
		
		DataMap style = new DataMap();
		StylePartEntity stylePart = this.styleService.getStylePart(signId.getMemberSn(), signId.getCreated());
		result.put("style", style);
		style.put("corner", CornerType.style.getTitle());
		if(ObjectUtils.isEmpty(stylePart)) {
			style.put("questions", "미체험");
			style.put("recommend", "-");
			style.put("check", "-");
			style.put("checkValue", null);
			style.put("status", "미체험");
		} else {
			style.put("questions", "종료");
			style.put("recommend", stylePart.getType() == null ? "-" : stylePart.getType().getTitle());
			style.put("check", stylePart.getStaffCheck().getTitle());
			style.put("checkValue", stylePart.getStaffCheck().getName());
			
			switch (stylePart.getStaffCheck()) {
			case present:
				style.put("status", "완료");
				break;
			case notyet:
				style.put("status", "사전 문답");
				break;
			default:
				break;
			}
		}
		
		DataMap mood = new DataMap();
		MoodPartEntity moodPart = this.moodService.getMoodPart(signId.getMemberSn(), signId.getCreated());
		result.put("mood", mood);
		mood.put("corner", CornerType.mood.getTitle());
		mood.put("recommend", "-");
		mood.put("check", "-");
		if(ObjectUtils.isEmpty(moodPart)) {
			mood.put("questions", "미체험");
			mood.put("recommend", "-");
			mood.put("check", "-");
			mood.put("status", "미체험");
		} else {
			mood.put("questions", "종료");
			mood.put("recommend", "-");
			mood.put("check", "-");
			mood.put("status", "완료");
		}
		
		DataMap refresh = new DataMap();
		RefreshPartEntity refreshPart = this.refreshService.getRefreshPart(signId.getMemberSn(), signId.getCreated());
		result.put("refresh", refresh);
		refresh.put("corner", CornerType.refresh.getTitle());
		refresh.put("recommend", "-");
		refresh.put("check", "-");
		if(ObjectUtils.isEmpty(refreshPart)) {
			refresh.put("questions", "-");
			refresh.put("recommend", "-");
			refresh.put("check", "-");
			refresh.put("status", "미체험");
		} else {
			refresh.put("questions", "-");
			refresh.put("recommend", "-");
			refresh.put("check", "-");
			refresh.put("status", "완료");
		}
		
		DataMap life = new DataMap();
		LifePartEntity lifePart = null;
		List<LifePartEntity> lifeParts = this.lifeService.getLifePart(signId.getMemberSn(), signId.getCreated());
		if(CollectionUtils.isEmpty(lifeParts)) {
			lifeParts = this.lifePartRepository.findByDateAndMemberPhone(today, member.getPhone());
		}
		
		for(LifePartEntity part : lifeParts) {
			switch (part.getType()) {
				case weekday:
					
					switch (part.getStatus()) {
					case status2:
					case status4:
						lifePart = part;
						break;
					default:
						break;
					}
					
					break;
				case weekend:
					switch (part.getStatus()) {
					case status1:
					case status2:
					case status3:
						lifePart = part;
						break;
					default:
						break;
					}
					break;
				
				default:
					break;
			}
		}
		
		result.put("life", life);
		life.put("corner", CornerType.life.getTitle());
		if(ObjectUtils.isEmpty(lifePart)) {
			life.put("check", "-");
			life.put("status", "미체험");
		} else {
			life.put("check", "-");
			life.put("status", "미체험");
			switch (lifePart.getType()) {
			case weekday:
				switch (lifePart.getStatus()) {
				case status2:
					life.put("check", lifePart.getStaffCheck().getLife());
					life.put("checkValue", lifePart.getStaffCheck().name());
					life.put("status", "예약");
					break;
				case status4:
					life.put("check", lifePart.getStaffCheck().getLife());
					life.put("checkValue", lifePart.getStaffCheck().name());
					life.put("status", "완료");
					break;
				default:
					break;
				}
				break;
			case weekend:
				switch (lifePart.getStatus()) {
				case status2:
					life.put("check", lifePart.getStaffCheck().getLife());
					life.put("checkValue", lifePart.getStaffCheck().name());
					life.put("status", "예약");//당첨
					break;
				case status4:
					life.put("check", lifePart.getStaffCheck().getLife());
					life.put("checkValue", lifePart.getStaffCheck().name());
					life.put("status", "완료");
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}
		
		List<SignEntity> signEntities = this.signDslRepository.findByIdMemberSnAndIdCreatedNotOrderByIdCreatedDesc(signId.getMemberSn(), member.getName(), member.getPhone(), signId.getCreated());
		
		List<DataMap> parts = new ArrayList<>();
		result.put("parts", parts);
		
		if(CollectionUtils.isNotEmpty(signEntities)) {
			signEntities.forEach(item -> {
				DataMap part = new DataMap();
				parts.add(part);
				
				MindPartEntity mindParts = this.mindService.getMindPart(item.getId().getMemberSn(), item.getId().getCreated());
				IndivPartEntity indivParts = this.indivService.getIndivPart(item.getId().getMemberSn(), item.getId().getCreated());
				StylePartEntity styleParts = this.styleService.getStylePart(item.getId().getMemberSn(), item.getId().getCreated());
				MoodPartEntity moodParts = this.moodService.getMoodPart(item.getId().getMemberSn(), item.getId().getCreated());
				RefreshPartEntity refreshParts = this.refreshService.getRefreshPart(item.getId().getMemberSn(), item.getId().getCreated());
				
				List<LifePartEntity> lifePartss = this.lifePartRepository.findByMemberSnAndDate(item.getId().getMemberSn(), item.getId().getCreated());

				DataMap minds = new DataMap();
				DataMap indivs = new DataMap();
				DataMap styles = new DataMap();
				DataMap moods = new DataMap();
				DataMap refreshs = new DataMap();
				DataMap lifes = new DataMap();

				part.put("created", item.getId().getCreated());
				
				try {
					result.put("nick", this.encryptService.decryptAES256(item.getNick()));
				} catch (Exception e) {
					part.put("nick", item.getNick());
				}
				part.put("worry", item.getWorryType() != null ? item.getWorryType().getDesc() : null);
				
				part.put("mind", minds);
				part.put("indiv", indivs);
				part.put("style", styles);
				part.put("mood", moods);
				part.put("refresh", refreshs);
				part.put("life", lifes);
				
				minds.put("corner", CornerType.mind.getTitle());
				if(ObjectUtils.isEmpty(mindParts)) {
					minds.put("status", "미체험");
				} else {
					switch (mindParts.getStaffCheck()) {
					case present:
						minds.put("status", "완료");
						break;
					case notyet:
						minds.put("status", "사전 문답");
						break;
					default:
						break;
					}
				}
				
				indivs.put("corner", CornerType.indiv.getTitle());
				if(ObjectUtils.isEmpty(indivParts)) {
					indivs.put("status", "미체험");
				} else {
					switch (indivParts.getStaffCheck()) {
					case present:
						indivs.put("status", "완료");
						break;
					case notyet:
						indivs.put("status", "사전 문답");
						break;
					default:
						break;
					}
				}
				
				styles.put("corner", CornerType.style.getTitle());
				if(ObjectUtils.isEmpty(styleParts)) {
					styles.put("status", "미체험");
				} else {
					switch (styleParts.getStaffCheck()) {
					case present:
						styles.put("status", "완료");
						break;
					case notyet:
						styles.put("status", "사전 문답");
						break;
					default:
						break;
					}
				}
				
				moods.put("corner", CornerType.mood.getTitle());
				if(ObjectUtils.isEmpty(moodParts)) {
					moods.put("status", "미체험");
				} else {
					moods.put("status", "완료");
				}
				
				refreshs.put("corner", CornerType.refresh.getTitle());
				if(ObjectUtils.isEmpty(refreshParts)) {
					refreshs.put("status", "미체험");
				} else {
					refreshs.put("status", "완료");
				}
				
				lifes.put("corner", CornerType.life.getTitle());
				if(CollectionUtils.isEmpty(lifePartss)) {
					lifes.put("status", "미체험");
				}
				for(LifePartEntity lifeEntity : lifePartss) {
					switch (lifeEntity.getStatus()) { //상태
					case status2:
						lifes.put("status", "미체험");
						break;
					case status4:
						lifes.put("status", "완료");
						break;
					default:
						lifes.put("status", "-");
						break;
					}
					lifes.put("status", "-");
					break;
				}
			});
		}
		
		return result;
	}

	@Override
	public DataMap mindStaffCheck(SignId signId, StaffCheck staffCheck) {

		String today = DateUtils.getToday("yyyyMMdd");
		
		if(!today.equals(signId.getCreated())) {
			DataMap result = new DataMap(false);
			result.put("reason", "유효한 날짜가 아닙니다.");
			result.put("code", 403);
			return result;
		}
		
		MindPartEntity mindPart = this.mindService.getMindPart(signId.getMemberSn(), signId.getCreated());
		
		if(ObjectUtils.isEmpty(mindPart)) {
			/*DataMap result = new DataMap(false);
			result.put("reason", "정보를 찾을수 없습니다.");
			result.put("code", 404);
			return result;*/
			
			mindPart = new MindPartEntity();
			mindPart.setSignMemberSn(signId.getMemberSn());
			mindPart.setSignCreated(signId.getCreated());
			mindPart.setCreated(DateUtils.getToday("yyyyMMddHHmmss"));
		}
		mindPart.setStaffCreated(DateUtils.getToday("yyyyMMddHHmmss"));
		mindPart.setStaffCheck(staffCheck);
		this.mindService.save(mindPart);
		
		DataMap result = new DataMap(true);
		result.put("saved", mindPart);
		
		return result;
	}

	@Override
	public DataMap indivStaffCheck(SignId signId, StaffCheck staffCheck) {

		String today = DateUtils.getToday("yyyyMMdd");
		
		if(!today.equals(signId.getCreated())) {
			DataMap result = new DataMap(false);
			result.put("reason", "유효한 날짜가 아닙니다.");
			result.put("code", 403);
			return result;
		}
		
		IndivPartEntity indivPart = this.indivService.getIndivPart(signId.getMemberSn(), signId.getCreated());
		
		if(ObjectUtils.isEmpty(indivPart)) {
			/*DataMap result = new DataMap(false);
			result.put("reason", "정보를 찾을수 없습니다.");
			result.put("code", 404);
			return result;*/

			indivPart = new IndivPartEntity();
			indivPart.setSignMemberSn(signId.getMemberSn());
			indivPart.setSignCreated(signId.getCreated());
			indivPart.setCreated(DateUtils.getToday("yyyyMMddHHmmss"));
		}
		indivPart.setStaffCreated(DateUtils.getToday("yyyyMMddHHmmss"));
		indivPart.setStaffCheck(staffCheck);
		this.indivService.save(indivPart);
		
		DataMap result = new DataMap(true);
		result.put("saved", indivPart);
		
		return result;
	}

	@Override
	public DataMap styleStaffCheck(SignId signId, StaffCheck staffCheck) {

		String today = DateUtils.getToday("yyyyMMdd");
		
		if(!today.equals(signId.getCreated())) {
			DataMap result = new DataMap(false);
			result.put("reason", "유효한 날짜가 아닙니다.");
			result.put("code", 403);
			return result;
		}
		
		StylePartEntity stylePart = this.styleService.getStylePart(signId.getMemberSn(), signId.getCreated());
		
		if(ObjectUtils.isEmpty(stylePart)) {
			/*DataMap result = new DataMap(false);
			result.put("reason", "정보를 찾을수 없습니다.");
			result.put("code", 404);
			return result;*/
			
			stylePart = new StylePartEntity();
			stylePart.setSignMemberSn(signId.getMemberSn());
			stylePart.setSignCreated(signId.getCreated());
			stylePart.setCreated(DateUtils.getToday("yyyyMMddHHmmss"));
		}
		stylePart.setStaffCreated(DateUtils.getToday("yyyyMMddHHmmss"));
		stylePart.setStaffCheck(staffCheck);
		this.styleService.save(stylePart);
		
		DataMap result = new DataMap(true);
		result.put("saved", stylePart);
		
		return result;
	}

	@Override
	public DataMap lifeStaffCheck(SignId signId, StaffCheck staffCheck) {

		String today = DateUtils.getToday("yyyyMMdd");
		
		if(!today.equals(signId.getCreated())) {
			DataMap result = new DataMap(false);
			result.put("reason", "유효한 날짜가 아닙니다.");
			result.put("code", 403);
			return result;
		}
		
		LifePartEntity lifePart = null;
		
		Optional<SignEntity> signEntity = this.signRepository.findById(signId);
		if(ObjectUtils.isEmpty(signEntity)) {
			DataMap result = new DataMap(false);
			result.put("reason", "회원정보을 찾을수 없습니다.");
			result.put("code", 404);
			return result;
		}
		
		SignEntity sign = signEntity.get();
		MemberEntity member = sign.getMember();
		
		switch (staffCheck) {
		case present:
			
			lifePart = this.lifePartRepository.findByMemberSnAndDateAndStatusAndStaffCheck(signId.getMemberSn(), signId.getCreated(), LifeStatus.status2, StaffCheck.notyet);
			if(ObjectUtils.isEmpty(lifePart)) {
				lifePart = this.lifePartRepository.findFirstByMemberPhoneAndDateAndStatusAndStaffCheck(member.getPhone(), signId.getCreated(), LifeStatus.status2, StaffCheck.notyet);
			}
			
			if(ObjectUtils.isEmpty(lifePart)) {
				DataMap result = new DataMap(false);
				result.put("reason", "정보을 찾을수 없습니다.");
				result.put("code", 404);
				return result;
			}
			
			lifePart.setStatus(LifeStatus.status4);
			break;
		case notyet:
			lifePart = this.lifePartRepository.findByMemberSnAndDateAndStatusAndStaffCheck(signId.getMemberSn(), signId.getCreated(), LifeStatus.status4, StaffCheck.present);
			if(ObjectUtils.isEmpty(lifePart)) {
				lifePart = this.lifePartRepository.findFirstByMemberPhoneAndDateAndStatusAndStaffCheck(member.getPhone(), signId.getCreated(), LifeStatus.status4, StaffCheck.present);
			}
			
			if(ObjectUtils.isEmpty(lifePart)) {
				DataMap result = new DataMap(false);
				result.put("reason", "정보을 찾을수 없습니다.");
				result.put("code", 404);
				return result;
			}
			
			lifePart.setStatus(LifeStatus.status2);
			break;

		default:
			break;
		}
		
		if(ObjectUtils.isEmpty(lifePart)) {
			DataMap result = new DataMap(false);
			result.put("reason", "정보을 찾을수 없습니다.");
			result.put("code", 404);
			return result;
		}

		lifePart.setStaffCheck(staffCheck);
		lifePart.setStaffCreated(DateUtils.getToday("yyyyMMddHHmmss"));
		
		this.lifePartRepository.save(lifePart);

		DataMap result = new DataMap(true);
		result.put("saved", lifePart);
		return result;
	}

	@Override
	public DataMap jammyIssue(SignId signId) {

		String today = DateUtils.getToday("yyyyMMdd");
		
		if(!today.equals(signId.getCreated())) {
			DataMap result = new DataMap(false);
			result.put("reason", "유효한 날짜가 아닙니다.");
			result.put("code", 403);
			return result;
		}

		Optional<MemberEntity> memberEntity = this.memberRepository.findById(signId.getMemberSn());
		
		if(memberEntity.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "회원을 찾을수 없습니다.");
			result.put("code", 404);
			return result;
		}
		
		MemberEntity member = memberEntity.get();
		
		if(member.getJammySn() > 0) {
			DataMap result = new DataMap(false);
			result.put("reason", "이미 발급된 회원입니다.");
			result.put("code", 500);
			return result;
		}
		
		JammyEntity jammy = this.jammyRepository.findFirstByIssuedNullOrderBySnAsc();
		
		if(ObjectUtils.isEmpty(jammy)) {
			DataMap result = new DataMap(false);
			result.put("reason", "발급할 재미포인트 찾을수 없습니다.");
			result.put("code", 403);
			return result;
		}
		jammy.setIssued(DateUtils.getToday("yyyyMMddHHmmss"));
		this.jammyRepository.save(jammy);
		
		member.setJammySn(jammy.getSn());
		this.memberRepository.save(member);
		
		DataMap result = new DataMap(true);
		result.put("saved", jammy);
		
		return result;
	}

}
