package com.haoyu.app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by acer1 on 2017/1/7.
 * <p>
 * 学员互评
 */
public class CorrectResult {
    @Expose
    @SerializedName("responseCode")
    private String responseCode;
    @Expose
    @SerializedName("responseMsg")
    private String responseMsg;
    @Expose
    @SerializedName("responseData")
    private CorrectResponseData responseData;
    @Expose
    @SerializedName("success")
    private boolean success;

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getResponseMsg() {
        return this.responseMsg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public CorrectResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(CorrectResponseData responseData) {
        this.responseData = responseData;
    }

    public boolean isSuccess() {
        return success;
    }
}
