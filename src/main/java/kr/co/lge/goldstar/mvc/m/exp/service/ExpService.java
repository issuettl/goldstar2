package kr.co.lge.goldstar.mvc.m.exp.service;

import org.springframework.web.multipart.MultipartFile;

import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyAnswerEntity;
import kr.co.rebel9.web.data.DataMap;

public interface ExpService {

	DataMap getExps();

	DataMap save(MultipartFile imageFile, DataMap dataMap);

	DataMap update(MultipartFile imageFile, DataMap dataMap);

	DataMap remove(SurveyAnswerEntity SurveyAnswerEntity);

	DataMap get(SurveyAnswerEntity SurveyAnswerEntity);

	DataMap sort(DataMap dataMap);

}
