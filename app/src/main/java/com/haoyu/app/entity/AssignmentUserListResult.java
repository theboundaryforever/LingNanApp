package com.haoyu.app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 创建日期：2017/2/6 on 12:38
 * 描述:已领取作业列表
 * 作者:马飞奔 Administrator
 */
public class AssignmentUserListResult {
    @Expose
    @SerializedName("responseCode")
    private String responseCode;
    @Expose
    @SerializedName("responseData")
    private AssignmentListData responseData;
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

    public AssignmentListData getResponseData() {
        return responseData;
    }

    public void setResponseData(AssignmentListData responseData) {
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

    public class AssignmentListData {
        @Expose
        @SerializedName("mAssignmentUsers")
        private List<MAssignmentUser> mAssignmentUsers;  //作业列表
        @Expose
        @SerializedName("paginator")
        private Paginator paginator;

        public List<MAssignmentUser> getmAssignmentUsers() {
            return mAssignmentUsers;
        }

        public void setmAssignmentUsers(List<MAssignmentUser> mAssignmentUsers) {
            this.mAssignmentUsers = mAssignmentUsers;
        }

        public Paginator getPaginator() {
            return paginator;
        }

        public void setPaginator(Paginator paginator) {
            this.paginator = paginator;
        }
    }

}
