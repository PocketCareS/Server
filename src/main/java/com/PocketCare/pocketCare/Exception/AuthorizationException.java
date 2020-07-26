package com.PocketCare.pocketCare.Exception;

public class AuthorizationException extends CustomException {
    Integer status;
    String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AuthorizationException(){
        this.status = 401;
        this.message = "Authorization exception";
    }
}
