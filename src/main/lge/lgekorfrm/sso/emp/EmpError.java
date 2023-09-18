package lgekorfrm.sso.emp;

import java.io.Serializable;

public class EmpError implements Serializable {
    private String request;
    private String code;
    private String service;
    private String message;
    private String refreshToken;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String toString() {
        return "request : " + request + ", code : " + code + ", service : " + service + ", message : " + message+ ", refreshToken : " + refreshToken;
    }

}
