package lgekorfrm.sso.member;

import java.io.Serializable;

public class MemberInformation implements Serializable{

	private static final long serialVersionUID = 1L;
		
	private String ci              ;                    // 동일 이용자 구분 연계정보
	private String di              ;                    // 중복가입 확인정보
	private String email           ;                    // 이메일
	private String mobileNo        ;                    // 휴대폰번호
	private String name            ;                    // 이름
	private String nationCd        ;                    // 내외국인구분코드
	private String zip             ;                    // 우편번호
	private String address1        ;                    // 주소1
	private String address2        ;                    // 주소2
	private String withDt          ;                    // 통합회원 탈퇴일
	private String imei            ;                    // 기기정보(App only)
	private String userId          ;                    // 사용자ID
	private String infoChgDt       ;                    // 회원정보 변경일시
	private String memNo           ;                    // 회원ID
	private String emailAgreeDtm   ;                    // 이메일 동의일시
	private String smsAgreeDtm     ;                    // SMS 동의일시
	private String mobileYn        ;                    // 휴대전화(TM) 동의여부
	private String mobileAgreeDtm  ;                    // 휴대전화(TM) 동의일시
	private String appPushAgreeDtm ;                    // 앱푸시 동의일시
	private String cacaoAgreeDtm   ;                    // 카카오(친구톡) 동의일시
	private String birthYY         ;                    // 출생 년도
	private String autoLoginYn     ;                    // 자동로그인여부
	private String managerNm       ;                    // 상담매니져(추천인)
	private String unifyId         ;                    // 온라인 통합계정 고객번호
	private String preferStoreArea1;                    // 선호매장지역1
	private String preferStoreArea2;                    // 선호매장지역2
	private String preferStore     ;                    // 선호매장
	private String birthDt         ;                    // 생년월일
	private String sexCd           ;                    // 성별구분코드
	private String region          ;                    // 지역 구분
	private String lastUseDt       ;                    // 마지막 이용일시
	private String dorDt           ;                    // 휴면전환(예정)일
	private String model           ;                    // 단말기정보(App only)
	private String empNo           ;                    // emp고객번호
	private String bestNo          ;                    // 베스트 고객번호
	private String memberUseYn     ;                    // 멤버십이용여부
	private String emailYn         ;                    // 이메일 동의여부
	private String smsYn           ;                    // SMS 동의여부
	private String appPushYn       ;                    // 앱푸시 동의여부
	private String cacaoYn         ;                    // 카카오(친구톡) 동의여부
	
	private String mallYn      ;                    // LGE온라인몰 가입여부
	private String careYn      ;                    // LGE 케어솔루션 가입여부
	private String bestshopYn  ;                    // LGE 베스트샵 가입여부
	private String membershipYn;                    // LGE 멤버십 가입여부
	private String mainYn      ;                    // LGE 대표사이트 가입여부 
	
	private String agNum     ;                    // 선호매장코드
	private String regDtm    ;						// 통합회원 가입일
	

	/**
	 * @return the agNum
	 */
	public String getAgNum() {
		return agNum;
	}
	/**
	 * @param agNum the agNum to set
	 */
	public void setAgNum(String agNum) {
		this.agNum = agNum;
	}
	
	/**
	 * @return the ci
	 */
	public String getCi() {
		return ci;
	}
	/**
	 * @param ci the ci to set
	 */
	public void setCi(String ci) {
		this.ci = ci;
	}
	/**
	 * @return the di
	 */
	public String getDi() {
		return di;
	}
	/**
	 * @param di the di to set
	 */
	public void setDi(String di) {
		this.di = di;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}
	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the nationCd
	 */
	public String getNationCd() {
		return nationCd;
	}
	/**
	 * @param nationCd the nationCd to set
	 */
	public void setNationCd(String nationCd) {
		this.nationCd = nationCd;
	}
	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}
	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}
	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @return the withDt
	 */
	public String getWithDt() {
		return withDt;
	}
	/**
	 * @param withDt the withDt to set
	 */
	public void setWithDt(String withDt) {
		this.withDt = withDt;
	}
	/**
	 * @return the imei
	 */
	public String getImei() {
		return imei;
	}
	/**
	 * @param imei the imei to set
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the infoChgDt
	 */
	public String getInfoChgDt() {
		return infoChgDt;
	}
	/**
	 * @param infoChgDt the infoChgDt to set
	 */
	public void setInfoChgDt(String infoChgDt) {
		this.infoChgDt = infoChgDt;
	}
	/**
	 * @return the memNo
	 */
	public String getMemNo() {
		return memNo;
	}
	/**
	 * @param memNo the memNo to set
	 */
	public void setMemNo(String memNo) {
		this.memNo = memNo;
	}
	/**
	 * @return the emailAgreeDtm
	 */
	public String getEmailAgreeDtm() {
		return emailAgreeDtm;
	}
	/**
	 * @param emailAgreeDtm the emailAgreeDtm to set
	 */
	public void setEmailAgreeDtm(String emailAgreeDtm) {
		this.emailAgreeDtm = emailAgreeDtm;
	}
	/**
	 * @return the smsAgreeDtm
	 */
	public String getSmsAgreeDtm() {
		return smsAgreeDtm;
	}
	/**
	 * @param smsAgreeDtm the smsAgreeDtm to set
	 */
	public void setSmsAgreeDtm(String smsAgreeDtm) {
		this.smsAgreeDtm = smsAgreeDtm;
	}
	/**
	 * @return the mobileYn
	 */
	public String getMobileYn() {
		return mobileYn;
	}
	/**
	 * @param mobileYn the mobileYn to set
	 */
	public void setMobileYn(String mobileYn) {
		this.mobileYn = mobileYn;
	}
	/**
	 * @return the mobileAgreeDtm
	 */
	public String getMobileAgreeDtm() {
		return mobileAgreeDtm;
	}
	/**
	 * @param mobileAgreeDtm the mobileAgreeDtm to set
	 */
	public void setMobileAgreeDtm(String mobileAgreeDtm) {
		this.mobileAgreeDtm = mobileAgreeDtm;
	}
	/**
	 * @return the appPushAgreeDtm
	 */
	public String getAppPushAgreeDtm() {
		return appPushAgreeDtm;
	}
	/**
	 * @param appPushAgreeDtm the appPushAgreeDtm to set
	 */
	public void setAppPushAgreeDtm(String appPushAgreeDtm) {
		this.appPushAgreeDtm = appPushAgreeDtm;
	}
	/**
	 * @return the cacaoAgreeDtm
	 */
	public String getCacaoAgreeDtm() {
		return cacaoAgreeDtm;
	}
	/**
	 * @param cacaoAgreeDtm the cacaoAgreeDtm to set
	 */
	public void setCacaoAgreeDtm(String cacaoAgreeDtm) {
		this.cacaoAgreeDtm = cacaoAgreeDtm;
	}
	/**
	 * @return the birthYY
	 */
	public String getBirthYY() {
		return birthYY;
	}
	/**
	 * @param birthYY the birthYY to set
	 */
	public void setBirthYY(String birthYY) {
		this.birthYY = birthYY;
	}
	/**
	 * @return the autoLoginYn
	 */
	public String getAutoLoginYn() {
		return autoLoginYn;
	}
	/**
	 * @param autoLoginYn the autoLoginYn to set
	 */
	public void setAutoLoginYn(String autoLoginYn) {
		this.autoLoginYn = autoLoginYn;
	}
	/**
	 * @return the managerNm
	 */
	public String getManagerNm() {
		return managerNm;
	}
	/**
	 * @param managerNm the managerNm to set
	 */
	public void setManagerNm(String managerNm) {
		this.managerNm = managerNm;
	}
	/**
	 * @return the unifyId
	 */
	public String getUnifyId() {
		return unifyId;
	}
	/**
	 * @param unifyId the unifyId to set
	 */
	public void setUnifyId(String unifyId) {
		this.unifyId = unifyId;
	}
	/**
	 * @return the preferStoreArea1
	 */
	public String getPreferStoreArea1() {
		return preferStoreArea1;
	}
	/**
	 * @param preferStoreArea1 the preferStoreArea1 to set
	 */
	public void setPreferStoreArea1(String preferStoreArea1) {
		this.preferStoreArea1 = preferStoreArea1;
	}
	/**
	 * @return the preferStoreArea2
	 */
	public String getPreferStoreArea2() {
		return preferStoreArea2;
	}
	/**
	 * @param preferStoreArea2 the preferStoreArea2 to set
	 */
	public void setPreferStoreArea2(String preferStoreArea2) {
		this.preferStoreArea2 = preferStoreArea2;
	}
	/**
	 * @return the preferStore
	 */
	public String getPreferStore() {
		return preferStore;
	}
	/**
	 * @param preferStore the preferStore to set
	 */
	public void setPreferStore(String preferStore) {
		this.preferStore = preferStore;
	}
	/**
	 * @return the birthDt
	 */
	public String getBirthDt() {
		return birthDt;
	}
	/**
	 * @param birthDt the birthDt to set
	 */
	public void setBirthDt(String birthDt) {
		this.birthDt = birthDt;
	}
	/**
	 * @return the sexCd
	 */
	public String getSexCd() {
		return sexCd;
	}
	/**
	 * @param sexCd the sexCd to set
	 */
	public void setSexCd(String sexCd) {
		this.sexCd = sexCd;
	}
	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	/**
	 * @return the lastUseDt
	 */
	public String getLastUseDt() {
		return lastUseDt;
	}
	/**
	 * @param lastUseDt the lastUseDt to set
	 */
	public void setLastUseDt(String lastUseDt) {
		this.lastUseDt = lastUseDt;
	}
	/**
	 * @return the dorDt
	 */
	public String getDorDt() {
		return dorDt;
	}
	/**
	 * @param dorDt the dorDt to set
	 */
	public void setDorDt(String dorDt) {
		this.dorDt = dorDt;
	}
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * @return the empNo
	 */
	public String getEmpNo() {
		return empNo;
	}
	/**
	 * @param empNo the empNo to set
	 */
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	/**
	 * @return the bestNo
	 */
	public String getBestNo() {
		return bestNo;
	}
	/**
	 * @param bestNo the bestNo to set
	 */
	public void setBestNo(String bestNo) {
		this.bestNo = bestNo;
	}
	/**
	 * @return the memberUseYn
	 */
	public String getMemberUseYn() {
		return memberUseYn;
	}
	/**
	 * @param memberUseYn the memberUseYn to set
	 */
	public void setMemberUseYn(String memberUseYn) {
		this.memberUseYn = memberUseYn;
	}
	/**
	 * @return the emailYn
	 */
	public String getEmailYn() {
		return emailYn;
	}
	/**
	 * @param emailYn the emailYn to set
	 */
	public void setEmailYn(String emailYn) {
		this.emailYn = emailYn;
	}
	/**
	 * @return the smsYn
	 */
	public String getSmsYn() {
		return smsYn;
	}
	/**
	 * @param smsYn the smsYn to set
	 */
	public void setSmsYn(String smsYn) {
		this.smsYn = smsYn;
	}
	/**
	 * @return the appPushYn
	 */
	public String getAppPushYn() {
		return appPushYn;
	}
	/**
	 * @param appPushYn the appPushYn to set
	 */
	public void setAppPushYn(String appPushYn) {
		this.appPushYn = appPushYn;
	}
	/**
	 * @return the cacaoYn
	 */
	public String getCacaoYn() {
		return cacaoYn;
	}
	/**
	 * @param cacaoYn the cacaoYn to set
	 */
	public void setCacaoYn(String cacaoYn) {
		this.cacaoYn = cacaoYn;
	}
	
	/**
	 * @return the mallYn
	 */
	public String getMallYn() {
		return mallYn;
	}
	/**
	 * @param mallYn the mallYn to set
	 */
	public void setMallYn(String mallYn) {
		this.mallYn = mallYn;
	}
	/**
	 * @return the careYn
	 */
	public String getCareYn() {
		return careYn;
	}
	/**
	 * @param careYn the careYn to set
	 */
	public void setCareYn(String careYn) {
		this.careYn = careYn;
	}
	/**
	 * @return the bestshopYn
	 */
	public String getBestshopYn() {
		return bestshopYn;
	}
	/**
	 * @param bestshopYn the bestshopYn to set
	 */
	public void setBestshopYn(String bestshopYn) {
		this.bestshopYn = bestshopYn;
	}
	/**
	 * @return the membershipYn
	 */
	public String getMembershipYn() {
		return membershipYn;
	}
	/**
	 * @param membershipYn the membershipYn to set
	 */
	public void setMembershipYn(String membershipYn) {
		this.membershipYn = membershipYn;
	}
	/**
	 * @return the mainYn
	 */
	public String getMainYn() {
		return mainYn;
	}
	/**
	 * @param mainYn the mainYn to set
	 */
	public void setMainYn(String mainYn) {
		this.mainYn = mainYn;
	}
	
	public String getRegDtm() {
		return regDtm;
	}
	public void setRegDtm(String regDtm) {
		this.regDtm = regDtm;
	}
	
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MembemInfoSessionData [ci=" + ci + ", di=" + di + ", email=" + email
				+ ", mobileNo=" + mobileNo + ", name=" + name + ", nationCd=" + nationCd + ", zip=" + zip
				+ ", address1=" + address1 + ", address2=" + address2 + ", withDt=" + withDt + ", imei=" + imei
				+ ", userId=" + userId + ", infoChgDt=" + infoChgDt + ", memNo=" + memNo + ", emailAgreeDtm="
				+ emailAgreeDtm + ", smsAgreeDtm=" + smsAgreeDtm + ", mobileYn=" + mobileYn + ", mobileAgreeDtm="
				+ mobileAgreeDtm + ", appPushAgreeDtm=" + appPushAgreeDtm + ", cacaoAgreeDtm=" + cacaoAgreeDtm
				+ ", birthYY=" + birthYY + ", autoLoginYn=" + autoLoginYn + ", managerNm=" + managerNm + ", unifyId="
				+ unifyId + ", preferStoreArea1=" + preferStoreArea1 + ", preferStoreArea2=" + preferStoreArea2
				+ ", preferStore=" + preferStore + ", birthDt=" + birthDt + ", sexCd=" + sexCd + ", region=" + region
				+ ", lastUseDt=" + lastUseDt + ", dorDt=" + dorDt + ", model=" + model + ", empNo=" + empNo
				+ ", bestNo=" + bestNo + ", memberUseYn=" + memberUseYn + ", emailYn=" + emailYn + ", smsYn=" + smsYn
				+ ", appPushYn=" + appPushYn + ", cacaoYn=" + cacaoYn + ", mallYn=" + mallYn + ", careYn=" + careYn
				+ ", bestshopYn=" + bestshopYn + ", membershipYn=" + membershipYn + ", mainYn=" + mainYn + ", regDtm= "+regDtm+"]";
	}

	
	
}