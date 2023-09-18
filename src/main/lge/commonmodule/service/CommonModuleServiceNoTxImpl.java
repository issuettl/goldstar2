/*******************************************************************************
 * FILE: /java/lgcommkt/commonmodule/service/CommonModuleServiceImpl.java
 * DESC : Common Module ServiceImpl
 *
 * PROJ : LGEKR5.0
 *******************************************************************************
 *                  Modification History
 *******************************************************************************
 *
 * CsrNo.           DATE                 AUTHOR        	FUNCTION          DESCRIPTION
 * ---------------------------------------------------------------------------------
 *                   2020/09/02           김시훈                                                 Initial Release
 * BTOCSITE-1663     2021/06/17    Gyusik.Choi                        WishList API 항목 수정 및 OBS업그레이드 관련 테스트요청
 * BTOCISTE-6509     2021/10/18    won.lee                            스팟성 한정 수량만 판매시 기능 개발
 * ---------------------------------------------------------------------------------
 ***********************************************************************************/
package lgekormkt.commonmodule.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.nimbusds.jwt.JWTClaimsSet;

import devonframe.configuration.ConfigService;
import devonframe.dataaccess.CommonDao;
import devonframe.exception.BusinessException;
//import lgekorfrm.preference.model.ComPreference;
import lgekorfrm.sso.TokenContext;
import lgekorfrm.sso.member.MemberInformation;
import lgekorfrm.sso.util.JwtUtils;
import lgekormkt.common.code.MKTCommonCodes;
import lgekormkt.common.util.StringUtil;
import lgekormkt.commonmodule.model.CommonParamVO;
import lgekormkt.commonmodule.model.ProductMessageVO;
import lgekormkt.commonmodule.model.RuleVO;
import lgekormkt.commonmodule.model.SalesCodeVO;

@Service("CommonModuleService")
public class CommonModuleServiceNoTxImpl implements CommonModuleService {

	@Resource(name = "commonDao")
	private CommonDao commonDao;

	@Resource(name = "configService")
    private ConfigService configService;

	@Resource(name="messageSourceAccessor")
	private MessageSourceAccessor msgAccessor;

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonModuleServiceNoTxImpl.class);

	public static final String MODEL_STATUS_ACTIVE = "ACTIVE";
	public static final String MODEL_STATUS_DISCONTINUED = "DISCONTINUED";
	public static final String LOCALE_CODE_US = "US";
	public static final String BIZ_TYPE_CUSTOMER = "B2C";
	public static final String BIZ_TYPE_BUSSINESS = "B2B";


	/**
	 * 가격규칙확인에 대한 ServiceImpl Method이다.
	 * @param RuleVO - 조회할 정보가 담긴 model
	 * @return RuleVO 가격규칙확인 결과(정상가, 할인가) : 0또는 값이 존재하지 않을경우 null 리턴
	 * @author Park Sehun
	 */
	public RuleVO confirmPriceRule(RuleVO model) {
		/** param */
		BigDecimal msrp = model.getMsrp();
		BigDecimal obsSellingPrice = model.getObsSellingPrice();
		BigDecimal promotionPrice = model.getPromotionPrice();
		String obsInventoryFlag = model.getObsInventoryFlag();
		String obsSellFlag = model.getObsSellFlag();
		String obsLoginFlag = model.getObsLoginFlag();
		String eCommerceFlag = model.geteCommerceFlag();
		String modelStatusCode = model.getModelStatusCode();
		String obsPriceFlag = model.getObsPriceFlag();

		/** 할인율 및 소수점 적용 */
		BigDecimal price = BigDecimal.ZERO;				//원가
		BigDecimal promoPrice = BigDecimal.ZERO;		//할인가
		BigDecimal discountedRate = BigDecimal.ZERO;	//할인율
		String[] arrPrice = null;						//원가 소수점 분리 배열
		String[] arrPromoPrice = null;					//할인가 소수점 분리 배열
		/* LGEUS-12083 Start */
		BigDecimal discountedPrice = BigDecimal.ZERO;		//할인가격
		String[] arrDiscountedPrice = null;						//할인가격 소수점 분리 배열
		/* LGEUS-12083 End */

		/** return */
		RuleVO priceRule = new RuleVO();
		String sPrice = null;
		String sPriceCent = null;
		String sPromoPrice = null;
		String sPromoPriceCent = null;
		String sDiscountedRate = null;
		/* LGEUS-12083 Start */
		String sDiscountedPrice = null;
		String sDiscountedPriceCent = null;
		/* LGEUS-12083 End */

		/** 가격룰 적용 */
		if( "ACTIVE".equals(modelStatusCode) ){
			price = msrp;
			boolean obsCondition = "Y".equals(obsInventoryFlag)
									&& "Y".equals(obsSellFlag)
									&& "Y".equals(obsLoginFlag)
									&& "Y".equals(eCommerceFlag)
									&& "Y".equals(obsPriceFlag);
			if( obsCondition ){	//obs 프라이스 적용
				promoPrice = obsSellingPrice;
			}else{				//닷컴 프라이스 적용
				promoPrice = promotionPrice;
			}

			/** 가격룰 예외 : 원가가 할인가 보다 크지 않으면 제외 */
			if( price.compareTo(promoPrice) != 1 ){
				price = promoPrice;
				promoPrice = BigDecimal.ZERO;
			}
			boolean priceZero = price.compareTo(BigDecimal.ZERO) == 0;
			boolean promoPriceZero = promoPrice.compareTo(BigDecimal.ZERO) == 0;

			/** 할인율 : 원가, 할인가 모두 존재할때만 연산 */
			if( !priceZero && !promoPriceZero ){
				discountedRate = price.subtract(promoPrice)
								.divide(price, 4 , BigDecimal.ROUND_HALF_UP)
								.multiply(new BigDecimal("100"))
								.setScale(0, BigDecimal.ROUND_DOWN);
				sDiscountedRate = Objects.toString(discountedRate);
				/* LGEUS-12083 Start */
				discountedPrice = price.subtract(promoPrice);
				DecimalFormat form = new DecimalFormat("0.00");
				arrDiscountedPrice = form.format(discountedPrice).split("\\.");
				sDiscountedPrice = arrDiscountedPrice[0];
				sDiscountedPriceCent = arrDiscountedPrice[1];
				/* LGEUS-12083 End */
			}

			/** 소주점 두자리 포맷 적용 및 가격 소수점 분리 */
			if( !priceZero ){
				DecimalFormat form = new DecimalFormat("0.00");
				arrPrice = form.format(price).split("\\.");
				sPrice = arrPrice[0];
				sPriceCent = arrPrice[1];
			}
			if( !promoPriceZero ){
				DecimalFormat form = new DecimalFormat("0.00");
				arrPromoPrice = form.format(promoPrice).split("\\.");
				sPromoPrice = arrPromoPrice[0];
				sPromoPriceCent = arrPromoPrice[1];
			}
		}

		priceRule.setPrice(sPrice);
		priceRule.setPriceCent(sPriceCent);
		priceRule.setPromoPrice(sPromoPrice);
		priceRule.setPromoPriceCent(sPromoPriceCent);
		priceRule.setDiscountedRate(sDiscountedRate);
		/* LGEUS-12083 Start */
		priceRule.setDiscountedPrice(sDiscountedPrice);
		priceRule.setDiscountedPriceCent(sDiscountedPriceCent);
		/* LGEUS-12083 End */
		return priceRule;
	}

	/**
	 * 버튼규칙확인에 대한 ServiceImpl Method이다.
	 * @param RuleVO - 조회할 정보가 담긴 vo
	 * @return RuleVO 버튼규칙확인 결과(Y/N)
	 * @author Park Sehun
	 */
	public RuleVO confirmButtonRule(RuleVO model) {
		RuleVO buttonRule = new RuleVO();
		String addToCartFlag = "N";
		String whereToBuyFlag = "N";
		String wtbExternalLinkUseFlag = "N"; // LGEUS-12198
		String inquiryToBuyFlag = "N";
		String findTheDealerFlag = "N";

		if( "B2B".equals(model.getBizType()) ){	//B2B
			inquiryToBuyFlag = "Y";
			//LGEUS-11950 START
			if("N".equals(model.getFindTheDealerFlag())){
				findTheDealerFlag = "N";
			}else{
				findTheDealerFlag = "Y";
			}
			//LGEUS-11950 END
		}else{ 									//B2C

			// LGEUS-12198
			if( "ACTIVE".equals(model.getModelStatusCode())
					&& "Y".equals(model.getObsInventoryFlag())
					&& "Y".equals(model.getObsSellFlag())
					&& "Y".equals(model.getObsLoginFlag())
					&& "Y".equals(model.geteCommerceFlag())
					&& "Y".equals(model.getObsPriceFlag()) ) {
				addToCartFlag = "Y";
			}

			if(!"Y".equals(model.getCtaDisalbeWtb())){

				if( "Y".equals(model.getWtbFlag()) ) {
					whereToBuyFlag = "Y";
				}

				if( "Y".equals(model.getWtbExternalLinkUseFlag()) ) {
					wtbExternalLinkUseFlag = "Y";
					whereToBuyFlag = "N";
				}

			}
			// LGEUS-12198 End
		}

		buttonRule.setAddToCartFlag( addToCartFlag );
		buttonRule.setWhereToBuyFlag( whereToBuyFlag );
		buttonRule.setWtbExternalLinkUseFlag( wtbExternalLinkUseFlag ); // LGEUS-12198
		buttonRule.setInquiryToBuyFlag( inquiryToBuyFlag );
		buttonRule.setFindTheDealerFlag( findTheDealerFlag );

		return buttonRule;
	}

	/**
	 * 일별 아이디를 카운트 정보를 반영 위한 ServiceImpl Method이다.
	 * @param input - 일별 아이디를 카운트 하기 위한 정보가 담긴 input
	 * @return -
	 * @author Park Sehun
	 */
	@Override
	public int updateAuthoring(Map<String, Object> input) {
		int queryResult = 0;
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			/** common Settings */
//			String timeZone = getComPreference("promotionTimeZone", null);
			String timeZone = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
			input.put("today", sdf.format(new Date()));

			input.put("localeCode", "KR");
			result = commonDao.select("commonmodule.retrieveAuthoring", input);				//Retrieve

			if( "0".equals(isNull(result,"COUNT_NO")) ){
				queryResult = commonDao.insert("commonmodule.insertAuthoring", input);		//INSERT
			}else{
				queryResult = commonDao.update("commonmodule.updateAuthoring", input);		//UPDATE
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return queryResult;
	}

	/**
	 * 공통코드
	 * @param input - locale Code
	 * @return - Map<String, Object> preferenceMap
	 * @author Choe YeongHwan
	 */
	@Override
	public Map<String, Object> retrievePreference() {
		// TODO Auto-generated method stub
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("localeCode", "KR");
		List<Map<String, Object>> preferenceList = commonDao.selectList("commonmodule.retrievePreference", input);

		Map<String, Object> result = new HashMap<String, Object>();
		if(0 < preferenceList.size()){
			for(Map<String, Object> preferenceMap : preferenceList){
				result.put((String) preferenceMap.get("preferenceCode"), preferenceMap.get("preferenceValue"));
			}
		}
		return result;
	}

	/**
	 * 로그인체크에 대한 ServiceImpl Method이다.
	 * @param mktFrontAccessCount - 조회할 정보가 담긴 MktFrontAccessCount
	 * @return 로그인체크 결과
	 */
	public Map<String, Object> loginCheck(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();

		Object accessToken = request.getSession().getAttribute("access_token");
		Map<?, ?> user = TokenContext.getThreadLocalInstance().getUser();
		LOGGER.debug( "request.getSession().getAttribute :" + accessToken );
		LOGGER.debug( "TokenContext.getThreadLocalInstance().getUser() :" + user );

		result.put("firstName", "shin");
		result.put("lastName", "eunae");
		result.put("loginFlag", "Y");
//		result.put("obsLoginFlag", WebContext.getComPreferenceMap().get("OBS_LOGIN_FLAG").getPreferenceValue());
		result.put("obsLoginFlag", "Y");

		return result;
	}

	/**
	 * Locale 기준 공통 코드 정보 조회
	 * @param null
	 * @return Map<String, Object> - COM_LOCALE_M 정보
	 * @author Kim Chung
	 */
	public Map<String, Object> retrieveLocaleInfo() {
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("localeCode", "KR");
		return commonDao.select("commonmodule.retrieveLocaleInfo", input);
	}

	/**
	 * ConfCode 조회
	 * @param CommonParamVO input - List CodeList
	 * @return Map<String, Object> - Code, Value
	 * @author Choe YeongHwan
	 */
	@Override
	public Map<String, Object> retrieveConfCode(CommonParamVO input) {
		// TODO Auto-generated method stub
		input.setLocaleCode("KR");
		List<HashMap<String, Object>> confList = new ArrayList<HashMap<String, Object>>();
		confList = commonDao.selectList("commonmodule.retrieveConfCode", input);

		Map<String, Object> result = new HashMap<String, Object>();
		if(0 < confList.size()){
			for(Map<String, Object> confMap : confList){
				result.put((String) confMap.get("confCode"), confMap.get("confValue"));
			}
		}
		return result;
	}

	/**
	 * 단 건 ConfCode 조회
	 * @param confCode - 조회 하려는 코드
	 * @return String result - 공통 코드 값
	 * @author Kim Chung
	 */
	public String getConfString(String confCode) {
		List<Map<String, Object>> confList = new ArrayList<Map<String, Object>>();
		String strResult = "";

		try{
			Map<String, Object> confMap = new HashMap<String, Object>();
			CommonParamVO input = new CommonParamVO();
			input.setLocaleCode( "KR" );
			input.setConfCode( confCode );

			confList = commonDao.selectList("commonmodule.retrieveConfCode", input);
			if(0 < confList.size()){
				for(Map<String, Object> tmpMap : confList){
					confMap.put((String) tmpMap.get("confCode"), tmpMap.get("confValue"));
				}
			}

			strResult = isNull(confMap, confCode);
		} catch (Exception e) {
			throw new BusinessException( e );
		}

		return strResult;
	}


	/**
	 * ModelId 기준으로 categoryId 정보를 조회
	 * @param Map<String, Object> input - modelUrlPath, localeCode, modelId
	 * @return Map<String, Object> - superCategoryId, categoryId, subCategoryId
	 * @author Park sehun
	 */
	@Override
	public Map<String, Object> retrieveCategoryModelMap(Map<String, Object> input) {
		Map<String, Object> result = null;
		try {
			result = commonDao.select("commonmodule.retrieveCategoryModelMap", input);
		} catch (Exception e) {
			throw new BusinessException( e );
		}
		return result;
	}

	/**
	 * OBS 정보 조회
	 * @param Map<String, Object> input - localeCode
	 * @return Map<String, Object> - obsInfo
	 * @author Choe YeongHwan
	 */
	@Override
	public Map<String, Object> retrieveObsInfo() {
		// TODO Auto-generated method stub
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("localeCode", "KR");
		Map<String, Object> obsInfo = commonDao.select("commonmodule.retrieveObsInfo", input);

		return obsInfo;
	}

	/**
	 * Code To String
	 * @param Map<String, Object> inputMap, String code, String type, boolean domainYn
	 * @return String - Value
	 * @author Choe YeongHwan
	 */
	@Override
	public String codeToString(Map<String, Object> inputMap, String code, boolean domainYn) {
		// TODO Auto-generated method stub
		String result = "";

		//Code Map 받아서  Code 유무, domain 사용 유무 체크후 Value 를 return 한다.
		if(inputMap.containsKey(code)){
			if(domainYn && (Objects.toString(inputMap.get(code)).indexOf("http://") == -1 || Objects.toString(inputMap.get(code)).indexOf("https://") == -1 )){
				StringBuffer strBuf = new StringBuffer();
				strBuf.append(configService.getString("serverInfo.url")).append(Objects.toString(inputMap.get(code)));
				result = strBuf.toString();
			}else{
				result = Objects.toString(inputMap.get(code));
			}
		}else{
			result = "";
		}
		return result;
	}

	/**
	 * map에서 key code의 value값을 체크하여 String으로 리턴한다.
	 * value가 null 일 경우, "" String으로 리턴
	 * @param inputMap - Map
	 * @param code - 체크 할 key값
	 * @return input
	 * @author Kim Chung
	 */
	public String isNull(Map<String, Object> inputMap, String code){
		String result = "";
		try {
			if( inputMap != null && inputMap.containsKey(code) ){
				result = Objects.toString( inputMap.get(code), "" );
			}
		} catch (Exception e) {
			throw new BusinessException( e );
		}
		return result;
	}

	/**
	 * map에서 key code의 value값을 체크하여 String으로 리턴한다.
	 * value가 null 일 경우, defaultValue로 리턴
	 * @param inputMap - Map
	 * @param code - 체크 할 key값
	 * @param defaultValue - null일 경우, 리턴 시킬 값
	 * @return input
	 * @author Kim Chung
	 */
	public String isNvl(Map<String, Object> inputMap, String code, String defaultValue){
		String result = defaultValue;
		try {
			if( inputMap != null && inputMap.containsKey(code) ){
				result = Objects.toString( inputMap.get(code), defaultValue );
				if( "".equals(result) ){
					result = defaultValue;
				}
			}
		} catch (Exception e) {
			throw new BusinessException( e );
		}
		return result;
	}

	/**
	 * [PDP] Sibling의 대표 모델 정보 조회 ( 맨 처음 노출 되는 Sibling의 default 모델 )
	 * @param SalesCodeVO.modelId - 조회 하려는 모델 ID
	 * @param SalesCodeVO.bizType - Business 구분(B2C, B2B)
	 * @param SalesCodeVO.modelStatusCode - 모델상태코드
	 * @return SalesCodeVO result - 대표 모델의 Sales Model Code 정보. 없을 경우 null을 리턴시킴
	 * @author Kim Chung
	 */
	public SalesCodeVO getDefaultSiblingModelInfo(SalesCodeVO inputMap) {
		SalesCodeVO result = null;
		inputMap.setLocaleCode( "KR" );
		try{
			List<SalesCodeVO> pList = commonDao.selectList( "commonmodule.retrieveSiblingData", inputMap );
			if( pList.size() > 0 && "SELF".equals(pList.get(0).getTarget()) ){
				result = pList.get(0);
			}
		} catch (Exception e) {
			throw new BusinessException( e );
		}
		return result;
	}

	/**
	 * [PDP] Sibling의 대표 모델 정보 조회 ( 맨 처음 노출 되는 Sibling의 default 모델 )
	 * @param HttpServletRequest request
	 * @return Boolean
	 * @author Choe Yeong Hwan
	 */
	@Override
	public Boolean getAccessTokenVelidation(HttpServletRequest request) {
		// TODO Auto-generated method stub
		//get token from session
		Object token = request.getSession().getAttribute("access_token");
		Boolean loginCheck = false;
		//if token is exist, validate token.
		if (token != null) {
			 try {

			  JWTClaimsSet jwtClaimsSet = JwtUtils.parseToken((String) token);
			  Date expiretime = jwtClaimsSet.getExpirationTime();
			  @SuppressWarnings("unused")
			  String id = jwtClaimsSet.getJWTID();
			  Date nowDate = new Date();

			  if(expiretime.compareTo(nowDate) > -1){
				  loginCheck = true;
			  }

			 } catch (Exception e) {
				 throw new BusinessException( e );
			 }
		}

		return loginCheck;
	}

	/**
	 * COM_PREFERENCE_M 데이터 조회
	 * @param strCode - 조회하려는 코드
	 * @param strDefault - 값이 없을 경우 Default 값
	 * @return String - value 값
	 * @author GP Rollout SI
	 */
//	public String getComPreference(String strCode, String strDefault){
//		String strValue = null;
//		try{
//			for (String key : WebContext.getComPreferenceMap().keySet()) {
//				if( key.equals(strCode) ){
//					ComPreference preference = WebContext.getComPreferenceMap().get(key);
//					strValue = preference.getPreferenceValue();
//				}
//			}//end for
//
//			if( strValue == null || "".equals( strValue ) ){
//				strValue = (strDefault == null? "" : strDefault);
//			}
//		} catch (Exception e) {
//			throw new BusinessException( e );
//		}
//		return strValue;
//	}

	/**
	 * Product MessageCode Info
 	 * @param
	 * @return GpBtnMessageVO
	 * @author GP Rollout SI
	 */
	public ProductMessageVO productMessageInfo(){
		ProductMessageVO bMsg = new ProductMessageVO();
		try{
			bMsg.setWhereToBuyBtnNm(msgAccessor.getMessage("component-whereToBuy", "Where to Buy"));
			bMsg.setAddToCartBtnNm(msgAccessor.getMessage("component-addToCart", "Add to Cart"));
			bMsg.setInquiryToBuyBtnNm(msgAccessor.getMessage("component-inquiryToBuy", "Inquiry to Buy"));
			bMsg.setFindTheDealerBtnNm(msgAccessor.getMessage("component-findTheDealer", "Find a Dealer"));
			bMsg.setProductSupportBtnNm(msgAccessor.getMessage("component-productSupport", "Product Support"));
			bMsg.setBtnNewLinkTitle(msgAccessor.getMessage("component-opensInANewWindow", "Opens in a new window"));
			bMsg.setBuyNowBtnNm(msgAccessor.getMessage("component-buyNow", "Buy Now"));
			bMsg.setResellerBtnNm(msgAccessor.getMessage("component-toTheAuthorizedReseller", "To the authorized reseller"));
			bMsg.setReStockAlertBtnNm(msgAccessor.getMessage("component-reStockAlert", "GET STOCK ALERT"));
			bMsg.setOutOfStockText(msgAccessor.getMessage("component-outOfStock", "Out Of Stock"));
			bMsg.setPreOrderBtnNm(msgAccessor.getMessage("PRE-ORDER", "PRE-ORDER"));
			bMsg.setAttBtnNm(msgAccessor.getMessage("ATT", "ATT"));
			bMsg.setWtbExternalBtnNm(msgAccessor.getMessage("Where_to_Buy", "Where to Buy"));

			List<Map<String, Object>> result = null;
			Map<String, Object> siblingMsgMap = null;
			try {
				Map<String, Object> input = new HashMap<String, Object>();
				input.put("localeCode", "KR");

				result = commonDao.selectList("commonmodule.retrieveSiblingTypeList", input);

				if(result != null && result.size() > 0){
					siblingMsgMap = new HashMap<String, Object>();
					for( Map<String, Object> rsultMap : result ){
						String temp = Objects.toString(rsultMap.get("siblingType"));
						String temp2 = msgAccessor.getMessage(Objects.toString(rsultMap.get("siblingType")), Objects.toString(rsultMap.get("siblingType")).toLowerCase());
						siblingMsgMap.put(temp, temp2);
					}
				}
			bMsg.setSiblingsMsg(siblingMsgMap);
			} catch (Exception e) {
				throw new BusinessException( e );
			}
		}catch(Exception e) {
			throw new BusinessException ( e );
		}
		return bMsg;
	}

	/**
	 * 해당 제품의 Support Page Url을 return한다. (없으면 Support Home URL return)
	 * @param input - 국가코드(localeCode), 제품ID(modelId)
	 * @return String - SupportLinkUrl
	 */
	public String getSupportLinkUrl(Map<String, Object> input) {
		String result = "";
		StringBuffer linkBuffer = new StringBuffer( "/KR/" );
		try{
			Map<String, Object> supportMap = commonDao.select("commonmodule.retrieveProductSupportUrl", input);
			if( supportMap != null){
				/** 01. Product Support Page */
				if( !"".equals( StringUtil.getString(supportMap, "csLinkCustModelCode")) ){
					linkBuffer.append( getConfString( "link-common_support_product" ) ); // [message] support/product
					linkBuffer.append( "/lg-" );
					linkBuffer.append( StringUtil.getString(supportMap, "csLinkCustModelCode") );
					// total link url : /us/support/product/lg-55EF9500.AUS
				}
			}else{
				/** 02. Supprot Home */
				linkBuffer.append( getConfString( "link-common_support" ) ); // [message] support
			}

			result = linkBuffer.toString();
		} catch (Exception e) {
			throw new BusinessException( e );
		}
		return result;
	}

	/**
	 * 국가별 MKT_WTB_SETTING_M 테이블의 Flag값 조회
	 * @return Map< String, Object> - localeCode : 국가코드
	 * @return Map< String, Object> - buyonlineType : Buy online 사용여부 및 종류 (none / lgcom / static / channel_ad)
	 * @return Map< String, Object> - wtbType : Where to Buy 영역 (All / Multi / None)
	 * @return Map< String, Object> - wtbUseFlag : Offline map 사용여부 및 종류 (none / lgcom / iframe_all / comcon_all / prispi_all)
	 * @author Kim Chung
	 */
	public Map< String, Object> retrieveWtbSettingUseFlagMap() {
		Map< String, Object> input = new HashMap< String, Object>();
		input.put( "localeCode", "KR" );
		Map< String, Object> result = null;
		try {
			result = commonDao.select("commonmodule.retrieveWtbSettingData", input);
		} catch (Exception e) {
			throw new BusinessException( e );
		}
		return result;
	}

	@Override
	public Map<String, Object> loginInfo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean isLogin = false;
		String name = "";

		HttpSession session = request.getSession();
		MemberInformation loginToken = (MemberInformation)session.getAttribute("MemberInformation");

		if(!StringUtil.isEmpty(loginToken)){
			isLogin = true;
			name = loginToken.getName();
		}

		result.put("isLogin", isLogin);
		result.put("name", name);


		return result;
	}

	@Override
	public Map<String, Object> getBizType(Map<String, Object> input) {
		String[] modelInfo = input.get("sku").toString().split("\\.");
		input.put("salesModelCode", modelInfo[0]);
		if(modelInfo.length > 1){
			input.put("salesSuffixCode", modelInfo[1]);
		}//with categoryId
		return commonDao.select("commonmodule.getBizType", input);
	}

	@Override
	public int retrieveCareCartCnt(Map<String, Object> input) {
		return commonDao.select("commonmodule.retrieveCareCartCnt", input);
	}


	public Map<String, Object> retrieveWishList (String type, Map<String, Object> input,JSONObject apiData){
		LOGGER.debug(">>> retrieveWishList  get start input : {}", input);
		Map<String, Object> resultMap = new LinkedHashMap<>();

		Map<String, Object> listMap = new LinkedHashMap<>();
		List<Map<String, Object>> wishList = new ArrayList<>();

		try {
			//1. 위시리스트 가져오기
			if(!apiData.isEmpty()){

				if(type.equals("list")){//위시리시트
					if(apiData.has("wishlist")){
						JSONObject wishData = apiData.getJSONObject("wishlist");
						listMap.put("wishListId",StringUtil.isNull(wishData.get("id")));

						//위시리스트 아이템 리스트
						JSONArray wishItem =  wishData.getJSONArray("items");
						if(wishItem.length() > 0){
							for (int i = 0; i < wishItem.length(); i++) {
								Map<String, Object> wishMap = new LinkedHashMap<>();

								JSONObject items = (JSONObject)wishItem.get(i);

								wishMap.put("wishListId", StringUtil.isNull(wishData.get("id")));
								wishMap.put("wishItemId", StringUtil.isNull(items.get("id")));
								wishMap.put("sku", StringUtil.isNull(items.getJSONObject("product").get("sku")));

								wishList.add(i, wishMap);
							}
						}
					}


					listMap.put("listData", wishList);

					resultMap.put("status", MKTCommonCodes.Status.RESULT_TRUE);
					resultMap.put("data", listMap);

				}else if(type.equals("add")){//위시리스트 추가
//					BTOCSITE-1663 Start
					if(apiData.has("addProductsToWishlistCustom")){
						JSONObject productsFromWishlist = apiData.getJSONObject("addProductsToWishlistCustom");
//					BTOCSITE-1663 End

						//wishMap.put("wishListId",StringUtil.isNull(wishData.get("id")));
						//위시리스트 아이템 리스트

						JSONArray userErrors = productsFromWishlist.getJSONArray("user_errors");
						if(userErrors.length() > 0){
							JSONObject userError =  userErrors.getJSONObject(0);

							LOGGER.debug(">>> retrieveWishList  userError : {}", userError.get("code"));
							if("PRODUCT_NOT_FOUND".equals(userError.get("code"))){
								resultMap.put("message", "상품을 찾을 수 없습니다.");
							} else if("UNDEFINED".equals(userError.get("code"))){
								resultMap.put("message", "품절된 상품입니다.");
							} else {

							}
							resultMap.put("status", MKTCommonCodes.Status.RESULT_FALSE);

						} else {
//							BTOCSITE-1663 Start
							JSONObject wishData = productsFromWishlist.getJSONObject("wishlists");
//							BTOCSITE-1663 End
							JSONArray wishItem =  wishData.getJSONArray("items");
							if(wishItem.length() > 0){

								for (int i = 0; i < wishItem.length(); i++) {
									Map<String, Object> wishMap = new LinkedHashMap<>();

									JSONObject items = (JSONObject)wishItem.get(i);

									String sku = StringUtil.isNull(input.get("sku"));
									if(sku.equals(StringUtil.isNull(items.getJSONObject("product").get("sku")))){

										wishMap.put("wishListId",StringUtil.isNull(wishData.get("id")));
										wishMap.put("wishItemId",StringUtil.isNull(items.get("id")));

										wishList.add(0, wishMap);

										break;

									}
								}
							}

							listMap.put("listData", wishList);

							resultMap.put("status", MKTCommonCodes.Status.RESULT_TRUE);
							resultMap.put("data", listMap);
						}
					}


				}else{//위시리스트 삭제
//					BTOCSITE-1663 Start
					if(apiData.has("removeProductsFromWishlistCustom")){
						JSONObject productsFromWishlist = apiData.getJSONObject("removeProductsFromWishlistCustom");
//					BTOCSITE-1663 End


						JSONArray userErrors = productsFromWishlist.getJSONArray("user_errors");
						if(userErrors.length() > 0){
							resultMap.put("status", MKTCommonCodes.Status.RESULT_FALSE);

						} else {

//							JSONObject wishData = productsFromWishlist.getJSONObject("wishlist");
							//wishMap.put("wishListId",StringUtil.isNull(wishData.get("id")));
							//위시리스트 아이템 리스트
//							JSONArray wishItem =  wishData.getJSONArray("items");
//							if(wishItem.length() > 0){
//								for (int i = 0; i < wishItem.length(); i++) {
//									Map<String, Object> wishMap = new LinkedHashMap<>();
//
//									JSONObject items = (JSONObject)wishItem.get(i);
//
//									String sku = StringUtil.isNull(items.getJSONObject("product").get("sku"));
//
//									if(sku.contains(".")){
//										input.put("salesModelCode", sku.split("\\.")[0]);
//										input.put("salesSufficeCode", sku.split("\\.")[1]);
//									}else{
//										input.put("salesModelCode", sku);
//									}
//
//									wishMap.put("wishListId",StringUtil.isNull(wishData.get("id")));
//									wishMap.put("wishItemId",StringUtil.isNull(items.get("id")));
//
//									wishList.add(i, wishMap);
//								}
//							}

//							listMap.put("listData", wishList);

							resultMap.put("status", MKTCommonCodes.Status.RESULT_TRUE);
//							resultMap.put("data", listMap);
						}

					}


				}

			}

		} catch (Exception e) {
			resultMap.put("status", MKTCommonCodes.Status.RESULT_FALSE);
			LOGGER.error("### retrieveWishList service Exception : " + ExceptionUtils.getStackTrace(e));
		}
		return resultMap;
	}

	@Override
	public String retrieveStdCategoryId(String modelId) {
		String cateId = commonDao.select("commonmodule.retrieveStdCategoryId", modelId);
		return cateId;
	}

	@Override
	public List<String> retrieveCareCartInfo(Map<String, Object> input) {
		List<String> result = commonDao.selectList("commonmodule.retrieveCareCartInfo", input);
		return result;
	}

	@Override
	public String retrieveCareType(String rtModelSeq) {
		return commonDao.select("commonmodule.retrieveCareType", rtModelSeq);
	}

	@Override
	public List<Map<String, Object>> retrieveOwnModel(String unifyId) {
		List<Map<String, Object>> result = commonDao.selectList("commonmodule.retrieveOwnModel", unifyId);
		return result;
	}
	
//	BTOCSITE-6509
	@Override
	public String retrieveObsLimitFlag(String modelId) {
		String obsLimitFlag = commonDao.select("commonmodule.retrieveObsLimitFlag", modelId);
		return obsLimitFlag;
	}
	
	@Override
	public String retrieveAdvanceBookingModelFlag(String modelId) {
		String obsLimitFlag = commonDao.select("commonmodule.retrieveAdvanceBookingModelFlag", modelId);
		return obsLimitFlag;
	}
	
}
