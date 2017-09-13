package com.haoyu.app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 创建日期：2017/2/6 on 11:33
 * 描述:作业相关数量结果集
 * 作者:马飞奔 Administrator
 */
public class AssignmentUserNumResult implements Serializable{
    @Expose
    @SerializedName("responseCode")
    private String responseCode;
    @Expose
    @SerializedName("responseData")
    private AssignmentUserNumData responseData;
    @Expose
    @SerializedName("responseMsg")
    private String responseMsg;
    @Expose
    @SerializedName("success")
    private Boolean success;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public AssignmentUserNumData getResponseData() {
        return responseData;
    }

    public void setResponseData(AssignmentUserNumData responseData) {
        this.responseData = responseData;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public class AssignmentUserNumData implements Serializable{
        @Expose
        @SerializedName("notReceivedNum")
        private int notReceivedNum;  //待领取数
        @Expose
        @SerializedName("markNum")
        private int markNum;  //已批阅数
        @Expose
        @SerializedName("allNum")
        private int allNum;  //全部数量
        @Expose
        @SerializedName("notMarkedNum")
        private int notMarkedNum;  //未批阅数

        public int getNotReceivedNum() {
            return notReceivedNum;
        }

        public void setNotReceivedNum(int notReceivedNum) {
            this.notReceivedNum = notReceivedNum;
        }

        public int getMarkNum() {
            return markNum;
        }

        public void setMarkNum(int markNum) {
            this.markNum = markNum;
        }

        public int getAllNum() {
            return allNum;
        }

        public void setAllNum(int allNum) {
            this.allNum = allNum;
        }

        public int getNotMarkedNum() {
            return notMarkedNum;
        }

        public void setNotMarkedNum(int notMarkedNum) {
            this.notMarkedNum = notMarkedNum;
        }
    }
}
