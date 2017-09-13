package com.haoyu.app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * /**
 * id	工作坊id	String	Y
 * title	标题	String	Y
 * summary	描述	String	N
 * qualifiedPoint	工作坊达标分数	BigDecimal	N
 * studentNum	学员数量	int	N
 * memberNum	成员数量	Int	N
 * activityNum	活动数量	int	N
 * faqQuestionNum	问答数量	int	N
 * resourceNum	资源数量	int	N

 */

public class ManagementMemberEntity {
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("mWorkshop")
    private String mWorkshop;
    @Expose
    @SerializedName("mUser")
    private MobileUser mUser;
    @Expose
    @SerializedName("role")
    private String role;
    @Expose
    @SerializedName("evaluate")
    private String evaluate;
    @Expose
    @SerializedName("finallyResult")
    private String finallyResult;
    @Expose
    @SerializedName("point")
    private int point;
    @Expose
    @SerializedName("completeActivityNum")
    private int completeActivityNum;
    @Expose
    @SerializedName("faqQuestionNum")
    private int faqQuestionNum;
    @Expose
    @SerializedName("state")
    private String state;
    @Expose
    @SerializedName("evaluateCreator")
    private MobileUser evaluateCreator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getmWorkshop() {
        return mWorkshop;
    }

    public void setmWorkshop(String mWorkshop) {
        this.mWorkshop = mWorkshop;
    }

    public MobileUser getmUser() {
        return mUser;
    }

    public void setmUser(MobileUser mUser) {
        this.mUser = mUser;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getFinallyResult() {
        return finallyResult;
    }

    public void setFinallyResult(String finallyResult) {
        this.finallyResult = finallyResult;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getCompleteActivityNum() {
        return completeActivityNum;
    }

    public void setCompleteActivityNum(int completeActivityNum) {
        this.completeActivityNum = completeActivityNum;
    }

    public int getFaqQuestionNum() {
        return faqQuestionNum;
    }

    public void setFaqQuestionNum(int faqQuestionNum) {
        this.faqQuestionNum = faqQuestionNum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public MobileUser getEvaluateCreator() {
        return evaluateCreator;
    }

    public void setEvaluateCreator(MobileUser evaluateCreator) {
        this.evaluateCreator = evaluateCreator;
    }
}
