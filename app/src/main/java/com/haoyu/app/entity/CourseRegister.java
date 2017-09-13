package com.haoyu.app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 创建日期：2017/2/8 on 16:21
 * 描述:课程注册情况实体类
 * 作者:马飞奔 Administrator
 */
public class CourseRegister {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("mCourse")
    @Expose
    private CourseMobileEntity mCourse;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("mUser")
    @Expose
    private MobileUser mUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CourseMobileEntity getmCourse() {
        return mCourse;
    }

    public void setmCourse(CourseMobileEntity mCourse) {
        this.mCourse = mCourse;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public MobileUser getmUser() {
        return mUser;
    }

    public void setmUser(MobileUser mUser) {
        this.mUser = mUser;
    }
}
