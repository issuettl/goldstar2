package lgekorfrm.sso.emp;

import java.io.Serializable;

public class EmpTokenValidationResponse implements Serializable {
    private String user_number;
    private String account_type;
    private Long expireTime;

    private EmpError error;

    public String getUser_number() {
        return user_number;
    }

    public void setUser_number(String user_number) {
        this.user_number = user_number;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
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
            return "user_number : " + user_number + ", account_type : " + account_type + ", expireTime : " + expireTime;
        }
    }
}
