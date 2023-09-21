/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.survey.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.lge.goldstar.mvc.m.manager.service.ManagerLogExecution;
import kr.co.lge.goldstar.mvc.m.survey.service.SurveyService;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyAnswerEntity;
import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.SurveyAnswerRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.SurveyRepository;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Service("SurveyService")
public class SurveyServiceImpl implements SurveyService{

	@Autowired
	private SurveyRepository surveyRepository;

	@Autowired
	private SurveyAnswerRepository surveyAnswerRepository;
	
	@Override
	@ManagerLogExecution(process = "getSurvey()", menu="내부활용문답 리스트", button="검색")
	public DataMap getSurvey() {
		
		DataMap result = new DataMap();

		List<SurveyEntity> surveyList = this.surveyRepository.findByDeletedOrderByOrdinalAsc(YesOrNo.N);

		result.put("surveyList", surveyList);
		
		return result;
	}

	@Override
	@ManagerLogExecution(process = "save(SurveyEntity surveyEntity)", menu="내부활용문답 리스트", button="등록하기")
	public DataMap save(SurveyEntity surveyEntity) {
		
		surveyEntity.setOrdinal(this.surveyRepository.maxOrdinal(YesOrNo.N).intValue());
		surveyEntity.setStatus(YesOrNo.N);
		surveyEntity.setDeleted(YesOrNo.N);
		
		List<SurveyAnswerEntity> answers = surveyEntity.getAnswers();
		surveyEntity.setAnswers(null);
		
		this.surveyRepository.save(surveyEntity);
		
		answers.forEach(item -> {
			item.setSurveySn(surveyEntity.getSn());
			item.setDeleted(YesOrNo.N);
		});
		
		this.surveyAnswerRepository.saveAll(answers);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "update(SurveyEntity surveyEntity)", menu="내부활용문답 리스트", button="수정하기")
	public DataMap update(SurveyEntity surveyEntity) {
		
		Optional<SurveyEntity> saved = this.surveyRepository.findById(surveyEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		if(CollectionUtils.isEmpty(surveyEntity.getAnswers())) {
			DataMap result = new DataMap(false);
			result.put("reason", "답변 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		List<SurveyAnswerEntity> answers = new ArrayList<>();
		surveyEntity.getAnswers().forEach(item -> {
			Optional<SurveyAnswerEntity> savedAnswer = this.surveyAnswerRepository.findById(item.getSn());
			if(saved.isPresent()) {
				
				SurveyAnswerEntity answerEntity = savedAnswer.get();
				answerEntity.setName(item.getName());
				answers.add(answerEntity);
			}
		});
		
		if(answers.size() != surveyEntity.getAnswers().size()) {
			DataMap result = new DataMap(false);
			result.put("reason", "답변 데이터를 매칭할 수 없습니다.");
			return result;
		}
		
		SurveyEntity savedEntity = saved.get();
		savedEntity.setQuestion(surveyEntity.getQuestion());
		this.surveyRepository.save(savedEntity);
		
		this.surveyAnswerRepository.saveAll(answers);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "surveySort(DataMap params)", menu="내부활용문답 리스트", button="정렬")
	public DataMap sort(DataMap params) {
		
		List<Integer> snList = params.getListAsInteger("snList[]");
		
		List<SurveyEntity> surveyEntities = this.surveyRepository.findBySnIn(snList);
		
		if(CollectionUtils.isEmpty(surveyEntities)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		Map<Integer, SurveyEntity> map = new HashMap<>();
		surveyEntities.forEach(item -> {
			map.put(item.getSn(), item);
		});
		
		int order = 1;
		for(Integer sn : snList) {
			map.get(sn).setOrdinal(order++);
		}
		
		this.surveyRepository.saveAll(surveyEntities);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "get(SurveyEntity surveyEntity)", menu="내부활용문답 리스트", button="상세")
	public DataMap get(SurveyEntity surveyEntity) {
		
		DataMap result = new DataMap(true);
		result.put("entity", this.surveyRepository.findById(surveyEntity.getSn()).get());
		
		return result;
	}

	@Override
	@ManagerLogExecution(process = "remove(SurveyEntity surveyEntity)", menu="내부활용문답 리스트", button="삭제")
	public DataMap remove(SurveyEntity surveyEntity) {
		
		Optional<SurveyEntity> saved = this.surveyRepository.findById(surveyEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		SurveyEntity savedEntity = saved.get();
		savedEntity.setDeleted(YesOrNo.Y);
		this.surveyRepository.save(savedEntity);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "status(SurveyEntity surveyEntity)", menu="내부활용문답 리스트", button="상태변경")
	public DataMap status(SurveyEntity surveyEntity) {
		
		Optional<SurveyEntity> saved = this.surveyRepository.findById(surveyEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		SurveyEntity savedEntity = saved.get();
		savedEntity.setStatus(surveyEntity.getStatus());
		this.surveyRepository.save(savedEntity);
		
		return new DataMap(true);
	}
}
