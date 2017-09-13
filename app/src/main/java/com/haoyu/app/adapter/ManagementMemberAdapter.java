package com.haoyu.app.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.haoyu.app.basehelper.BaseArrayRecyclerAdapter;

import com.haoyu.app.entity.ManagementMemberEntity;
import com.haoyu.app.entity.MobileUser;
import com.haoyu.app.gdei.teacher.R;
import com.haoyu.app.swipe.SwipeMenuLayout;
import com.haoyu.app.imageloader.GlideImgManager;


import java.util.List;

/**
 * Created by acer1 on 2017/2/8.
 * 成员管理
 */
public class ManagementMemberAdapter extends BaseArrayRecyclerAdapter<ManagementMemberEntity> {
    private Activity context;
    private onItemClickCallBack itemClickCallBack;
    private onDisposeCallBack disposeCallBack;

    public ManagementMemberAdapter(Activity context, List<ManagementMemberEntity> mDatas) {
        super(mDatas);
        this.context = context;
    }

    public void setItemClickCallBack(onItemClickCallBack itemClickCallBack) {
        this.itemClickCallBack = itemClickCallBack;
    }

    public void setDisposeCallBack(onDisposeCallBack disposeCallBack) {
        this.disposeCallBack = disposeCallBack;
    }

    @Override
    public void onBindHoder(RecyclerHolder holder, ManagementMemberEntity mobileEntity, final int position) {
        final SwipeMenuLayout swipeLayout = holder.obtainView(R.id.swipeLayout);
        View contentView = holder.obtainView(R.id.contentView);
        ImageView ic_consult = holder.obtainView(R.id.ic_consult);
        TextView tv_title = holder.obtainView(R.id.tv_title);
        TextView tv_createDate = holder.obtainView(R.id.tv_createDate);
        Button bt_alter = holder.obtainView(R.id.bt_alter);
        Button bt_delete = holder.obtainView(R.id.bt_delete);
        final MobileUser mobileUser;
        swipeLayout.setIos(true);
        if (mobileEntity.getmUser() != null) {
            mobileUser = mobileEntity.getmUser();
            if (mobileUser != null) {
                if (mobileUser.getRealName() != null) {
                    tv_title.setText(mobileUser.getRealName());
                } else if (mobileUser.getDeptName() != null) {
                    tv_createDate.setText(mobileUser.getDeptName());
                }
                if (mobileUser.getAvatar() != null) {
                   /* Glide.with(context)
                            .load(mobileUser.getAvatar())
                            .placeholder(R.drawable.course_de_pic)
                            .error(R.drawable.user_default)
                            .dontAnimate().into(ic_consult);*/
                    GlideImgManager.loadCircleImage(context,mobileUser.getAvatar()
                            , R.drawable.user_default, R.drawable.user_default, ic_consult);
                } else {
                    ic_consult.setImageResource(R.drawable.user_default);
                }

            }
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.contentView:
                            if (swipeLayout.isExpand()) {
                                swipeLayout.smoothClose();
                            } else {
                                if (itemClickCallBack != null) {
                                    itemClickCallBack.onItemCallBack(position, mobileUser);
                                }
                            }
                            break;
                        case R.id.bt_alter:
                            swipeLayout.smoothClose();
                            if (disposeCallBack != null) {
                                disposeCallBack.onAlter(position, mobileUser);
                            }
                            break;
                        case R.id.bt_delete:
                            swipeLayout.smoothClose();
                            if (disposeCallBack != null) {
                                disposeCallBack.onDelete(position, mobileUser);
                            }
                            break;
                    }
                }
            };
            contentView.setOnClickListener(listener);
            bt_alter.setOnClickListener(listener);
            bt_delete.setOnClickListener(listener);
        }
    }


    @Override
    public int bindView(int viewtype) {
        return R.layout.management_member_item;
    }

    public interface onItemClickCallBack {
        void onItemCallBack(int position, MobileUser entity);
    }

    public interface onDisposeCallBack {
        void onAlter(int position, MobileUser entity);

        void onDelete(int position, MobileUser entity);
    }
}
