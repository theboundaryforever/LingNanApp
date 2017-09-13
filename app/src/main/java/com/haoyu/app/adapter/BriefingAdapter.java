package com.haoyu.app.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.haoyu.app.basehelper.BaseArrayRecyclerAdapter;
import com.haoyu.app.entity.BriefingEntity;
import com.haoyu.app.gdei.teacher.R;
import com.haoyu.app.swipe.SwipeMenuLayout;
import com.haoyu.app.utils.TimeUtil;

import java.util.List;

/**
 * 创建日期：2017/1/5 on 14:31
 * 描述: 工作坊简报适配器
 * 作者:马飞奔 Administrator
 */
public class BriefingAdapter extends BaseArrayRecyclerAdapter<BriefingEntity> {
    private onItemClickCallBack itemClickCallBack;
    private onDisposeCallBack disposeCallBack;

    public BriefingAdapter(List<BriefingEntity> mDatas) {
        super(mDatas);
    }

    public void setItemClickCallBack(onItemClickCallBack itemClickCallBack) {
        this.itemClickCallBack = itemClickCallBack;
    }

    public void setDisposeCallBack(onDisposeCallBack disposeCallBack) {
        this.disposeCallBack = disposeCallBack;
    }

    @Override
    public void onBindHoder(RecyclerHolder holder, final BriefingEntity entity, final int position) {
        final SwipeMenuLayout swipeLayout = holder.obtainView(R.id.swipeLayout);
        View contentView = holder.obtainView(R.id.ll_click);
        Button bt_alter = holder.obtainView(R.id.bt_alter);
        Button bt_delete = holder.obtainView(R.id.bt_delete);

        TextView briefContent = holder.obtainView(R.id.briefContent);
        TextView briefCreateTime = holder.obtainView(R.id.briefCreateTime);
        swipeLayout.setIos(true);
        briefContent.setText(entity.getTitle());
        briefCreateTime.setText(TimeUtil.getSlashDate(entity.getCreateTime()));
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ll_click:
                        if (swipeLayout.isExpand()) {
                            swipeLayout.smoothClose();
                        } else {
                            if (itemClickCallBack != null) {
                                itemClickCallBack.onItemCallBack(position, entity.getId());
                            }
                        }
                        break;
                    case R.id.bt_alter:
                        swipeLayout.smoothClose();
                        if (disposeCallBack != null) {
                            disposeCallBack.onAlter(position, entity);
                        }
                        break;
                    case R.id.bt_delete:
                        swipeLayout.smoothClose();
                        if (disposeCallBack != null) {
                            disposeCallBack.onDelete(position, entity);
                        }
                        break;
                }
            }
        };
        contentView.setOnClickListener(listener);
        bt_alter.setOnClickListener(listener);
        bt_delete.setOnClickListener(listener);
    }

    @Override
    public int bindView(int viewtype) {
        return R.layout.workshop_brief_item;
    }

    public interface onItemClickCallBack {
        void onItemCallBack(int position, String id);
    }

    public interface onDisposeCallBack {
        void onAlter(int position, BriefingEntity entity);

        void onDelete(int position, BriefingEntity entity);
    }
}
