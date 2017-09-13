package com.haoyu.app.adapter;

import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.haoyu.app.basehelper.BaseArrayRecyclerAdapter;
import com.haoyu.app.entity.MobileUser;
import com.haoyu.app.gdei.teacher.R;

import java.util.List;

/**
 * Created by acer1 on 2017/3/16.
 * 教研活动类型
 */
public class TeachingResearchInvitedAdapter extends BaseArrayRecyclerAdapter<MobileUser> {
    private ArrayMap<Integer, Boolean> isSelected = new ArrayMap<>();

    public TeachingResearchInvitedAdapter(List<MobileUser> mDatas) {
        super(mDatas);
        for (int i = 0; i < mDatas.size(); i++) {
            isSelected.put(i, false);
        }
    }

    @Override
    public boolean updateItem(int position) {
        setChecked(position);
        return super.updateItem(position);
    }

    @Override
    public void onBindHoder(RecyclerHolder holder, MobileUser mobileUser, final int position) {
        CheckBox checkBox = holder.obtainView(R.id.checkBox);
        TextView tv_userName = holder.obtainView(R.id.tv_userName);
        tv_userName.setText(mobileUser.getRealName());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChecked(position);
            }
        });
        checkBox.setChecked(isSelected.get(position));
    }

    public void selectAll() {
        for (int i = 0; i < mDatas.size(); i++) {
            isSelected.put(i, true);
        }
        notifyDataSetChanged();
    }

    public void clearAll() {
        for (int i = 0; i < mDatas.size(); i++) {
            isSelected.put(i, false);
        }
        notifyDataSetChanged();
    }

    private void setChecked(int position) {
        boolean isChecked = !isSelected.get(position);
        isSelected.put(position, isChecked);
        notifyDataSetChanged();
        if (selectListener != null) {
            selectListener.onSelect(isSelected);
        }
    }

    public ArrayMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    private OnSelectListener selectListener;

    public void setOnSelectListener(OnSelectListener selectListener) {
        this.selectListener = selectListener;
    }

    @Override
    public int bindView(int viewtype) {
        return R.layout.invited_user_item;
    }

    public interface OnSelectListener {
        void onSelect(ArrayMap<Integer, Boolean> isSelected);
    }
}
