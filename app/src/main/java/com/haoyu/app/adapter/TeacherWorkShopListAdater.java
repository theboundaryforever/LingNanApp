package com.haoyu.app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haoyu.app.activity.AppMultiImageShowActivity;
import com.haoyu.app.basehelper.BaseArrayRecyclerAdapter;
import com.haoyu.app.entity.WorkShopMobileEntity;
import com.haoyu.app.gdei.teacher.R;
import com.haoyu.app.utils.PixelFormat;
import com.haoyu.app.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2017/2/4 on 16:15
 * 描述:
 * 作者:马飞奔 Administrator
 */
public class TeacherWorkShopListAdater extends BaseArrayRecyclerAdapter<WorkShopMobileEntity> {
    private Activity context;
    private int imageWidth;
    private int imageHeight;

    public TeacherWorkShopListAdater(Activity context, List<WorkShopMobileEntity> mDatas) {
        super(mDatas);
        this.context = context;
        imageWidth = ScreenUtils.getScreenWidth(context) / 3 - PixelFormat.formatPxToDip(context, 20);
        imageHeight = imageWidth / 3 * 2;
    }

    @Override
    public void onBindHoder(RecyclerHolder holder, final WorkShopMobileEntity entity, int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                imageWidth, imageHeight);
        ImageView workshop_img = holder.obtainView(R.id.workshop_img);
        workshop_img.setLayoutParams(params);
        TextView workshop_title = holder.obtainView(R.id.workshop_title);
        TextView workshop_trainName = holder.obtainView(R.id.workshop_trainName);
        Glide.with(context)
                .load(entity.getImageUrl())
                .placeholder(R.drawable.app_default)
                .error(R.drawable.app_default)
                .dontAnimate().into(workshop_img);
        workshop_title.setText(entity.getTitle());
        workshop_trainName.setText(entity.getTrainName());
        workshop_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> imgList = new ArrayList<>();
                imgList.add(entity.getImageUrl());
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
        return R.layout.teacher_workshop_list_item;
    }
}
