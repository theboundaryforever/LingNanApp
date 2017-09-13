package com.haoyu.app.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.haoyu.app.basehelper.BaseArrayRecyclerAdapter;
import com.haoyu.app.entity.MobileUser;
import com.haoyu.app.gdei.teacher.R;
import com.haoyu.app.swipe.SwipeMenuLayout;

import java.util.List;

/**
 * Created by acer1 on 2017/2/16.
 * 受邀人员
 */
public class TeachingInvitedAdapter extends BaseArrayRecyclerAdapter<MobileUser> {
    private Context context;

    public TeachingInvitedAdapter(Context context, List<MobileUser> mDatas) {
        super(mDatas);
        this.context = context;
    }

    @Override
    public int bindView(int viewtype) {
        return R.layout.teaching_study_lecture_item2;
    }

    @Override
    public void onBindHoder(RecyclerHolder holder, final MobileUser mobileUser, final int position) {
        final SwipeMenuLayout swipeLayout = holder.obtainView(R.id.swipeLayout);
        View contentView = holder.obtainView(R.id.contentView);
        swipeLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
        Button bt_alter = holder.obtainView(R.id.bt_alter);
        Button bt_delete = holder.obtainView(R.id.bt_delete);
        TextView tv_name = holder.obtainView(R.id.tv_name);
        tv_name.setText(mobileUser.getRealName());
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

    private onItemClickCallBack itemClickCallBack;
    private onDisposeCallBack disposeCallBack;

    public void setItemClickCallBack(onItemClickCallBack itemClickCallBack) {
        this.itemClickCallBack = itemClickCallBack;
    }

    public void setDisposeCallBack(onDisposeCallBack disposeCallBack) {
        this.disposeCallBack = disposeCallBack;
    }

    public interface onItemClickCallBack {
        void onItemCallBack(int position, MobileUser entity);
    }

    public interface onDisposeCallBack {
        void onAlter(int position, MobileUser entity);

        void onDelete(int position, MobileUser entity);
    }

}
