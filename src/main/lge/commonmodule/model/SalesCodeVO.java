/*******************************************************************************
 * FILE: /java/lgcommkt/commonmodule/model/SalesCodeVO.java
 * DESC : Default Product Sales Model Code Info VO
 *
 * PROJ : LG.com 5.0
 *******************************************************************************
 *                  Modification History
 *******************************************************************************
 * CSR No.      DATE              AUTHOR             DESCRIPTION
 *******************************************************************************
 *              2019/06/20        Kim Chung           Initial Release
 *******************************************************************************/
package lgekormkt.commonmodule.model;

public class SalesCodeVO {

	/* PARAMETER */
	/** 국가코드 */
	private String localeCode;

	/** 제품 ID */
	private String modelId;

	/** Business 구분(B2C, B2B) */
	private String bizType;

	/** 모델상태코드 */
	private String modelStatusCode;

	/* MKT_MODEL_M */
	/** 제품이름 */
	private String modelName;

	/** 제품Type (MKT_MODEL_M : G-general, B-bundle, A-accessory, P-package) */
	private String modelType;

	/** 영업모델 */
	private String salesModelCode;

	/** 영업Suffix */
	private String salesSuffixCode;

	/* MKT_SIBLING_M */
	/** Default 시블링 대표 모델 구분값 */
	private String defaultModelFlag;

	/** 시블링 코드 */
	private String siblingCode;

	/** 시블링 그룹 코드 */
	private String siblingGroupCode;

	/** 시블링 타입 */
	private String siblingType;

	/** target 설정 값 */
	private String target;

	/* MKT_SIBLING_DISPLAY_ORDER_M */
	/** PDP에 노출되는 Title값 */
	private String pdpTitle;

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getModelStatusCode() {
		return modelStatusCode;
	}

	public void setModelStatusCode(String modelStatusCode) {
		this.modelStatusCode = modelStatusCode;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getSalesModelCode() {
		return salesModelCode;
	}

	public void setSalesModelCode(String salesModelCode) {
		this.salesModelCode = salesModelCode;
	}

	public String getSalesSuffixCode() {
		return salesSuffixCode;
	}

	public void setSalesSuffixCode(String salesSuffixCode) {
		this.salesSuffixCode = salesSuffixCode;
	}

	public String getDefaultModelFlag() {
		return defaultModelFlag;
	}

	public void setDefaultModelFlag(String defaultModelFlag) {
		this.defaultModelFlag = defaultModelFlag;
	}

	public String getSiblingCode() {
		return siblingCode;
	}

	public void setSiblingCode(String siblingCode) {
		this.siblingCode = siblingCode;
	}

	public String getSiblingGroupCode() {
		return siblingGroupCode;
	}

	public void setSiblingGroupCode(String siblingGroupCode) {
		this.siblingGroupCode = siblingGroupCode;
	}

	public String getSiblingType() {
		return siblingType;
	}

	public void setSiblingType(String siblingType) {
		this.siblingType = siblingType;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getPdpTitle() {
		return pdpTitle;
	}

	public void setPdpTitle(String pdpTitle) {
		this.pdpTitle = pdpTitle;
	}

}
