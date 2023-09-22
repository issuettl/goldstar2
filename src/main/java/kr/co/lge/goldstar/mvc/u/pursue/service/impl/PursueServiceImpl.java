/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.pursue.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.lge.goldstar.mvc.u.pursue.service.PursueService;
import kr.co.lge.goldstar.mvc.u.sign.service.SignService;
import kr.co.lge.goldstar.orm.jpa.entity.PursueAnswerType;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.lge.goldstar.orm.jpa.entity.pursue.PursueEntity;
import kr.co.lge.goldstar.orm.jpa.entity.pursue.PursueMemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.pursue.PursuePartEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.PursueAnswerRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.PursueMemberRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.PursuePartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.PursueRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Service("u.PursueService")
public class PursueServiceImpl implements PursueService {

	@Autowired
	private PursueRepository pursueRepository;
	
	@Autowired
	private PursuePartRepository pursuePartRepository;
	
	@Autowired
	private PursueAnswerRepository pursueAnswerRepository;
	
	@Autowired
	private PursueMemberRepository pursueMemberRepository;
	
	@Autowired
	private SignService signService;

	@Override
	public List<PursueEntity> getQuestions() {
		return this.pursueRepository.findByStatusAndDeletedOrderByOrdinalAsc(YesOrNo.Y, YesOrNo.N);
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
		
		boolean isExsit = this.pursuePartRepository.countBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated()) > 0;
		if(isExsit) {
			DataMap result = new DataMap(false);
			result.put("reason", "이미 완료하였습니다.");
			result.put("code", 9998);
			return result;
		}
		
		PursuePartEntity pursuePartEntity = this.pursuePartRepository.findBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated());
		
		if(ObjectUtils.isEmpty(pursuePartEntity)) {
			pursuePartEntity = new PursuePartEntity();
			pursuePartEntity.setCreated(DateUtils.getToday("yyyyMMddHHmmss"));
			pursuePartEntity.setSignMemberSn(signEntity.getId().getMemberSn());
			pursuePartEntity.setSignCreated(signEntity.getId().getCreated());
			
			//저장
			this.pursuePartRepository.save(pursuePartEntity);
		}
		if(!ObjectUtils.isEmpty(pursuePartEntity.getType())) {
			DataMap result = new DataMap(false);
			result.put("reason", "이미 완료하였습니다.");
			result.put("code", 9998);
			return result;
		}
		
		String created = DateUtils.getToday("yyyyMMddHHmmss");
		List<PursueMemberEntity> pursueMemberEntities = new ArrayList<>();
		for(Integer answer : answers) {
			PursueMemberEntity memberEntity = new PursueMemberEntity();
			pursueMemberEntities.add(memberEntity);
			memberEntity.setCreated(created);
			memberEntity.setPart(pursuePartEntity);
			memberEntity.setAnswer(this.pursueAnswerRepository.findById(answer).orElseThrow());
		}
		
		this.pursueMemberRepository.saveAll(pursueMemberEntities);
		
		Map<PursueAnswerType, Integer> count = new LinkedHashMap<>();
		
		Collections.reverse(pursueMemberEntities);
		pursueMemberEntities.forEach(item -> {
			
			if(count.containsKey(item.getAnswer().getType2())) {
				count.put(item.getAnswer().getType2(), count.get(item.getAnswer().getType2()) + 1);
			}
			else {
				count.put(item.getAnswer().getType2(), 1);
			}
			if(count.containsKey(item.getAnswer().getType1())) {
				count.put(item.getAnswer().getType1(), count.get(item.getAnswer().getType1()) + 1);
			}
			else {
				count.put(item.getAnswer().getType1(), 1);
			}
		});
		
		Set<PursueAnswerType> keys = count.keySet();
		
		DataMap[] counts = new DataMap[keys.size()];
		
		int i = 0;
		for(PursueAnswerType type : keys) {
			DataMap countMap = new DataMap();
			countMap.put("type", type);
			countMap.put("count", count.get(type));
			counts[i] = countMap;
			i++;
		}
		
		Arrays.sort(counts, typeSort);

		PursueAnswerType resultType = (PursueAnswerType) counts[counts.length - 1].get("type");
		//마지막 질문에 대한 타입
		if(resultType == null) {
			resultType = pursueMemberEntities.get(pursueMemberEntities.size() - 1).getAnswer().getType2();
		}
		
		pursuePartEntity.setType(resultType);
		this.pursuePartRepository.save(pursuePartEntity);
		
		SignEntity signIn = this.signService.getSignIn();
		signIn.setPursueType(resultType);
		this.signService.savePursue(signEntity);
		
		DataMap result = new DataMap(true);
		result.put("title", resultType.getTitle());
		result.put("recommend", resultType.getRecommend());
		result.put("desc", resultType.getDesc());
		
		return result;
	}
	
	Comparator<DataMap> typeSort = new Comparator<DataMap>() {
        int result = 0;
        @Override
        public int compare(DataMap o1, DataMap o2) {
            if(o1.getAsInt("count") > o2.getAsInt("count")){
            	result = 1;
            }
            else if(o1.getAsInt("count") == o2.getAsInt("count")){
            	result = 0;
            }
            else{
            	result = -1;
            }
            return result;
        }
    };

	@Override
	public PursuePartEntity getPursuePart() {
		
		SignEntity signEntity = this.signService.getSignIn();
		
		return this.pursuePartRepository.findBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated());
	}
}
