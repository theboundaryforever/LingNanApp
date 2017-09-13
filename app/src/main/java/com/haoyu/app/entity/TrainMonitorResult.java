package com.haoyu.app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 创建日期：2017/1/18 on 13:56
 * 描述:
 * 作者:马飞奔 Administrator
 */
public class TrainMonitorResult {
    @Expose
    @SerializedName("responseCode")
    private String responseCode;
    @Expose
    @SerializedName("responseData")
    private TrainMonitorData responseData;
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

    public TrainMonitorData getResponseData() {
        return responseData;
    }

    public void setResponseData(TrainMonitorData responseData) {
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

    public class TrainMonitorData {
        @Expose
        @SerializedName("registerHeadcount")
        private int registerHeadcount;     //报名人数
        @Expose
        @SerializedName("participateHeadcount")
        private int participateHeadcount;     //参训人数
        @Expose
        @SerializedName("passHeadcount")
        private int passHeadcount;      //合格人数

        public int getRegisterHeadcount() {
            return registerHeadcount;
        }

        public void setRegisterHeadcount(int registerHeadcount) {
            this.registerHeadcount = registerHeadcount;
        }

        public int getParticipateHeadcount() {
            return participateHeadcount;
        }

        public void setParticipateHeadcount(int participateHeadcount) {
            this.participateHeadcount = participateHeadcount;
        }

        public int getPassHeadcount() {
            return passHeadcount;
        }

        public void setPassHeadcount(int passHeadcount) {
            this.passHeadcount = passHeadcount;
        }
    }
}
