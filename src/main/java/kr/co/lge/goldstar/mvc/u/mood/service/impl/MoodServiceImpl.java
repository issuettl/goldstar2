/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.mood.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.lge.goldstar.mvc.u.mood.service.MoodService;
import kr.co.lge.goldstar.mvc.u.sign.service.SignService;
import kr.co.lge.goldstar.orm.jpa.entity.MoodAnswerType;
import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodMemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodPartEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MoodAnswerRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MoodMemberRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MoodPartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MoodRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Service("u.MoodService")
public class MoodServiceImpl implements MoodService {

	@Autowired
	private MoodRepository moodRepository;
	
	@Autowired
	private MoodPartRepository moodPartRepository;
	
	@Autowired
	private MoodAnswerRepository moodAnswerRepository;
	
	@Autowired
	private MoodMemberRepository moodMemberRepository;
	
	@Autowired
	private SignService signService;

	@Override
	public List<MoodEntity> getQuestions() {
		return this.moodRepository.findByStatusAndDeletedOrderByOrdinalAsc(YesOrNo.Y, YesOrNo.N);
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
		
		boolean isExsit = this.moodPartRepository.countBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated()) > 0;
		if(isExsit) {
			DataMap result = new DataMap(false);
			result.put("reason", "이미 참여한 코너입니다.");
			return result;
		}
		
		MoodPartEntity moodPartEntity = this.moodPartRepository.findBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated());
		
		if(ObjectUtils.isEmpty(moodPartEntity)) {
			moodPartEntity = new MoodPartEntity();
			moodPartEntity.setCreated(DateUtils.getToday("yyyyMMddHHmmss"));
			moodPartEntity.setSignMemberSn(signEntity.getId().getMemberSn());
			moodPartEntity.setSignCreated(signEntity.getId().getCreated());
			moodPartEntity.setStaffCheck(StaffCheck.present);
			
			//저장
			this.moodPartRepository.save(moodPartEntity);
		}
		
		if(!ObjectUtils.isEmpty(moodPartEntity.getType())) {
			DataMap result = new DataMap(false);
			result.put("reason", "이미 참여한 코너입니다.");
			return result;
		}
		
		String created = DateUtils.getToday("yyyyMMddHHmmss");
		List<MoodMemberEntity> moodMemberEntities = new ArrayList<>();
		for(Integer answer : answers) {
			MoodMemberEntity memberEntity = new MoodMemberEntity();
			moodMemberEntities.add(memberEntity);
			memberEntity.setCreated(created);
			memberEntity.setPart(moodPartEntity);
			memberEntity.setAnswer(this.moodAnswerRepository.findById(answer).orElseThrow());
		}
		
		this.moodMemberRepository.saveAll(moodMemberEntities);
		
		Map<MoodAnswerType, Integer> count = new HashMap<>();
		
		moodMemberEntities.forEach(item -> {
			if(count.containsKey(item.getAnswer().getType())) {
				count.put(item.getAnswer().getType(), count.get(item.getAnswer().getType()) + 1);
			}
			else {
				count.put(item.getAnswer().getType(), 1);
			}
		});
		
		MoodAnswerType resultType = null;
		Set<MoodAnswerType> keys = count.keySet();
		for(MoodAnswerType type : keys) {
			if(count.get(type) > 1) {
				resultType = type;
			}
		}
		
		//마지막 질문에 대한 타입
		if(resultType == null) {
			resultType = moodMemberEntities.get(moodMemberEntities.size() - 1).getAnswer().getType();
		}
		
		moodPartEntity.setType(resultType);
		this.moodPartRepository.save(moodPartEntity);
		
		DataMap result = new DataMap(true);
		result.put("title", resultType.getTitle());
		result.put("recommend", resultType.getRecommend());
		result.put("desc", resultType.getDesc());
		result.put("todo", resultType.getTodo());
		result.put("image", resultType.getImage());
		
		return result;
	}

	@Override
	public MoodPartEntity getMoodPart() {
		
		SignEntity signEntity = this.signService.getSignIn();
		
		return this.moodPartRepository.findBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated());
	}
}
