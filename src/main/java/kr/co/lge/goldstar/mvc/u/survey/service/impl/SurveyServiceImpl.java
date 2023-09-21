/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.survey.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.lge.goldstar.mvc.u.sign.service.SignService;
import kr.co.lge.goldstar.mvc.u.survey.service.SurveyService;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyEntity;
import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyMemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyPartEntity;
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

	@Override
	public List<SurveyEntity> getQuestions() {
		return this.surveyRepository.findByStatusAndDeletedOrderByOrdinalAsc(YesOrNo.Y, YesOrNo.N);
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
}
