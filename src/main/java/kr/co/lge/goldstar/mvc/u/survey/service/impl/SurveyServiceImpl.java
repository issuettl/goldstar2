/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.survey.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import kr.co.lge.goldstar.mvc.m.system.service.SystemService;
import kr.co.lge.goldstar.mvc.u.sign.service.SignService;
import kr.co.lge.goldstar.mvc.u.survey.service.SurveyService;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyAnswerEntity;
import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyEntity;
import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyMemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.system.SystemEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.SurveyAnswerRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.SurveyMemberRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.SurveyPartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.SurveyRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Service("u.SurveyService")
public class SurveyServiceImpl implements SurveyService {

	@Autowired
	private SurveyRepository surveyRepository;
	
	@Autowired
	private SurveyPartRepository surveyPartRepository;
	
	@Autowired
	private SurveyAnswerRepository surveyAnswerRepository;
	
	@Autowired
	private SurveyMemberRepository surveyMemberRepository;
	
	@Autowired
	private SignService signService;
	
	@Autowired
	private SystemService systemService;
    
    @Value("${multipart.path.survey}")
    private String expPath;

	@Override
	public List<SurveyEntity> getQuestions() {
		
		List<SurveyEntity> surveyList = this.surveyRepository.findByStatusAndDeletedOrderByOrdinalAsc(YesOrNo.Y, YesOrNo.N);
		
		SystemEntity systemInfo = this.systemService.getEntity(1);
		if(ObjectUtils.isEmpty(systemInfo)) {
			return surveyList;
		}
		
		if(CollectionUtils.isEmpty(surveyList)) {
			return surveyList;
		}
		
		List<SurveyEntity> surveySystem = new ArrayList<>();
		for(SurveyEntity entity : surveyList) {
			
			if(YesOrNo.Y.equals(systemInfo.getProduct()) && entity.getSn() == 1) {
				surveySystem.add(entity);
			}
			
			if(YesOrNo.Y.equals(systemInfo.getSurvey()) && entity.getSn() != 1) {
				surveySystem.add(entity);
			}
		}
		
		return surveySystem;
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
		
		boolean isExsit = this.surveyPartRepository.countBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated()) > 0;
		if(isExsit) {
			DataMap result = new DataMap(false);
			result.put("reason", "이미 완료하였습니다.");
			result.put("code", 9998);
			return result;
		}
		
		SurveyPartEntity surveyPartEntity = this.surveyPartRepository.findBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated());
		
		if(ObjectUtils.isEmpty(surveyPartEntity)) {
			surveyPartEntity = new SurveyPartEntity();
			surveyPartEntity.setCreated(DateUtils.getToday("yyyyMMddHHmmss"));
			surveyPartEntity.setSignMemberSn(signEntity.getId().getMemberSn());
			surveyPartEntity.setSignCreated(signEntity.getId().getCreated());
			
			//저장
			this.surveyPartRepository.save(surveyPartEntity);
		}
		
		String created = DateUtils.getToday("yyyyMMddHHmmss");
		List<SurveyMemberEntity> surveyMemberEntities = new ArrayList<>();
		for(Integer answer : answers) {
			SurveyMemberEntity memberEntity = new SurveyMemberEntity();
			surveyMemberEntities.add(memberEntity);
			memberEntity.setCreated(created);
			memberEntity.setPart(surveyPartEntity);
			memberEntity.setAnswer(this.surveyAnswerRepository.findById(answer).orElseThrow());
		}
		
		this.surveyMemberRepository.saveAll(surveyMemberEntities);
		
		DataMap result = new DataMap(true);
		return result;
	}

	@Override
	public SurveyPartEntity getSurveyPart() {
		
		SignEntity signEntity = this.signService.getSignIn();
		
		return this.surveyPartRepository.findBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated());
	}

	@Override
	public DataMap getImageFile(int sn) {
		
		SurveyAnswerEntity answerEntity = this.surveyAnswerRepository.findBySnAndDeleted(sn, YesOrNo.N);
		if(ObjectUtils.isEmpty(answerEntity)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		DataMap result = new DataMap();
		result.put("defaultPath", this.expPath);
		result.put("filePath", answerEntity.getImage());
		result.put("contentType", answerEntity.getImageContentType());
		
		return result;
	}
}
