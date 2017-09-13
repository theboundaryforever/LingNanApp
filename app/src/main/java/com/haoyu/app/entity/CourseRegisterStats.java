package com.haoyu.app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 创建日期：2017/2/8 on 16:19
 * 描述:
 * 作者:马飞奔 Administrator
 */
public class CourseRegisterStats {
    @Expose
    @SerializedName("mCourseRegister")
    private CourseRegister mCourseRegister;
    @Expose
    @SerializedName("mCourseResult")
    private CourseResult mCourseResult;
    @Expose
    @SerializedName("completeAssignmentNum")
    private int completeAssignmentNum;

    public CourseRegister getmCourseRegister() {
        return mCourseRegister;
    }

    public void setmCourseRegister(CourseRegister mCourseRegister) {
        this.mCourseRegister = mCourseRegister;
    }

    public CourseResult getmCourseResult() {
        return mCourseResult;
    }

    public void setmCourseResult(CourseResult mCourseResult) {
        this.mCourseResult = mCourseResult;
    }

    public int getCompleteAssignmentNum() {
        return completeAssignmentNum;
    }

    public void setCompleteAssignmentNum(int completeAssignmentNum) {
        this.completeAssignmentNum = completeAssignmentNum;
    }

    public class CourseResult {
        @Expose
        @SerializedName("score")
        private Double score;
        @Expose
        @SerializedName("state")
        private String state;

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
