package com.haoyu.app.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoyu.app.base.BaseActivity;
import com.haoyu.app.base.BaseResponseResult;
import com.haoyu.app.gdei.teacher.R;
import com.haoyu.app.utils.Constants;
import com.haoyu.app.utils.OkHttpClientManager;
import com.haoyu.app.view.AppToolBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Request;

/**
 * Created by acer1 on 2017/2/8.
 * 向学员发送消息
 */
public class StudentSendMessageActivity extends BaseActivity {
    private StudentSendMessageActivity context = this;
    @BindView(R.id.toolBar)
    AppToolBar toolBar;
    @BindView(R.id.et_content)
    EditText content;
    private ArrayList<String> workShopIdList = new ArrayList<>();

    @Override
    public int setLayoutResID() {
        return R.layout.activity_student_send_msg;
    }

    @Override
    public void initView() {
        workShopIdList = getIntent().getStringArrayListExtra("listId");
    }

    @Override
    public void setListener() {

        toolBar.setOnLeftClickListener(new AppToolBar.OnLeftClickListener() {
            @Override
            public void onLeftClick(View view) {
                finish();
            }
        });
        toolBar.setOnRightClickListener(new AppToolBar.OnRightClickListener() {
            @Override
            public void onRightClick(View view) {
                sendMsg();
            }
        });




    }

    private void sendMsg() {
        String msg = content.getText().toString();
        if (msg != null && !msg.equals("")) {
            for (int i = 0; i < workShopIdList.size(); i++) {
                initData(workShopIdList.get(i), msg);
            }
        } else {
            toast(context, "请输入内容");
        }

    }

    private void initData(String receiverId, String msg) {
        String url = Constants.OUTRT_NET + "/m/message";
        Map<String, String> map = new HashMap<>();
        map.put("sender.id", context.getUserId());
        map.put("receiver.id", receiverId);
        map.put("content", msg);
        OkHttpClientManager.postAsyn(context, url, new OkHttpClientManager.ResultCallback<BaseResponseResult>() {
            @Override
            public void onError(Request request, Exception e) {
                toastFullScreen("发送失败", false);
            }

            @Override
            public void onResponse(BaseResponseResult response) {
                if (response != null && response.getSuccess() != null && response.getSuccess()) {
                    toastFullScreen("发送成功", true);
                    finish();
                } else {
                    toastFullScreen("发送失败", false);
                }
            }
        }, map);

    }
}
