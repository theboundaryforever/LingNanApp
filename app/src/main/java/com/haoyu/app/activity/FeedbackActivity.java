package com.haoyu.app.activity;

import android.app.AlertDialog;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.haoyu.app.base.BaseActivity;
import com.haoyu.app.base.BaseResponseResult;
import com.haoyu.app.dialog.MaterialDialog;
import com.haoyu.app.gdei.teacher.R;
import com.haoyu.app.utils.Constants;
import com.haoyu.app.utils.OkHttpClientManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Request;

/**
 * 意见反馈
 *
 * @author xiaoma
 */
public class FeedbackActivity extends BaseActivity implements OnClickListener {
    private FeedbackActivity context = this;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_problem_detail)
    EditText et_problem_detail;
    @BindView(R.id.bt_submit)
    Button bt_submit;   //提交

    @Override
    public int setLayoutResID() {
        return R.layout.activity_feekback;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setListener() {
        iv_back.setOnClickListener(context);
        bt_submit.setOnClickListener(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_submit:
                String problem_detail = et_problem_detail.getText().toString().trim();
                if (problem_detail.length() == 0) {
                    showMaterialDialog("提示", "请输入问题描述！");
                } else {
                    commit(problem_detail);
                }
                break;
        }
    }

    private void commit(String problem_detail) {
        String url = Constants.OUTRT_NET + "/m/feedback/create";
        Map<String, String> map = new HashMap<>();
        map.put("content", problem_detail);
        addSubscription(OkHttpClientManager.postAsyn(context, url, new OkHttpClientManager.ResultCallback<BaseResponseResult>() {
            @Override
            public void onBefore(Request request) {
                showTipDialog();
            }

            @Override
            public void onError(Request request, Exception e) {
                hideTipDialog();
                onNetWorkError(context);
            }

            @Override
            public void onResponse(BaseResponseResult response) {
                hideTipDialog();
                if (response != null && response.getResponseCode() != null && response.getResponseCode().equals("00")) {
                    et_problem_detail.setText("");
                    showTipsDialog();
                }
            }
        }, map));
    }

    private void showTipsDialog() {
        MaterialDialog dialog = new MaterialDialog(context);
        dialog.setTitle("反馈结果");
        dialog.setMessage("感谢您提交的反馈信息！\n您的意见将有助于改进我们的平台。");
        dialog.setNegativeButton("返回上一级", new MaterialDialog.ButtonClickListener() {
            @Override
            public void onClick(View v, AlertDialog dialog) {
                finish();
            }
        });
        dialog.setPositiveButton("留在此页", null);
        dialog.setNegativeTextColor(ContextCompat.getColor(context, R.color.defaultColor));
        dialog.setPositiveTextColor(ContextCompat.getColor(context, R.color.blow_gray));
        dialog.show();
    }
}
