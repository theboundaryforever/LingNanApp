package com.haoyu.app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 创建日期：2017/2/4 on 15:41
 * 描述: 教师版首页结果集（参与辅导的课程列表、参与管理的工作坊列表）
 * 作者:马飞奔 Administrator
 */
public class TeacherHomePageResult {
    @Expose
    @SerializedName("responseCode")
    private String responseCode;
    @Expose
    @SerializedName("responseData")
    private TeacherHomePageData responseData;
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

    public TeacherHomePageData getResponseData() {
        return responseData;
    }

    public void setResponseData(TeacherHomePageData responseData) {
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

    public class TeacherHomePageData {
        @Expose
        @SerializedName("mCourses")
        private List<CourseMobileEntity> mCourses;
        @Expose
        @SerializedName("mWorkshops")
        private List<WorkShopMobileEntity> mWorkshops;

        public List<CourseMobileEntity> getmCourses() {
            return mCourses;
        }

        public void setmCourses(List<CourseMobileEntity> mCourses) {
            this.mCourses = mCourses;
        }

        public List<WorkShopMobileEntity> getmWorkshops() {
            return mWorkshops;
        }

        public void setmWorkshops(List<WorkShopMobileEntity> mWorkshops) {
            this.mWorkshops = mWorkshops;
        }
    }
}
