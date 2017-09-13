package com.haoyu.app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 创建日期：2017/2/8 on 16:17
 * 描述:
 * 作者:马飞奔 Administrator
 */
public class StudentStatisticListResult {
    @Expose
    @SerializedName("responseCode")
    private String responseCode;
    @Expose
    @SerializedName("responseData")
    private StudentStatisticData responseData;
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

    public StudentStatisticData getResponseData() {
        return responseData;
    }

    public void setResponseData(StudentStatisticData responseData) {
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

    public class StudentStatisticData{
        @Expose
        @SerializedName("mCourseRegisterStats")
        private List<CourseRegisterStats> mCourseRegisterStats;
        @Expose
        @SerializedName("paginator")
        private Paginator paginator;

        public List<CourseRegisterStats> getmCourseRegisterStats() {
            return mCourseRegisterStats;
        }

        public void setmCourseRegisterStats(List<CourseRegisterStats> mCourseRegisterStats) {
            this.mCourseRegisterStats = mCourseRegisterStats;
        }

        public Paginator getPaginator() {
            return paginator;
        }

        public void setPaginator(Paginator paginator) {
            this.paginator = paginator;
        }
    }
}
