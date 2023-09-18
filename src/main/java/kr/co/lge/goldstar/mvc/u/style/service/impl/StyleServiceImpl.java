/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.style.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.lge.goldstar.mvc.u.style.service.StyleService;
import kr.co.lge.goldstar.mvc.u.sign.service.SignService;
import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.StyleAnswerType;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.lge.goldstar.orm.jpa.entity.style.StyleEntity;
import kr.co.lge.goldstar.orm.jpa.entity.style.StyleMemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.style.StylePartEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.StyleAnswerRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.StyleMemberRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.StylePartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.StyleRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Service("u.StyleService")
public class StyleServiceImpl implements StyleService {

	@Autowired
	private StyleRepository styleRepository;
	
	@Autowired
	private StylePartRepository stylePartRepository;
	
	@Autowired
	private StyleAnswerRepository styleAnswerRepository;
	
	@Autowired
	private StyleMemberRepository styleMemberRepository;
	
	@Autowired
	private SignService signService;

	@Override
	public List<StyleEntity> getQuestions() {
		return this.styleRepository.findByStatusAndDeletedOrderByOrdinalAsc(YesOrNo.Y, YesOrNo.N);
	}

	@Override
	public DataMap memberAnswers(List<Integer> answers) {
		
		if(!this.signService.existSignIn()) {
			DataMap result = new DataMap(false);
			result.put("reason", "인증정보가 만료 되었습니다.\n화면을 새로고침 해주세요.");
			result.put("code", 9999);
			return result;
		}

		SignEntity signEntity = this.signService.getSignIn();
		
		boolean isExsit = this.stylePartRepository.countBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated()) > 0;
		if(isExsit) {
			DataMap result = new DataMap(false);
			result.put("reason", "이미 참여한 코너입니다.");
			return result;
		}
		
		StylePartEntity stylePartEntity = this.stylePartRepository.findBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated());
		
		if(ObjectUtils.isEmpty(stylePartEntity)) {
			stylePartEntity = new StylePartEntity();
			stylePartEntity.setCreated(DateUtils.getToday("yyyyMMddHHmmss"));
			stylePartEntity.setSignMemberSn(signEntity.getId().getMemberSn());
			stylePartEntity.setSignCreated(signEntity.getId().getCreated());
			stylePartEntity.setStaffCheck(StaffCheck.notyet);
			
			//저장
			this.stylePartRepository.save(stylePartEntity);
		}
		
		if(!ObjectUtils.isEmpty(stylePartEntity.getType())) {
			DataMap result = new DataMap(false);
			result.put("reason", "이미 참여한 코너입니다.");
			return result;
		}
		
		String created = DateUtils.getToday("yyyyMMddHHmmss");
		List<StyleMemberEntity> styleMemberEntities = new ArrayList<>();
		for(Integer answer : answers) {
			StyleMemberEntity memberEntity = new StyleMemberEntity();
			styleMemberEntities.add(memberEntity);
			memberEntity.setCreated(created);
			memberEntity.setPart(stylePartEntity);
			memberEntity.setAnswer(this.styleAnswerRepository.findById(answer).orElseThrow());
		}
		
		this.styleMemberRepository.saveAll(styleMemberEntities);
		
		Map<StyleAnswerType, Integer> count = new HashMap<>();
		
		styleMemberEntities.forEach(item -> {
			if(count.containsKey(item.getAnswer().getType())) {
				count.put(item.getAnswer().getType(), count.get(item.getAnswer().getType()) + 1);
			}
			else {
				count.put(item.getAnswer().getType(), 1);
			}
		});
		
		StyleAnswerType resultType = null;
		Set<StyleAnswerType> keys = count.keySet();
		for(StyleAnswerType type : keys) {
			if(count.get(type) > 1) {
				resultType = type;
			}
		}
		
		//마지막 질문에 대한 타입
		if(resultType == null) {
			resultType = styleMemberEntities.get(styleMemberEntities.size() - 1).getAnswer().getType();
		}
		
		stylePartEntity.setType(resultType);
		this.stylePartRepository.save(stylePartEntity);
		
		DataMap result = new DataMap(true);
		result.put("title", resultType.getTitle());
		result.put("recommend", resultType.getRecommend());
		result.put("desc", resultType.getDesc());
		result.put("todo", resultType.getTodo());
		result.put("image", resultType.getImage());
		
		return result;
	}

	@Override
	public StylePartEntity getStylePart() {

		SignEntity signEntity = this.signService.getSignIn();
		
		return this.stylePartRepository.findBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated());
	}
}
