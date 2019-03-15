package com.example.bob.health_helper.Bean;

import com.google.gson.annotations.SerializedName;

public class Response<T> {
    @SerializedName("success")
    private boolean success;
    @SerializedName("err")
    private java.lang.Error err;
    @SerializedName("data")
    private T data;

    public boolean getSuccess() {
        return success;
    }

    public java.lang.Error getErr() {
        return err;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
