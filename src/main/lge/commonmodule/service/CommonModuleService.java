/*******************************************************************************
 * FILE: /java/lgcommkt/commonmodule/service/CommonModuleService.java
 * DESC : Common Module Service
 *
 * PROJ : LGEKR5.0
 *******************************************************************************
 *                  Modification History
 *******************************************************************************
 *
 * CsrNo.           DATE                 AUTHOR        	FUNCTION          DESCRIPTION
 * ---------------------------------------------------------------------------------
 *                   2020/09/02           김시훈                                                 Initial Release
 * BTOCSITE-6509     2021/10/18           won.lee                          스팟성 한정 수량만 판매시 기능 개발                  
 * ---------------------------------------------------------------------------------
 ***********************************************************************************/


package lgekormkt.commonmodule.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import lgekormkt.commonmodule.model.CommonParamVO;
import lgekormkt.commonmodule.model.ProductMessageVO;
import lgekormkt.commonmodule.model.RuleVO;
import lgekormkt.commonmodule.model.SalesCodeVO;

public interface CommonModuleService {



	/**
	 * 가격규칙확인에 대한 ServiceImpl Method이다.
	 * @param RuleVO - 조회할 정보가 담긴 model
	 * @return RuleVO 가격규칙확인 결과(정상가, 할인가) : 값이 존재하지 않을경우 null 리턴
	 * @author Park Sehun
	 */
	public RuleVO confirmPriceRule(RuleVO model);

	/**
	 * 버튼규칙확인에 대한 ServiceImpl Method이다.
	 * @param RuleVO - 조회할 정보가 담긴 vo
	 * @return RuleVO 버튼규칙확인 결과(Y/N)
	 * @author Park Sehun
	 */
	public RuleVO confirmButtonRule(RuleVO model);

	/**
	 * 일별 아이디를 카운트 정보를 반영 위한 ServiceImpl Method이다.
	 * @param input - 일별 아이디를 카운트 하기 위한 정보가 담긴 input
	 * @return -
	 * @author Park Sehun
	 */
	public int updateAuthoring(Map<String, Object> input);

	/**
	 * Com Preference 공통코드
	 * @param input - locale Code
	 * @return - Map<String, Object> preferenceMap
	 * @author Choe YeongHwan
	 */
	public Map<String, Object> retrievePreference();

	/**
	 * Login Check Method
	 * @param HttpServletRequest
	 * @return - Map<String, Object> loginCheck
	 * @author Choe YeongHwan
	 */
	public Map<String, Object> loginCheck(HttpServletRequest request);

	/**
	 * Com Locale M 테이블 조회 Method
	 * @param
	 * @return - Map<String, Object> LocaleInfo
	 * @author Choe YeongHwan
	 */
	public Map<String, Object> retrieveLocaleInfo();

	/**
	 * ConfCode 조회
	 * @param CommonParamVO input - List CodeList
	 * @return Map<String, Object> - Code, Value
	 * @author Choe YeongHwan
	 */
	public Map<String, Object> retrieveConfCode(CommonParamVO input);

	/**
	 * 단 건 ConfCode 조회
	 * @param confCode - 조회 하려는 코드
	 * @return String result - 공통 코드 값
	 * @author Kim Chung
	 */
	public String getConfString(String confCode);

	/**
	 * ModelId 기준으로 categoryId 정보를 조회
	 * @param Map<String, Object> input - modelUrlPath, localeCode, modelId
	 * @return Map<String, Object> - superCategoryId, categoryId, subCategoryId
	 * @author Park Sehun
	 */
	public Map<String, Object> retrieveCategoryModelMap(Map<String, Object> input);

	/**
	 * obs 설정 정보 Method
	 * @param
	 * @return - Map<String, Object> obsInfo
	 * @author Choe YeongHwan
	 */
	public Map<String, Object> retrieveObsInfo();

	/**
	 * 입력받은 데이터 에 대해 String 으로 리턴한다 (url 은 도메인 붙히는 여부 설정 가능하다.)
	 * @param Map<String, Object> inputMap, String code, String type, boolean domainYn
	 * @return - String
	 * @author Choe YeongHwan
	 */
	public String codeToString(Map<String, Object> inputMap, String code, boolean domainYn);

	/**
	 * map에서 key code의 value값을 체크하여 String으로 리턴한다.
	 * value가 null 일 경우, "" String으로 리턴
	 * @param inputMap - Map
	 * @param code - 체크 할 key값
	 * @return input
	 * @author Kim Chung
	 */
	public String isNull(Map<String, Object> inputMap, String code);

	/**
	 * map에서 key code의 value값을 체크하여 String으로 리턴한다.
	 * value가 null 일 경우, defaultValue로 리턴
	 * @param inputMap - Map
	 * @param code - 체크 할 key값
	 * @param defaultValue - null일 경우, 리턴 시킬 값
	 * @return input
	 * @author Kim Chung
	 */
	public String isNvl(Map<String, Object> inputMap, String code, String defaultValue);

	/**
	 * [PDP] Sibling의 대표 모델 정보 조회 ( 맨 처음 노출 되는 Sibling의 default 모델 )
	 * @param SalesCodeVO.modelId - 조회 하려는 모델 ID
	 * @param SalesCodeVO.bizType - Business 구분(B2C, B2B)
	 * @param SalesCodeVO.modelStatusCode - 모델상태코드
	 * @return SalesCodeVO result - 대표 모델의 Sales Model Code 정보. 없을 경우 null을 리턴시킴
	 * @author Kim Chung
	 */
	public SalesCodeVO getDefaultSiblingModelInfo(SalesCodeVO inputMap);

	/**
	 * [PDP] Sibling의 대표 모델 정보 조회 ( 맨 처음 노출 되는 Sibling의 default 모델 )
	 * @param HttpServletRequest request
	 * @return Boolean
	 * @author Choe Yeong Hwan
	 */
	public Boolean getAccessTokenVelidation(HttpServletRequest request);

	/**
	 * COM_PREFERENCE_M 데이터 조회
	 * @param strCode - 조회하려는 코드
	 * @param strDefault - 값이 없을 경우 Default 값
	 * @return String - value 값
	 * @author GP Rollout SI
	 */
//	public String getComPreference(String strCode, String strDefault);

	/**
	 * Product MessageCode Info
 	 * @param
	 * @return GpBtnMessageVO
	 * @author GP Rollout SI
	 */
	public ProductMessageVO productMessageInfo();

	/**
	 * Product Support Url Info
 	 * @param
	 * @return String
	 * @author GP Rollout SI
	 */
	public String getSupportLinkUrl(Map<String, Object> input);

	/**
	 * 국가별 MKT_WTB_SETTING_M 테이블의 Flag값 조회
	 * @return Map< String, Object> - localeCode : 국가코드
	 * @return Map< String, Object> - buyonlineType : Buy online 사용여부 및 종류 (none / lgcom / static / channel_ad)
	 * @return Map< String, Object> - wtbType : Where to Buy 영역 (All / Multi / None)
	 * @return Map< String, Object> - wtbUseFlag : Offline map 사용여부 및 종류 (none / lgcom / iframe_all / comcon_all / prispi_all)
	 * @author Kim Chung
	 */
	public Map< String, Object> retrieveWtbSettingUseFlagMap();

	public Map<String, Object> loginInfo(HttpServletRequest request);

	public Map<String, Object> getBizType(Map<String, Object> input);

	public int retrieveCareCartCnt(Map<String, Object> input);

	public Map<String, Object> retrieveWishList (String type, Map<String, Object> input,JSONObject apiData);

	public String retrieveStdCategoryId(String modelId);

	public List<String> retrieveCareCartInfo(Map<String, Object> input);

	public String retrieveCareType(String rtModelSeq);

	public List<Map<String, Object>> retrieveOwnModel(String unifyId);
	
//	BTOCSITE-6509
	public String retrieveObsLimitFlag(String modelId);
    public String retrieveAdvanceBookingModelFlag(String modelId);
}
