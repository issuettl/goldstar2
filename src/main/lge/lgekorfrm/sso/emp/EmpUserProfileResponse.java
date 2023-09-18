package lgekorfrm.sso.emp;

import java.io.Serializable;

public class EmpUserProfileResponse implements Serializable {

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
}
