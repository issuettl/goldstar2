/*******************************************************************************
 * FILE: /java/lgcommkt/commonmodule/controller/CommonModuleController.java
 * DESC : Common Module Controller
 *
 * PROJ : LGEKR5.0
 *******************************************************************************
 *                  Modification History
 *******************************************************************************
 *
 * CsrNo.             DATE                 AUTHOR        	FUNCTION          DESCRIPTION
 * ---------------------------------------------------------------------------------
 *                    2020/09/02           김시훈                                                 Initial Release
 * BTOCSITE-751       2021/05/26           최규식                                                 BTOCSITE-751 MKT 다운타임 체크 로직 추가
 * BTOCSITE-1481      2021/06/09           최규식                                                 MKT TIMEOUT 체크로직 추가
 * BTOCSITE-1742      2021/06/16           최규식                                                 OBS 장바구니 담기와 바로구매시에 에러코드 처리 일치화
 * BTOCSITE-2137      2021/06/24           최규식                                                 로그 추가 작업 (내부 분석용) [1013]
 * BTOCSITE-2348      2021/06/25           최규식                                                 로그 추가 작업_2 (내부 분석용)
 * BTOCSITE-2792      2021/07/14           최규식                                                 라이브커머스 과유입에 따른 장바구니 비호출처리
 * BTOCSITE-2722      2021.07.22           채민희                                                 로깅 개선 작업 - SM 내부 개선 작업
 * BTOCSITE-659       2021/08/26           최규식                                                 마이컬렉션 추천 서비스로 개편
 * BTOCSITE-5233      2021/08/09           최규식                                                 마이컬렉션 - 새로운 스토리 (많이 본 스토리) 영역 리셋 건
 * BTOCSITE-6509      2021/10/18           won.lee                         스팟성 한정 수량만 판매시 기능 개발
 * BTOCSITE-8430      2021/11/23           최규식                                                 미니카트 수량 확인요청
 * BTOCSITE-8287      2021/12/21           이지혜                                                 생년월일 추가
 * BTOCSITE-10373     2021/12/30           최규식                                                 로그인 유저의 다른브라우저 사용시 OBS Cart Id 호출 처리 수정
 * BTOCSITE-11797     2022/02/03           최규식                                                 모니터링 개선건 - 비교하기 페이지에서 제품 [장바구니] 버튼 선택 오류수정- 545번
 * BTOCSITE-15206     2022/04/26           최규식                                                 [모니터링 개선건] 세션만료시 구매 장바구니 처리로직 보완 작업
 * LGECOMVIO-20       2022/05/06	       ParkSeJun      	  		 PDP UI/UX 개선
 *******************************************************************************/
package lgekormkt.commonmodule.controller;



import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lgekormkt.wishList.model.WishListParamVO;
import lgekormkt.wishList.service.WishListService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils; //BTOCSITE-2722
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor; //BTOCSITE-6509
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import devonframe.configuration.ConfigService;
import devonframe.exception.BusinessException;
import devonframe.message.MessageRefresher;
import devonframe.util.NullUtil;
import lgekorfrm.context.WebContext;
import lgekorfrm.sso.member.MemberInformation;
import lgekormkt.caresolution.service.CartService;
import lgekormkt.caresolution.service.CombineService;
import lgekormkt.common.code.MKTCommonCodes;
import lgekormkt.common.util.AccessTokenUtil;
import lgekormkt.common.util.MKTCommonUtil;
import lgekormkt.common.util.MktUtil;
import lgekormkt.common.util.ResponseUtil;
import lgekormkt.common.util.StringUtil;
import lgekormkt.commonmodule.service.BlockUrlService;
import lgekormkt.commonmodule.service.CommonModuleService;
import lgekormkt.commonmodule.service.OBSApiService;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.personalizeevents.PersonalizeEventsClient;
import software.amazon.awssdk.services.personalizeevents.model.Event;
import software.amazon.awssdk.services.personalizeevents.model.PutEventsRequest;


@Controller
@RequestMapping("/kr/mkt")
public class CommonModuleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonModuleController.class);

	public static final String JSON_RESPONSE_SUCCESS_MESSAGE ="success";
    public static final String JSON_RESPONSE_FAILURE_MESSAGE ="fail";

	@Resource(name = "CommonModuleService")
	private CommonModuleService commonModuleService;
//
//	@Resource(name = "GpCommonModuleService")
//	private GpCommonModuleService gpCommonModuleService;
//
	@Resource(name="messageSource")
	MessageSource messageSource;

	@Resource(name = "configService")
	private ConfigService configService;

	@Resource(name = "CartService")
	private CartService cartService;

	@Resource(name = "OBSApiService")
    private OBSApiService OBSApiService;

	@Resource(name = "CombineService")
    private CombineService combineService;

	@Resource(name = "BlockUrlService")
    private BlockUrlService blockUrlService;

	/*BTOCSITE-6509*/
	@Resource(name = "messageSourceAccessor")
	MessageSourceAccessor messageSourceAccessor;

	@Resource(name = "WishListService")
	private WishListService wishListService;

	/**
	 * 일별 아이디를 카운트 정보를 반영 위한 Controller Method이다.
	 * @param input - 일별 아이디를 카운트 하기 위한 정보가 담긴 Map
	 * @return ajaxView
	 * 확인사항
	 * - Type(model, promotion)에 따른 ID적용(modelId, promotionId)
	 * - setTimeZone 수정(locale util 적용이후 America/New_York 값 수정)
	 */
	@RequestMapping(value="/ajax/commonmodule/updateAuthoring", method=RequestMethod.POST)
	public void updateAuthoring(@RequestParam Map<String, Object> input, HttpServletResponse rep) {
		int result = 0;
		String message = null;
    	String status = JSON_RESPONSE_SUCCESS_MESSAGE;

    	try {
    		if( input.containsKey("id") ){
//    			if( LOCALE_CODE_US.equals(WebContext.getLocaleCd()) ){
    			result = commonModuleService.updateAuthoring(input);
//    			}else{
//    				result =  gpCommonModuleService.updateAuthoring(input);
//    			}
    		}

    		if(result == 0){
				status = JSON_RESPONSE_FAILURE_MESSAGE;
				message = "Access count cannot be updated for id.";
			}

		} catch (Exception e) {
			status = JSON_RESPONSE_FAILURE_MESSAGE;
			message = "server errer";
			throw new BusinessException ("[POST] CommonModuleController updateAuthoring REQUESTED : ", e);
		}
		ResponseUtil.sendResponse(rep, result, status, message);
	}

	/**
	 * 로그인체크에 대한 Controller Method이다.
	 * @param input - 조회할 정보가 담긴 MktFrontAccessCount
	 * @return
	 * @return String - 조회결과 화면정보
	 */
	@RequestMapping(value = "/ajax/commonmodule/loginInfo", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loginInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> input) {
		Map<String, Object> result = new HashMap<>();
//		String message = null;
		String status = JSON_RESPONSE_SUCCESS_MESSAGE;
		Map<String, Object> loginInfo = new HashMap<>();
		try {
			loginInfo = commonModuleService.loginInfo(request);
//			Map<String, Object> getloginFlag = AccessTokenUtil.getLoginFlag(request);
//			String loginFlag = getloginFlag.get("loginFlag").toString();

//			if(loginFlag.equals("R")){
//				result.put("ssoCheckUrl", "reload");
//			}
		}catch (Exception e) {
			 status = JSON_RESPONSE_FAILURE_MESSAGE;
			 throw new BusinessException ("SendResponse : ", e);
		}
		result.put("status", status);
		result.put("data", loginInfo);
		return result;
	}

	/**
	 * 회원정보에 대한 Controller Method이다.
	 * @param input - 조회할 정보가 담긴 MktFrontAccessCount
	 * @return
	 * @return String - 조회결과 화면정보
	 */
	@RequestMapping(value = "/ajax/commonmodule/memberInfo", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> memberInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> input) {
		Map<String, Object> result = new HashMap<>();
//		String message = null;
		String status = JSON_RESPONSE_SUCCESS_MESSAGE;
		Map<String, Object> memberInfo = new HashMap<>();
		try {
			MemberInformation seesionMember = (MemberInformation)request.getSession().getAttribute("MemberInformation");

			if(seesionMember!=null ){
				memberInfo.put("unifyId", StringUtil.isNull(seesionMember.getUnifyId()));
				memberInfo.put("preferStoreArea1", StringUtil.isNull(seesionMember.getPreferStoreArea1()));
				memberInfo.put("preferStoreArea2", StringUtil.isNull(seesionMember.getPreferStoreArea2()));
				memberInfo.put("preferStore", StringUtil.isNull(seesionMember.getPreferStore()));
				memberInfo.put("birthYY", StringUtil.isNull(seesionMember.getBirthYY()));
				memberInfo.put("sexCd", StringUtil.isNull(seesionMember.getSexCd()));
				memberInfo.put("region", StringUtil.isNull(seesionMember.getRegion()));
				memberInfo.put("lastUseDt", StringUtil.isNull(seesionMember.getLastUseDt()));
				memberInfo.put("dorDt", StringUtil.isNull(seesionMember.getDorDt()));
				memberInfo.put("model", StringUtil.isNull(seesionMember.getModel()));
				memberInfo.put("empNo", StringUtil.isNull(seesionMember.getEmpNo()));
				memberInfo.put("bestNo", StringUtil.isNull(seesionMember.getBestNo()));
				memberInfo.put("memberUseYn", StringUtil.isNull(seesionMember.getMemberUseYn()));
				memberInfo.put("emailYn", StringUtil.isNull(seesionMember.getEmailYn()));
				memberInfo.put("smsYn", StringUtil.isNull(seesionMember.getSmsYn()));
				memberInfo.put("appPushYn", StringUtil.isNull(seesionMember.getAppPushYn()));
				memberInfo.put("cacaoYn", StringUtil.isNull(seesionMember.getCacaoYn()));
				memberInfo.put("birthDt", StringUtil.isNull(seesionMember.getBirthDt())); //BTOCSITE-8287

				List<Map<String, Object>> productResult = commonModuleService.retrieveOwnModel(seesionMember.getUnifyId());
				String modelCode = "" ;
				String salesDt = "" ;
				String sourceGubun = "" ;
				String purChnlCode = "" ;
				String ownerFlag = "" ;

				for(int i=0;i<productResult.size();i++){
					modelCode += StringUtil.isNull(productResult.get(i).get("modelCode"))+"/";
					salesDt += StringUtil.isNull(productResult.get(i).get("salesDt"))+"/";
					sourceGubun += StringUtil.isNull(productResult.get(i).get("sourceGubun"))+"/";
					purChnlCode += StringUtil.isNull(productResult.get(i).get("purChnlCode"))+"/";
					ownerFlag += StringUtil.isNull(productResult.get(i).get("ownerFlag"))+"/";
				}

				if(modelCode.length() > 0){
					modelCode = modelCode.substring(0, modelCode.length()-1);
				}
				if(salesDt.length() > 0){
					salesDt = salesDt.substring(0, salesDt.length()-1);
				}
				if(sourceGubun.length() > 0){
					sourceGubun = sourceGubun.substring(0, sourceGubun.length()-1);
				}
				if(purChnlCode.length() > 0){
					purChnlCode = purChnlCode.substring(0, purChnlCode.length()-1);
				}
				if(ownerFlag.length() > 0){
					ownerFlag = ownerFlag.substring(0, ownerFlag.length()-1);
				}
				memberInfo.put("modelCode",StringUtil.isNull(modelCode));
				memberInfo.put("salesDt",StringUtil.isNull(salesDt));
				memberInfo.put("sourceGubun",StringUtil.isNull(sourceGubun));
				memberInfo.put("purChnlCode",StringUtil.isNull(purChnlCode));
				memberInfo.put("ownerFlag", StringUtil.isNull(ownerFlag));
				result.put("memberInfo", memberInfo);
			}
			else result.put("memberInfo", null);

		}catch (Exception e) {
			 status = JSON_RESPONSE_FAILURE_MESSAGE;
			 throw new BusinessException ("SendResponse : ", e);
		}
		result.put("status", status);
		result.put("data", memberInfo);
		return result;
	}


	/**
	 * 로그인체크에 대한 Controller Method이다.
	 * @param input - 조회할 정보가 담긴 MktFrontAccessCount
	 * @return String - 조회결과 화면정보
	 */
	@RequestMapping(value = "/ajax/CommonModule/messageRefresh", method=RequestMethod.GET)
	public void messageRefresh(HttpServletResponse rep) {

    	MessageRefresher.refreshIncludingAncestors(messageSource);
    	ResponseUtil.sendResponse(rep, null);
	}

	/**
	 * 로그인체크에 대한 Controller Method이다.
	 * @param input - 조회할 정보가 담긴 MktFrontAccessCount
	 * @return String - 조회결과 화면정보
	 */
	@RequestMapping(value = "/ajax/CommonModule/webContextInfo", method=RequestMethod.GET)
	public void webContextInfo(HttpServletResponse rep) {

		Map<String, Object> result = new HashMap<String, Object>();
		/*result.put("countryCd", WebContext.getCountryCd());
		result.put("languageCd", WebContext.getLanguageCd());
		result.put("localeCd", WebContext.getLocaleCd());
		result.put("regionName", WebContext.getLGLocale().getRegionName());
		result.put("dateFomat", WebContext.getLGLocale().getDateFormatInfo());
		result.put("dateFomat", WebContext.getLGLocale().getTimezoneName());*/

    	ResponseUtil.sendResponse(rep, result);
	}

	/**
	 * 로그인체크에 대한 Controller Method이다.
	 * @param input - 조회할 정보가 담긴 MktFrontAccessCount
	 * @return String - 조회결과 화면정보
	 */
	@RequestMapping(value = "/api/CommonModule/messageReturn", method=RequestMethod.POST)
	public void messageReturn(HttpServletRequest request, HttpServletResponse rep, @RequestParam Map<String, Object> input) {

    	ResponseUtil.sendResponse(rep, input);
	}

	/*버튼*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/commonModule/addCart.lgajax", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public @ResponseBody Map<String, Object> addCartProduct(@RequestParam Map<String, Object> input
			, HttpServletRequest req, HttpServletResponse res) throws Exception{
		Map<String, Object> obsReturnData = new HashMap<>();
		Map<String, Object> careReturnData = new HashMap<>();

		Map<String, Object> returnData = new HashMap<>();
		Map<String, Object> resultData = new HashMap<>();

//		BTOCSITE-2348 Start
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
//		BTOCSITE-2348 END


		/*
		 * DISPOSABLE,PRODUCT = obs cart
		 * CARESHIP,CARESOLUTION = 내부 cart
		*/

//		typeFlag P,C
		if(input.get("typeFlag").equals("P")){
//			BTOCSITE-751 START
			// downtime
	    	Map<String, Object> downTimeResult = blockUrlService.retrieveDownTimeInfo("CSB10");
	    	if("Y".equals(downTimeResult.get("downtimeChk"))){
	    		Map<String,Object> dwSystemInfo = (Map<String, Object>) downTimeResult.get("dwSystemInfo");
	    		returnData.put("message", "시스템 점검중입니다.<BR>점검시간 : " + dwSystemInfo.get("downTime") + "<BR> ~ " + dwSystemInfo.get("openTime"));
				returnData.put("status" , JSON_RESPONSE_FAILURE_MESSAGE);
				return returnData;
	    	}
//	    	BTOCSITE-751 END

	    	/*BTOCSITE-6509 Start*/
	    	String obsLimitFlag = commonModuleService.retrieveObsLimitFlag(input.get("id").toString());
	    	String advanceBookingModelFlag = commonModuleService.retrieveAdvanceBookingModelFlag(input.get("id").toString());

			if(advanceBookingModelFlag.equals("Y")){
				returnData.put("message", messageSourceAccessor.getMessage("advance_booking_model_alert_msg","본 제품은 상세페이지에서 '사전예약'만 가능합니다."));
				returnData.put("status" , JSON_RESPONSE_FAILURE_MESSAGE);
				return returnData;
			}
	    	if(obsLimitFlag.equals("Y")){
				returnData.put("message", "본 제품은 상세페이지에서 '바로구매'만 가능합니다.");
	    		returnData.put("status" , JSON_RESPONSE_FAILURE_MESSAGE);
				return returnData;
			}
			/*BTOCSITE-6509 End*/

			obsReturnData = obsCart(input, req, res);
		}else{
			careReturnData = careSolutionCart(input, input.get("id").toString(), req, res);
		}
	/*	Map<String, Object> distinguishData = commonModuleService.getBizType(input);
		if(distinguishData.get("bizType").equals("PRODUCT") || distinguishData.get("bizType").equals("DISPOSABLE")){
			obsReturnData = obsCart(input, req, res);
		}else{
			careReturnData = careSolutionCart(input, distinguishData.get("modelId").toString(), req, res);
		}*/


		boolean obsFlag = true;
		boolean careFlag = true;

		try{
			if(!obsReturnData.isEmpty()){
				if(obsReturnData.get("success") != null){
					if(!obsReturnData.get("success").equals("Y")){
						obsFlag = false;
						if(obsReturnData.get("code") != null && !obsReturnData.get("code").equals("")){
							String code = obsReturnData.get("code").toString();
//							BTOCSITE-1742 Start
							if(code.equals("INSUFFICIENT_STOCK")){
								returnData.put("message", "주문 가능한 수량이 초과되었습니다.");
								returnData.put("status" , JSON_RESPONSE_FAILURE_MESSAGE);
								return returnData;
							}else if(code.equals("NOT_SALABLE")){
								returnData.put("message", "주문 가능한 상태가 아닙니다.");
								returnData.put("status" , JSON_RESPONSE_FAILURE_MESSAGE);
								return returnData;
							}else if(code.equals("PRODUCT_NOT_FOUND")){
								returnData.put("message", "선택하신 제품을 장바구니에 담을 수 없습니다.");
								returnData.put("status" , JSON_RESPONSE_FAILURE_MESSAGE);
								return returnData;
							}else if(code.equals("CART_ID_INVALID")){
								returnData.put("message", "선택하신 제품을 장바구니에 담을 수 없습니다.<br/>새로고침 후 다시시도 해 주세요.");
								returnData.put("status" , JSON_RESPONSE_FAILURE_MESSAGE);
								return returnData;
                            }else if(code.equals("BUYNOW_API_CALL")) {
								returnData.put("message", "바로구매만 가능한 상품입니다.");
								returnData.put("status", JSON_RESPONSE_FAILURE_MESSAGE);
								return returnData;
							}else if(code.equals("OVER_MAXQTY")) {		/* BTOCSITE-22033 */
								returnData.put("message", "주문 가능한 수량이 초과되었습니다.");
								returnData.put("status", JSON_RESPONSE_FAILURE_MESSAGE);
								return returnData;
							}else {
								returnData.put("message", "선택하신 제품을 장바구니에 담을 수 없습니다.<br/>새로고침 후 다시시도 해 주세요.");
								returnData.put("status", JSON_RESPONSE_FAILURE_MESSAGE);
								return returnData;
							}
//							BTOCSITE-1742 End
						}
					}
				}
			}

			if(!careReturnData.isEmpty()){
				if(careReturnData.get("success") != null){
					if(!careReturnData.get("success").equals("Y")){
						careFlag = false;
					}
				}
			}

//			if(obsReturnData.get("sizeOverFlag") != null){
//				returnData.put("message", "9개 이하만 넣었다.");
//				returnData.put("status" , JSON_RESPONSE_FAILURE_MESSAGE);
//				return returnData;
//			}

			if(obsReturnData.get("productSizeOver") != null){
				returnData.put("message", "동일한 제품을 장바구니에 9개 이상 담을 수 없습니다. 수량을 확인해주세요.");
				returnData.put("status" , JSON_RESPONSE_FAILURE_MESSAGE);
				return returnData;
			}

			if(careReturnData.get("careCookieOverSize") != null){
				returnData.put("message", "케어솔루션/케어십은 총 7개까지 이용가능합니다.");
				returnData.put("status" , JSON_RESPONSE_FAILURE_MESSAGE);
				return returnData;
			}

			if(careReturnData.get("waterOverSize") != null){
				returnData.put("message", "케어솔루션 정수기/공기청정기 제품의 경우 최대 3대, 그 외 제품의 경우 최대 2대로 제한되며 총 7개를 초과하여 선택하실 수 없습니다.");
		    	returnData.put("status", JSON_RESPONSE_FAILURE_MESSAGE);
				return returnData;
			}

			if(obsFlag && careFlag){
				int obsCnt = 0;	int careCnt = 0;
//				BTOCSITE-2792 Start
//				Map<String, Object> ret = returnCartId(req, res);
				String cartId = "";


				if(input.get("typeFlag").equals("P")){
					cartId = StringUtil.envl(obsReturnData.get("cartId"), "");
				}else{
					Map<String, Object> ret = returnCartId(req, res);
					cartId = StringUtil.envl(ret.get("cartId"), "");
				}


//				BTOCSITE-2348 Start
				LOGGER.debug("### [CommonModuleController addCartProduct DeBug Log 001 ] " + df.format(cal.getTime()) + " : cartId : " + cartId);
//				BTOCSITE-2348 END

				String url   = configService.getString("obsAPIUrl");
				String addQuery = "{cart(cart_id:\""+cartId+"\") { items {product{sku}}total_quantity}}";
				JSONObject cartCnt = OBSApiService.obsApiCall(req, url, addQuery);
//				BTOCSITE-2792 END

//				BTOCSITE-2348 Start
				LOGGER.debug("### [CommonModuleController addCartProduct DeBug Log 002 ] " + df.format(cal.getTime()) + " : cartCnt : " + cartCnt);
//				BTOCSITE-2348 END


//              BTOCSITE-1481 START
              if(cartCnt.has("exception")){
                  JSONObject exceptionData = cartCnt.getJSONObject("exception");
                  returnData.putAll(MktUtil.systemErrorAlert(StringUtil.envl(exceptionData.get("message"), "")));
                  return returnData;
              }
//              BTOCSITE-1481 END


//				BTOCSITE-2348 Start
//				obsCnt = cartCnt.getJSONObject("data").getJSONObject("cart").getInt("total_quantity");
				if (!cartCnt.isEmpty() && !cartCnt.has("errors")) {
					if (cartCnt.has("data")) {
						JSONObject dataObj = cartCnt.getJSONObject("data");
						if (dataObj.has("cart")) {
							JSONObject cartObj = dataObj.getJSONObject("cart");
							if (cartObj.has("total_quantity")) {
								obsCnt = cartObj.getInt("total_quantity");
							}
						}
					}
				}
//				BTOCSITE-2348 END


//				Map<String, Object> cookieVal = cartService.getCookieCart(req);
//				List<Map<String, Object>> cookieDataList = (List<Map<String, Object>>) cookieVal.get("cookieList");
//				System.out.println(careReturnData.get("cookieCnt"));
				if(careReturnData.get("cookieCnt") != null){
					if(Integer.parseInt(careReturnData.get("cookieCnt").toString()) > 7){
						returnData.put("status" , JSON_RESPONSE_FAILURE_MESSAGE);
						returnData.putAll(MktUtil.systemAlert("케어솔루션/케어십은 총 7개까지 이용가능합니다."));
						return returnData;
					}
					careCnt = Integer.parseInt(careReturnData.get("cookieCnt").toString());
				}else{
					Map<String, Object> login = AccessTokenUtil.getLoginFlag(req);
					if((boolean)login.get("isLogin")){
						input.put("unifyId", ((MemberInformation)login.get("memberInformation")).getUnifyId());
						careCnt = commonModuleService.retrieveCareCartCnt(input);
					}else{
						Map<String, Object> cookieVal = cartService.getCookieCart(req);
						List<Map<String, Object>> cookieDataList = (List<Map<String, Object>>) cookieVal.get("cookieList");
						if(cookieDataList.size() > 7){
							returnData.put("status" , JSON_RESPONSE_FAILURE_MESSAGE);
							returnData.putAll(MktUtil.systemAlert("케어솔루션/케어십은 총 7개까지 이용가능합니다."));
							return returnData;
						}
						careCnt = cookieDataList.size();
					}
				}

				String cartUrl = "";
				if(obsCnt == 0 && careCnt == 0){
					cartUrl = "/add-to-cart/rental-care-solution";
				}else if(obsCnt > 0 && careCnt == 0){
					cartUrl = "/shop/lgobscart/cart/quote";
				}else if(obsCnt == 0 && careCnt > 0){
					cartUrl = "/add-to-cart/rental-care-solution";
				}else{
					cartUrl = "/shop/lgobscart/cart/quote";
				}
				resultData.put("cartCnt", obsCnt+careCnt);
				resultData.put("cartUrl", cartUrl);
				resultData.put("success", "Y");
				returnData.put("status", JSON_RESPONSE_SUCCESS_MESSAGE);
				returnData.put("data"   , resultData);
			}else if(obsReturnData.get("ssoCheckUrl") != null || careReturnData.get("ssoCheckUrl") != null){
				returnData.put("status" , JSON_RESPONSE_FAILURE_MESSAGE);
				returnData.put("ssoCheckUrl", "reload");
			}else if(obsReturnData.get("ssoCheckUrl") != null || careReturnData.get("ssoCheckUrl") != null){
				returnData.putAll(MKTCommonUtil.systemErrorAlert());
			}else{
				if(obsReturnData.get("message") != null){

					returnData.putAll(MKTCommonUtil.systemErrorAlert(StringUtil.envl(obsReturnData.get("message"), "")));
				} else {

					returnData.putAll(MKTCommonUtil.systemErrorAlert());
				}

			}
		}catch (Exception e) {
			returnData.putAll(MKTCommonUtil.systemErrorAlert());
			throw new BusinessException("[POST] MKT addCart Exception : " , e);
		}
		return returnData;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> careSolutionCart(Map<String, Object> input, String modelId
			, HttpServletRequest req, HttpServletResponse res) throws Exception{
		Map<String, Object> resultData = new HashMap<>();
		try{
			String puriCategoryId = combineService.standardCategoryId("정수기");
			String airCategoryId = combineService.standardCategoryId("공기청정기");
//			int waterMaxCnt = 3; int etcMaxCnt = 2;
			Map<String, Object> login = AccessTokenUtil.getLoginFlag(req);

			Map<String, Object> cookieVal = cartService.getCookieCart(req);
			List<Map<String, Object>> cookieDataList = (List<Map<String, Object>>) cookieVal.get("cookieList");
			if((boolean)login.get("isLogin")){
				input.put("unifyId", ((MemberInformation)login.get("memberInformation")).getUnifyId());
				input.put("createId", ((MemberInformation)login.get("memberInformation")).getMobileNo());
				if(cookieDataList.size() > 0){
		    		for(Map<String, Object> map : cookieDataList){
		        		input.putAll(map);
		        		cartService.insertCookieCart(input);
		    		}
	    		}else{
	    			input.put("unifyId", ((MemberInformation)login.get("memberInformation")).getUnifyId());
	    			List<String> careCartCate = commonModuleService.retrieveCareCartInfo(input);

//	    			int waterCnt = 0;
	    			Map<String, Integer> categoryCntMap = new HashMap<>();
	    			for(int i=0;i<careCartCate.size();i++){
	    				if(careCartCate.get(i).equals(puriCategoryId)){
	    					int categoryCnt = Integer.parseInt(NullUtil.nvl(categoryCntMap.get(careCartCate.get(i)), "0")) + 1;
							categoryCntMap.put(careCartCate.get(i), categoryCnt);
	    				}else if(!careCartCate.get(i).equals(puriCategoryId)){
	    					if(categoryCntMap.get(careCartCate.get(i)) != null){
								int categoryCnt = Integer.parseInt(categoryCntMap.get(careCartCate.get(i)).toString()) + 1;
								categoryCntMap.put(careCartCate.get(i), categoryCnt);
							}else{
								categoryCntMap.put(careCartCate.get(i), 1);
							}
	    				}
	    			}

	    			String nowCateId = commonModuleService.retrieveStdCategoryId(modelId);
					if(categoryCntMap.get(nowCateId) != null){
						if(nowCateId.equals(puriCategoryId)||nowCateId.equals(airCategoryId)){//BTOCSITE-17227
							if(categoryCntMap.get(nowCateId) > 2){
								resultData.put("waterOverSize","over");
								return resultData;
							}
						}else{
							if(categoryCntMap.get(nowCateId) > 1){
								resultData.put("waterOverSize","over");
								return resultData;
							}
						}
					}

	    			int cookieCnt = commonModuleService.retrieveCareCartCnt(input);
	    			if(cookieCnt > 6){
	    				resultData.put("careCookieOverSize","over");
						return resultData;
	    			}
	    			Map<String, Object> paramMap = new HashMap<>();
	    			paramMap.put("unifyId", input.get("unifyId"));
	    			paramMap.put("modelId", input.get("id"));
	    			paramMap.put("modelQty", 1);
	    			paramMap.put("rtModelSeq", input.get("rtSeq"));
	    			paramMap.put("createId", ((MemberInformation)login.get("memberInformation")).getMobileNo());
//	    			BTOCSITE-11797 Start
	    			if(input.get("requireCare") != null && !"".equals(StringUtil.envl(input.get("requireCare"), ""))){
	    				if(input.get("requireCare").equals("true")){
	    					paramMap.put("careType", "C");
	    				}else{
	    					paramMap.put("careType", "R");
	    				}
	    			}else{
	    				paramMap.put("careType", "R");
	    			}
	    			if(input.get("rtSeq") != null && !"".equals(StringUtil.envl(input.get("rtSeq"), ""))){
	    				String careType = commonModuleService.retrieveCareType(input.get("rtSeq").toString());
	    				paramMap.put("careType", careType);
	    			}
//	    			BTOCSITE-11797 End
	    			cartService.insertCookieCart(paramMap);

	    		}
				Cookie[] cookies = req.getCookies();
				if (cookies != null && cookies.length > 0) {
			    	for(Cookie c : cookies){
			    		if("cartInfo".equals(c.getName()) && !StringUtil.isEmpty(c.getValue())){
			    		c.setMaxAge(0);
			    		c.setValue(null);
			    		c.setPath("/");
			    		String domain = configService.getString("serverInfo.domain");
			        	c.setDomain(domain);
		                res.addCookie(c);
			    		}
			    	}
		    	}

				//careCart cnt session
				HttpSession session = WebContext.getSession();
				session.removeAttribute("careCnt");

				List<String> userCartList = cartService.retrieveUserCartList(input);
				resultData.put("success", "Y");
				resultData.put("cookieCnt", userCartList.size());
			}else{
				String newCookie = "";
				String careType = "";
				if(input.get("requireCare") != null){
					if(input.get("requireCare").equals("true")){
						careType = "C";
					}else{
						careType = "R";
					}
				}else{
					careType = "R";
				}
				if(input.get("rtSeq") != null){
    				careType = commonModuleService.retrieveCareType(input.get("rtSeq").toString());
    			}
				if(cookieDataList.size() > 0){
					if(cookieDataList.size() > 6){
						resultData.put("careCookieOverSize","over");
	    				return resultData;
					}else{
						Map<String, Integer> categoryCntMap = new HashMap<>();
						for(int i=0;i<cookieDataList.size();i++){
							String id = cookieDataList.get(i).get("modelId").toString();
							String cateId = commonModuleService.retrieveStdCategoryId(id);
							if(puriCategoryId.equals(cateId)){
								int categoryCnt = Integer.parseInt(NullUtil.nvl(categoryCntMap.get(cateId), "0")) + 1;
								categoryCntMap.put(cateId, categoryCnt);
							}else if(!puriCategoryId.equals(cateId) && categoryCntMap.get(cateId) != null){
								int categoryCnt = Integer.parseInt(categoryCntMap.get(cateId).toString()) + 1;
								categoryCntMap.put(cateId, categoryCnt);
							}else{
								categoryCntMap.put(cateId, 1);
							}
						}
						String nowCateId = commonModuleService.retrieveStdCategoryId(modelId);
						if(categoryCntMap.get(nowCateId) != null){
							if(nowCateId.equals(puriCategoryId)||nowCateId.equals(airCategoryId)){
								if(categoryCntMap.get(nowCateId) > 2){
									resultData.put("waterOverSize","over");
									return resultData;
								}
							}else{
								if(categoryCntMap.get(nowCateId) > 1){
									resultData.put("waterOverSize","over");
									return resultData;
								}
							}
						}
					}


					newCookie += cookieVal.get("cookieData")+"^^"+(cookieDataList.size()+1)+"@@"+modelId+"@@1@@"+input.get("rtSeq")+"@@"+careType;
				}else{
					newCookie += "1@@"+modelId+"@@1@@"+input.get("rtSeq")+"@@"+careType;
				}
				Cookie cookie = new Cookie("cartInfo", newCookie);
				String domain = configService.getString("serverInfo.domain");
				cookie.setDomain(domain);
				cookie.setPath("/");
				res.addCookie(cookie);

				HttpSession session = WebContext.getSession();
				String[] cookieLen = newCookie.split("//^//^");

				session.setAttribute("careCnt", cookieLen.length);

				resultData.put("success", "Y");
				resultData.put("cookieCnt", cookieDataList.size()+1);
			}
		}catch (Exception e) {
			resultData.put("success", "N");
		}
		return resultData;
	}

	private Map<String, Object> obsCart(Map<String, Object> input, HttpServletRequest req, HttpServletResponse res) throws Exception {

//		BTOCSITE-2348 Start
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
//		BTOCSITE-2348 END


		Map<String, Object> resultData = new HashMap<>();
		Map<String, Object> ret = returnCartId(req, res);

//		BTOCSITE-2348 Start
		LOGGER.debug("### [CommonModuleController obsCart DeBug Log 001 ] " + df.format(cal.getTime()) + " : ret : " + ret);
//		BTOCSITE-2348 END


		if(ret.get("ssoCheckUrl") != null){
			resultData.put("ssoCheckUrl", "reload");
			resultData.put("success", "N");
			return resultData;
		}

		String url   = configService.getString("obsAPIUrl");
		String addQuery = "";
		JSONObject jsonCart = null;

		/* sku = {sku1|2,sku2|1} */
		String quantity = "1";
		if(input.get("sku").toString().split(",").length > 1){
			String[] arr = input.get("sku").toString().split(",");
			boolean resultFlag = true;
			for(int i=0;i<arr.length;i++){
				String[] arr2 = arr[i].split("\\|");

				/*Map<String, Integer> categoryCntMap = new HashMap<>();
				String nowSku = arr2[0];
				String chkQue = "{cart(cart_id:\""+ret.get("cartId")+"\") { items {product{sku}quantity}}}";
				JSONObject cnt = OBSApiService.obsApiCall(req, url, chkQue);
				JSONArray compareArr = cnt.getJSONObject("data").getJSONObject("cart").getJSONArray("items");

				for(int j=0;j<compareArr.length();j++){
					JSONObject compareJson =  (JSONObject) compareArr.get(j);
					categoryCntMap.put(compareJson.getJSONObject("product").get("sku").toString(), Integer.parseInt(StringUtil.envl(compareJson.get("quantity"), "0")));
				}

				if(!categoryCntMap.isEmpty()){
					if(categoryCntMap.get(nowSku) != null){
						int cntCheck = Integer.parseInt(categoryCntMap.get(nowSku).toString()) + Integer.parseInt(arr2[1]);
						if(cntCheck > 9){
							resultData.put("productSizeOver","over");
							return resultData;
						}
					}
				}*/

				addQuery = "mutation {addSimpleProductsToCart(input: {cart_id: \"" + ret.get("cartId")
				+ "\" cart_items: [{data: {quantity: "+arr2[1]+" sku: \"" + arr2[0] + "\" }}]}){cart {items {id product {sku } quantity}}}}";

				LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^obsCart^^targetURL 1 ^^" + url); //BTOCSITE-2722
				LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^obsCart^^inputParam 1 ^^" + addQuery); //BTOCSITE-2722

				jsonCart = OBSApiService.obsApiCall(req, url, addQuery);

				LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^obsCart^^returnData 1 ^^" + jsonCart); //BTOCSITE-2722

//				BTOCSITE-2348 Start
				LOGGER.debug("### [CommonModuleController obsCart DeBug Log 002 ] " + df.format(cal.getTime()) + " : jsonCart : " + jsonCart);
//				BTOCSITE-2348 END


//              BTOCSITE-1481 START
              if(jsonCart.has("exception")){
                  JSONObject exceptionData = jsonCart.getJSONObject("exception");
                  resultData.put("success", "N");
                  resultData.put("message", StringUtil.envl(exceptionData.get("message"), ""));
                  resultData.put("code", "API_EXCEPTION");
                  return resultData;
              }
//              BTOCSITE-1481 END

				if(jsonCart.has("errors")){
					resultData.put("success", "N");
					JSONArray jArr = (JSONArray) jsonCart.get("errors");
					JSONObject error = (JSONObject) jArr.get(0);
					String code = "";
					if(error.get("code") != null){
						code = error.get("code").toString();
					}
					//CART_ID_INVALID : 구매(바로구매x)후에 카트id 초기화 된 후 다시 카트 담을때 초기화 된 id를 다시 새로고침
					if(code.equals("CART_ID_INVALID")){
						String idQuery = "mutation{createEmptyCart}";

						LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^obsCart^^targetURL 2 ^^" + url); //BTOCSITE-2722
						LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^obsCart^^inputParam 2 ^^" + addQuery); //BTOCSITE-2722

						JSONObject jsonObj = OBSApiService.obsApiCall(req, url, idQuery);

						LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^obsCart^^returnData 2 ^^" + jsonObj); //BTOCSITE-2722

//						BTOCSITE-2348 Start
						LOGGER.debug("### [CommonModuleController obsCart DeBug Log 003 ] " + df.format(cal.getTime()) + " : jsonObj : " + jsonObj);
//						BTOCSITE-2348 END


//		              BTOCSITE-1481 START
			              if(jsonCart.has("exception")){
			                  JSONObject exceptionData = jsonCart.getJSONObject("exception");
			                  resultData.put("success", "N");
			                  resultData.put("message", StringUtil.envl(exceptionData.get("message"), ""));
			                  resultData.put("code", "API_EXCEPTION");
			                  return resultData;
			              }
//			              BTOCSITE-1481 END


						if(jsonObj.has("data")){
							JSONObject apiData = jsonObj.getJSONObject("data");
							String visitorCartId = apiData.getString("createEmptyCart");
							System.out.println(visitorCartId);
							Cookie[] cookies = req.getCookies();
							for(Cookie c : cookies){
					    		if("OBS_CARTID".equals(c.getName())){
						    		c.setValue(visitorCartId);
						    		c.setPath("/");
						        	c.setDomain(configService.getString("serverInfo.domain"));
						            res.addCookie(c);
						        	LOGGER.debug(">>> reset cookie name : " + c.getName() + " value:" +  c.getValue());
					    		}
					    	}
							addQuery = "mutation {addSimpleProductsToCart(input: {cart_id: \"" + visitorCartId
							+ "\" cart_items: [{data: {quantity: "+arr2[1]+" sku: \"" + arr2[0] + "\" }}]}){cart {items {id product {sku } quantity}}}}";

							LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^obsCart^^targetURL 3 ^^" + url); //BTOCSITE-2722
							LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^obsCart^^inputParam 3 ^^" + addQuery); //BTOCSITE-2722

							jsonCart = OBSApiService.obsApiCall(req, url, addQuery);

							LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^obsCart^^returnData 3 ^^" + jsonCart); //BTOCSITE-2722

//							BTOCSITE-2348 Start
							LOGGER.debug("### [CommonModuleController obsCart DeBug Log 004 ] " + df.format(cal.getTime()) + " : jsonCart : " + jsonCart);
//							BTOCSITE-2348 END



//			              BTOCSITE-1481 START
							if(jsonCart.has("exception")){
								JSONObject exceptionData = jsonCart.getJSONObject("exception");
								resultData.put("success", "N");
								resultData.put("message", StringUtil.envl(exceptionData.get("message"), ""));
								resultData.put("code", "API_EXCEPTION");
								return resultData;
							}
//				              BTOCSITE-1481 END


						}
						if(!jsonCart.has("errors")){
							resultData.put("success", "Y");
						}else{
							resultData.put("success", "N");
							JSONArray jArr2 = (JSONArray) jsonCart.get("errors");
							JSONObject error2 = (JSONObject) jArr2.get(0);
							String code2 = "";
							if(error2.get("code") != null){
								code2 = error2.get("code").toString();
							}
							resultData.put("code", code2);
							resultData.put("success", "N");
						}
					}else{
						resultData.put("success", "N");
						resultData.put("code", code);
						resultFlag = false;
						break;
					}
				}
			}
			if(resultFlag){
				resultData.put("success", "Y");
			}
		}else{
			if(input.get("sku").toString().split("\\|").length > 1){
				String[] arr = input.get("sku").toString().split("\\|");
				input.put("sku", arr[0]);
				quantity = arr[1];
			}
//			quantity = "5";
			/*Map<String, Integer> categoryCntMap = new HashMap<>();
			String nowSku = input.get("sku").toString();
			String chkQue = "{cart(cart_id:\""+ret.get("cartId")+"\") { items {product{sku}quantity}}}";
			JSONObject cnt = OBSApiService.obsApiCall(req, url, chkQue);
			JSONArray compareArr = cnt.getJSONObject("data").getJSONObject("cart").getJSONArray("items");

			for(int i=0;i<compareArr.length();i++){
				JSONObject compareJson =  (JSONObject) compareArr.get(i);
				categoryCntMap.put(compareJson.getJSONObject("product").get("sku").toString(), Integer.parseInt(StringUtil.envl(compareJson.get("quantity"), "0")));
			}

			if(Integer.parseInt(quantity) > 9){
				resultData.put("productSizeOver","over");
				return resultData;
			}
//			boolean sizeOverFlag = true;
			if(!categoryCntMap.isEmpty()){
				if(categoryCntMap.get(nowSku) != null){
					int cntCheck = Integer.parseInt(categoryCntMap.get(nowSku).toString()) + Integer.parseInt(quantity);
					if(cntCheck > 9){
						resultData.put("productSizeOver","over");
						return resultData;
					}
				}
			}*/


			addQuery = "mutation {addSimpleProductsToCart(input: {cart_id: \"" + ret.get("cartId")
			+ "\" cart_items: [{data: {quantity: "+quantity+" sku: \"" + input.get("sku") + "\" }}]}){cart {items {id product {sku } quantity}}}}";

			LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^obsCart^^targetURL 4 ^^" + url); //BTOCSITE-2722
			LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^obsCart^^inputParam 4 ^^" + addQuery); //BTOCSITE-2722

			jsonCart = OBSApiService.obsApiCall(req, url, addQuery);

			LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^obsCart^^returnData 4 ^^" + jsonCart); //BTOCSITE-2722

//			BTOCSITE-2348 Start
			LOGGER.debug("### [CommonModuleController obsCart DeBug Log 005 ] " + df.format(cal.getTime()) + " : jsonCart : " + jsonCart);
//			BTOCSITE-2348 END


//          BTOCSITE-1481 START
			if(jsonCart.has("exception")){
				JSONObject exceptionData = jsonCart.getJSONObject("exception");
				resultData.put("success", "N");
				resultData.put("message", StringUtil.envl(exceptionData.get("message"), ""));
				resultData.put("code", "API_EXCEPTION");
				return resultData;
			}
//          BTOCSITE-1481 END



			if(!jsonCart.has("errors")){
				resultData.put("success", "Y");

//				BTOCSITE-2792 Start
				resultData.put("cartId", ret.get("cartId"));
//				BTOCSITE-2792 END

			}else{
				resultData.put("success", "N");
				JSONArray jArr = (JSONArray) jsonCart.get("errors");
				JSONObject error = (JSONObject) jArr.get(0);
				String code = "";
				if(error.get("code") != null){
					code = error.get("code").toString();
				}
				if(code.equals("CART_ID_INVALID")){
					String idQuery = "mutation{createEmptyCart}";

					LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^obsCart^^targetURL 5 ^^" + url); //BTOCSITE-2722
					LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^obsCart^^inputParam 5 ^^" + addQuery); //BTOCSITE-2722

					JSONObject jsonObj = OBSApiService.obsApiCall(req, url, idQuery);

					LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^obsCart^^returnData 5 ^^" + jsonObj); //BTOCSITE-2722

//					BTOCSITE-2348 Start
					LOGGER.debug("### [CommonModuleController obsCart DeBug Log 006 ] " + df.format(cal.getTime()) + " : jsonObj : " + jsonObj);
//					BTOCSITE-2348 END


//	              BTOCSITE-1481 START
					if(jsonObj.has("exception")){
						JSONObject exceptionData = jsonObj.getJSONObject("exception");
						resultData.put("success", "N");
						resultData.put("message", StringUtil.envl(exceptionData.get("message"), ""));
						resultData.put("code", "API_EXCEPTION");
						return resultData;
					}
//		              BTOCSITE-1481 END


					if(jsonObj.has("data")){
						JSONObject apiData = jsonObj.getJSONObject("data");
						String visitorCartId = apiData.getString("createEmptyCart");
						System.out.println(visitorCartId);
						Cookie[] cookies = req.getCookies();
						for(Cookie c : cookies){
				    		if("OBS_CARTID".equals(c.getName())){
					    		c.setValue(visitorCartId);
					    		c.setPath("/");
					        	c.setDomain(configService.getString("serverInfo.domain"));
					            res.addCookie(c);
					        	LOGGER.debug(">>> reset cookie name : " + c.getName() + " value:" +  c.getValue());
				    		}
				    	}
						addQuery = "mutation {addSimpleProductsToCart(input: {cart_id: \"" + visitorCartId
						+ "\" cart_items: [{data: {quantity: "+quantity+" sku: \"" + input.get("sku") + "\" }}]}){cart {items {id product {sku } quantity}}}}";

						LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^obsCart^^targetURL 6 ^^" + url); //BTOCSITE-2722
						LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^obsCart^^inputParam 6 ^^" + addQuery); //BTOCSITE-2722

						jsonCart = OBSApiService.obsApiCall(req, url, addQuery);

						LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^obsCart^^returnData 6 ^^" + jsonCart); //BTOCSITE-2722

//						BTOCSITE-2348 Start
						LOGGER.debug("### [CommonModuleController obsCart DeBug Log 007 ] " + df.format(cal.getTime()) + " : jsonCart : " + jsonCart);
//						BTOCSITE-2348 END

//						BTOCSITE-2792 Start
						resultData.put("cartId", visitorCartId);
//						BTOCSITE-2792 END


					}

//	              BTOCSITE-1481 START
					if(jsonCart.has("exception")){
						JSONObject exceptionData = jsonCart.getJSONObject("exception");
						resultData.put("success", "N");
						resultData.put("message", StringUtil.envl(exceptionData.get("message"), ""));
						resultData.put("code", "API_EXCEPTION");
						return resultData;
					}
//		              BTOCSITE-1481 END



					if(!jsonCart.has("errors")){
						resultData.put("success", "Y");
					}else{
						resultData.put("success", "N");
						JSONArray jArr2 = (JSONArray) jsonCart.get("errors");
						JSONObject error2 = (JSONObject) jArr2.get(0);
						String code2 = "";
						if(error2.get("code") != null){
							code2 = error2.get("code").toString();
						}
						resultData.put("code", code2);
						resultData.put("success", "N");
					}
				}else{
					resultData.put("code", code);
					resultData.put("success", "N");
				}
			}
		}
		return resultData;
	}

	private Map<String, Object> returnCartId(HttpServletRequest req, HttpServletResponse res){
		Map<String, Object> returnData = new HashMap<>();

//		BTOCSITE-2348 Start
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
//		BTOCSITE-2348 END


		try{
			Map<String, Object> login = AccessTokenUtil.getLoginFlag(req);

			String url   = configService.getString("obsAPIUrl");
			JSONObject apiData = new JSONObject();

			if((boolean)login.get("isLogin")){
				String idQuery = "{ customerCart{ id }}";

				LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^returnCartId^^targetURL 1^^" + url); //BTOCSITE-2722
				LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^returnCartId^^inputParam 1^^" + idQuery); //BTOCSITE-2722

				JSONObject jsonObj = OBSApiService.obsApiCall(req, url, idQuery);

				LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^returnCartId^^returnData 1^^" + jsonObj); //BTOCSITE-2722

//				BTOCSITE-2348 Start
				LOGGER.debug("### [CommonModuleController returnCartId DeBug Log ] " + df.format(cal.getTime()) + " : jsonObj : " + jsonObj);
//				BTOCSITE-2348 END



//              BTOCSITE-1481 START
				if(jsonObj.has("exception")){
					JSONObject exceptionData = jsonObj.getJSONObject("exception");
					returnData.put("success", "N");
					returnData.put("message", StringUtil.envl(exceptionData.get("message"), ""));

					return returnData;
				}
//              BTOCSITE-1481 END

				if(jsonObj.has("data")){
					apiData = jsonObj.getJSONObject("data");
					//카트 Id 가져오기
					JSONObject customerCart = apiData.getJSONObject("customerCart");

//			    	BTOCSITE-15206 Start
					String cartId = StringUtil.envl(customerCart.get("id"), "");
					returnData.put("cartId", cartId);

					Cookie cookie = new Cookie("OBS_CARTID", cartId);
					cookie.setPath("/");
					String domain = configService.getString("serverInfo.domain");
					cookie.setDomain(domain);
					res.addCookie(cookie);
//			    	BTOCSITE-15206 END

//					BTOCSITE-2792 Start
					LOGGER.debug(">>>>> returnCartId ::: " + customerCart.get("id"));

					LOGGER.debug("### [CommonModuleController returnCartId DeBug Log ] " + df.format(cal.getTime()) + " : obsCartId by obs : " + customerCart.get("id"));
//					BTOCSITE-2792 END


				}
			}else{
				if("R".equals(login.get("loginFlag"))){
					returnData.put("ssoCheckUrl", "reload");
					return returnData;
				}else{
					/******************비회원******************/
					LOGGER.info(">>>>> returnCartId ::: 비회원");
//					HttpSession session = req.getSession(false);
					boolean cookieFlag = false;
					Cookie[] cookies = req.getCookies();
					if (cookies != null && cookies.length > 0) {
						for(Cookie c : cookies){
							if("OBS_CARTID".equals(c.getName())){
								cookieFlag = true;
								break;
							}
						}
					}
					//session에  visitorCartId가 없으면
					if(!cookieFlag){
						String idQuery = "mutation{createEmptyCart}";

						LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^returnCartId^^targetURL 2^^" + url); //BTOCSITE-2722
						LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^returnCartId^^inputParam 2^^" + idQuery); //BTOCSITE-2722

						JSONObject jsonObj = OBSApiService.obsApiCall(req, url, idQuery);

						LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^returnCartId^^returnData 2^^" + jsonObj); //BTOCSITE-2722

//						BTOCSITE-2348 Start
						LOGGER.debug("### [CommonModuleController returnCartId DeBug Log ] " + df.format(cal.getTime()) + " : jsonObj : " + jsonObj);
//						BTOCSITE-2348 END


//		              BTOCSITE-1481 START
						if(jsonObj.has("exception")){
							JSONObject exceptionData = jsonObj.getJSONObject("exception");
							returnData.put("success", "N");
							returnData.put("message", StringUtil.envl(exceptionData.get("message"), ""));

							return returnData;
						}
//		              BTOCSITE-1481 END

						if(jsonObj.has("data")){
							apiData = jsonObj.getJSONObject("data");
							String visitorCartId = apiData.getString("createEmptyCart");
							returnData.put("cartId", visitorCartId);
							//session에  visitorCartId 담는다
//							session.setAttribute("OBS_CARTID", visitorCartId);

							Cookie cookie = new Cookie("OBS_CARTID", visitorCartId);
							cookie.setPath("/");
							String domain = configService.getString("serverInfo.domain");
							cookie.setDomain(domain);
							res.addCookie(cookie);

//							BTOCSITE-2792 Start
							LOGGER.debug("### [CommonModuleController returnCartId DeBug Log ] " + df.format(cal.getTime()) + " : obsCartId Create by obs : " + visitorCartId);
//							BTOCSITE-2792 END


						}
					}else{
						//session에  visitorCartId가 있으면 parameter에 그대로 넣는다
						String obsCartId = "";
						if (cookies != null && cookies.length > 0) {
							for(Cookie c : cookies){
								if("OBS_CARTID".equals(c.getName())){
									obsCartId = c.getValue();
								}
							}
						}
						returnData.put("cartId", obsCartId);
//						BTOCSITE-2792 Start
						LOGGER.debug("### [CommonModuleController returnCartId DeBug Log ] " + df.format(cal.getTime()) + " : obsCartId by cookie : " + obsCartId);
//						BTOCSITE-2792 END

					}
				}
			}
		}catch (Exception e) {
			throw new BusinessException("returnCartId Exception : " , e);
		}
		return returnData;
	}

	/*헤더*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/commonModule/miniCart.lgajax", method=RequestMethod.POST,
	produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public @ResponseBody Map<String, Object> cartCountchk(HttpServletRequest req, HttpServletResponse res){
		Map<String, Object> returnData = new HashMap<>();
		Map<String, Object> resultData = new HashMap<>();



//		BTOCSITE-2348 Start
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
//		BTOCSITE-2348 END


		int careCartCnt = 0;
		int obsCnt = 0;

//		BTOCSITE-2792 Start
		Map<String, Object> login = AccessTokenUtil.getLoginFlag(req);

		try {

			Map<String, Object> ret = returnCartIdForMinicart(req, res);

			if(ret.get("cartId") != null && !ret.get("cartId").equals("")){

				String url   = configService.getString("obsAPIUrl");
				String addQuery = "{cart(cart_id:\""+ret.get("cartId")+"\") { items {product{sku}}total_quantity}}";

				LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^cartCountchk^^targetURL^^" + url); //BTOCSITE-2722
				LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^cartCountchk^^inputParam^^" + addQuery); //BTOCSITE-2722

				JSONObject cartCnt = OBSApiService.obsApiCall(req, url, addQuery);

				LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^cartCountchk^^returnData^^" + cartCnt); //BTOCSITE-2722

//				BTOCSITE-2348 Start
				LOGGER.debug("### [CommonModuleController cartCountchk DeBug Log 002 ] " + df.format(cal.getTime()) + " : cartCnt : " + cartCnt);
//				BTOCSITE-2348 END


//				BTOCSITE-2348 Start
				if (!cartCnt.isEmpty() && !cartCnt.has("errors")) {
					if (cartCnt.has("data")) {
						JSONObject dataObj = cartCnt.getJSONObject("data");
						if (dataObj.has("cart")) {
							JSONObject cartObj = dataObj.getJSONObject("cart");
							if (cartObj.has("total_quantity")) {
								obsCnt = cartObj.getInt("total_quantity");
							}
						}
					}
				}
//				BTOCSITE-2348 END
			}

			if((boolean)login.get("isLogin")){

				Map<String, Object> cookieVal = cartService.getCookieCart(req);
				List<Map<String, Object>> cookieDataList = (List<Map<String, Object>>) cookieVal.get("cookieList");

				Map<String, Object> input = new  HashMap<>();
				input.put("unifyId", ((MemberInformation)login.get("memberInformation")).getUnifyId());
				input.put("createId", ((MemberInformation)login.get("memberInformation")).getMobileNo());
				if(req.getHeader("referer").indexOf("/add-to-cart/rental-care-solution") < 0){
					if(cookieDataList.size() > 0){
						for(Map<String, Object> map : cookieDataList){
							input.putAll(map);
							cartService.insertCookieCart(input);
						}
					}
				}
				resetCookieCart(req, res);
				careCartCnt = commonModuleService.retrieveCareCartCnt(input);
			}else{
				Cookie[] cookies = req.getCookies();
				if (cookies != null && cookies.length > 0) {
			    	for(Cookie c : cookies){
			    		if("cartInfo".equals(c.getName()) && !StringUtil.isEmpty(c.getValue())){
			    			String[] cookieCareCart = c.getValue().split("\\^\\^");
				    		careCartCnt = cookieCareCart.length;
			    		}
			    	}
		    	}
			}


			String cartUrl = "";
			if(obsCnt == 0 && careCartCnt == 0){
				cartUrl = "/shop/lgobscart/cart/quote";
			}else if(obsCnt > 0 && careCartCnt == 0){
				cartUrl = "/shop/lgobscart/cart/quote";
			}else if(obsCnt == 0 && careCartCnt > 0){
				cartUrl = "/add-to-cart/rental-care-solution";
			}else{
				cartUrl = "/shop/lgobscart/cart/quote";
			}

			resultData.put("success", "Y");
			resultData.put("cartCnt", obsCnt+careCartCnt);
			resultData.put("cartUrl", cartUrl);
			returnData.put("status", JSON_RESPONSE_SUCCESS_MESSAGE);
			returnData.put("data"   , resultData);

		} catch (Exception e) {
			resultData.put("success", "N");
			returnData.put("status" , JSON_RESPONSE_FAILURE_MESSAGE);
			returnData.put("message", "server error");
			returnData.put("data"   , resultData);
			throw new BusinessException("[POST] MKT addCart Exception : " , e);
		}

//		BTOCSITE-2792 End

		return returnData;
	}


	@RequestMapping(value = "/commonModule/addWish.lgajax", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public Map<String, Object> addWishProduct(@RequestParam Map<String, Object> input, HttpServletRequest req) throws Exception{


		Map<String, Object> returnData = new HashMap<>();
		Map<String, Object> resultData = new HashMap<>();


		String type = req.getParameter("type")==null?"list":req.getParameter("type");

		try{

			Map<String, Object> login = AccessTokenUtil.getLoginFlag(req);
			input.put("isLogin", login.get("isLogin"));


			if((boolean)login.get("isLogin")){
				MemberInformation memberInformation = (MemberInformation)login.get("memberInformation");
				input.put("unifyId", memberInformation.getUnifyId());
				input.put("userId", memberInformation.getUserId());

				//obs wish List 가져오기

//				BTOCSITE-751 START
				// downtime
		    	Map<String, Object> downTimeResult = blockUrlService.retrieveDownTimeInfo("CSB10");
		    	if("Y".equals(downTimeResult.get("downtimeChk"))){
		    		Map<String,Object> dwSystemInfo = (Map<String, Object>) downTimeResult.get("dwSystemInfo");
		    		returnData.put("message", "시스템 점검중입니다.<BR>점검시간 : " + dwSystemInfo.get("downTime") + "<BR> ~ " + dwSystemInfo.get("openTime"));
					returnData.put("status" , JSON_RESPONSE_FAILURE_MESSAGE);
					return returnData;
		    	}
//		    	BTOCSITE-751 END

//				JSONObject apiData = OBSApiService.getWishApi(type, req, input, configService.getString("obsAPIUrl"));

//				BTOCSITE-1481 START
//				if(apiData.has("exception")){
//					JSONObject exceptionData = apiData.getJSONObject("exception");
//					returnData.putAll(MktUtil.systemErrorAlert(StringUtil.envl(exceptionData.get("message"), "")));
//					return returnData;
//				} else {
//					resultData = commonModuleService.retrieveWishList(type, input , apiData);
//				}
//				BTOCSITE-1481 END

				//[LGECOMVIO-530]  START
				input.put("wishType", MKTCommonCodes.WISHLIST_TYPE_CODE_ONLINE); //lge.com
				Map<String, Object> listMap = new HashMap<>();
				List<Map<String,Object>> listData = new ArrayList<>();

				if("list".equals(MapUtils.getString(input,"type"))){

					try{

						List<Map<String,Object>> list = wishListService.getWishInfoList(new WishListParamVO(input));

						if(CollectionUtils.isNotEmpty(list)){
						   for (Map wish : list){
							   Map<String, Object> wishInfo = new HashMap<>();
							   wishInfo.put("wishListId",wish.get("wishListId"));
							   wishInfo.put("wishItemId",wish.get("wishListId"));
							   wishInfo.put("sku",wish.get("sku"));
							   wishInfo.put("modelId",wish.get("modelId"));
							   listData.add(wishInfo);
						   }
							listMap.put("listData",listData);
						}
						resultData.put("data",listMap);
						resultData.put("status",MKTCommonCodes.Status.RESULT_TRUE);
						resultData.put("success","Y");
					}catch  (Exception e){
						resultData.put("status",MKTCommonCodes.Status.RESULT_FALSE);
						resultData.put("success","N");
					}

				}else{
					Map<String, Object> wishInfo = new HashMap<>();
					//필수 값 체크
                    if(StringUtil.isNullAndEmpty(input.get("id"))){
						returnData.put("status" , JSON_RESPONSE_FAILURE_MESSAGE);
						returnData.put("message", "");
						return returnData;
                    }

					try{
						String wishlistId = "";

						if("add".equals(type)){
							input.put("wishlistId", input.get("wishListId"));
							wishlistId = String.valueOf(wishListService.insertWishList(new WishListParamVO(input)));

						}else if("remove".equals(type)){
							if(StringUtil.isNullAndEmpty(input.get("wishItemId"))){
								returnData.put("status" , JSON_RESPONSE_FAILURE_MESSAGE);
								returnData.put("message", "");
								return returnData;
							}

							input.put("wishlistId", input.get("wishItemId"));
							wishlistId = String.valueOf(wishListService.deleteWishList(new WishListParamVO(input)));
						}

						wishInfo.put("wishListId",wishlistId);
						wishInfo.put("wishItemId",wishlistId);
						listData.add(wishInfo);
						listMap.put("listData",listData);

						resultData.put("data",listMap);
						resultData.put("status",MKTCommonCodes.Status.RESULT_TRUE);
						resultData.put("success","Y");

					}catch  (Exception e){
						resultData.put("status",MKTCommonCodes.Status.RESULT_FALSE);
						resultData.put("success","N");
					}
				}
				//[LGECOMVIO-530]  END

			}else{
//				if("R".equals(login.get("loginFlag"))){
//					returnData.put("ssoCheckUrl", "reload");
//					return returnData;
//				} else {

					Map<String, Object> data = new HashMap<>();
					Map<String, Object> alert = new HashMap<>();

					alert.put("isConfirm", true);
					alert.put("title", "선택하신 제품을 위시리스트에 담기 위해서는<br>LG전자 통합 사이트의 로그인이 필요합니다."); /*BTOCSITE-26649 변경*/

					alert.put("cancelBtnName", "취소");
					alert.put("cancelUrl", null);
					alert.put("okBtnName", "로그인");
					alert.put("okUrl", "/sso/api/Login?state=");

					data.put("success", "N");
					data.put("alert", alert);
					returnData.put("data", data);
					returnData.put("status", MKTCommonCodes.Status.RESULT_SUCCESS);
					returnData.put("message", null);

					return returnData;
//				}
			}

			if((boolean)resultData.get("status")){
				resultData.put("success", "Y");
				returnData.put("status", JSON_RESPONSE_SUCCESS_MESSAGE);
				returnData.put("data"   , resultData);
			}else{


				returnData.putAll(MktUtil.systemAlert(StringUtil.envl(resultData.get("message"), "")));

				return returnData;
			}


		}catch(Exception e){

			LOGGER.error("### [addWishProduct] Exception : " + ExceptionUtils.getStackTrace(e)); //BTOCSITE-2722
		}
		return returnData;

    }

	public void resetCookieCart(HttpServletRequest req, HttpServletResponse res){
    	Cookie[] cookies = req.getCookies();
    	LOGGER.debug(">>> reset cookies : " +  cookies);
    	String domain = configService.getString("serverInfo.domain");
    	LOGGER.debug(">>> reset cookie domain : " +  domain);
    	for(Cookie c : cookies){
    		if("cartInfo".equals(c.getName())){
	    		c.setMaxAge(0);
	    		c.setValue("");
	    		c.setPath("/");
	        	c.setDomain(domain);
	            res.addCookie(c);
	        	LOGGER.debug(">>> reset cookie name : " + c.getName() + " value:" +  c.getValue());
    		}
    	}
    }

	@RequestMapping(value = "/commonModule/retrieveOwnModel", method=RequestMethod.POST)
	public void retrieveOwnModel(@RequestParam String unifyId, HttpServletResponse res) throws Exception{
		String message = null;
    	String status = JSON_RESPONSE_SUCCESS_MESSAGE;
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try{
    		List<Map<String, Object>> resultList = commonModuleService.retrieveOwnModel(unifyId);
    		resultMap.put("result", resultList);
    	}catch (Exception e) {
    		status = JSON_RESPONSE_FAILURE_MESSAGE;
			message = "server errer";
    		throw new BusinessException("retrieveOwnModel : "+e);
		}
    	ResponseUtil.sendResponse(res, resultMap, status, message);
	}

	@RequestMapping(value = "/commonModule/obscnt.lgajax", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> obscnt(HttpServletRequest request, HttpServletResponse response,
			@RequestBody(required = false) Map<String, String> input) {
		Map<String, Object> result = new HashMap<>();
		Map<String,String>  rdata = new HashMap<>();
		// String message = null;
		String status = JSON_RESPONSE_SUCCESS_MESSAGE;
		String scnt = null;
		String sOBS_CARTID = null;

		try {
			LOGGER.debug("[obscnt.lgajax]input" + input);
			if (input == null) {
				// OBS CART 수량 초기화
				request.getSession().removeAttribute("OBSCART_CNT");
				rdata = null;
			} else {
				scnt = input.get("cnt");
				sOBS_CARTID = input.get("obscart_id");
				LOGGER.debug("[obscnt.lgajax](before)CART_ID" + getCookie(request, "OBS_CARTID") + "개수 "
						+ request.getSession().getAttribute("OBSCART_CNT"));

				// 세션 저장 OBS_CARTID, OBSCART_CNT
				request.getSession().setAttribute("OBS_CARTID", sOBS_CARTID);
				request.getSession().setAttribute("OBSCART_CNT", scnt);

				// 쿠키 저장 OBS_CARTID
				String domain = configService.getString("serverInfo.domain");
				Cookie obsCartIDCookie = new Cookie("OBS_CARTID", sOBS_CARTID);
				obsCartIDCookie.setDomain(domain);
				obsCartIDCookie.setPath("/");
				response.addCookie(obsCartIDCookie);

				rdata.put("cnt", scnt);
				rdata.put("obscart_id", sOBS_CARTID);

				LOGGER.debug("[obscnt.lgajax](after) CART_ID" + sOBS_CARTID + "개수 "
						+ request.getSession().getAttribute("OBSCART_CNT"));

			}

		} catch (Exception e) {
			status = JSON_RESPONSE_FAILURE_MESSAGE;
		}
		result.put("status", status);
		result.put("data", rdata);

		return result;
	}

	public String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			return "";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return "";
	}


//	BTOCSITE-2792 Start
	private Map<String, Object> returnCartIdForMinicart(HttpServletRequest req, HttpServletResponse res){

		Map<String, Object> returnData = new HashMap<>();

//		BTOCSITE-2348 Start
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
//		BTOCSITE-2348 END


		try{
//			BTOCSITE-10373 Start
			//session에  CartId가 없으면 로그인 여부 체크 후 CartId 조회함.
			Map<String, Object> login = AccessTokenUtil.getLoginFlag(req);

			String url   = configService.getString("obsAPIUrl");
			JSONObject apiData = new JSONObject();

			if((boolean)login.get("isLogin")){

				String idQuery = "{ customerCart{ id }}";

				LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^returnCartIdForMinicart^^targetURL^^" + url); //BTOCSITE-2722
				LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^returnCartIdForMinicart^^inputParam^^" + idQuery); //BTOCSITE-2722

				JSONObject jsonObj = OBSApiService.obsApiCall(req, url, idQuery);

				LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^returnCartIdForMinicart^^returnData^^" + jsonObj); //BTOCSITE-2722

//					BTOCSITE-2348 Start
				LOGGER.debug("### [CommonModuleController returnCartIdForMinicart DeBug Log ] " + df.format(cal.getTime()) + " : jsonObj : " + jsonObj);
//					BTOCSITE-2348 END


//              	BTOCSITE-1481 START
				if(jsonObj.has("exception")){
					JSONObject exceptionData = jsonObj.getJSONObject("exception");
					returnData.put("success", "N");
					returnData.put("message", StringUtil.envl(exceptionData.get("message"), ""));

					return returnData;
				}
//              	BTOCSITE-1481 END

				if(jsonObj.has("data")){
					apiData = jsonObj.getJSONObject("data");
					//카트 Id 가져오기
					JSONObject customerCart = apiData.getJSONObject("customerCart");
					returnData.put("cartId", customerCart.get("id"));

//						BTOCSITE-2792 Start
					LOGGER.debug(">>>>> returnCartIdForMinicart ::: " + customerCart.get("id"));

					LOGGER.debug("### [CommonModuleController returnCartIdForMinicart DeBug Log ] " + df.format(cal.getTime()) + " : obsCartId by obs : " + customerCart.get("id"));
//						BTOCSITE-2792 END

				}

			} else {

				if("R".equals(login.get("loginFlag"))){
					returnData.put("ssoCheckUrl", "reload");
					return returnData;
				}else{
					/******************비회원******************/
					LOGGER.info(">>>>> returnCartIdForMinicart ::: 비회원");
					boolean cookieFlag = false;
					Cookie[] cookies = req.getCookies();
					if (cookies != null && cookies.length > 0) {
						for(Cookie c : cookies){
							if("OBS_CARTID".equals(c.getName())){
								cookieFlag = true;
								break;
							}
						}
					}

					//session에  visitorCartId가 없으면
					if(!cookieFlag){
						String idQuery = "mutation{createEmptyCart}";

						LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^returnCartIdForMinicart^^targetURL 2^^" + url); //BTOCSITE-2722
						LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^returnCartIdForMinicart^^inputParam 2^^" + idQuery); //BTOCSITE-2722

						JSONObject jsonObj = OBSApiService.obsApiCall(req, url, idQuery);

						LOGGER.warn(MKTCommonCodes.INTERPACE_COMMON_KEYWORD + "^^" + MKTCommonCodes.INTERPACE_SYSTEM_OBS + "^^[MKT]CommonModuleController^^returnCartIdForMinicart^^returnData 2^^" + jsonObj); //BTOCSITE-2722

//					BTOCSITE-2348 Start
						LOGGER.debug("### [CommonModuleController returnCartIdForMinicart DeBug Log ] " + df.format(cal.getTime()) + " : jsonObj : " + jsonObj);
//					BTOCSITE-2348 END


//	              BTOCSITE-1481 START
						if(jsonObj.has("exception")){
							JSONObject exceptionData = jsonObj.getJSONObject("exception");
							returnData.put("success", "N");
							returnData.put("message", StringUtil.envl(exceptionData.get("message"), ""));

							return returnData;
						}
//	              BTOCSITE-1481 END

						if(jsonObj.has("data")){
							apiData = jsonObj.getJSONObject("data");
							String visitorCartId = apiData.getString("createEmptyCart");
							returnData.put("cartId", visitorCartId);
							//session에  visitorCartId 담는다
//						session.setAttribute("OBS_CARTID", visitorCartId);
							Cookie cookie = new Cookie("OBS_CARTID", visitorCartId);
							cookie.setPath("/");
							String domain = configService.getString("serverInfo.domain");
							cookie.setDomain(domain);
							res.addCookie(cookie);

//						BTOCSITE-2792 Start
							LOGGER.debug("### [CommonModuleController returnCartIdForMinicart DeBug Log ] " + df.format(cal.getTime()) + " : obsCartId Create by obs : " + visitorCartId);
//						BTOCSITE-2792 END


						}
					}else{
						//session에  visitorCartId가 있으면 parameter에 그대로 넣는다
						String obsCartId = "";
						if (cookies != null && cookies.length > 0) {
							for(Cookie c : cookies){
								if("OBS_CARTID".equals(c.getName())){
									obsCartId = c.getValue();
								}
							}
						}
						returnData.put("cartId", obsCartId);
//					BTOCSITE-2792 Start
						LOGGER.debug("### [CommonModuleController returnCartIdForMinicart DeBug Log ] " + df.format(cal.getTime()) + " : obsCartId by cookie : " + obsCartId);
//					BTOCSITE-2792 END

					}
				}
			}

//			BTOCSITE-10373 End
		}catch (Exception e) {
			throw new BusinessException("returnCartIdForMinicart Exception : " , e);
		}
		return returnData;
	}
//		BTOCSITE-2792 End


//	BTOCSITE-659 Start
	@RequestMapping(value = "/commonModule/addAWSPdpUp.lgajax", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
    public Map<String, Object> addAWSPdpUp(@RequestParam Map<String, Object> input, HttpServletRequest req) throws Exception{
		LOGGER.info("▼▼▼ CommonModuleController addAWSPdpUp Starting ▼▼▼");

		String message = null;
		String status = JSON_RESPONSE_SUCCESS_MESSAGE;
		String success = JSON_RESPONSE_SUCCESS_MESSAGE;

		String itemId = req.getParameter("itemId")==null?"":req.getParameter("itemId");

		String unifyId  = "";
		int responseCode = 0;

		try {
			Map<String, Object> login = AccessTokenUtil.getLoginFlag(req);
			MemberInformation memberInformation = (MemberInformation)login.get("memberInformation");
			unifyId = memberInformation.getUnifyId();

		} catch (Exception e) {
			// LGECOMVIO-20 Start
			unifyId = "";
			String ga = getCookie(req, "_ga");
			if(!"".equals(ga)){
				String[] gaList = ga.split("\\.");
				if (gaList.length > 2) {
					unifyId = gaList[gaList.length-2]+"."+gaList[gaList.length-1];
					//input.put("unifyId", gaList[gaList.length-2]+"."+gaList[gaList.length-1]);
				}
			}
			LOGGER.debug("CommonModuleController addAWSPdpUp Not Login");
			// LGECOMVIO-20 End
		}


		if(!"".equals(unifyId) && !"".equals(itemId)){

			String trackingId =  configService.getString("pdpUpApi.trackingId");

			String userId = unifyId;
			String sessionId = unifyId;

			// Change to the region where your resources are located
			Region region = Region.AP_NORTHEAST_2;

			try {

				String accessKeyId = configService.getString("accessKeyId");
				String secretKeyId = configService.getString("secretKeyId");
				AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretKeyId);

				PersonalizeEventsClient personalizeEventsClient = PersonalizeEventsClient.builder()
						.credentialsProvider(StaticCredentialsProvider.create(awsCreds))
						.region(region)
						.build();

				Event event = Event.builder()
							.sentAt(Instant.ofEpochMilli(System.currentTimeMillis() + 10 * 60 * 1000))
							.itemId(itemId)
							.properties("{\"count\":1}")
							.eventType("realtime_event_tracking")
							.build();

				PutEventsRequest putEventsRequest = PutEventsRequest.builder()
						.trackingId(trackingId)
						.userId(userId)
						.sessionId(sessionId)
						.eventList(event)
						.build();

				responseCode = personalizeEventsClient.putEvents(putEventsRequest).sdkHttpResponse().statusCode();

				personalizeEventsClient.close();

				if(responseCode == 200){
					status = JSON_RESPONSE_SUCCESS_MESSAGE;
					success = JSON_RESPONSE_SUCCESS_MESSAGE;
					message = "";

				} else {
					status = JSON_RESPONSE_SUCCESS_MESSAGE;
					success = JSON_RESPONSE_FAILURE_MESSAGE;
					message = "";
				}


			} catch (Exception e) {
				status = JSON_RESPONSE_FAILURE_MESSAGE;
				success = JSON_RESPONSE_FAILURE_MESSAGE;
				message = "server error";
			}
		} else {
			status = JSON_RESPONSE_SUCCESS_MESSAGE;
			success = JSON_RESPONSE_FAILURE_MESSAGE;
			message = "not signed in or not itemId";
		}

		Map<String, Object> returnData = new HashMap<>();

		returnData.put("status" , status);
		returnData.put("success", success);
		returnData.put("message", message);

		if(!"".equals(unifyId) && !"".equals(itemId)){

		}
		LOGGER.info(" CommonModuleController addAWSPdpUp unifyId : " + unifyId +" // itemId : "+ itemId +" // returnData : "+   returnData);
		LOGGER.info("▲▲▲ CommonModuleController addAWSPdpUp Starting ▲▲▲");


		return returnData;


    }


	@RequestMapping(value = "/commonModule/addAWSStory.lgajax", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> addAWSStory(@RequestParam Map<String, Object> input, HttpServletRequest req, HttpServletResponse res) throws Exception{
		LOGGER.info("▼▼▼ CommonModuleController addAWSStory Starting ▼▼▼");

		String message = null;
		String status = JSON_RESPONSE_SUCCESS_MESSAGE;
		String success = JSON_RESPONSE_SUCCESS_MESSAGE;

		String itemId = req.getParameter("itemId")==null?"":req.getParameter("itemId");

		String unifyId  = "";
		int responseCode = 0;



//		BTOCSITE-5233 Start
		if(!"".equals(itemId)){
			Cookie cookie = new Cookie("storyPath", itemId);
			cookie.setMaxAge(60*60*24*180); // 180 일 보관
			cookie.setPath("/");
			String domain = "lge.co.kr";
			cookie.setDomain(domain);
			res.addCookie(cookie);
		}
//		BTOCSITE-5233 End


		try {
			Map<String, Object> login = AccessTokenUtil.getLoginFlag(req);
			MemberInformation memberInformation = (MemberInformation)login.get("memberInformation");
			unifyId = memberInformation.getUnifyId();

		} catch (Exception e) {
			input.put("unifyId", "");
			unifyId = "";
		}


		if(!"".equals(unifyId) && !"".equals(itemId)){

			String trackingId =  configService.getString("storyApi.trackingId");

			String userId = unifyId;
			String sessionId = unifyId;

			// Change to the region where your resources are located
			Region region = Region.AP_NORTHEAST_2;

			try {

				String accessKeyId = configService.getString("accessKeyId");
				String secretKeyId = configService.getString("secretKeyId");
				AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretKeyId);

				PersonalizeEventsClient personalizeEventsClient = PersonalizeEventsClient.builder()
						.credentialsProvider(StaticCredentialsProvider.create(awsCreds))
						.region(region)
						.build();

				Event event = Event.builder()
						.sentAt(Instant.ofEpochMilli(System.currentTimeMillis() + 10 * 60 * 1000))
						.itemId(itemId)
						.eventType("realtime_event_tracking")
						.build();

				PutEventsRequest putEventsRequest = PutEventsRequest.builder()
						.trackingId(trackingId)
						.userId(userId)
						.sessionId(sessionId)
						.eventList(event)
						.build();

				responseCode = personalizeEventsClient.putEvents(putEventsRequest).sdkHttpResponse().statusCode();

				personalizeEventsClient.close();

				if(responseCode == 200){
					status = JSON_RESPONSE_SUCCESS_MESSAGE;
					success = JSON_RESPONSE_SUCCESS_MESSAGE;
					message = "";

				} else {
					status = JSON_RESPONSE_SUCCESS_MESSAGE;
					success = JSON_RESPONSE_FAILURE_MESSAGE;
					message = "";
				}


			} catch (Exception e) {
				status = JSON_RESPONSE_FAILURE_MESSAGE;
				success = JSON_RESPONSE_FAILURE_MESSAGE;
				message = "server error";
			}
		} else {
			status = JSON_RESPONSE_SUCCESS_MESSAGE;
			success = JSON_RESPONSE_FAILURE_MESSAGE;
			message = "not signed in or not itemId";
		}

		Map<String, Object> returnData = new HashMap<>();

		returnData.put("status" , status);
		returnData.put("success", success);
		returnData.put("message", message);

		LOGGER.info(" CommonModuleController addAWSStory unifyId : " + unifyId +" // itemId : "+ itemId +" // returnData : "+   returnData);
		LOGGER.info("▲▲▲ CommonModuleController addAWSStory Starting ▲▲▲");

		return returnData;



	}

//		BTOCSITE-659 END

//		BTOCSITE-11297 START


	/**
	 * 유저 Crema id 및 name을 불러오기 위한 Controller Method이다.
	 *
	 * @return
	 * @return String - 조회결과 화면정보
	 */
	@RequestMapping(value = "/commonModule/cremaInfo.lgajax", method=RequestMethod.POST)
	@ResponseBody
	public void cremaInfo(HttpServletRequest request, HttpServletResponse response) {


		Map<String, Object> result = new HashMap<>();
		String status = JSON_RESPONSE_SUCCESS_MESSAGE;
		String message = "";
		Map<String, Object> cremaInfo = new HashMap<>();
		String encName = "";
		String encUid = "";
		try {
			MemberInformation sessionMember = (MemberInformation)request.getSession().getAttribute("MemberInformation");

			if(sessionMember!=null){
				String maskName = maskingName(sessionMember.getName());
				String uid = sessionMember.getUnifyId();

				String passphrase = "6VlF3aycGWan";
				byte key[] = sha256(passphrase);
				byte iv[] = md5(passphrase);
				byte encrypted[] = aes256(maskName, key, iv);
				byte encrypted2[] = aes256(uid, key, iv);
				encName = "V2" + encodeByteToHex(encrypted);
				encUid = "V2" + encodeByteToHex(encrypted2);
				cremaInfo.put("cremaId", encUid);
				cremaInfo.put("cremaName", encName);
			}
			else {
				cremaInfo.put("cremaId", null);
				cremaInfo.put("cremaName", null);
			}
		}catch (Exception e) {
			 status = JSON_RESPONSE_FAILURE_MESSAGE;
			 message = "error";
			 throw new BusinessException ("SendResponse : ", e);
		}
		result.put("status", status);
		result.put("data", cremaInfo);
		LOGGER.debug( "[Ajax] cremaInfo resultMap : " + result);

		ResponseUtil.sendResponse(response, result, status, message);
	}

	private static byte[] aes256(String str, byte key[], byte iv[]) throws Exception {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(
            Cipher.ENCRYPT_MODE,
            new SecretKeySpec(key, "AES"),
            new IvParameterSpec(iv)
        );
        return c.doFinal(str.getBytes("UTF-8"));
    }
    private static byte[] sha256(String msg) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes());
        return md.digest();
    }
    private static byte[] md5(String msg) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(msg.getBytes());
        return md.digest();
    }
    private static String encodeByteToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b: bytes) {
          builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static String maskingName(String str) {
		String replaceString = str;

		String pattern = "";
		if(str.length() == 2) {
			pattern = "^(.)(.+)$";
		} else {
			pattern = "^(.)(.+)(.)$";
		}

		Matcher matcher = Pattern.compile(pattern).matcher(str);
		if(matcher.matches()) {
			replaceString = "";

			for(int i=1;i<=matcher.groupCount();i++) {
				String replaceTarget = matcher.group(i);
				if(i == 2) {
					char[] c = new char[replaceTarget.length()];
					Arrays.fill(c, '*');

					replaceString = replaceString + String.valueOf(c);
				} else {
					replaceString = replaceString + replaceTarget;
				}
			}
		}


		return replaceString;
	}

//	BTOCSITE-11297 END
}

















