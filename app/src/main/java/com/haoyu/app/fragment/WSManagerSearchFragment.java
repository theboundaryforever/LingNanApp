package com.haoyu.app.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.haoyu.app.base.BaseFragment;
import com.haoyu.app.gdei.teacher.R;
import com.haoyu.app.rxBus.MessageEvent;
import com.haoyu.app.rxBus.RxBus;

/**
 * Created by acer1 on 2017/6/28.
 */
public class WSManagerSearchFragment extends BaseFragment implements View.OnClickListener {

    private ImageView iv_search;
    private EditText et_name;

    @Override
    public int createView() {
        return R.layout.fragment_managent_member_search;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        iv_search = view.findViewById(R.id.iv_search);
        et_name = view.findViewById(R.id.et_name);
    }

    @Override
    public void setListener() {
        iv_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                String msg = et_name.getText().toString().trim();
                if (msg.length() <= 0) {
                    toast("请输入姓名");
                } else {
                    MessageEvent event = new MessageEvent();
                    event.setAction("nameSearch");
                    event.setObj(msg);
                    RxBus.getDefault().post(event);

                }
                break;
        }
    }
}
