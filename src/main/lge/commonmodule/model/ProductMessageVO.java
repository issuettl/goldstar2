/*******************************************************************************
 * FILE: /java/lgcommkt/commonmodule/model/ProductMessageVO.java
 * DESC : Button MessageCode VO
 *
 * PROJ : LG.com 5.0
 *******************************************************************************
 *                  Modification History
 *******************************************************************************
 * CSR No.      DATE              AUTHOR             DESCRIPTION
 *******************************************************************************
 *            2019/11/04       GP Rollout SI        Initial Release
 * PJTOBS-3    2020/07/10       Jiseob.shin        	 	reStockAlertPopup 재고알림
 *******************************************************************************/
package lgekormkt.commonmodule.model;

import java.util.Map;

public class ProductMessageVO {

	/** Add To Cart Button Name */
	private String addToCartBtnNm;
	
	/** Where To Buy Button Name */
	private String whereToBuyBtnNm;
	
	/** Inquiry To Buy Button Name */
	private String inquiryToBuyBtnNm;
	
	/** Find The Dealer Button Name */
	private String findTheDealerBtnNm;
	
	/** Pre Order Button Name */
	private String preOrderBtnNm;
	
	/** ATT Button Name */
	private String attBtnNm;
	
	/** WTB External Button Name */
	private String wtbExternalBtnNm;
	
	/** Buy Now Button Name */
	private String buyNowBtnNm;
	
	/** To the authorized reseller Button Name */
	private String resellerBtnNm;
	
	/** Product Support Button Name */
	private String productSupportBtnNm;

	/** Button Target New Link Title */
	private String btnNewLinkTitle;
	
	/** Re Stock Alert Button Name */
	private String reStockAlertBtnNm;
	
	/** OutOfStockText */
	private String outOfStockText;

	private Map<String, Object> siblingsMsg;
	
	public String getAddToCartBtnNm() {
		return addToCartBtnNm;
	}

	public void setAddToCartBtnNm(String addToCartBtnNm) {
		this.addToCartBtnNm = addToCartBtnNm;
	}

	public String getWhereToBuyBtnNm() {
		return whereToBuyBtnNm;
	}

	public void setWhereToBuyBtnNm(String whereToBuyBtnNm) {
		this.whereToBuyBtnNm = whereToBuyBtnNm;
	}

	public String getInquiryToBuyBtnNm() {
		return inquiryToBuyBtnNm;
	}

	public void setInquiryToBuyBtnNm(String inquiryToBuyBtnNm) {
		this.inquiryToBuyBtnNm = inquiryToBuyBtnNm;
	}

	public String getFindTheDealerBtnNm() {
		return findTheDealerBtnNm;
	}

	public void setFindTheDealerBtnNm(String findTheDealerBtnNm) {
		this.findTheDealerBtnNm = findTheDealerBtnNm;
	}

	public String getPreOrderBtnNm() {
		return preOrderBtnNm;
	}

	public void setPreOrderBtnNm(String preOrderBtnNm) {
		this.preOrderBtnNm = preOrderBtnNm;
	}

	public String getAttBtnNm() {
		return attBtnNm;
	}

	public void setAttBtnNm(String attBtnNm) {
		this.attBtnNm = attBtnNm;
	}

	public String getWtbExternalBtnNm() {
		return wtbExternalBtnNm;
	}

	public void setWtbExternalBtnNm(String wtbExternalBtnNm) {
		this.wtbExternalBtnNm = wtbExternalBtnNm;
	}

	public String getBuyNowBtnNm() {
		return buyNowBtnNm;
	}

	public void setBuyNowBtnNm(String buyNowBtnNm) {
		this.buyNowBtnNm = buyNowBtnNm;
	}

	public String getResellerBtnNm() {
		return resellerBtnNm;
	}

	public void setResellerBtnNm(String resellerBtnNm) {
		this.resellerBtnNm = resellerBtnNm;
	}

	public String getProductSupportBtnNm() {
		return productSupportBtnNm;
	}

	public void setProductSupportBtnNm(String productSupportBtnNm) {
		this.productSupportBtnNm = productSupportBtnNm;
	}

	public String getBtnNewLinkTitle() {
		return btnNewLinkTitle;
	}

	public void setBtnNewLinkTitle(String btnNewLinkTitle) {
		this.btnNewLinkTitle = btnNewLinkTitle;
	}

	public Map<String, Object> getSiblingsMsg() {
		return siblingsMsg;
	}

	public void setSiblingsMsg(Map<String, Object> siblingsMsg) {
		this.siblingsMsg = siblingsMsg;
	}

	public String getReStockAlertBtnNm() {
		return reStockAlertBtnNm;
	}

	public void setReStockAlertBtnNm(String reStockAlertBtnNm) {
		this.reStockAlertBtnNm = reStockAlertBtnNm;
	}

	public String getOutOfStockText() {
		return outOfStockText;
	}

	public void setOutOfStockText(String outOfStockText) {
		this.outOfStockText = outOfStockText;
	}
	
}
