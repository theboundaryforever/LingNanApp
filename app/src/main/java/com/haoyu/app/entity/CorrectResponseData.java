package com.haoyu.app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by acer1 on 2017/1/7.
 */
public class CorrectResponseData {
    @Expose
    @SerializedName("mEvaluateSubmission")
    private MEvaluateSubmission mEvaluateSubmission;
    @Expose
    @SerializedName("mAssignmentUser")
    private MAssignmentUser mAssignmentUser;

    public void setMEvaluateSubmission(MEvaluateSubmission mEvaluateSubmission) {
        this.mEvaluateSubmission = mEvaluateSubmission;
    }

    public MEvaluateSubmission getMEvaluateSubmission() {
        return this.mEvaluateSubmission;
    }

    public void setMAssignmentUser(MAssignmentUser mAssignmentUser) {
        this.mAssignmentUser = mAssignmentUser;
    }

    public MAssignmentUser getMAssignmentUser() {
        return this.mAssignmentUser;
    }

}
