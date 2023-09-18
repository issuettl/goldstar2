package lgekorfrm.sso.emp;

import java.io.Serializable;

public class EmpAccessTokenResponse implements Serializable {
    private String access_token;
    private String refresh_token;
    private String expires_in;
    private String oauth2_backend_url;

    private EmpError error;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getOauth2_backend_url() {
        return oauth2_backend_url;
    }

    public void setOauth2_backend_url(String oauth2_backend_url) {
        this.oauth2_backend_url = oauth2_backend_url;
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
            return "access_token : " + access_token + ", refresh_token : " + refresh_token + ", expires_in : " + expires_in + ", oauth2_backend_url : " + oauth2_backend_url;
        }
    }
}
