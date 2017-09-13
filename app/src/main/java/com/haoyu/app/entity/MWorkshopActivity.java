package com.haoyu.app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 创建日期：2017/1/3 on 20:30
 * 描述:
 * 作者:马飞奔 Administrator
 */
public class MWorkshopActivity implements Serializable {
    /**
     * id	活动ID	String	Y
     * title	活动标题	String	Y
     * type	活动类型	String	Y	discussion:主题研讨
     * lessonPlan:集体备课
     * lcec:听课评课
     * test:测验
     * debate:辩论
     * survey:调查问卷
     * video:音视频
     * completeState	完成状态	String	Y	未参与
     * 已完成
     * 进行中
     */
    public static String TYPE_DISCUSSION = "discussion";
    public static String TYPE_LESSONPLAN = "lesson_plan";
    public static String TYPE_LCEC = "lcec";
    public static String TYPE_TEST = "test";
    public static String TYPE_DEBATE = "debate";
    public static String TYPE_SURVEY = "survey";
    public static String TYPE_VIDEO = "video";
    public static String DISCUSSION_CONTENT = "教学研讨";
    public static String LESSONPLAN_CONTENT = "集体备课";
    public static String LCEC_CONTENT = "听课评课";
    public static String TEST_CONTENT = "在线测验";
    public static String DEBATE_CONTENT = "在线辩论";
    public static String SURVEY_CONTENT = "调查问卷";
    public static String VIDEO_CONTENT = "教学观摩";
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("completeState")
    private String completeState;
    @Expose
    @SerializedName("timePeriod")
    private TimePeriod timePeriod;
    @Expose
    @SerializedName("inCurrentDate")
    private boolean inCurrentDate;

    //当前位置
    @Expose
    @SerializedName("position")
    private int position;

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }

    public boolean isInCurrentDate() {
        return inCurrentDate;
    }

    public void setInCurrentDate(boolean inCurrentDate) {
        this.inCurrentDate = inCurrentDate;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompleteState() {
        return completeState;
    }

    public void setCompleteState(String completeState) {
        this.completeState = completeState;
    }
}
