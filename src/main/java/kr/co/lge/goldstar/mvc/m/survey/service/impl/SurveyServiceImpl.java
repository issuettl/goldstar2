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
import kr.co.lge.goldstar.orm.jpa.entity.IndivAnswerType;
import kr.co.lge.goldstar.orm.jpa.entity.MindAnswerType;
import kr.co.lge.goldstar.orm.jpa.entity.MoodAnswerType;
import kr.co.lge.goldstar.orm.jpa.entity.StyleAnswerType;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.indiv.IndivAnswerEntity;
import kr.co.lge.goldstar.orm.jpa.entity.indiv.IndivEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mind.MindAnswerEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mind.MindEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodAnswerEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodEntity;
import kr.co.lge.goldstar.orm.jpa.entity.style.StyleAnswerEntity;
import kr.co.lge.goldstar.orm.jpa.entity.style.StyleEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.IndivAnswerRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.IndivMemberRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.IndivRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MindAnswerRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MindMemberRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MindRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MoodAnswerRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MoodMemberRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MoodRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.StyleAnswerRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.StyleMemberRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.StyleRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.core.utils.StringUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Service("SurveyService")
public class SurveyServiceImpl implements SurveyService{

	@Autowired
	private MindRepository mindRepository;
	
	@Autowired
	private IndivRepository indivRepository;
	
	@Autowired
	private StyleRepository styleRepository;
	
	@Autowired
	private MoodRepository moodRepository;

	@Autowired
	private MindAnswerRepository mindAnswerRepository;
	
	@Autowired
	private IndivAnswerRepository indivAnswerRepository;
	
	@Autowired
	private StyleAnswerRepository styleAnswerRepository;
	
	@Autowired
	private MoodAnswerRepository moodAnswerRepository;

	@Autowired
	private MindMemberRepository mindMemberRepository;
	
	@Autowired
	private IndivMemberRepository indivMemberRepository;
	
	@Autowired
	private StyleMemberRepository styleMemberRepository;
	
	@Autowired
	private MoodMemberRepository moodMemberRepository;
	
	@Override
	@ManagerLogExecution(process = "getSurvey()", menu="사전 문답 리스트", button="검색")
	public DataMap getSurvey() {
		
		DataMap result = new DataMap();

		List<MindEntity> mindList = this.mindRepository.findByDeletedOrderByOrdinalAsc(YesOrNo.N);
		List<IndivEntity> indivList = this.indivRepository.findByDeletedOrderByOrdinalAsc(YesOrNo.N);
		List<StyleEntity> styleList = this.styleRepository.findByDeletedOrderByOrdinalAsc(YesOrNo.N);
		List<MoodEntity> moodList = this.moodRepository.findByDeletedOrderByOrdinalAsc(YesOrNo.N);

		result.put("mindList", mindList);
		result.put("indivList", indivList);
		result.put("styleList", styleList);
		result.put("moodList", moodList);

		result.put("mindTypes", MindAnswerType.values());
		result.put("indivTypes", IndivAnswerType.values());
		result.put("styleTypes", StyleAnswerType.values());
		result.put("moodTypes", MoodAnswerType.values());
		
		return result;
	}

	@Override
	@ManagerLogExecution(process = "getAnswer(DataMap params)", menu="사전 문답 데이터", button="검색")
	public DataMap getAnswer(DataMap params) {
		
		DataMap result = new DataMap();
		/*
		List<MindEntity> mindList = this.mindRepository.findByStatusAndDeletedOrderByOrdinalAsc(YesOrNo.Y, YesOrNo.N);
		List<IndivEntity> indivList = this.indivRepository.findByStatusAndDeletedOrderByOrdinalAsc(YesOrNo.Y, YesOrNo.N);
		List<StyleEntity> styleList = this.styleRepository.findByStatusAndDeletedOrderByOrdinalAsc(YesOrNo.Y, YesOrNo.N);
		List<MoodEntity> moodList = this.moodRepository.findByStatusAndDeletedOrderByOrdinalAsc(YesOrNo.Y, YesOrNo.N);
		*/

		List<MindEntity> mindList = this.mindRepository.findByDeletedOrderByOrdinalAsc(YesOrNo.N);
		List<IndivEntity> indivList = this.indivRepository.findByDeletedOrderByOrdinalAsc(YesOrNo.N);
		List<StyleEntity> styleList = this.styleRepository.findByDeletedOrderByOrdinalAsc(YesOrNo.N);
		List<MoodEntity> moodList = this.moodRepository.findByDeletedOrderByOrdinalAsc(YesOrNo.N);

		result.put("mindList", mindList);
		result.put("indivList", indivList);
		result.put("styleList", styleList);
		result.put("moodList", moodList);

		String today = DateUtils.getToday("yyyyMMdd");
		String startDate = params.getAsString("dateStart");
		if(!StringUtils.hasText(startDate) || startDate.length() != 8) {
			startDate = "20221216";
		}
		
		if(Integer.valueOf(startDate) < 20221216) {
			startDate = "20221216";
		}
		
		String endDate = params.getAsString("dateEnd");
		if(!StringUtils.hasText(endDate) || endDate.length() != 8) {
			endDate = today;
		}

		final String start = startDate + "000000";
		final String end = endDate + "235959";
		mindList.forEach(question -> {
			question.getAnswers().forEach(answer -> {
				answer.setCount(this.mindMemberRepository.countByCreatedGreaterThanEqualAndCreatedLessThanEqualAndAnswerSn(start, end, answer.getSn()));
				question.setTotal(question.getTotal() + answer.getCount());
			});
		});
		
		indivList.forEach(question -> {
			question.getAnswers().forEach(answer -> {
				answer.setCount(this.indivMemberRepository.countByCreatedGreaterThanEqualAndCreatedLessThanEqualAndAnswerSn(start, end, answer.getSn()));
				question.setTotal(question.getTotal() + answer.getCount());
			});
		});
		
		styleList.forEach(question -> {
			question.getAnswers().forEach(answer -> {
				answer.setCount(this.styleMemberRepository.countByCreatedGreaterThanEqualAndCreatedLessThanEqualAndAnswerSn(start, end, answer.getSn()));
				question.setTotal(question.getTotal() + answer.getCount());
			});
		});
		
		moodList.forEach(question -> {
			question.getAnswers().forEach(answer -> {
				answer.setCount(this.moodMemberRepository.countByCreatedGreaterThanEqualAndCreatedLessThanEqualAndAnswerSn(start, end, answer.getSn()));
				question.setTotal(question.getTotal() + answer.getCount());
			});
		});
		
		return result;
	}

	@Override
	@ManagerLogExecution(process = "save(MindEntity mindEntity)", menu="사전 문답 리스트", button="등록하기-마음고침")
	public DataMap save(MindEntity mindEntity) {
		
		mindEntity.setOrdinal(this.mindRepository.maxOrdinal(YesOrNo.N).intValue());
		mindEntity.setStatus(YesOrNo.N);
		mindEntity.setDeleted(YesOrNo.N);
		
		List<MindAnswerEntity> answers = mindEntity.getAnswers();
		mindEntity.setAnswers(null);
		
		this.mindRepository.save(mindEntity);
		
		answers.forEach(item -> {
			item.setMindSn(mindEntity.getSn());
			item.setDeleted(YesOrNo.N);
		});
		
		this.mindAnswerRepository.saveAll(answers);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "save(IndivEntity indivEntity)", menu="사전 문답 리스트", button="등록하기-개성고침")
	public DataMap save(IndivEntity indivEntity) {
		
		indivEntity.setOrdinal(this.indivRepository.maxOrdinal(YesOrNo.N).intValue());
		indivEntity.setStatus(YesOrNo.N);
		indivEntity.setDeleted(YesOrNo.N);
		
		List<IndivAnswerEntity> answers = indivEntity.getAnswers();
		indivEntity.setAnswers(null);
		
		this.indivRepository.save(indivEntity);
		
		answers.forEach(item -> {
			item.setIndivSn(indivEntity.getSn());
			item.setDeleted(YesOrNo.N);
		});
		
		this.indivAnswerRepository.saveAll(answers);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "save(StyleEntity styleEntity)", menu="사전 문답 리스트", button="등록하기-스타일고침")
	public DataMap save(StyleEntity styleEntity) {

		styleEntity.setOrdinal(this.styleRepository.maxOrdinal(YesOrNo.N).intValue());
		styleEntity.setStatus(YesOrNo.N);
		styleEntity.setDeleted(YesOrNo.N);
		
		List<StyleAnswerEntity> answers = styleEntity.getAnswers();
		styleEntity.setAnswers(null);
		
		this.styleRepository.save(styleEntity);
		
		answers.forEach(item -> {
			item.setStyleSn(styleEntity.getSn());
			item.setDeleted(YesOrNo.N);
		});
		
		this.styleAnswerRepository.saveAll(answers);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "save(MoodEntity moodEntity)", menu="사전 문답 리스트", button="등록하기-기분고침")
	public DataMap save(MoodEntity moodEntity) {

		moodEntity.setOrdinal(this.moodRepository.maxOrdinal(YesOrNo.N).intValue());
		moodEntity.setStatus(YesOrNo.N);
		moodEntity.setDeleted(YesOrNo.N);
		
		List<MoodAnswerEntity> answers = moodEntity.getAnswers();
		moodEntity.setAnswers(null);
		
		this.moodRepository.save(moodEntity);
		
		answers.forEach(item -> {
			item.setMoodSn(moodEntity.getSn());
			item.setDeleted(YesOrNo.N);
		});
		
		this.moodAnswerRepository.saveAll(answers);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "update(MindEntity mindEntity)", menu="사전 문답 리스트", button="수정하기-마음고침")
	public DataMap update(MindEntity mindEntity) {
		
		Optional<MindEntity> saved = this.mindRepository.findById(mindEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		if(CollectionUtils.isEmpty(mindEntity.getAnswers())) {
			DataMap result = new DataMap(false);
			result.put("reason", "답변 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		List<MindAnswerEntity> answers = new ArrayList<>();
		mindEntity.getAnswers().forEach(item -> {
			Optional<MindAnswerEntity> savedAnswer = this.mindAnswerRepository.findById(item.getSn());
			if(saved.isPresent()) {
				
				MindAnswerEntity answerEntity = savedAnswer.get();
				answerEntity.setName(item.getName());
				answers.add(answerEntity);
			}
		});
		
		if(answers.size() != mindEntity.getAnswers().size()) {
			DataMap result = new DataMap(false);
			result.put("reason", "답변 데이터를 매칭할 수 없습니다.");
			return result;
		}
		
		MindEntity savedEntity = saved.get();
		savedEntity.setQuestion(mindEntity.getQuestion());
		this.mindRepository.save(savedEntity);
		
		this.mindAnswerRepository.saveAll(answers);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "update(IndivEntity indivEntity)", menu="사전 문답 리스트", button="수정하기-개성고침")
	public DataMap update(IndivEntity indivEntity) {
		
		Optional<IndivEntity> saved = this.indivRepository.findById(indivEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		if(CollectionUtils.isEmpty(indivEntity.getAnswers())) {
			DataMap result = new DataMap(false);
			result.put("reason", "답변 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		List<IndivAnswerEntity> answers = new ArrayList<>();
		indivEntity.getAnswers().forEach(item -> {
			Optional<IndivAnswerEntity> savedAnswer = this.indivAnswerRepository.findById(item.getSn());
			if(saved.isPresent()) {
				
				IndivAnswerEntity answerEntity = savedAnswer.get();
				answerEntity.setName(item.getName());
				answers.add(answerEntity);
			}
		});
		
		if(answers.size() != indivEntity.getAnswers().size()) {
			DataMap result = new DataMap(false);
			result.put("reason", "답변 데이터를 매칭할 수 없습니다.");
			return result;
		}
		
		IndivEntity savedEntity = saved.get();
		savedEntity.setQuestion(indivEntity.getQuestion());
		this.indivRepository.save(savedEntity);
		
		this.indivAnswerRepository.saveAll(answers);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "update(StyleEntity styleEntity)", menu="사전 문답 리스트", button="수정하기-스타일고침")
	public DataMap update(StyleEntity styleEntity) {
		
		Optional<StyleEntity> saved = this.styleRepository.findById(styleEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		if(CollectionUtils.isEmpty(styleEntity.getAnswers())) {
			DataMap result = new DataMap(false);
			result.put("reason", "답변 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		List<StyleAnswerEntity> answers = new ArrayList<>();
		styleEntity.getAnswers().forEach(item -> {
			Optional<StyleAnswerEntity> savedAnswer = this.styleAnswerRepository.findById(item.getSn());
			if(saved.isPresent()) {
				
				StyleAnswerEntity answerEntity = savedAnswer.get();
				answerEntity.setName(item.getName());
				answers.add(answerEntity);
			}
		});
		
		if(answers.size() != styleEntity.getAnswers().size()) {
			DataMap result = new DataMap(false);
			result.put("reason", "답변 데이터를 매칭할 수 없습니다.");
			return result;
		}
		
		StyleEntity savedEntity = saved.get();
		savedEntity.setQuestion(styleEntity.getQuestion());
		this.styleRepository.save(savedEntity);
		
		this.styleAnswerRepository.saveAll(answers);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "update(MoodEntity moodEntity)", menu="사전 문답 리스트", button="수정하기-기분고침")
	public DataMap update(MoodEntity moodEntity) {
		
		Optional<MoodEntity> saved = this.moodRepository.findById(moodEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		if(CollectionUtils.isEmpty(moodEntity.getAnswers())) {
			DataMap result = new DataMap(false);
			result.put("reason", "답변 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		List<MoodAnswerEntity> answers = new ArrayList<>();
		moodEntity.getAnswers().forEach(item -> {
			Optional<MoodAnswerEntity> savedAnswer = this.moodAnswerRepository.findById(item.getSn());
			if(saved.isPresent()) {
				
				MoodAnswerEntity answerEntity = savedAnswer.get();
				answerEntity.setName(item.getName());
				answers.add(answerEntity);
			}
		});
		
		if(answers.size() != moodEntity.getAnswers().size()) {
			DataMap result = new DataMap(false);
			result.put("reason", "답변 데이터를 매칭할 수 없습니다.");
			return result;
		}
		
		MoodEntity savedEntity = saved.get();
		savedEntity.setQuestion(moodEntity.getQuestion());
		this.moodRepository.save(savedEntity);
		
		this.moodAnswerRepository.saveAll(answers);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "mindSort(DataMap params)", menu="사전 문답 리스트", button="정렬-마음고침")
	public DataMap mindSort(DataMap params) {
		
		List<Integer> snList = params.getListAsInteger("snList[]");
		
		List<MindEntity> mindEntities = this.mindRepository.findBySnIn(snList);
		
		if(CollectionUtils.isEmpty(mindEntities)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		Map<Integer, MindEntity> map = new HashMap<>();
		mindEntities.forEach(item -> {
			map.put(item.getSn(), item);
		});
		
		int order = 1;
		for(Integer sn : snList) {
			map.get(sn).setOrdinal(order++);
		}
		
		this.mindRepository.saveAll(mindEntities);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "indivSort(DataMap params)", menu="사전 문답 리스트", button="정렬-개성고침")
	public DataMap indivSort(DataMap params) {
		
		List<Integer> snList = params.getListAsInteger("snList[]");
		
		List<IndivEntity> indivEntities = this.indivRepository.findBySnIn(snList);
		
		if(CollectionUtils.isEmpty(indivEntities)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		Map<Integer, IndivEntity> map = new HashMap<>();
		indivEntities.forEach(item -> {
			map.put(item.getSn(), item);
		});
		
		int order = 1;
		for(Integer sn : snList) {
			map.get(sn).setOrdinal(order++);
		}
		
		this.indivRepository.saveAll(indivEntities);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "styleSort(DataMap params)", menu="사전 문답 리스트", button="정렬-스타일고침")
	public DataMap styleSort(DataMap params) {
		
		List<Integer> snList = params.getListAsInteger("snList[]");
		
		List<StyleEntity> styleEntities = this.styleRepository.findBySnIn(snList);
		
		if(CollectionUtils.isEmpty(styleEntities)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		Map<Integer, StyleEntity> map = new HashMap<>();
		styleEntities.forEach(item -> {
			map.put(item.getSn(), item);
		});
		
		int order = 1;
		for(Integer sn : snList) {
			map.get(sn).setOrdinal(order++);
		}
		
		this.styleRepository.saveAll(styleEntities);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "moodSort(DataMap params)", menu="사전 문답 리스트", button="정렬-기분고침")
	public DataMap moodSort(DataMap params) {
		
		List<Integer> snList = params.getListAsInteger("snList[]");
		
		List<MoodEntity> moodEntities = this.moodRepository.findBySnIn(snList);
		
		if(CollectionUtils.isEmpty(moodEntities)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		Map<Integer, MoodEntity> map = new HashMap<>();
		moodEntities.forEach(item -> {
			map.put(item.getSn(), item);
		});
		
		int order = 1;
		for(Integer sn : snList) {
			map.get(sn).setOrdinal(order++);
		}
		
		this.moodRepository.saveAll(moodEntities);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "get(MindEntity mindEntity)", menu="사전 문답 리스트", button="상세-마음고침")
	public DataMap get(MindEntity mindEntity) {
		
		DataMap result = new DataMap(true);
		result.put("entity", this.mindRepository.findById(mindEntity.getSn()).get());
		
		return result;
	}

	@Override
	@ManagerLogExecution(process = "get(IndivEntity indivEntity)", menu="사전 문답 리스트", button="상세-개성고침")
	public DataMap get(IndivEntity indivEntity) {
		
		DataMap result = new DataMap(true);
		result.put("entity", this.indivRepository.findById(indivEntity.getSn()).get());
		
		return result;
	}

	@Override
	@ManagerLogExecution(process = "get(StyleEntity styleEntity)", menu="사전 문답 리스트", button="상세-스타일고침")
	public DataMap get(StyleEntity styleEntity) {
		
		DataMap result = new DataMap(true);
		result.put("entity", this.styleRepository.findById(styleEntity.getSn()).get());
		
		return result;
	}

	@Override
	@ManagerLogExecution(process = "get(MoodEntity moodEntity)", menu="사전 문답 리스트", button="상세-기분고침")
	public DataMap get(MoodEntity moodEntity) {
		
		DataMap result = new DataMap(true);
		result.put("entity", this.moodRepository.findById(moodEntity.getSn()).get());
		
		return result;
	}

	@Override
	@ManagerLogExecution(process = "remove(MindEntity mindEntity)", menu="사전 문답 리스트", button="삭제-마음고침")
	public DataMap remove(MindEntity mindEntity) {
		
		Optional<MindEntity> saved = this.mindRepository.findById(mindEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		MindEntity savedEntity = saved.get();
		savedEntity.setDeleted(YesOrNo.Y);
		this.mindRepository.save(savedEntity);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "remove(IndivEntity indivEntity)", menu="사전 문답 리스트", button="삭제-개성고침")
	public DataMap remove(IndivEntity indivEntity) {
		
		Optional<IndivEntity> saved = this.indivRepository.findById(indivEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		IndivEntity savedEntity = saved.get();
		savedEntity.setDeleted(YesOrNo.Y);
		this.indivRepository.save(savedEntity);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "remove(StyleEntity styleEntity)", menu="사전 문답 리스트", button="삭제-스타일고침")
	public DataMap remove(StyleEntity styleEntity) {
		
		Optional<StyleEntity> saved = this.styleRepository.findById(styleEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		StyleEntity savedEntity = saved.get();
		savedEntity.setDeleted(YesOrNo.Y);
		this.styleRepository.save(savedEntity);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "remove(MoodEntity moodEntity)", menu="사전 문답 리스트", button="삭제-기분고침")
	public DataMap remove(MoodEntity moodEntity) {
		
		Optional<MoodEntity> saved = this.moodRepository.findById(moodEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		MoodEntity savedEntity = saved.get();
		savedEntity.setDeleted(YesOrNo.Y);
		this.moodRepository.save(savedEntity);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "status(MindEntity mindEntity)", menu="사전 문답 리스트", button="상태변경-마음고침")
	public DataMap status(MindEntity mindEntity) {
		
		Optional<MindEntity> saved = this.mindRepository.findById(mindEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		MindEntity savedEntity = saved.get();
		savedEntity.setStatus(mindEntity.getStatus());
		this.mindRepository.save(savedEntity);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "status(IndivEntity indivEntity)", menu="사전 문답 리스트", button="상태변경-개성고침")
	public DataMap status(IndivEntity indivEntity) {
		
		Optional<IndivEntity> saved = this.indivRepository.findById(indivEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		IndivEntity savedEntity = saved.get();
		savedEntity.setStatus(indivEntity.getStatus());
		this.indivRepository.save(savedEntity);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "status(StyleEntity styleEntity)", menu="사전 문답 리스트", button="상태변경-스타일고침")
	public DataMap status(StyleEntity styleEntity) {
		
		Optional<StyleEntity> saved = this.styleRepository.findById(styleEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		StyleEntity savedEntity = saved.get();
		savedEntity.setStatus(styleEntity.getStatus());
		this.styleRepository.save(savedEntity);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "status(MoodEntity moodEntity)", menu="사전 문답 리스트", button="상태변경-기분고침")
	public DataMap status(MoodEntity moodEntity) {
		
		Optional<MoodEntity> saved = this.moodRepository.findById(moodEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		MoodEntity savedEntity = saved.get();
		savedEntity.setStatus(moodEntity.getStatus());
		this.moodRepository.save(savedEntity);
		
		return new DataMap(true);
	}

}
