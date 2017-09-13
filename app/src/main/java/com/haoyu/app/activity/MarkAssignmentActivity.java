package com.haoyu.app.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoyu.app.adapter.EvaluateItemAdapter;
import com.haoyu.app.base.BaseActivity;
import com.haoyu.app.base.BaseResponseResult;
import com.haoyu.app.dialog.MaterialDialog;
import com.haoyu.app.entity.EvaluateItemSubmissions;
import com.haoyu.app.entity.MAssignmentUser;
import com.haoyu.app.entity.MFileInfo;
import com.haoyu.app.entity.MarkAssignmentResult;
import com.haoyu.app.gdei.teacher.R;
import com.haoyu.app.rxBus.MessageEvent;
import com.haoyu.app.rxBus.RxBus;
import com.haoyu.app.utils.Action;
import com.haoyu.app.utils.Common;
import com.haoyu.app.utils.Constants;
import com.haoyu.app.utils.OkHttpClientManager;
import com.haoyu.app.utils.Page;
import com.haoyu.app.view.FullyLinearLayoutManager;
import com.haoyu.app.view.LoadFailView;
import com.haoyu.app.view.LoadingView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Request;

/**
 * 创建日期：2017/2/6 on 17:38
 * 描述:批改作业
 * 作者:马飞奔 Administrator
 */
public class MarkAssignmentActivity extends BaseActivity implements View.OnClickListener {
    private MarkAssignmentActivity context = this;
    @BindView(R.id.iv_back)
    View iv_back;
    @BindView(R.id.tv_userName)
    TextView tv_userName;   //用户名称
    @BindView(R.id.loadingView)
    LoadingView loadingView;
    @BindView(R.id.loadFailView)
    LoadFailView loadFailView;
    @BindView(R.id.contentView)
    View contentView;
    @BindView(R.id.tv_assignmentName)
    TextView tv_assignmentName;  //作业名称
    @BindView(R.id.mFilePager)
    ViewPager mFilePager;  //文件列表
    @BindView(R.id.mFileIndicator)
    LinearLayout mFileIndicator;  //文件列表指示器
    @BindView(R.id.contentRV)
    RecyclerView contentRV;  //评价内容列表
    @BindView(R.id.scoreLayout)
    View scoreLayout;   //作业打分布局（默认不可见）
    @BindView(R.id.tv_score)
    TextView tv_score;  //作业打分
    @BindView(R.id.tv_fullScore)
    TextView tv_fullScore;  //满分
    @BindView(R.id.bt_return)
    Button bt_return;
    @BindView(R.id.bt_submit)
    Button bt_submit;   //发回重做，提交按钮
    private String courseId, userName, relationId, state, mEvaluateSubmissionId, evaluateRelationId;
    private double fullScore;

    @Override
    public int setLayoutResID() {
        return R.layout.activity_mark_assignment;
    }

    @Override
    public void initView() {
        courseId = getIntent().getStringExtra("courseId");
        userName = getIntent().getStringExtra("userName");
        relationId = getIntent().getStringExtra("relationId");
        state = getIntent().getStringExtra("state");
        tv_userName.setText(userName);
    }

    public void initData() {
        String url = Constants.OUTRT_NET + "/" + courseId + "/teach/m/assignment/mark/" + relationId + "/markByTeacher";
        OkHttpClientManager.getAsyn(context, url, new OkHttpClientManager.ResultCallback<MarkAssignmentResult>() {
            @Override
            public void onBefore(Request request) {
                loadingView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Request request, Exception e) {
                loadingView.setVisibility(View.GONE);
                loadFailView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onResponse(MarkAssignmentResult response) {
                loadingView.setVisibility(View.GONE);
                contentView.setVisibility(View.VISIBLE);
                if (response != null && response.getResponseData() != null && response.getResponseData().getmEvaluateSubmission() != null) {
                    mEvaluateSubmissionId = response.getResponseData().getmEvaluateSubmission().getId();
                    evaluateRelationId = response.getResponseData().getmEvaluateSubmission().getEvaluateRelationId();
                }
                if (response != null && response.getResponseData() != null && response.getResponseData().getmAssignmentUser() != null) {
                    updateUI(response.getResponseData().getmAssignmentUser());
                }
                if (response != null && response.getResponseData() != null && response.getResponseData().getmEvaluateSubmission() != null
                        && response.getResponseData().getmEvaluateSubmission().getmEvaluateItemSubmissions() != null) {
                    updateUI(response.getResponseData().getmEvaluateSubmission().getmEvaluateItemSubmissions());
                }
            }
        });
    }

    private ImageView[] indicatorViews;

    private void updateUI(MAssignmentUser mAssignmentUser) {
        if (mAssignmentUser.getmFileInfos() != null && mAssignmentUser.getmFileInfos().size() > 0) {
            Page page = new Page(mAssignmentUser.getmFileInfos(), 2);
            ArrayMap<Integer, List<MFileInfo>> mFileDatas = new ArrayMap<>();
            int totalPage = page.getTotalPage();
            for (int i = 0; i < totalPage; i++) {
                mFileDatas.put(i, page.getPage(i + 1));
            }
            if (totalPage > 1) {
                indicatorViews = new ImageView[totalPage];
                for (int i = 0; i < totalPage; i++) {   //位置从0开始 页数从1开始
                    indicatorViews[i] = new ImageView(context);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = (int) getResources().getDimension(R.dimen.margin_size_4);
                    indicatorViews[i].setLayoutParams(params);
                    indicatorViews[i].setImageResource(R.drawable.course_yuandian_default);
                    mFileIndicator.addView(indicatorViews[i]);
                }
                indicatorViews[0].setImageResource(R.drawable.course_yuandian_press);
            }
            FilePageAdapter adapter = new FilePageAdapter(mFileDatas);
            mFilePager.setAdapter(adapter);
        }
        if (mAssignmentUser.getState() != null && mAssignmentUser.getState().equals("return")) {
            bt_return.setText("已退回重做");
            bt_return.setEnabled(false);
            bt_submit.setVisibility(View.GONE);
        } else if (mAssignmentUser.getState() != null && mAssignmentUser.getState().equals("complete")) {
            bt_submit.setText("重新批阅");
            bt_submit.setEnabled(false);
            tv_score.setText(String.valueOf(getScore(mAssignmentUser.getResponseScore())));
        }
        if (mAssignmentUser.getmAssignment() != null) {
            tv_assignmentName.setText(mAssignmentUser.getmAssignment().getTitle());
            tv_fullScore.setText(String.valueOf((int) (100 - mAssignmentUser.getmAssignment().getMarkScorePct())));
            fullScore = 100 - mAssignmentUser.getmAssignment().getMarkScorePct();
        } else {
            tv_fullScore.setText(String.valueOf(100));
            fullScore = 100;
        }
    }

    private int getScore(double score) {
        BigDecimal b = new BigDecimal(score);
        int count = (int) b.setScale(0, BigDecimal.ROUND_HALF_UP).floatValue();
        return count;
    }

    private void updateUI(List<EvaluateItemSubmissions> mDatas) {
        for (EvaluateItemSubmissions item : mDatas) {
            item.setEvaluateMark(fullScore / mDatas.size());
        }
        EvaluateItemAdapter evaluateAdapter = new EvaluateItemAdapter(mDatas, state);
        FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(context);
        layoutManager.setOrientation(FullyLinearLayoutManager.VERTICAL);
        contentRV.setLayoutManager(layoutManager);
        contentRV.setAdapter(evaluateAdapter);
        evaluateAdapter.setScoreChangeListener(new EvaluateItemAdapter.ScoreChangeListener() {
            @Override
            public void scoreChange(ArrayMap<Integer, EvaluateItemSubmissions> evaluateMap) {
                scoreLayout.setVisibility(View.VISIBLE);
                itemSubmissionsMap = evaluateMap;
                bt_submit.setEnabled(true);
                int score = 0;
                for (Integer index : evaluateMap.keySet()) {
                    score += evaluateMap.get(index).getScore();
                }
                tv_score.setText(String.valueOf(score));
            }
        });
    }

    private ArrayMap<Integer, EvaluateItemSubmissions> itemSubmissionsMap;

    @Override
    public void setListener() {
        iv_back.setOnClickListener(context);
        loadFailView.setOnRetryListener(new LoadFailView.OnRetryListener() {
            @Override
            public void onRetry(View v) {
                initData();
            }
        });
        bt_return.setOnClickListener(context);
        bt_submit.setOnClickListener(context);
        mFilePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (indicatorViews != null && indicatorViews.length > 0) {
                    for (int i = 0; i < indicatorViews.length; i++) {
                        if (i == position)
                            indicatorViews[i].setImageResource(R.drawable.course_yuandian_press);
                        else
                            indicatorViews[i].setImageResource(R.drawable.course_yuandian_default);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_return:   //发回重做
                MaterialDialog dialog = new MaterialDialog(context);
                dialog.setTitle("提示");
                dialog.setMessage("作业退回后无法重新批阅，只能等待学员再次提交，确定要退回该作业吗？");
                dialog.setNegativeButton("取消", null);
                dialog.setPositiveButton("确定", new MaterialDialog.ButtonClickListener() {
                    @Override
                    public void onClick(View v, AlertDialog dialog) {
                        dialog.dismiss();
                        backtoRedo();
                    }
                });
                dialog.show();
                break;
            case R.id.bt_submit:  //提交批阅
                MaterialDialog mainDialog = new MaterialDialog(context);
                mainDialog.setTitle("提示");
                mainDialog.setMessage("确定要提交对作业\n的打分吗？");
                mainDialog.setNegativeButton("取消", null);
                mainDialog.setPositiveButton("确定", new MaterialDialog.ButtonClickListener() {
                    @Override
                    public void onClick(View v, AlertDialog dialog) {
                        commit();
                    }
                });
                mainDialog.show();
                break;
        }
    }

    /*发回重做*/
    private void backtoRedo() {
        String userId = getUserId();
        String url = Constants.OUTRT_NET + "/" + courseId + "/teach/unique_uid_" + userId
                + "/m/assignment/user/" + relationId;
        Map<String, String> map = new HashMap<>();
        map.put("_method", "put");
        map.put("state", "return");
        OkHttpClientManager.postAsyn(context, url, new OkHttpClientManager.ResultCallback<BaseResponseResult>() {
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
                    MessageEvent event = new MessageEvent();
                    event.action = Action.RETURN_ASSIGNMENT_REDO;
                    RxBus.getDefault().post(event);
                    finish();
                } else {
                    toastFullScreen("退回失败", false);
                }
            }
        }, map);
    }

    /*提交批阅*/
    private void commit() {
        String userId = getUserId();
        String url = Constants.OUTRT_NET + "/" + courseId + "/teach/unique_uid_"
                + userId + "/m/evaluate/submission/" + mEvaluateSubmissionId;
        Map<String, String> map = new HashMap<>();
        map.put("_method", "put");
        map.put("evaluateRelation.id", evaluateRelationId);
        map.put("evaluateRelation.relation.id", relationId);
        if (itemSubmissionsMap != null) {
            for (Integer index : itemSubmissionsMap.keySet()) {
                EvaluateItemSubmissions item = itemSubmissionsMap.get(index);
                if (item != null) {
                    map.put("evaluateItemSubmissionMap[" + item.getId() + "].score", String.valueOf(item.getStarCount()));
                }
            }
        }
        OkHttpClientManager.postAsyn(context, url, new OkHttpClientManager.ResultCallback<BaseResponseResult>() {
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
                    int score = 0;
                    if (itemSubmissionsMap != null) {
                        for (Integer index : itemSubmissionsMap.keySet()) {
                            score += itemSubmissionsMap.get(index).getScore();
                        }
                    }
                    MessageEvent event = new MessageEvent();
                    event.action = Action.READ_OVER_ASSIGNMENT;
                    event.arg1 = score;
                    RxBus.getDefault().post(event);
                    finish();
                } else {
                    toast(context, "提交失败");
                }
            }
        }, map);
    }

    class FilePageAdapter extends PagerAdapter {
        private ArrayMap<Integer, List<MFileInfo>> mFileDatas;

        public FilePageAdapter(ArrayMap<Integer, List<MFileInfo>> mFileDatas) {
            this.mFileDatas = mFileDatas;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mFileDatas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.assignment_file_item, null);
            final List<MFileInfo> mFileInfos = mFileDatas.get(position);
            ImageView iv_fileType1 = (ImageView) view.findViewById(R.id.iv_fileType1);
            ImageView iv_fileType2 = (ImageView) view.findViewById(R.id.iv_fileType2);
            TextView tv_mFileName1 = (TextView) view.findViewById(R.id.tv_mFileName1);
            TextView tv_mFileName2 = (TextView) view.findViewById(R.id.tv_mFileName2);
            View line = view.findViewById(R.id.line);
            View file_layout1 = view.findViewById(R.id.file_layout1);
            View file_layout2 = view.findViewById(R.id.file_layout2);
            if (mFileInfos != null && mFileInfos.size() > 0) {
                final MFileInfo fileInfo1 = mFileInfos.get(0);
                Common.setFileType(fileInfo1.getUrl(), iv_fileType1);
                tv_mFileName1.setText(fileInfo1.getFileName());
                file_layout1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (fileInfo1 != null && fileInfo1.getUrl() != null) {
                            Intent intent = new Intent(context, MFileInfoActivity.class);
                            intent.putExtra("fileInfo", fileInfo1);
                            startActivity(intent);
                        } else {
                            toast(context, "文件链接不存在");
                        }
                    }
                });
                if (mFileInfos.size() > 1) {
                    final MFileInfo fileInfo2 = mFileInfos.get(1);
                    Common.setFileType(fileInfo2.getUrl(), iv_fileType2);
                    tv_mFileName2.setText(fileInfo2.getFileName());
                    file_layout2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (fileInfo2 != null && fileInfo2.getUrl() != null) {
                                Intent intent = new Intent(context, MFileInfoActivity.class);
                                intent.putExtra("fileInfo", fileInfo2);
                                startActivity(intent);
                            } else {
                                toast(context, "文件链接不存在");
                            }
                        }
                    });

                } else {
                    line.setVisibility(View.GONE);
                    file_layout2.setVisibility(View.GONE);
                }
            }
            container.addView(view, 0);//添加页卡
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);//删除页卡
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
