package com.haoyu.app.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.haoyu.app.adapter.CourseRegisterStatsAdapter;
import com.haoyu.app.base.BaseFragment;
import com.haoyu.app.entity.CourseRegisterStats;
import com.haoyu.app.entity.CourseStatisticsResult;
import com.haoyu.app.entity.Paginator;
import com.haoyu.app.entity.StudentStatisticListResult;
import com.haoyu.app.gdei.teacher.R;
import com.haoyu.app.utils.Constants;
import com.haoyu.app.utils.OkHttpClientManager;
import com.haoyu.app.utils.TimeUtil;
import com.haoyu.app.view.LoadFailView;
import com.haoyu.app.view.LoadingView;
import com.haoyu.app.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import okhttp3.Request;


/**
 * 创建日期：2017/2/4 on 17:19
 * 描述:
 * 作者:马飞奔 Administrator
 */
public class PageStatisticsFragment extends BaseFragment implements View.OnClickListener, XRecyclerView.LoadingListener {
    private String courseId;   //课程Id
    @BindViews({R.id.loadView, R.id.loadView1, R.id.loadView2, R.id.loadView3})
    LoadingView[] loadingViews;
    @BindView(R.id.loadFailView)
    LoadFailView loadFailView;
    @BindViews({R.id.empty_msg1, R.id.empty_msg2, R.id.empty_msg3})
    TextView[] emptyMsgs;
    @BindView(R.id.contentView)
    LinearLayout contentView;
    @BindViews({R.id.allLayout, R.id.qualifiedLayout, R.id.noQualifiedLayout})
    View[] layoutViews;
    @BindViews({R.id.errorView1, R.id.errorView2, R.id.errorView3})
    TextView[] errorViews;
    @BindViews({R.id.course_title, R.id.course_time, R.id.course_period, R.id.course_enroll})
    TextView[] courseViews;//课程标题,课程开课时间,课程学时,课程报读人数
    @BindViews({R.id.questionNUm, R.id.answerNum, R.id.noteNum, R.id.resourcesNum, R.id.discussNum})
    TextView[] numViews;//提问数，回答数，笔记数，资源数，研讨数
    @BindViews({R.id.rb_all, R.id.rb_qualified, R.id.rb_noqualified})
    RadioButton[] radioButtons;
    @BindViews({R.id.allRV, R.id.qualifiedRV, R.id.noQualifiedRV})
    XRecyclerView[] xRecyclerViews;
    private List<CourseRegisterStats> allDatas = new ArrayList<>();
    private List<CourseRegisterStats> qualifiedDatas = new ArrayList<>();
    private List<CourseRegisterStats> noqualifiedDatas = new ArrayList<>();
    private CourseRegisterStatsAdapter allAdapter, qualifiedAdapter, noqualifiedAdapter;
    private boolean isLoadAll, isLoadQualified, isLoadNoqualified;
    private boolean isRefresh, isLoadMore, needDialog = true;
    private int page1 = 1, page2 = 1, page3 = 1;
    private int limit = 20;
    private int checkIndex = 1;

    @Override
    public int createView() {
        return R.layout.fragment_page_statistics;
    }

    @Override
    public void initView(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            courseId = bundle.getString("entityId");
        }
        LinearLayoutManager allManager = new LinearLayoutManager(context);
        allManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerViews[0].setLayoutManager(allManager);
        LinearLayoutManager qualifiedManager = new LinearLayoutManager(context);
        qualifiedManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerViews[1].setLayoutManager(qualifiedManager);
        LinearLayoutManager noQualifiedManager = new LinearLayoutManager(context);
        noQualifiedManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerViews[2].setLayoutManager(noQualifiedManager);
        xRecyclerViews[0].setPullRefreshEnabled(false);
        xRecyclerViews[1].setPullRefreshEnabled(false);
        xRecyclerViews[2].setPullRefreshEnabled(false);
        xRecyclerViews[0].setLoadingListener(this);
        xRecyclerViews[1].setLoadingListener(this);
        xRecyclerViews[2].setLoadingListener(this);
    }

    @Override
    public void initData() {
        String url1 = Constants.OUTRT_NET + "/" + courseId + "/teach/m/course_stat/" + courseId;
        addSubscription(OkHttpClientManager.getAsyn(context, url1, new OkHttpClientManager.ResultCallback<CourseStatisticsResult>() {
            @Override
            public void onBefore(Request request) {
                loadingViews[0].setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Request request, Exception e) {
                loadingViews[0].setVisibility(View.GONE);
                loadFailView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onResponse(CourseStatisticsResult response) {
                loadingViews[0].setVisibility(View.GONE);
                contentView.setVisibility(View.VISIBLE);
                if (response != null && response.getResponseData() != null) {
                    updateUI(response.getResponseData());
                }
                loadAll();
            }
        }));
    }

    @Override
    public void setListener() {
        for (RadioButton radioButton : radioButtons)
            radioButton.setOnClickListener(this);
        loadFailView.setOnRetryListener(new LoadFailView.OnRetryListener() {
            @Override
            public void onRetry(View v) {
                initData();
            }
        });
        for (TextView errorView : errorViews) {
            errorView.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_all:
                checkIndex = 1;
                setSelectIndex(checkIndex);
                break;
            case R.id.rb_qualified:
                checkIndex = 2;
                setSelectIndex(checkIndex);
                break;
            case R.id.rb_noqualified:
                checkIndex = 3;
                setSelectIndex(checkIndex);
                break;
            case R.id.errorView1:
                loadAll();
                break;
            case R.id.errorView2:
                loadQualified();
                break;
            case R.id.errorView3:
                loadNoqualified();
                break;

        }
    }

    private void setSelectIndex(int checkIndex) {
        for (RadioButton radioButton : radioButtons)
            radioButton.setChecked(false);
        for (View layoutView : layoutViews)
            layoutView.setVisibility(View.GONE);
        isRefresh = false;
        isLoadMore = false;
        needDialog = true;
        switch (checkIndex) {
            case 1:
                radioButtons[0].setChecked(true);
                layoutViews[0].setVisibility(View.VISIBLE);
                if (!isLoadAll) {
                    loadAll();
                }
                break;
            case 2:
                radioButtons[1].setChecked(true);
                layoutViews[1].setVisibility(View.VISIBLE);
                if (!isLoadQualified) {
                    loadQualified();
                }
                break;
            case 3:
                radioButtons[2].setChecked(true);
                layoutViews[2].setVisibility(View.VISIBLE);
                if (!isLoadNoqualified) {
                    loadNoqualified();
                }
                break;
        }
    }

    /*加载全部列表*/
    private void loadAll() {
        String url = Constants.OUTRT_NET + "/" + courseId + "/teach/m/course_register_stat/" + courseId
                + "?page=" + page1 + "&limit=" + limit;
        addSubscription(OkHttpClientManager.getAsyn(context, url, new OkHttpClientManager.ResultCallback<StudentStatisticListResult>() {
            @Override
            public void onBefore(Request request) {
                if (needDialog) {
                    loadingViews[1].setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                loadingViews[1].setVisibility(View.GONE);
                if (isRefresh) {
                    xRecyclerViews[0].refreshComplete(false);
                } else if (isLoadMore) {
                    xRecyclerViews[0].loadMoreComplete(false);
                } else {
                    errorViews[0].setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onResponse(StudentStatisticListResult response) {
                isLoadAll = true;
                loadingViews[1].setVisibility(View.GONE);
                if (response != null && response.getResponseData() != null
                        && response.getResponseData().getmCourseRegisterStats() != null
                        && response.getResponseData().getmCourseRegisterStats().size() > 0) {
                    updateAllUI(response.getResponseData().getmCourseRegisterStats(), response.getResponseData().getPaginator());
                } else {
                    if (isRefresh) {
                        xRecyclerViews[0].refreshComplete(true);
                    } else if (isLoadMore) {
                        xRecyclerViews[0].loadMoreComplete(true);
                    } else {
                        emptyMsgs[0].setVisibility(View.VISIBLE);
                    }
                }
            }
        }));
    }

    /*加载合格列表*/
    private void loadQualified() {
        String url = Constants.OUTRT_NET + "/" + courseId + "/teach/m/course_register_stat/" + courseId
                + "?page=" + page2 + "&limit=" + limit + "&courseResultState=pass";
        addSubscription(OkHttpClientManager.getAsyn(context, url, new OkHttpClientManager.ResultCallback<StudentStatisticListResult>() {
            @Override
            public void onBefore(Request request) {
                if (needDialog) {
                    loadingViews[2].setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                loadingViews[2].setVisibility(View.GONE);
                if (isRefresh) {
                    xRecyclerViews[1].refreshComplete(false);
                } else if (isLoadMore) {
                    xRecyclerViews[1].loadMoreComplete(false);
                } else {
                    errorViews[1].setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onResponse(StudentStatisticListResult response) {
                isLoadQualified = true;
                loadingViews[2].setVisibility(View.GONE);
                xRecyclerViews[1].setVisibility(View.VISIBLE);
                if (response != null && response.getResponseData() != null
                        && response.getResponseData().getmCourseRegisterStats() != null
                        && response.getResponseData().getmCourseRegisterStats().size() > 0) {
                    updateQualifiedUI(response.getResponseData().getmCourseRegisterStats(), response.getResponseData().getPaginator());
                } else {
                    if (isRefresh) {
                        xRecyclerViews[1].refreshComplete(true);
                    } else if (isLoadMore) {
                        xRecyclerViews[1].loadMoreComplete(true);
                    } else {
                        emptyMsgs[1].setVisibility(View.VISIBLE);
                    }
                }
            }
        }));
    }

    /*加载不合格列表*/
    private void loadNoqualified() {
        String url = Constants.OUTRT_NET + "/" + courseId + "/teach/m/course_register_stat/" + courseId
                + "?page=" + page3 + "&limit=" + limit + "&courseResultState=nopass";
        addSubscription(OkHttpClientManager.getAsyn(context, url, new OkHttpClientManager.ResultCallback<StudentStatisticListResult>() {
            @Override
            public void onBefore(Request request) {
                if (needDialog) {
                    loadingViews[3].setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                loadingViews[3].setVisibility(View.GONE);
                if (isRefresh) {
                    xRecyclerViews[2].refreshComplete(false);
                } else if (isLoadMore) {
                    xRecyclerViews[2].loadMoreComplete(false);
                } else {
                    errorViews[3].setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onResponse(StudentStatisticListResult response) {
                isLoadNoqualified = true;
                loadingViews[3].setVisibility(View.GONE);
                xRecyclerViews[2].setVisibility(View.VISIBLE);
                if (response != null && response.getResponseData() != null
                        && response.getResponseData().getmCourseRegisterStats() != null
                        && response.getResponseData().getmCourseRegisterStats().size() > 0) {
                    updateNoQualifiedUI(response.getResponseData().getmCourseRegisterStats(), response.getResponseData().getPaginator());
                } else {
                    if (isRefresh) {
                        xRecyclerViews[2].refreshComplete(true);
                    } else if (isLoadMore) {
                        xRecyclerViews[2].loadMoreComplete(true);
                    } else {
                        emptyMsgs[2].setVisibility(View.VISIBLE);
                    }
                }
            }
        }));
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        isLoadMore = false;
        needDialog = false;
        switch (checkIndex) {
            case 1:
                page1 = 1;
                loadAll();
                break;
            case 2:
                page2 = 1;
                loadQualified();
                break;
            case 3:
                page3 = 1;
                loadNoqualified();
                break;
        }
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        isLoadMore = true;
        needDialog = false;
        switch (checkIndex) {
            case 1:
                page1 += 1;
                loadAll();
                break;
            case 2:
                page2 += 1;
                loadQualified();
                break;
            case 3:
                page3 += 1;
                loadNoqualified();
                break;
        }
    }


    private void updateUI(CourseStatisticsResult.CourseStatisticsData responseData) {
        if (responseData.getmCourse() != null) {
            courseViews[0].setText(responseData.getmCourse().getTitle());
            if (responseData.getmCourse().getTimePeriod() != null) {
                long startTime = responseData.getmCourse().getTimePeriod().getStartTime();
                long endTime = responseData.getmCourse().getTimePeriod().getEndTime();
                courseViews[1].setText("开课时间：" + TimeUtil.convertTimeOfDay(startTime, endTime));
            } else {
                courseViews[1].setText("开课时间：未设置");
            }
            courseViews[2].setText(responseData.getmCourse().getStudyHours() + "学时");
            courseViews[3].setText(responseData.getmCourse().getRegisterNum() + "人报读");
        }
        numViews[0].setText(String.valueOf(responseData.getFaqQuestionNum()));
        numViews[1].setText(String.valueOf(responseData.getFaqAnswerNum()));
        numViews[2].setText(String.valueOf(responseData.getNoteNum()));
        numViews[3].setText(String.valueOf(responseData.getResourceNum()));
        numViews[4].setText(String.valueOf(responseData.getDiscussionNum()));
        allAdapter = new CourseRegisterStatsAdapter(context, allDatas, responseData.getActivityAssignmentNum());
        xRecyclerViews[0].setAdapter(allAdapter);
        qualifiedAdapter = new CourseRegisterStatsAdapter(context, qualifiedDatas, responseData.getActivityAssignmentNum());
        xRecyclerViews[1].setAdapter(qualifiedAdapter);
        noqualifiedAdapter = new CourseRegisterStatsAdapter(context, noqualifiedDatas, responseData.getActivityAssignmentNum());
        xRecyclerViews[2].setAdapter(noqualifiedAdapter);
    }

    private void updateAllUI(List<CourseRegisterStats> mDatas, Paginator paginator) {
        if (isRefresh) {
            allDatas.clear();
            xRecyclerViews[0].refreshComplete(true);
        } else if (isLoadMore) {
            xRecyclerViews[0].loadMoreComplete(true);
        }
        allDatas.addAll(mDatas);
        allAdapter.notifyDataSetChanged();
        if (paginator != null && paginator.getHasNextPage()) {
            xRecyclerViews[0].setLoadingMoreEnabled(true);
        } else {
            xRecyclerViews[0].setLoadingMoreEnabled(false);
        }
    }

    private void updateQualifiedUI(List<CourseRegisterStats> mDatas, Paginator paginator) {
        if (isRefresh) {
            qualifiedDatas.clear();
            xRecyclerViews[1].refreshComplete(true);
        } else if (isLoadMore) {
            xRecyclerViews[1].loadMoreComplete(true);
        }
        qualifiedDatas.addAll(mDatas);
        qualifiedAdapter.notifyDataSetChanged();
        if (paginator != null && paginator.getHasNextPage()) {
            xRecyclerViews[1].setLoadingMoreEnabled(true);
        } else {
            xRecyclerViews[1].setLoadingMoreEnabled(false);
        }
    }

    private void updateNoQualifiedUI(List<CourseRegisterStats> mDatas, Paginator paginator) {
        if (isRefresh) {
            noqualifiedDatas.clear();
            xRecyclerViews[2].refreshComplete(true);
        } else if (isLoadMore) {
            xRecyclerViews[2].loadMoreComplete(true);
        }
        noqualifiedDatas.addAll(mDatas);
        noqualifiedAdapter.notifyDataSetChanged();
        if (paginator != null && paginator.getHasNextPage()) {
            xRecyclerViews[2].setLoadingMoreEnabled(true);
        } else {
            xRecyclerViews[2].setLoadingMoreEnabled(false);
        }
    }
}
