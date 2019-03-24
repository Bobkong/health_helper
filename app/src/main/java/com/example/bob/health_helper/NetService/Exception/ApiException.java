package com.example.bob.health_helper.NetService.Exception;

public class ApiException extends Exception {
    private int code;
    private String displayMessage;

    public ApiException(Throwable throwable,int code){
        super(throwable);
        this.code=code;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
