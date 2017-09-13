package com.haoyu.app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 创建日期：2017/1/19 on 16:04
 * 描述:
 * 作者:马飞奔 Administrator
 */
public class TrainProcessResult {
    @Expose
    @SerializedName("responseCode")
    private String responseCode;
    @Expose
    @SerializedName("responseData")
    private TrainProcessData responseData;
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

    public TrainProcessData getResponseData() {
        return responseData;
    }

    public void setResponseData(TrainProcessData responseData) {
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

    public class TrainProcessData {
        /**
         * projectNum	项目数	Int	Y
         * trainNum	培训数	Int	Y
         * userTeacherNum	师资数	Int	Y
         * studentNum	学员数	Int	Y
         * trainRegisterNum	平台培训人次	Int	Y
         * joinedTrainStudentNum	培训参与人数	Int	Y
         */
        @Expose
        @SerializedName("projectNum")
        private int projectNum;
        @Expose
        @SerializedName("trainNum")
        private int trainNum;
        @Expose
        @SerializedName("userTeacherNum")
        private int userTeacherNum;
        @Expose
        @SerializedName("studentNum")
        private int studentNum;
        @Expose
        @SerializedName("trainRegisterNum")
        private int trainRegisterNum;
        @Expose
        @SerializedName("joinedTrainStudentNum")
        private int joinedTrainStudentNum;

        public int getProjectNum() {
            return projectNum;
        }

        public void setProjectNum(int projectNum) {
            this.projectNum = projectNum;
        }

        public int getTrainNum() {
            return trainNum;
        }

        public void setTrainNum(int trainNum) {
            this.trainNum = trainNum;
        }

        public int getUserTeacherNum() {
            return userTeacherNum;
        }

        public void setUserTeacherNum(int userTeacherNum) {
            this.userTeacherNum = userTeacherNum;
        }

        public int getStudentNum() {
            return studentNum;
        }

        public void setStudentNum(int studentNum) {
            this.studentNum = studentNum;
        }

        public int getTrainRegisterNum() {
            return trainRegisterNum;
        }

        public void setTrainRegisterNum(int trainRegisterNum) {
            this.trainRegisterNum = trainRegisterNum;
        }

        public int getJoinedTrainStudentNum() {
            return joinedTrainStudentNum;
        }

        public void setJoinedTrainStudentNum(int joinedTrainStudentNum) {
            this.joinedTrainStudentNum = joinedTrainStudentNum;
        }
    }
}
