/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.pursue.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.lge.goldstar.mvc.m.manager.service.ManagerLogExecution;
import kr.co.lge.goldstar.mvc.m.pursue.service.PursueService;
import kr.co.lge.goldstar.orm.jpa.entity.PursueAnswerType;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.pursue.PursueAnswerEntity;
import kr.co.lge.goldstar.orm.jpa.entity.pursue.PursueEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.PursueAnswerRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.PursueRepository;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Service("PursueService")
public class PursueServiceImpl implements PursueService{

	@Autowired
	private PursueRepository pursueRepository;

	@Autowired
	private PursueAnswerRepository pursueAnswerRepository;
	
	@Override
	@ManagerLogExecution(process = "getPursue()", menu="고객유형문답 리스트", button="검색")
	public DataMap getPursue() {
		
		DataMap result = new DataMap();

		List<PursueEntity> pursueList = this.pursueRepository.findByDeletedOrderByOrdinalAsc(YesOrNo.N);

		result.put("pursueList", pursueList);
		result.put("pursueTypes", PursueAnswerType.values());
		
		return result;
	}

	@Override
	@ManagerLogExecution(process = "update(PursueEntity pursueEntity)", menu="고객유형문답 리스트", button="수정하기")
	public DataMap update(PursueEntity pursueEntity) {
		
		Optional<PursueEntity> saved = this.pursueRepository.findById(pursueEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		if(CollectionUtils.isEmpty(pursueEntity.getAnswers())) {
			DataMap result = new DataMap(false);
			result.put("reason", "답변 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		List<PursueAnswerEntity> answers = new ArrayList<>();
		pursueEntity.getAnswers().forEach(item -> {
			Optional<PursueAnswerEntity> savedAnswer = this.pursueAnswerRepository.findById(item.getSn());
			if(saved.isPresent()) {
				
				PursueAnswerEntity answerEntity = savedAnswer.get();
				answerEntity.setName(item.getName());
				answers.add(answerEntity);
			}
		});
		
		if(answers.size() != pursueEntity.getAnswers().size()) {
			DataMap result = new DataMap(false);
			result.put("reason", "답변 데이터를 매칭할 수 없습니다.");
			return result;
		}
		
		PursueEntity savedEntity = saved.get();
		savedEntity.setQuestion(pursueEntity.getQuestion());
		this.pursueRepository.save(savedEntity);
		
		this.pursueAnswerRepository.saveAll(answers);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "pursueSort(DataMap params)", menu="고객유형문답 리스트", button="정렬")
	public DataMap sort(DataMap params) {
		
		List<Integer> snList = params.getListAsInteger("snList[]");
		
		List<PursueEntity> pursueEntities = this.pursueRepository.findBySnIn(snList);
		
		if(CollectionUtils.isEmpty(pursueEntities)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		Map<Integer, PursueEntity> map = new HashMap<>();
		pursueEntities.forEach(item -> {
			map.put(item.getSn(), item);
		});
		
		int order = 1;
		for(Integer sn : snList) {
			map.get(sn).setOrdinal(order++);
		}
		
		this.pursueRepository.saveAll(pursueEntities);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "get(PursueEntity pursueEntity)", menu="고객유형문답 리스트", button="상세")
	public DataMap get(PursueEntity pursueEntity) {
		
		DataMap result = new DataMap(true);
		result.put("entity", this.pursueRepository.findById(pursueEntity.getSn()).get());
		
		return result;
	}

	@Override
	@ManagerLogExecution(process = "status(PursueEntity pursueEntity)", menu="고객유형문답 리스트", button="상태변경")
	public DataMap status(PursueEntity pursueEntity) {
		
		Optional<PursueEntity> saved = this.pursueRepository.findById(pursueEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		PursueEntity savedEntity = saved.get();
		savedEntity.setStatus(pursueEntity.getStatus());
		this.pursueRepository.save(savedEntity);
		
		return new DataMap(true);
	}

}
