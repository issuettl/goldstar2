/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.indiv.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.lge.goldstar.mvc.u.indiv.service.IndivService;
import kr.co.lge.goldstar.mvc.u.sign.service.SignService;
import kr.co.lge.goldstar.orm.jpa.entity.IndivAnswerType;
import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.indiv.IndivEntity;
import kr.co.lge.goldstar.orm.jpa.entity.indiv.IndivMemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.indiv.IndivPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.IndivAnswerRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.IndivMemberRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.IndivPartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.IndivRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Service("u.IndivService")
public class IndivServiceImpl implements IndivService {

	@Autowired
	private IndivRepository indivRepository;
	
	@Autowired
	private IndivPartRepository indivPartRepository;
	
	@Autowired
	private IndivAnswerRepository indivAnswerRepository;
	
	@Autowired
	private IndivMemberRepository indivMemberRepository;
	
	@Autowired
	private SignService signService;

	@Override
	public List<IndivEntity> getQuestions() {
		return this.indivRepository.findByStatusAndDeletedOrderByOrdinalAsc(YesOrNo.Y, YesOrNo.N);
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
		
		boolean isExsit = this.indivPartRepository.countBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated()) > 0;
		if(isExsit) {
			DataMap result = new DataMap(false);
			result.put("reason", "이미 참여한 코너입니다.");
			return result;
		}
		
		IndivPartEntity indivPartEntity = this.indivPartRepository.findBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated());
		
		if(ObjectUtils.isEmpty(indivPartEntity)) {
			indivPartEntity = new IndivPartEntity();
			indivPartEntity.setCreated(DateUtils.getToday("yyyyMMddHHmmss"));
			indivPartEntity.setSignMemberSn(signEntity.getId().getMemberSn());
			indivPartEntity.setSignCreated(signEntity.getId().getCreated());
			indivPartEntity.setStaffCheck(StaffCheck.notyet);
			
			//저장
			this.indivPartRepository.save(indivPartEntity);
		}
		
		if(!ObjectUtils.isEmpty(indivPartEntity.getType())) {
			DataMap result = new DataMap(false);
			result.put("reason", "이미 참여한 코너입니다.");
			return result;
		}
		
		String created = DateUtils.getToday("yyyyMMddHHmmss");
		List<IndivMemberEntity> indivMemberEntities = new ArrayList<>();
		for(Integer answer : answers) {
			IndivMemberEntity memberEntity = new IndivMemberEntity();
			indivMemberEntities.add(memberEntity);
			memberEntity.setCreated(created);
			memberEntity.setPart(indivPartEntity);
			memberEntity.setAnswer(this.indivAnswerRepository.findById(answer).orElseThrow());
		}
		
		this.indivMemberRepository.saveAll(indivMemberEntities);
		
		Map<IndivAnswerType, Integer> count = new HashMap<>();
		
		indivMemberEntities.forEach(item -> {
			if(count.containsKey(item.getAnswer().getType())) {
				count.put(item.getAnswer().getType(), count.get(item.getAnswer().getType()) + 1);
			}
			else {
				count.put(item.getAnswer().getType(), 1);
			}
		});
		
		IndivAnswerType resultType = null;
		Set<IndivAnswerType> keys = count.keySet();
		for(IndivAnswerType type : keys) {
			if(count.get(type) > 1) {
				resultType = type;
			}
		}
		
		//마지막 질문에 대한 타입
		if(resultType == null) {
			resultType = indivMemberEntities.get(indivMemberEntities.size() - 1).getAnswer().getType();
		}
		
		indivPartEntity.setType(resultType);
		this.indivPartRepository.save(indivPartEntity);
		
		DataMap result = new DataMap(true);
		result.put("title", resultType.getTitle());
		result.put("recommend", resultType.getRecommend());
		result.put("desc", resultType.getDesc());
		result.put("todo", resultType.getTodo());
		result.put("image", resultType.getImage());
		
		return result;
	}

	@Override
	public IndivPartEntity getIndivPart() {
		
		SignEntity signEntity = this.signService.getSignIn();
		
		return this.indivPartRepository.findBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated());
	}
}
