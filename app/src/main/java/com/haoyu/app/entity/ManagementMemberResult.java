package com.haoyu.app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by acer1 on 2017/2/8.
 */
public class ManagementMemberResult {
    @Expose
    @SerializedName("responseCode")
    private String responseCode;
    @Expose
    @SerializedName("responseMsg")
    private String responseMsg;
    @Expose
    @SerializedName("responseData")
    private ManagementResponseData responseData;
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

    public void setResponseData(ManagementResponseData responseData) {
        this.responseData = responseData;
    }

    public ManagementResponseData getResponseData() {
        return this.responseData;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public class ManagementResponseData {

        @Expose
        @SerializedName("workshopUsers")
        private List<ManagementMemberEntity> WorkshopUsers;
        ;
        @Expose
        @SerializedName("paginator")
        private Paginator paginator;

        public List<ManagementMemberEntity> getWorkshopUsers() {
            return WorkshopUsers;
        }

        public void setWorkshopUsers(List<ManagementMemberEntity> workshopUsers) {
            WorkshopUsers = workshopUsers;
        }

        public Paginator getPaginator() {
            return paginator;
        }

        public void setPaginator(Paginator paginator) {
            this.paginator = paginator;
        }
    }

}
