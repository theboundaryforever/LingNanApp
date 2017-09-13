package com.haoyu.app.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.haoyu.app.base.BaseActivity;
import com.haoyu.app.gdei.teacher.R;
import com.haoyu.app.rxBus.MessageEvent;
import com.haoyu.app.rxBus.RxBus;

import butterknife.BindView;

/**
 * Created by acer1 on 2017/2/21.
 * 搜索成员
 */
public class SearchMemeberActivity extends BaseActivity implements View.OnClickListener {
    private SearchMemeberActivity context = this;
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.et_name)
    EditText et_name;

    @Override
    public int setLayoutResID() {
        return R.layout.fragment_managent_member_search;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setListener() {
        rl_back.setOnClickListener(context);
        iv_search.setOnClickListener(context);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_search:
                String msg = et_name.getText().toString().trim();
                if (msg == null || msg.equals("")) {
                    toast(context, "请输入姓名");
                } else {
                    MessageEvent event = new MessageEvent();
                    event.setAction("nameSearch");
                    event.setObj(msg);
                    RxBus.getDefault().post(event);
                    finish();
                }
                break;
        }
    }
}
