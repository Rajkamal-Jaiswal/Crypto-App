package com.e.cellpaycrypto.apimodels;

import com.google.gson.annotations.SerializedName;

public class ModelResponse {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("resultCode")
    private String resultCode;

    @SerializedName("message")
    private String message;

    public String getUserId() {
        return userId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getMessage() {
        return message;
    }
}