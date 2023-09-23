package kr.co.lge.goldstar.mvc.m.exp.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.lge.goldstar.mvc.m.exp.service.ExpService;
import kr.co.lge.goldstar.mvc.m.manager.service.ManagerLogExecution;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyAnswerEntity;
import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.SurveyAnswerRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.SurveyRepository;
import kr.co.rebel9.core.utils.FileUtils;
import kr.co.rebel9.web.data.DataMap;

@Service("ExpService")
public class ExpServiceImpl implements ExpService {

	@Autowired
	private SurveyRepository surveyRepository;

	@Autowired
	private SurveyAnswerRepository surveyAnswerRepository;
    
    @Value("${multipart.path.survey}")
    private String expPath;

	@Override
	@ManagerLogExecution(process = "getExps()", menu="체험제품 리스트", button="검색")
	public DataMap getExps() {
		
		DataMap result = new DataMap(true);
		result.put("entity", this.surveyRepository.findById(1).get());
		
		return result;
	}

	@Override
	@ManagerLogExecution(process = "save(MultipartFile imageFile, DataMap params)", menu="체험제품 리스트", button="등록하기")
	public DataMap save(MultipartFile imageFile, DataMap params) {
		
		if(ObjectUtils.isEmpty(imageFile)){
			DataMap result = new DataMap(false);
			result.put("reason", "이미지 파일을 찾을 수 없습니다.");
			return result;
        }
		
        UUID uuid = UUID.randomUUID();
        String dateDir = FileUtils.getDateDir();
        String imagePath = new StringBuilder(dateDir).append(uuid).append("_i").toString();

        try {
			org.apache.commons.io.FileUtils.writeByteArrayToFile(
			        new File(new StringBuilder(this.expPath).append(imagePath).toString()), 
			        imageFile.getBytes());
		} catch (IOException e) {
			DataMap result = new DataMap(false);
			result.put("reason", e.getMessage());
			return result;
		}
        
        int surveySn = 1;
        
        Optional<SurveyEntity> savedSurvey = this.surveyRepository.findById(surveySn);
		if(!savedSurvey.isPresent()) {
			DataMap result = new DataMap(false);
			result.put("reason", "체험 제품 마스터 정보를 찾을 수 없습니다.");
			return result;
		}
		
		SurveyAnswerEntity answer = new SurveyAnswerEntity();

		answer.setSurveySn(surveySn);
		answer.setName(params.getAsString("name"));
		answer.setOrdinal(this.surveyAnswerRepository.maxOrdinal(surveySn, YesOrNo.N).intValue());
		answer.setDeleted(YesOrNo.N);
		answer.setSurvey(savedSurvey.get());

		answer.setImage(imagePath);
		answer.setImageContentType(imageFile.getContentType());
		
		this.surveyAnswerRepository.save(answer);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "update(MultipartFile imageFile, DataMap params)", menu="체험제품 리스트", button="수정하기")
	public DataMap update(MultipartFile imageFile, DataMap params) {
		
		SurveyAnswerEntity answer = this.surveyAnswerRepository.findBySnAndDeleted(params.getAsInt("sn"), YesOrNo.N);
		if(ObjectUtils.isEmpty(answer)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		UUID uuid = UUID.randomUUID();
        String dateDir = FileUtils.getDateDir();

        try {
        	if(!ObjectUtils.isEmpty(imageFile)){
        		
                String imagePath = new StringBuilder(dateDir).append(uuid).append("_i").toString();
				org.apache.commons.io.FileUtils.writeByteArrayToFile(
				        new File(new StringBuilder(this.expPath).append(imagePath).toString()), 
				        imageFile.getBytes());
				
				answer.setImage(imagePath);
				answer.setImageContentType(imageFile.getContentType());
        	}
			
		} catch (IOException e) {
			DataMap result = new DataMap(false);
			result.put("reason", e.getMessage());
			return result;
		}

        answer.setName(params.getAsString("name"));

		this.surveyAnswerRepository.save(answer);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "remove(SurveyAnswerEntity answerEntity)", menu="체험제품 리스트", button="삭제")
	public DataMap remove(SurveyAnswerEntity answerEntity) {
		
		Optional<SurveyAnswerEntity> saved = this.surveyAnswerRepository.findById(answerEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		SurveyAnswerEntity savedEntity = saved.get();
		savedEntity.setDeleted(YesOrNo.Y);
		this.surveyAnswerRepository.save(savedEntity);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "get(SurveyAnswerEntity answerEntity)", menu="체험제품 리스트", button="상세")
	public DataMap get(SurveyAnswerEntity answerEntity) {
		
		DataMap result = new DataMap(true);
		result.put("entity", this.surveyAnswerRepository.findById(answerEntity.getSn()).get());
		
		return result;
	}

	@Override
	@ManagerLogExecution(process = "sort(DataMap params)", menu="체험제품 리스트", button="정렬")
	public DataMap sort(DataMap params) {
		
		List<Integer> snList = params.getListAsInteger("snList[]");
		
		List<SurveyAnswerEntity> answerEntities = this.surveyAnswerRepository.findBySnIn(snList);
		
		if(CollectionUtils.isEmpty(answerEntities)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		Map<Integer, SurveyAnswerEntity> map = new HashMap<>();
		answerEntities.forEach(item -> {
			map.put(item.getSn(), item);
		});
		
		int order = 1;
		for(Integer sn : snList) {
			map.get(sn).setOrdinal(order++);
		}
		
		this.surveyAnswerRepository.saveAll(answerEntities);
		
		return new DataMap(true);
	}
}
