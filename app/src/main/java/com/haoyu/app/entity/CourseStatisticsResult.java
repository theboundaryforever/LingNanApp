package com.haoyu.app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 创建日期：2017/2/8 on 15:53
 * 描述: 课程统计-课程统计信息
 * 作者:马飞奔 Administrator
 */
public class CourseStatisticsResult {
    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("responseData")
    @Expose
    private CourseStatisticsData responseData;
    @SerializedName("responseMsg")
    @Expose
    private String responseMsg;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public CourseStatisticsData getResponseData() {
        return responseData;
    }

    public void setResponseData(CourseStatisticsData responseData) {
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

    public class CourseStatisticsData {
        /**
         * 名称	说明	类型	必填	备注
         * mCourse	课程	MCourse	Y
         * activityAssignmentNum	作业数	int	Y
         * faqQuestionNum	提问数	int	Y
         * faqAnswerNum	回答数	int	Y
         * noteNum	笔记数	int	Y
         * resourceNum	资源数	int	Y
         * discussionNum	研讨数	int	Y
         * registerNum	选课人数	int	Y
         */
        @Expose
        @SerializedName("mCourse")
        private CourseMobileEntity mCourse;
        @Expose
        @SerializedName("activityAssignmentNum")
        private int activityAssignmentNum;
        @Expose
        @SerializedName("faqQuestionNum")
        private int faqQuestionNum;
        @Expose
        @SerializedName("faqAnswerNum")
        private int faqAnswerNum;
        @Expose
        @SerializedName("noteNum")
        private int noteNum;
        @Expose
        @SerializedName("resourceNum")
        private int resourceNum;
        @Expose
        @SerializedName("discussionNum")
        private int discussionNum;
        @Expose
        @SerializedName("registerNum")
        private int registerNum;

        public CourseMobileEntity getmCourse() {
            return mCourse;
        }

        public void setmCourse(CourseMobileEntity mCourse) {
            this.mCourse = mCourse;
        }

        public int getActivityAssignmentNum() {
            return activityAssignmentNum;
        }

        public void setActivityAssignmentNum(int activityAssignmentNum) {
            this.activityAssignmentNum = activityAssignmentNum;
        }

        public int getFaqQuestionNum() {
            return faqQuestionNum;
        }

        public void setFaqQuestionNum(int faqQuestionNum) {
            this.faqQuestionNum = faqQuestionNum;
        }

        public int getFaqAnswerNum() {
            return faqAnswerNum;
        }

        public void setFaqAnswerNum(int faqAnswerNum) {
            this.faqAnswerNum = faqAnswerNum;
        }

        public int getNoteNum() {
            return noteNum;
        }

        public void setNoteNum(int noteNum) {
            this.noteNum = noteNum;
        }

        public int getResourceNum() {
            return resourceNum;
        }

        public void setResourceNum(int resourceNum) {
            this.resourceNum = resourceNum;
        }

        public int getDiscussionNum() {
            return discussionNum;
        }

        public void setDiscussionNum(int discussionNum) {
            this.discussionNum = discussionNum;
        }

        public int getRegisterNum() {
            return registerNum;
        }

        public void setRegisterNum(int registerNum) {
            this.registerNum = registerNum;
        }
    }
}
