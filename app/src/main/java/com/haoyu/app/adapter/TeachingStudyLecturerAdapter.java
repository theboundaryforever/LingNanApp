package com.haoyu.app.adapter;

import android.view.View;
import android.widget.TextView;

import com.haoyu.app.basehelper.BaseArrayRecyclerAdapter;

import com.haoyu.app.entity.MobileUser;
import com.haoyu.app.gdei.teacher.R;

import java.util.List;

/**
 * Created by acer1 on 2017/2/16.
 */
public class TeachingStudyLecturerAdapter extends BaseArrayRecyclerAdapter<MobileUser> {

    public TeachingStudyLecturerAdapter(List<MobileUser> mDatas) {
        super(mDatas);
    }


    @Override
    public int bindView(int viewtype) {
        return R.layout.teaching_study_lecture_item;
    }


    @Override
    public void onBindHoder(RecyclerHolder holder, final MobileUser mobileUser, int position) {
        TextView tv_name = holder.obtainView(R.id.tv_name);
        if (mobileUser != null && mobileUser.getRealName() != null) {
            tv_name.setText(mobileUser.getRealName());
            tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    personName.setName(mobileUser);
                }
            });
        } else {
            tv_name.setText("匿名用户");
        }

    }

    public interface PersonName {
        void setName(MobileUser mobileUser);
    }

    public PersonName personName;

    public void setPersonName(PersonName personName) {
        this.personName = personName;
    }

}
