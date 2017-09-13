package com.haoyu.app.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.haoyu.app.base.BaseActivity;
import com.haoyu.app.base.BaseResponseResult;
import com.haoyu.app.gdei.teacher.R;
import com.haoyu.app.rxBus.MessageEvent;
import com.haoyu.app.rxBus.RxBus;
import com.haoyu.app.utils.Action;
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
 * 学员批量评价
 * excell,优秀
 * qualified,合适
 * faile不合格
 */
public class StudentEvaluateActivity extends BaseActivity implements View.OnClickListener {
    private StudentEvaluateActivity context = this;
    @BindView(R.id.toolBar)
    AppToolBar toolBar;
    @BindView(R.id.radio1)
    RadioButton radio1;
    @BindView(R.id.radio2)
    RadioButton radio2;
    @BindView(R.id.radio3)
    RadioButton radio3;
    @BindView(R.id.evaluate_confirm)
    LinearLayout evaluate_confirm;
    @BindView(R.id.excell)
    RelativeLayout excell;
    @BindView(R.id.qualified)
    RelativeLayout qualified;
    @BindView(R.id.faile)
    RelativeLayout faile;
    private String workShopId;
    private ArrayList<String> workShopIdList = new ArrayList<>();

    @Override
    public int setLayoutResID() {
        return R.layout.activity_student_evaluate;
    }

    @Override
    public void initView() {
        workShopIdList = getIntent().getStringArrayListExtra("listId");
        workShopId = getIntent().getStringExtra("workshopId");
    }

    @Override
    public void setListener() {

        evaluate_confirm.setOnClickListener(context);
        excell.setOnClickListener(context);
        qualified.setOnClickListener(context);
        faile.setOnClickListener(context);
        toolBar.setOnLeftClickListener(new AppToolBar.OnLeftClickListener() {
            @Override
            public void onLeftClick(View view) {
                finish();
            }
        });

    }

    private String workshopResult;

    public void initData2() {

        String workshopUserIds = "";
        if (workShopIdList.size() > 0) {
            for (int i = 0; i < workShopIdList.size(); i++) {
                if (i < workShopIdList.size() - 1) {
                    workshopUserIds += workShopIdList.get(i) + ",";
                } else {
                    workshopUserIds += workShopIdList.get(i);
                }
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("_method", "put");
        map.put("workshopUserIds", workshopUserIds);
        map.put("workshopResult", workshopResult);
        String url = Constants.OUTRT_NET + "/master_" + workShopId + "/m/workshop_user/evaluate";
        OkHttpClientManager.postAsyn(context, url, new OkHttpClientManager.ResultCallback<BaseResponseResult>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                showTipDialog();
            }

            @Override
            public void onError(Request request, Exception e) {
                hideTipDialog();
                toastFullScreen("评价失败，请稍后重试", false);
            }

            @Override
            public void onResponse(BaseResponseResult response) {

                if (response != null && response.getSuccess() != null && response.getSuccess()) {
                    toastFullScreen("评价成功", true);
                    MessageEvent event = new MessageEvent();
                    event.setAction(Action.ASSESS_MEMBER);
                    RxBus.getDefault().post(event);
                    finish();
                } else {
                    toastFullScreen("评价失败，请稍后重试", false);
                }

            }
        }, map);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.evaluate_confirm:
                if (workshopResult != null) {
                    initData2();
                } else {
                    toast(context, "请选择评价");
                }
                break;
            case R.id.excell:
                workshopResult = "excellent";
                radio1.setChecked(true);
                radio2.setChecked(false);
                radio3.setChecked(false);
                break;
            case R.id.qualified:
                workshopResult = "qualified";
                radio2.setChecked(true);
                radio1.setChecked(false);
                radio3.setChecked(false);
                break;
            case R.id.faile:
                workshopResult = "fail";
                radio3.setChecked(true);
                radio2.setChecked(false);
                radio1.setChecked(false);
                break;

        }
    }


}
