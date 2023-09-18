/*******************************************************************************
 * FILE: /java/lgcommkt/commonmodule/model/RuleVO.java
 * DESC : Common Module Model
 *   
 * PROJ : LG.com 5.0 
 *******************************************************************************
 *                  Modification History
 *******************************************************************************
 * CSR No.           DATE              AUTHOR             DESCRIPTION
 *******************************************************************************
 * LGEUS-12083     2019/08/26		Jinmoongi   	      Change % off to $ off on promo pricing
 * LGEUS-12198     2019/10/17		 joyeongsik			 Direct link external via WTB for exclusive products
 *******************************************************************************/
package lgekormkt.commonmodule.model;

import java.math.BigDecimal;

public class RuleVO {
	
	/* common param */
	/** OBS모델여부 */
	private String obsPriceFlag = "Y";
	/** OBS재고여부 */
	private String obsInventoryFlag;
	/** OBS판매여부 */
	private String obsSellFlag;
	/** OBS로그인여부 */
	private String obsLoginFlag;
	/** eCommerce적용여부 */
	private String eCommerceFlag ;
	/** 모델 상태 코드 */
	private String modelStatusCode;
	
	/* price Rule param */
	/** OBS판매가격 */
	private BigDecimal obsSellingPrice;
	/** MSRP판매가격 */
	private BigDecimal msrp;
	/** promotion판매가격 */
	private BigDecimal promotionPrice;
	
	/* price Rule return */
	/** 가격룰 정상가 */
	private String price;
	/** 가격룰 정상가 소수점 뒷자리 */
	private String priceCent;
	/** 가격룰 할인가 */
	private String promoPrice;
	/** 가격룰 할인가 소수점 뒷자리 */
	private String promoPriceCent;
	/** 가격룰 할인율 */
	private String discountedRate;
	
	/* LGEUS-12083 Start */
	/** 가격 할인가 */
	private String discountedPrice;
	/** 가격 할인가 소수점 뒷자리 */
	private String discountedPriceCent;
	/* LGEUS-12083 End */
	
	/* button Rule param */
	/** Business 구분(B2C, B2B), (구)b2bPageFlag */
	private String bizType;
	/** whereToBuy 사용여부 */
	private String wtbFlag;
	/** whereToBuy 특정카테고리 사용여부 (Y : disable) */
	private String ctaDisalbeWtb;
	
	/* button Rule return */
	/** addToCart버튼 노출여부 */
	private String addToCartFlag;
	/** whereToBuy버튼 노출여부 */
	private String whereToBuyFlag;
	/** WTB External 버튼 노출여부 */
	private String wtbExternalLinkUseFlag;	// LGEUS-12198
	/** inquiryToBuy버튼 노출여부 */
	private String inquiryToBuyFlag;
	/** findTheDealer버튼 노출여부 */
	private String findTheDealerFlag;
	
	
	/* button Rule param */
	/** categoryId */
	private String categoryId;
	/** whereToBuy 사용여부 */
	private String wtbType;
	/** addToCart 특정카테고리 사용여부 (Y : disable) */
	private String ctaDisalbeCart;
	
	/* button Rule return */
	/** WTB External 버튼명 */
	private String wtbExternalLinkName;
	/** buyNow버튼 노출여부 (Y / N) */
	private String buyNowFlag;
	/** productSupport버튼 노출여부 (Y / N) */
	private String productSupportFlag;
	/** reseller버튼 노출여부 (Y / N) */
	private String resellerFlag;
	/** reseller버튼 링크 URL */
	private String resellerLinkUrl;
	/** BuyNow버튼 링크 URL */
	private String buynowUrl;
	/** ReStockFunc 사용여부(Y / N) */
	private String reStockFuncFlag;
	/** ReStockAlert버튼 노출여부(Y / N) */
	private String reStockAlertFlag;
	
	
	/* Getters & Setters */
	public String getObsInventoryFlag() {
		return obsInventoryFlag;
	}
	public String getObsPriceFlag() {
		return obsPriceFlag;
	}
	public void setObsPriceFlag(String obsPriceFlag) {
		this.obsPriceFlag = obsPriceFlag;
	}
	public void setObsInventoryFlag(String obsInventoryFlag) {
		this.obsInventoryFlag = (obsInventoryFlag == null ? "N" : obsInventoryFlag);
	}
	public String getObsSellFlag() {
		return obsSellFlag;
	}
	public void setObsSellFlag(String obsSellFlag) {
		this.obsSellFlag = (obsSellFlag == null ? "N" : obsSellFlag);
	}
	public String getObsLoginFlag() {
		return obsLoginFlag;
	}
	public void setObsLoginFlag(String obsLoginFlag) {
		this.obsLoginFlag = (obsLoginFlag == null ? "N" : obsLoginFlag);
	}
	public String geteCommerceFlag() {
		return eCommerceFlag;
	}
	public void seteCommerceFlag(String eCommerceFlag) {
		this.eCommerceFlag = (eCommerceFlag == null ? "N" : eCommerceFlag);
	}
	public String getModelStatusCode() {
		return modelStatusCode;
	}
	public void setModelStatusCode(String modelStatusCode) {
		this.modelStatusCode = modelStatusCode;
	}
	public BigDecimal getObsSellingPrice() {
		return obsSellingPrice;
	}
	public void setObsSellingPrice(BigDecimal obsSellingPrice) {
		this.obsSellingPrice = (obsSellingPrice == null ? BigDecimal.ZERO : obsSellingPrice);
	}
	public BigDecimal getMsrp() {
		return msrp;
	}
	public void setMsrp(BigDecimal msrp) {
		this.msrp = (msrp == null ? BigDecimal.ZERO : msrp);
	}
	public BigDecimal getPromotionPrice() {
		return promotionPrice;
	}
	public void setPromotionPrice(BigDecimal promotionPrice) {
		this.promotionPrice = (promotionPrice == null ? BigDecimal.ZERO : promotionPrice);
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPriceCent() {
		return priceCent;
	}
	public void setPriceCent(String priceCent) {
		this.priceCent = priceCent;
	}
	public String getPromoPrice() {
		return promoPrice;
	}
	public void setPromoPrice(String promoPrice) {
		this.promoPrice = promoPrice;
	}
	public String getPromoPriceCent() {
		return promoPriceCent;
	}
	public void setPromoPriceCent(String promoPriceCent) {
		this.promoPriceCent = promoPriceCent;
	}
	public String getDiscountedRate() {
		return discountedRate;
	}
	public void setDiscountedRate(String discountedRate) {
		this.discountedRate = discountedRate;
	}
	
	/* LGEUS-12083 Start */
	public String getDiscountedPrice() {
		return discountedPrice;
	}
	public void setDiscountedPrice(String discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
	public String getDiscountedPriceCent() {
		return discountedPriceCent;
	}
	public void setDiscountedPriceCent(String discountedPriceCent) {
		this.discountedPriceCent = discountedPriceCent;
	}
	/* LGEUS-12083 End */
	
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getWtbFlag() {
		return wtbFlag;
	}
	public void setWtbFlag(String wtbFlag) {
		this.wtbFlag = (wtbFlag == null ? "N" : wtbFlag);
	}
	public String getCtaDisalbeWtb() {
		return ctaDisalbeWtb;
	}
	public void setCtaDisalbeWtb(String ctaDisalbeWtb) {
		this.ctaDisalbeWtb = (ctaDisalbeWtb == null ? "N" : ctaDisalbeWtb);
	}
	public String getAddToCartFlag() {
		return addToCartFlag;
	}
	public void setAddToCartFlag(String addToCartFlag) {
		this.addToCartFlag = addToCartFlag;
	}
	public String getWhereToBuyFlag() {
		return whereToBuyFlag;
	}
	public void setWhereToBuyFlag(String whereToBuyFlag) {
		this.whereToBuyFlag = whereToBuyFlag;
	}
	
	// LGEUS-12198
	public String getWtbExternalLinkUseFlag() {
		return wtbExternalLinkUseFlag;
	}
	public void setWtbExternalLinkUseFlag(String wtbExternalLinkUseFlag) {
		this.wtbExternalLinkUseFlag = wtbExternalLinkUseFlag;
	}
	// LGEUS-12198 End
	
	public String getInquiryToBuyFlag() {
		return inquiryToBuyFlag;
	}
	public void setInquiryToBuyFlag(String inquiryToBuyFlag) {
		this.inquiryToBuyFlag = inquiryToBuyFlag;
	}
	public String getFindTheDealerFlag() {
		return findTheDealerFlag;
	}
	public void setFindTheDealerFlag(String findTheDealerFlag) {
		this.findTheDealerFlag = findTheDealerFlag;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getWtbType() {
		return wtbType;
	}
	public void setWtbType(String wtbType) {
		this.wtbType = wtbType;
	}
	public String getCtaDisalbeCart() {
		return ctaDisalbeCart;
	}
	public void setCtaDisalbeCart(String ctaDisalbeCart) {
		this.ctaDisalbeCart = ctaDisalbeCart;
	}
	public String getWtbExternalLinkName() {
		return wtbExternalLinkName;
	}
	public void setWtbExternalLinkName(String wtbExternalLinkName) {
		this.wtbExternalLinkName = wtbExternalLinkName;
	}
	public String getBuyNowFlag() {
		return buyNowFlag;
	}
	public void setBuyNowFlag(String buyNowFlag) {
		this.buyNowFlag = buyNowFlag;
	}
	public String getProductSupportFlag() {
		return productSupportFlag;
	}
	public void setProductSupportFlag(String productSupportFlag) {
		this.productSupportFlag = productSupportFlag;
	}
	public String getResellerFlag() {
		return resellerFlag;
	}
	public void setResellerFlag(String resellerFlag) {
		this.resellerFlag = resellerFlag;
	}
	public String getResellerLinkUrl() {
		return resellerLinkUrl;
	}
	public void setResellerLinkUrl(String resellerLinkUrl) {
		this.resellerLinkUrl = resellerLinkUrl;
	}
	public String getBuynowUrl() {
		return buynowUrl;
	}
	public void setBuynowUrl(String buynowUrl) {
		this.buynowUrl = buynowUrl;
	}
	public String getReStockFuncFlag() {
		return reStockFuncFlag;
	}
	public void setReStockFuncFlag(String reStockFuncFlag) {
		this.reStockFuncFlag = reStockFuncFlag;
	}
	public String getReStockAlertFlag() {
		return reStockAlertFlag;
	}
	public void setReStockAlertFlag(String reStockAlertFlag) {
		this.reStockAlertFlag = reStockAlertFlag;
	}
	
	
}