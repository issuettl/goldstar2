/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.mind.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.lge.goldstar.mvc.u.mind.service.MindService;
import kr.co.lge.goldstar.mvc.u.sign.service.SignService;
import kr.co.lge.goldstar.orm.jpa.entity.MindAnswerType;
import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mind.MindEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mind.MindMemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mind.MindPartEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MindAnswerRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MindMemberRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MindPartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MindRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Service("u.MindService")
public class MindServiceImpl implements MindService {

	@Autowired
	private MindRepository mindRepository;
	
	@Autowired
	private MindPartRepository mindPartRepository;
	
	@Autowired
	private MindAnswerRepository mindAnswerRepository;
	
	@Autowired
	private MindMemberRepository mindMemberRepository;
	
	@Autowired
	private SignService signService;

	@Override
	public List<MindEntity> getQuestions() {
		return this.mindRepository.findByStatusAndDeletedOrderByOrdinalAsc(YesOrNo.Y, YesOrNo.N);
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
		
		boolean isExsit = this.mindPartRepository.countBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated()) > 0;
		if(isExsit) {
			DataMap result = new DataMap(false);
			result.put("reason", "이미 참여한 코너입니다.");
			return result;
		}
		
		MindPartEntity mindPartEntity = this.mindPartRepository.findBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated());
		
		if(ObjectUtils.isEmpty(mindPartEntity)) {
			mindPartEntity = new MindPartEntity();
			mindPartEntity.setCreated(DateUtils.getToday("yyyyMMddHHmmss"));
			mindPartEntity.setSignMemberSn(signEntity.getId().getMemberSn());
			mindPartEntity.setSignCreated(signEntity.getId().getCreated());
			mindPartEntity.setStaffCheck(StaffCheck.notyet);
			
			//저장
			this.mindPartRepository.save(mindPartEntity);
		}
		
		if(!ObjectUtils.isEmpty(mindPartEntity.getType())) {
			DataMap result = new DataMap(false);
			result.put("reason", "이미 참여한 코너입니다.");
			return result;
		}
		
		String created = DateUtils.getToday("yyyyMMddHHmmss");
		List<MindMemberEntity> mindMemberEntities = new ArrayList<>();
		for(Integer answer : answers) {
			MindMemberEntity memberEntity = new MindMemberEntity();
			mindMemberEntities.add(memberEntity);
			memberEntity.setCreated(created);
			memberEntity.setPart(mindPartEntity);
			memberEntity.setAnswer(this.mindAnswerRepository.findById(answer).orElseThrow());
		}
		
		this.mindMemberRepository.saveAll(mindMemberEntities);
		
		Map<MindAnswerType, Integer> count = new HashMap<>();
		
		mindMemberEntities.forEach(item -> {
			if(count.containsKey(item.getAnswer().getType())) {
				count.put(item.getAnswer().getType(), count.get(item.getAnswer().getType()) + 1);
			}
			else {
				count.put(item.getAnswer().getType(), 1);
			}
		});
		
		MindAnswerType resultType = null;
		Set<MindAnswerType> keys = count.keySet();
		for(MindAnswerType type : keys) {
			if(count.get(type) > 1) {
				resultType = type;
			}
		}
		
		//마지막 질문에 대한 타입
		if(resultType == null) {
			resultType = mindMemberEntities.get(mindMemberEntities.size() - 1).getAnswer().getType();
		}
		
		mindPartEntity.setType(resultType);
		this.mindPartRepository.save(mindPartEntity);
		
		DataMap result = new DataMap(true);
		result.put("title", resultType.getTitle());
		result.put("recommend", resultType.getRecommend());
		result.put("desc", resultType.getDesc());
		result.put("todo", resultType.getTodo());
		result.put("image", resultType.getImage());
		
		return result;
	}

	@Override
	public MindPartEntity getMindPart() {
		SignEntity signEntity = this.signService.getSignIn();
		
		return this.mindPartRepository.findBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated());
	}
}
