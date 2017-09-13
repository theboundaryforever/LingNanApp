package com.haoyu.app.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.haoyu.app.basehelper.BaseArrayRecyclerAdapter;
import com.haoyu.app.entity.CourseRegisterStats;
import com.haoyu.app.gdei.teacher.R;

import java.util.List;

/**
 * 创建日期：2017/2/8 on 17:04
 * 描述:
 * 作者:马飞奔 Administrator
 */
public class CourseRegisterStatsAdapter extends BaseArrayRecyclerAdapter<CourseRegisterStats> {
    private Context context;
    private int totalCount;

    public CourseRegisterStatsAdapter(Context context, List<CourseRegisterStats> mDatas, int totalCount) {
        super(mDatas);
        this.context = context;
        this.totalCount = totalCount;
    }

    @Override
    public void onBindHoder(RecyclerHolder holder, CourseRegisterStats entity, int position) {
        TextView tv_name = holder.obtainView(R.id.tv_name);
        TextView tv_assignment = holder.obtainView(R.id.tv_assignment);
        TextView tv_score = holder.obtainView(R.id.tv_score);
        TextView tv_state = holder.obtainView(R.id.tv_state);
        if (entity.getmCourseRegister() != null && entity.getmCourseRegister().getmUser() != null) {
            tv_name.setText(entity.getmCourseRegister().getmUser().getRealName());
        } else {
            tv_name.setText("--");
        }
        tv_assignment.setText(entity.getCompleteAssignmentNum() + "/" + totalCount);
        if (entity.getmCourseResult() != null && entity.getmCourseResult().getScore() != null) {
            tv_score.setText(String.valueOf(entity.getmCourseResult().getScore().intValue()));
        } else {
            tv_score.setText("--");
        }
        if (entity.getmCourseResult() != null && entity.getmCourseResult().getState() != null
                && entity.getmCourseResult().getState().equals("pass")) {
            tv_state.setText("合格");
            tv_state.setTextColor(ContextCompat.getColor(context, R.color.defaultColor));
        } else if (entity.getmCourseResult() != null && entity.getmCourseResult().getState() != null
                && entity.getmCourseResult().getState().equals("nopass")) {
            tv_state.setText("不合格");
            tv_state.setTextColor(ContextCompat.getColor(context, R.color.darkorange));
        } else {
            tv_state.setText("待评");
            tv_state.setTextColor(ContextCompat.getColor(context, R.color.blue));
        }
    }

    @Override
    public int bindView(int viewtype) {
        return R.layout.course_register_state_item;
    }
}
