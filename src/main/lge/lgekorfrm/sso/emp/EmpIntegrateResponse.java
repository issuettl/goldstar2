package lgekorfrm.sso.emp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class EmpIntegrateResponse implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpIntegrateResponse.class);

    private EmpUserProfileAccount account;
    private EmpError error;

    public EmpUserProfileAccount getAccount() {
        return account;
    }

    public void setAccount(EmpUserProfileAccount account) {
        this.account = account;
    }

    public EmpError getError() {
        return error;
    }

    public void setError(EmpError error) {
        this.error = error;
    }

    public String toString() {
        if(error != null){
            return error.toString();
        }else {
            return account.toString();
        }
    }

    /**
     * EMP ID 통합 여부
     * response 에 account Object 가 존재하거나, 에러코드 중 MR.024.26 인 경우 통합으로 판단한다.
     * @return
     */
    public boolean isIntegrated() {
        boolean needIntegrate = false;
        try {
            if(this.account != null || (this.error != null && this.error.getCode().trim().equals("MR.024.26"))){
                needIntegrate = true;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return needIntegrate;
    }

    /**
     * EMP ID통합 가능한 케이스.
     * response error code : MR.024.23, MR.024.24
     * @return
     */
    public boolean availableIntegrate() {
        boolean needIntegrate = false;
        try {
            if (this.error != null && (this.error.getCode().trim().equals("MR.024.23")
                    || this.error.getCode().trim().equals("MR.024.24"))) {
                needIntegrate = true;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return needIntegrate;
    }

    /**
     * ThinQ 계정으로 한영회 계정이 존재하지 않는 경우 (서비스 추가가입필요)
     * response error code : MR.024.25
     * @return
     */
    public boolean needSignUp(){
        boolean needSignUp = false;
        try {
            if (this.error != null && this.error.getCode().trim().equals("MR.024.25")) {
                needSignUp = true;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return needSignUp;
    }

    /**
     * 통합대상UserNo
     * "message" : "Suitable Account[itg_trgt_user_no:,itg_trgt_except_user_no:,]"
     * @return
     */
    public String getItgTrgtUserNo() {
        String itgTrgtUserNo = "";
        if (availableIntegrate()) {
            String keyUserNo = "itg_trgt_user_no";
            itgTrgtUserNo = this.error.getMessage().substring(
                    this.error.getMessage().indexOf(keyUserNo) + keyUserNo.length() + 1,
                    this.error.getMessage().indexOf(","));  //indexOf
        }
        return itgTrgtUserNo;
    }

    /**
     * //통합대상제외UserNo
     * "message" : "Suitable Account[itg_trgt_user_no:,itg_trgt_except_user_no:,]"
     * @return
     */
    public String getItgTrgtExceptUserNo() {
        String itgTrgtExceptUserNo = "";
        if (availableIntegrate()) {
            String keyExceptUserNo = "itg_trgt_except_user_no";
            itgTrgtExceptUserNo = this.error.getMessage().substring(
                    this.error.getMessage().indexOf(keyExceptUserNo) + keyExceptUserNo.length() + 1,
                    this.error.getMessage().lastIndexOf(","));  //lastIndexOf
        }
        return itgTrgtExceptUserNo;
    }


}
