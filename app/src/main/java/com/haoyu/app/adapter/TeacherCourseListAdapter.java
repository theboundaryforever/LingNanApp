package com.haoyu.app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoyu.app.activity.AppMultiImageShowActivity;
import com.haoyu.app.basehelper.BaseArrayRecyclerAdapter;
import com.haoyu.app.entity.CourseMobileEntity;
import com.haoyu.app.gdei.teacher.R;
import com.haoyu.app.imageloader.GlideImgManager;
import com.haoyu.app.utils.PixelFormat;
import com.haoyu.app.utils.ScreenUtils;
import com.haoyu.app.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2017/2/4 on 15:48
 * 描述:
 * 作者:马飞奔 Administrator
 */
public class TeacherCourseListAdapter extends BaseArrayRecyclerAdapter<CourseMobileEntity> {
    private Activity context;
    private int imageWidth;
    private int imageHeight;

    public TeacherCourseListAdapter(Activity context, List<CourseMobileEntity> mDatas) {
        super(mDatas);
        this.context = context;
        imageWidth = ScreenUtils.getScreenWidth(context) / 3 - PixelFormat.formatPxToDip(context, 20);
        imageHeight = imageWidth / 3 * 2;
    }

    @Override
    public void onBindHoder(RecyclerHolder holder, final CourseMobileEntity entity, int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                imageWidth, imageHeight);
        ImageView course_img = holder.obtainView(R.id.course_img);
        TextView course_title = holder.obtainView(R.id.course_title);
        TextView course_code = holder.obtainView(R.id.course_code);
        TextView course_period = holder.obtainView(R.id.course_period);
        TextView course_state = holder.obtainView(R.id.course_state);
        View line = holder.obtainView(R.id.line);
        course_img.setLayoutParams(params);
        GlideImgManager.loadImage(context, entity.getImage(), R.drawable.app_default, R.drawable.app_default, course_img);
        course_title.setText(entity.getTitle());
        String codeStr = "";
        if (entity.getCode() != null && entity.getCode().trim().length() > 0) {
            codeStr += entity.getCode();
        }
        if (entity.getTermNo() != null && entity.getCode().trim().length() > 0) {
            codeStr += "/" + entity.getTermNo();
        }
        course_code.setText(codeStr);
        if (entity.getTimePeriod() != null) {
            course_period.setText("开课：" + TimeUtil.getSlashDate(entity.getTimePeriod().getStartTime()));
            course_state.setVisibility(View.VISIBLE);
            if (entity.getTimePeriod().getEndTime() >= System.currentTimeMillis()) {
                course_state.setText("进行中");
            } else {
                course_state.setText("已结束");
            }
        } else {
            course_period.setText("开课：暂未设置");
            course_state.setVisibility(View.GONE);
        }
        if (position == getItemCount() - 1) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
        }
        course_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> imgList = new ArrayList<>();
                imgList.add(entity.getImage());
                Intent intent = new Intent(context, AppMultiImageShowActivity.class);
                intent.putStringArrayListExtra("photos", imgList);
                intent.putExtra("position", 0);
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.zoom_in, 0);
            }
        });
    }

    @Override
    public int bindView(int viewtype) {
        return R.layout.teacher_course_list_item;
    }
}
