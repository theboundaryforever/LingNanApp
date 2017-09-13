package com.haoyu.app.adapter;

import android.view.View;
import android.widget.ImageView;

import com.haoyu.app.basehelper.BaseArrayRecyclerAdapter;
import com.haoyu.app.entity.CourseChildSectionEntity;
import com.haoyu.app.entity.CourseSectionEntity;
import com.haoyu.app.entity.MultiItemEntity;
import com.haoyu.app.gdei.teacher.R;

import java.util.List;

/**
 * 创建日期：2017/9/12 on 16:36
 * 描述:
 * 作者:马飞奔 Administrator
 */
public class CourseSectionAdapter extends BaseArrayRecyclerAdapter<MultiItemEntity> {

    private OnSectionClickListener onSectionClickListener;

    public CourseSectionAdapter(List<MultiItemEntity> mDatas) {
        super(mDatas);
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getItemType();
    }

    @Override
    public int bindView(int viewtype) {
        if (viewtype == 0)
            return R.layout.course_section_item;
        else
            return R.layout.course_section_child_item;
    }

    @Override
    public void onBindHoder(RecyclerHolder holder, MultiItemEntity item, final int position) {
        int viewType = holder.getItemViewType();
        if (viewType == 0) {
            CourseSectionEntity sectionEntity = (CourseSectionEntity) item;
            if (sectionEntity.getTitle() != null && sectionEntity.getTitle().trim().length() > 0)
                holder.setText(R.id.course_title, sectionEntity.getTitle());
            else
                holder.setText(R.id.course_title, "无标题");
        } else {
            final CourseChildSectionEntity childEntity = (CourseChildSectionEntity) item;
            ImageView ic_selection_state = holder.obtainView(R.id.ic_selection_state);
            ic_selection_state.setVisibility(View.GONE);
            if (childEntity.getTitle() != null && childEntity.getTitle().trim().length() > 0)
                holder.setText(R.id.tv_selection_title, childEntity.getTitle());
            else
                holder.setText(R.id.tv_selection_title, "无标题");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onSectionClickListener != null)
                        onSectionClickListener.onSectionSelected(childEntity);
                }
            });
        }
    }

    public interface OnSectionClickListener {
        void onSectionSelected(CourseChildSectionEntity entity);
    }

    public void setOnSectionClickListener(OnSectionClickListener onSectionClickListener) {
        this.onSectionClickListener = onSectionClickListener;
    }
}
