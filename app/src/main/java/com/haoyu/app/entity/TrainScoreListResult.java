package com.haoyu.app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 创建日期：2017/1/19 on 18:07
 * 描述:
 * 作者:马飞奔 Administrator
 */
public class TrainScoreListResult {
    @Expose
    @SerializedName("responseCode")
    private String responseCode;
    @Expose
    @SerializedName("responseData")
    private TrainScoreData responseData;
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

    public TrainScoreData getResponseData() {
        return responseData;
    }

    public void setResponseData(TrainScoreData responseData) {
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

    public class TrainScoreData{
        @Expose
        @SerializedName("mTrainRegisters")
        private List<MTrainRegisterStat> mTrainRegisterStat;
        @Expose
        @SerializedName("paginator")
        private Paginator paginator;

        public List<MTrainRegisterStat> getmTrainRegisterStat() {
            return mTrainRegisterStat;
        }

        public void setmTrainRegisterStat(List<MTrainRegisterStat> mTrainRegisterStat) {
            this.mTrainRegisterStat = mTrainRegisterStat;
        }

        public Paginator getPaginator() {
            return paginator;
        }

        public void setPaginator(Paginator paginator) {
            this.paginator = paginator;
        }
    }
}
