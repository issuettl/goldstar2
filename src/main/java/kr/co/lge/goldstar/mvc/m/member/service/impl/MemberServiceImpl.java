/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.member.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.lge.goldstar.core.pagination.PageRequestSupport;
import kr.co.lge.goldstar.mvc.c.encrypt.service.EncryptService;
import kr.co.lge.goldstar.mvc.m.manager.service.ManagerLogExecution;
import kr.co.lge.goldstar.mvc.m.member.service.MemberService;
import kr.co.lge.goldstar.mvc.m.sign.service.SignService;
import kr.co.lge.goldstar.orm.jpa.entity.CornerType;
import kr.co.lge.goldstar.orm.jpa.entity.LifeStatus;
import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.WorryType;
import kr.co.lge.goldstar.orm.jpa.entity.indiv.IndivPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.life.LifePartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.JammyEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mind.MindPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.refresh.RefreshPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.style.StylePartEntity;
import kr.co.lge.goldstar.orm.jpa.repository.dsl.MemberDslRepository;
import kr.co.lge.goldstar.orm.jpa.repository.dsl.SignDslRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.IndivPartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.JammyRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.LifePartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MemberRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MindPartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MoodPartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.RefreshPartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.SignRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.StylePartRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Service("m.MemberService")
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDslRepository memberDslRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private SignRepository signRepository;
	
	@Autowired
	private SignDslRepository signDslRepository;
	
	@Autowired
	private MindPartRepository mindPartRepository;
	
	@Autowired
	private StylePartRepository stylePartRepository;
	
	@Autowired
	private IndivPartRepository indivPartRepository;
	
	@Autowired
	private MoodPartRepository moodPartRepository;
	
	@Autowired
	private RefreshPartRepository refreshPartRepository;
	
	@Autowired
	private LifePartRepository lifePartRepository;
	
	@Autowired
	private EncryptService encryptService;
	
	@Autowired
	private SignService signService;
	
	@Autowired
	private JammyRepository jammyRepository;
	
	@Override
	@ManagerLogExecution(process = "getPage(DataMap params)", menu="회원 리스트", button="검색")
	public Page<MemberEntity> getPage(DataMap params) {
		
		Page<MemberEntity> page = this.memberDslRepository.findPageBySearch(
                params, PageRequestSupport.getPageRequest(params));
        
        if(ObjectUtils.isEmpty(page) || ObjectUtils.isEmpty(page.getContent())){
            return page;
        }
        
        //if 콘텐츠 변경
        page.getContent().forEach(item -> {
    		try {
    			item.setNameDec(this.encryptService.decryptAES256(item.getName()));
    		} catch (Exception e) {
    		}
        	try {
        		item.setPhoneDec(this.encryptService.decryptAES256(item.getPhone()));
    		} catch (Exception e) {
    		}
			item.setNameDec(this.encryptService.maskingName(item.getNameDec()));
			item.setPhoneDec(this.encryptService.maskingPhone(item.getPhoneDec()));
        	item.setVisit(this.signRepository.countByIdMemberSnAndWorryTypeNotNull(item.getSn()));

        	long mind = this.mindPartRepository.countBySignMemberSnAndStaffCheck(item.getSn(), StaffCheck.present);
        	long indiv = this.indivPartRepository.countBySignMemberSnAndStaffCheck(item.getSn(), StaffCheck.present);
        	long style = this.stylePartRepository.countBySignMemberSnAndStaffCheck(item.getSn(), StaffCheck.present);
        	long mood = this.moodPartRepository.countBySignMemberSn(item.getSn());
        	long refresh = this.refreshPartRepository.countBySignMemberSnAndStaffCheck(item.getSn(), StaffCheck.present);
        	
        	long life = this.lifePartRepository.countByMemberSnAndStatus(item.getSn(), LifeStatus.status4);
        	
        	item.setPart(mind+indiv+style+mood+refresh+life);
        });
        
        return page;
	}

	@Override
	@ManagerLogExecution(process = "getMember(DataMap params)", menu="회원 상세", button="보기")
	public DataMap getMember(DataMap params) {

		DataMap result = new DataMap();

		int memberSn = params.getAsInt("sn");
		Optional<MemberEntity> member = this.memberRepository.findById(memberSn);
		if(member.isEmpty()) {
			return result;
		}
		MemberEntity memberEntity = member.get();
		result.put("member", memberEntity);

		try {
			memberEntity.setNameDec(this.encryptService.decryptAES256(memberEntity.getName()));
		} catch (Exception e) {
		}
    	try {
    		memberEntity.setPhoneDec(this.encryptService.decryptAES256(memberEntity.getPhone()));
		} catch (Exception e) {
		}
		
		memberEntity.setVisit(this.signRepository.countByIdMemberSn(memberEntity.getSn()));

    	long mindCount = this.mindPartRepository.countBySignMemberSnAndStaffCheck(memberEntity.getSn(), StaffCheck.present);
    	long indivCount = this.indivPartRepository.countBySignMemberSnAndStaffCheck(memberEntity.getSn(), StaffCheck.present);
    	long styleCount = this.stylePartRepository.countBySignMemberSnAndStaffCheck(memberEntity.getSn(), StaffCheck.present);
    	long moodCount = this.moodPartRepository.countBySignMemberSn(memberEntity.getSn());
    	long refreshCount = this.refreshPartRepository.countBySignMemberSnAndStaffCheck(memberEntity.getSn(), StaffCheck.present);
    	long lifeCount = this.lifePartRepository.countByMemberSnAndStatus(memberEntity.getSn(), LifeStatus.status4);
    	memberEntity.setPart(mindCount+indivCount+styleCount+moodCount+refreshCount+lifeCount);
    	
		String today = DateUtils.getToday("yyyyMMdd");
		
		SignEntity signEntity = this.signService.getSignEntity(memberSn, today);
		
		result.put("sign", signEntity);
		result.put("worryTypes", WorryType.values());

		MindPartEntity mindPart = this.mindPartRepository.findBySignMemberSnAndSignCreated(memberSn, today);
		IndivPartEntity indivPart = this.indivPartRepository.findBySignMemberSnAndSignCreated(memberSn, today);
		StylePartEntity stylePart = this.stylePartRepository.findBySignMemberSnAndSignCreated(memberSn, today);
		MoodPartEntity moodPart = this.moodPartRepository.findBySignMemberSnAndSignCreated(memberSn, today);
		RefreshPartEntity refreshPart = this.refreshPartRepository.findBySignMemberSnAndSignCreated(memberSn, today);
		List<LifePartEntity> lifeParts = this.lifePartRepository.findByMemberSnAndDate(memberSn, today);

		result.put("cornerTypes", CornerType.values());
		result.put("mindPart", mindPart);
		result.put("indivPart", indivPart);
		result.put("stylePart", stylePart);
		result.put("moodPart", moodPart);
		result.put("refreshPart", refreshPart);
		
		LifePartEntity lifePart = null;
		for(LifePartEntity part : lifeParts) {
			switch (part.getType()) {
			case weekday: //평일
				switch (part.getStatus()) { //상태
				case status2:
				case status4:
					lifePart = part;
					break;
				default:
					break;
				}
				break;
			case weekend: //주말
				switch (part.getStatus()) { //상태
				case status2:
				case status4:
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
		result.put("lifePart", lifePart);
		
		result.put("staffChecks", StaffCheck.values());

		result.put("jammy", memberEntity.getJammy());
		
		List<SignEntity> signEntities = this.signDslRepository.findByIdMemberSnAndIdCreatedNotOrderByIdCreatedDesc(memberSn, memberEntity.getName(), memberEntity.getPhone(), today);
		if(CollectionUtils.isEmpty(signEntities)) {
			return result;
		}
		
		List<DataMap> parts = new ArrayList<>();
		result.put("parts", parts);
		signEntities.forEach(item -> {

			DataMap part = new DataMap();
			parts.add(part);
			
			MindPartEntity mind = this.mindPartRepository.findBySignMemberSnAndSignCreated(memberSn, item.getId().getCreated());
			IndivPartEntity indiv = this.indivPartRepository.findBySignMemberSnAndSignCreated(memberSn, item.getId().getCreated());
			StylePartEntity style = this.stylePartRepository.findBySignMemberSnAndSignCreated(memberSn, item.getId().getCreated());
			MoodPartEntity mood = this.moodPartRepository.findBySignMemberSnAndSignCreated(memberSn, item.getId().getCreated());
			RefreshPartEntity refresh = this.refreshPartRepository.findBySignMemberSnAndSignCreated(memberSn, item.getId().getCreated());
			List<LifePartEntity> lifes = this.lifePartRepository.findByMemberSnAndDate(memberSn, item.getId().getCreated());
			
			try {
				item.setNickDec(this.encryptService.decryptAES256(item.getNick()));
			} catch (Exception e) {
			}
			
			part.put("sign", item);
			part.put("mind", mind);
			part.put("indiv", indiv);
			part.put("style", style);
			part.put("mood", mood);
			part.put("refresh", refresh);
			
			for(LifePartEntity life : lifes) {
				switch (life.getType()) {
				case weekday: //평일
					switch (life.getStatus()) { //상태
					case status2:
					case status4:
						part.put("life", life);
						break;
					default:
						break;
					}
					break;
				case weekend: //주말
					switch (life.getStatus()) { //상태
					case status2:
					case status4:
						part.put("life", life);
						break;
					default:
						break;
					}
					break;
				default:
					break;
				}
			}
		});
		
		return result;
	}

	@Override
	@ManagerLogExecution(process = "staffCheck(DataMap params)", menu="회원 상세", button="체험상태변경")
	public DataMap staffCheck(DataMap params) {
		
		CornerType corner = CornerType.valueOf(params.getAsString("corner"));
		StaffCheck staffCheck = StaffCheck.valueOf(params.getAsString("staff"));
		int sn = params.getAsInt("sn");
		String today = DateUtils.getToday("yyyyMMddHHmmss");
		
		switch (corner) {
		case mind:
			Optional<MindPartEntity> mind = this.mindPartRepository.findById(sn);
			if(mind.isEmpty()) {
				DataMap result = new DataMap(false);
				result.put("reason", "데이터를 찾을 수 없습니다.");
				return result;
			}
			
			MindPartEntity mindEntity = mind.get();
			mindEntity.setStaffCheck(staffCheck);
			mindEntity.setStaffCreated(today);
			this.mindPartRepository.save(mindEntity);
			return new DataMap(true);
		case indiv:
			Optional<IndivPartEntity> indiv = this.indivPartRepository.findById(sn);
			if(indiv.isEmpty()) {
				DataMap result = new DataMap(false);
				result.put("reason", "데이터를 찾을 수 없습니다.");
				return result;
			}
			
			IndivPartEntity indivEntity = indiv.get();
			indivEntity.setStaffCheck(staffCheck);
			indivEntity.setStaffCreated(today);
			this.indivPartRepository.save(indivEntity);
			return new DataMap(true);
		case style:
			Optional<StylePartEntity> style = this.stylePartRepository.findById(sn);
			if(style.isEmpty()) {
				DataMap result = new DataMap(false);
				result.put("reason", "데이터를 찾을 수 없습니다.");
				return result;
			}
			
			StylePartEntity styleEntity = style.get();
			styleEntity.setStaffCheck(staffCheck);
			styleEntity.setStaffCreated(today);
			this.stylePartRepository.save(styleEntity);
			return new DataMap(true);
		case life:
			Optional<LifePartEntity> life = this.lifePartRepository.findById(sn);
			if(life.isEmpty()) {
				DataMap result = new DataMap(false);
				result.put("reason", "데이터를 찾을 수 없습니다.");
				return result;
			}
			
			LifePartEntity lifeEntity = life.get();
			
			switch (staffCheck) {
			case present:
				lifeEntity.setStatus(LifeStatus.status4);
				break;
			case notyet:
				lifeEntity.setStatus(LifeStatus.status2);
				break;
			default:
				break;
			}
			
			lifeEntity.setStaffCheck(staffCheck);
			lifeEntity.setStaffCreated(today);
			this.lifePartRepository.save(lifeEntity);
			return new DataMap(true);

		default:
			break;
		}
		
		DataMap result = new DataMap(false);
		result.put("reason", "코너를 찾을 수 없습니다.");
		return result;
	}

	@Override
	@ManagerLogExecution(process = "jammyIssue(DataMap params)", menu="회원 상세", button="JAMMY 포인트 발급")
	public DataMap jammyIssue(DataMap params) {
		
		Optional<MemberEntity> memberEntity = this.memberRepository.findById(params.getAsInt("sn"));
		if(memberEntity.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "회원을 찾을수 없습니다.");
			return result;
		}
		
		MemberEntity member = memberEntity.get();
		if(member.getJammySn() > 0) {
			DataMap result = new DataMap(false);
			result.put("reason", "이미 발급된 회원입니다.");
			return result;
		}
		
		JammyEntity jammy = this.jammyRepository.findFirstByIssuedNullOrderBySnAsc();
		if(ObjectUtils.isEmpty(jammy)) {
			DataMap result = new DataMap(false);
			result.put("reason", "발급할 재미포인트를 찾을수 없습니다.");
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

	@Override
	public List<MemberEntity> encode() {

		List<MemberEntity> members = this.memberRepository.findAll();
		
		List<MemberEntity> memberList = new ArrayList<>();
		members.forEach(item -> {
			try {
    			item.setNameDec(this.encryptService.decryptAES256(item.getName()));
        		item.setPhoneDec(this.encryptService.decryptAES256(item.getPhone()));
    		} catch (Exception e) {
    			
    			try {
        			item.setName(this.encryptService.encryptAES256(item.getName()));
            		item.setPhone(this.encryptService.encryptAES256(item.getPhone()));

        			item.setNameDec(this.encryptService.decryptAES256(item.getName()));
            		item.setPhoneDec(this.encryptService.decryptAES256(item.getPhone()));
            		
            		memberList.add(item);
        		} catch (Exception e1) {
        		}
    		}
		});
		
		if(CollectionUtils.isNotEmpty(memberList)) {
			this.memberRepository.saveAll(memberList);
		}
		
		return memberList;
	}

}
