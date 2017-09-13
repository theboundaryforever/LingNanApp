package com.haoyu.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;

import com.haoyu.app.base.BaseActivity;
import com.haoyu.app.fragment.PageDiscussionFragment;
import com.haoyu.app.fragment.PageHomeWorkFragment;
import com.haoyu.app.fragment.PageQuestionFragment;
import com.haoyu.app.fragment.PageResourcesFragment;
import com.haoyu.app.fragment.PageStatisticsFragment;
import com.haoyu.app.fragment.TeacherCourseFragment;
import com.haoyu.app.gdei.teacher.R;
import com.haoyu.app.view.AppToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 创建日期：2017/2/4 on 17:05
 * 描述:
 * 作者:马飞奔 Administrator
 */
public class TeacherCourseTabActivity extends BaseActivity {
    private TeacherCourseTabActivity context = this;
    private static final int PAGE_COURSE = 0;  //学习
    private static final int PAGE_RESOURCES = 1;    //资源
    private static final int PAGE_DISCUSSION = 2;   //讨论
    private static final int PAGE_QUESTION = 3; //问答
    private static final int PAGE_HOMEWORK = 4; //作业
    private static final int PAGE_STATISTICS = 5; //统计
    private FragmentManager fragmentManager;
    private List<Fragment> fragments = new ArrayList<>();
    @BindView(R.id.toolBar)
    AppToolBar toolBar;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    private String courseId;
    private TeacherCourseFragment teacherCourseFragment;
    private PageResourcesFragment pageResourcesFragment;
    private PageDiscussionFragment pageDiscussionFragment;
    private PageQuestionFragment pageQuestionFragment;
    private PageHomeWorkFragment pageHomeWorkFragment;
    private PageStatisticsFragment pageStatisticsFragment;

    @Override
    public int setLayoutResID() {
        return R.layout.activity_teacher_course_tab;
    }

    @Override
    public void initView() {
        courseId = getIntent().getStringExtra("courseId"); // 课程Id，通过intent获取
        String courseTitle = getIntent().getStringExtra("courseTitle");
        fragmentManager = getSupportFragmentManager();
        toolBar.setTitle_text(courseTitle);
        changeTabIndex(PAGE_COURSE);
    }

    private void changeTabIndex(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case PAGE_COURSE:
                if (teacherCourseFragment == null) {
                    teacherCourseFragment = new TeacherCourseFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("entityId", courseId);
                    teacherCourseFragment.setArguments(bundle);
                    transaction.add(R.id.content, teacherCourseFragment);
                    fragments.add(teacherCourseFragment);
                } else {
                    transaction.show(teacherCourseFragment);
                }
                break;
            case PAGE_RESOURCES:
                if (pageResourcesFragment == null) {
                    pageResourcesFragment = new PageResourcesFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("entityId", courseId);
                    pageResourcesFragment.setArguments(bundle);
                    transaction.add(R.id.content, pageResourcesFragment);
                    fragments.add(pageResourcesFragment);
                } else {
                    transaction.show(pageResourcesFragment);
                }
                break;
            case PAGE_DISCUSSION:
                if (pageDiscussionFragment == null) {
                    pageDiscussionFragment = new PageDiscussionFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("entityId", courseId);
                    pageDiscussionFragment.setArguments(bundle);
                    transaction.add(R.id.content, pageDiscussionFragment);
                    fragments.add(pageDiscussionFragment);
                } else {
                    transaction.show(pageDiscussionFragment);
                }
                break;
            case PAGE_QUESTION:
                if (pageQuestionFragment == null) {
                    pageQuestionFragment = new PageQuestionFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("entityId", courseId);
                    pageQuestionFragment.setArguments(bundle);
                    transaction.add(R.id.content, pageQuestionFragment);
                    fragments.add(pageQuestionFragment);
                } else {
                    transaction.show(pageQuestionFragment);
                }
                break;
            case PAGE_HOMEWORK:
                if (pageHomeWorkFragment == null) {
                    pageHomeWorkFragment = new PageHomeWorkFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("entityId", courseId);
                    pageHomeWorkFragment.setArguments(bundle);
                    transaction.add(R.id.content, pageHomeWorkFragment);
                    fragments.add(pageHomeWorkFragment);
                } else {
                    transaction.show(pageHomeWorkFragment);
                }
                break;
            case PAGE_STATISTICS:
                if (pageStatisticsFragment == null) {
                    pageStatisticsFragment = new PageStatisticsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("entityId", courseId);
                    pageStatisticsFragment.setArguments(bundle);
                    transaction.add(R.id.content, pageStatisticsFragment);
                    fragments.add(pageStatisticsFragment);
                } else {
                    transaction.show(pageStatisticsFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction transaction) {
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                transaction.hide(fragment);
            }
        }
    }

    @Override
    public void setListener() {
        toolBar.setOnTitleClickListener(new AppToolBar.TitleOnClickListener() {
            @Override
            public void onLeftClick(View view) {
                finish();
            }

            @Override
            public void onRightClick(View view) {
                Intent intent = new Intent(context, AppDownloadActivity.class);
                startActivity(intent);
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch (checkId) {
                    case R.id.rb_section:
                        changeTabIndex(PAGE_COURSE);
                        return;
                    case R.id.rb_resources:
                        changeTabIndex(PAGE_RESOURCES);
                        return;
                    case R.id.rb_discuss:
                        changeTabIndex(PAGE_DISCUSSION);
                        return;
                    case R.id.rb_wenda:
                        changeTabIndex(PAGE_QUESTION);
                        return;
                    case R.id.rb_homeWork:
                        changeTabIndex(PAGE_HOMEWORK);
                        return;
                    case R.id.rb_Statistics:
                        changeTabIndex(PAGE_STATISTICS);
                        return;
                }
            }
        });
    }
}
