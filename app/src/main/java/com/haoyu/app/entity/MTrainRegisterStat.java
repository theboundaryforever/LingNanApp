package com.haoyu.app.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 创建日期：2017/1/19 on 18:27
 * 描述:
 * 作者:马飞奔 Administrator
 */
public class MTrainRegisterStat implements Serializable{
    /**
     * mUser	用户对象	MUser	Y
     trainRegisterId	培训报名id	String	Y
     projectName	项目名	String	Y
     registedCourseNum	选课数	Int	Y	报读课程数
     totalStudyHours	获得学时	Int	Y	总获取学时
     courseEvaluate	课程结果	String	Y	直接显示返回的数据，- 表示课程不限制学时
     返回的文字有：合格，未达标，-
     workshopNum	工作坊数	String	Y	参与工作坊数
     workshopEvaluate	工作坊结果	String	Y	直接显示返回的数据，- 表示没有参加工作坊
     返回的文字有：待评价，优秀，合格，未达标，-
     communityEvaluate	社区结果	String	Y	直接显示返回的数据，- 表示没有参加社区
     返回的文字有：待评价，优秀，合格，未达标，-
     mTrain	培训对象	Map	Y
     mCourseRegisters	选课列表	List	Y
     */
    @Expose
    @SerializedName("mUser")
    private MobileUser mUser;
    @Expose
    @SerializedName("trainRegisterId")
    private String trainRegisterId;
    @Expose
    @SerializedName("projectName")
    private String projectName;
    @Expose
    @SerializedName("registedCourseNum")
    private int registedCourseNum;
    @Expose
    @SerializedName("totalStudyHours")
    private int totalStudyHours;
    @Expose
    @SerializedName("courseEvaluate")
    private String courseEvaluate;
    @Expose
    @SerializedName("workshopNum")
    private int workshopNum;
    @Expose
    @SerializedName("workshopEvaluate")
    private String workshopEvaluate;
    @Expose
    @SerializedName("communityEvaluate")
    private String communityEvaluate;
    @Expose
    @SerializedName("mTrain")
    private MyTrainMobileEntity mTrain;
    @Expose
    @SerializedName("mCourseRegisters")
    private List<CourseRegisters>mCourseRegisters;

    public MobileUser getmUser() {
        return mUser;
    }

    public void setmUser(MobileUser mUser) {
        this.mUser = mUser;
    }

    public String getTrainRegisterId() {
        return trainRegisterId;
    }

    public void setTrainRegisterId(String trainRegisterId) {
        this.trainRegisterId = trainRegisterId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getRegistedCourseNum() {
        return registedCourseNum;
    }

    public void setRegistedCourseNum(int registedCourseNum) {
        this.registedCourseNum = registedCourseNum;
    }

    public int getTotalStudyHours() {
        return totalStudyHours;
    }

    public void setTotalStudyHours(int totalStudyHours) {
        this.totalStudyHours = totalStudyHours;
    }

    public String getCourseEvaluate() {
        return courseEvaluate;
    }

    public void setCourseEvaluate(String courseEvaluate) {
        this.courseEvaluate = courseEvaluate;
    }

    public int getWorkshopNum() {
        return workshopNum;
    }

    public void setWorkshopNum(int workshopNum) {
        this.workshopNum = workshopNum;
    }

    public String getWorkshopEvaluate() {
        return workshopEvaluate;
    }

    public void setWorkshopEvaluate(String workshopEvaluate) {
        this.workshopEvaluate = workshopEvaluate;
    }

    public String getCommunityEvaluate() {
        return communityEvaluate;
    }

    public void setCommunityEvaluate(String communityEvaluate) {
        this.communityEvaluate = communityEvaluate;
    }

    public MyTrainMobileEntity getmTrain() {
        return mTrain;
    }

    public void setmTrain(MyTrainMobileEntity mTrain) {
        this.mTrain = mTrain;
    }

    public List<CourseRegisters> getmCourseRegisters() {
        return mCourseRegisters;
    }

    public void setmCourseRegisters(List<CourseRegisters> mCourseRegisters) {
        this.mCourseRegisters = mCourseRegisters;
    }

    public class CourseRegisters{
        @Expose
        @SerializedName("score")
        private double score;
        @Expose
        @SerializedName("mCourse")
        private CourseMobileEntity mCourse;

        public CourseMobileEntity getmCourse() {
            return mCourse;
        }

        public void setmCourse(CourseMobileEntity mCourse) {
            this.mCourse = mCourse;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }
}
