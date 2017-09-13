package com.haoyu.app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 创建日期：2017/2/7 on 9:28
 * 描述: 批改作业
 * 作者:马飞奔 Administrator
 */
public class MarkAssignmentResult {
    @Expose
    @SerializedName("responseCode")
    private String responseCode;
    @Expose
    @SerializedName("responseData")
    private MarkAssignment responseData;
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

    public MarkAssignment getResponseData() {
        return responseData;
    }

    public void setResponseData(MarkAssignment responseData) {
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

    public class MarkAssignment {
        @Expose
        @SerializedName("mAssignmentUser")
        private MAssignmentUser mAssignmentUser;   //作业完成情况
        @Expose
        @SerializedName("mEvaluateSubmission")
        private EvaluateSubmission mEvaluateSubmission;  //评价对象

        public MAssignmentUser getmAssignmentUser() {
            return mAssignmentUser;
        }

        public void setmAssignmentUser(MAssignmentUser mAssignmentUser) {
            this.mAssignmentUser = mAssignmentUser;
        }

        public EvaluateSubmission getmEvaluateSubmission() {
            return mEvaluateSubmission;
        }

        public void setmEvaluateSubmission(EvaluateSubmission mEvaluateSubmission) {
            this.mEvaluateSubmission = mEvaluateSubmission;
        }
    }

    public class EvaluateSubmission {
        @Expose
        @SerializedName("id")
        private String id;
        @Expose
        @SerializedName("evaluateRelationId")
        private String evaluateRelationId;
        @Expose
        @SerializedName("mEvaluateItemSubmissions")
        private List<EvaluateItemSubmissions> mEvaluateItemSubmissions;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEvaluateRelationId() {
            return evaluateRelationId;
        }

        public void setEvaluateRelationId(String evaluateRelationId) {
            this.evaluateRelationId = evaluateRelationId;
        }

        public List<EvaluateItemSubmissions> getmEvaluateItemSubmissions() {
            return mEvaluateItemSubmissions;
        }

        public void setmEvaluateItemSubmissions(List<EvaluateItemSubmissions> mEvaluateItemSubmissions) {
            this.mEvaluateItemSubmissions = mEvaluateItemSubmissions;
        }
    }
}
