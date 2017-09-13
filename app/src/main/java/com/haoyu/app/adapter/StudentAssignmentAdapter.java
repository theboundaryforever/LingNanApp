package com.haoyu.app.adapter;

import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoyu.app.activity.StudentAssignmentActivity;
import com.haoyu.app.basehelper.BaseArrayRecyclerAdapter;
import com.haoyu.app.entity.ManagementMemberEntity;
import com.haoyu.app.entity.MobileUser;
import com.haoyu.app.gdei.teacher.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer1 on 2017/2/8.
 * evaluate	评价	String	N	null:未评价
 * excellent：优秀
 * qualified：合格
 * fail：不合格
 * point	积分	BigDecimal	N
 */
public class StudentAssignmentAdapter extends BaseArrayRecyclerAdapter<ManagementMemberEntity> {
    private StudentAssignmentActivity context;
    private List<String> userIdList = new ArrayList<>();
    public StudentAssignmentAdapter(StudentAssignmentActivity context, List<ManagementMemberEntity> mDatas, List<String> userIdList) {
        super(mDatas);
        this.context = context;
        this.userIdList = userIdList;
    }

    @Override
    public void onBindHoder(RecyclerHolder holder, final ManagementMemberEntity managementMemberEntity, int position) {
        View ll_view = holder.obtainView(R.id.ll_view);
        final LinearLayout checkBox = holder.obtainView(R.id.checkBox);
        final CheckBox p_check = holder.obtainView(R.id.p_check);
        TextView p_name = holder.obtainView(R.id.p_name);
        TextView p_score = holder.obtainView(R.id.p_score);
        TextView p_evaluate = holder.obtainView(R.id.p_evaluate);
        TextView p_all_evaluate = holder.obtainView(R.id.p_all_evaluate);
        LinearLayout p_all_evaluate_lin = holder.obtainView(R.id.p_all_evaluate_lin);
        TextView creator_eva = holder.obtainView(R.id.creator_eva);//评估人
        MobileUser mobileUser;
        String state = context.getCheckState();
        if (state != null && state.equals("3")) {
            p_name.setGravity(Gravity.LEFT);
            checkBox.setVisibility(View.VISIBLE);
            p_check.setChecked(false);
            p_all_evaluate_lin.setVisibility(View.GONE);
            p_check.setVisibility(View.VISIBLE);
        } else if (state != null && state.equals("1")) {
            p_name.setGravity(Gravity.LEFT);
            checkBox.setVisibility(View.VISIBLE);
            p_check.setChecked(true);
            p_all_evaluate_lin.setVisibility(View.GONE);
            p_check.setVisibility(View.VISIBLE);
        } else if (state != null && state.equals("2")) {
            //  p_name.setGravity(Gravity.LEFT);
            p_all_evaluate_lin.setVisibility(View.GONE);
            p_all_evaluate_lin.setVisibility(View.GONE);
            checkBox.setVisibility(View.VISIBLE);
            p_check.setVisibility(View.VISIBLE);
            p_check.setChecked(false);
        } else {
            // p_name.setGravity(Gravity.CENTER);
            checkBox.setVisibility(View.GONE);
            p_check.setChecked(true);
            p_check.setVisibility(View.VISIBLE);
            p_all_evaluate.setVisibility(View.VISIBLE);

        }
        if (userIdList != null && userIdList.size() > 0) {
            String userId = null;
            if (managementMemberEntity != null && managementMemberEntity.getFinallyResult() != null && managementMemberEntity.getId() != null) {
                userId = managementMemberEntity.getId();
            }
            if (userIdList.contains(userId) && userId != null) {
                p_check.setChecked(true);
            }
        }

        //总评价
        if (managementMemberEntity != null && managementMemberEntity.getFinallyResult() != null) {
            String evaluate = managementMemberEntity.getFinallyResult();
            if (evaluate.equals("excellent")) {
                p_all_evaluate.setText("优秀");
                p_all_evaluate.setTextColor(context.getResources().getColor(R.color.orange));
            } else if (evaluate.equals("qualified")) {
                p_all_evaluate.setText("合格");
                p_all_evaluate.setTextColor(context.getResources().getColor(R.color.defaultColor));
            } else if (evaluate.equals("fail")) {
                p_all_evaluate.setText("未达标");
                p_all_evaluate.setTextColor(context.getResources().getColor(R.color.lightpink));
            }
        } else {
            p_evaluate.setTextColor(context.getResources().getColor(R.color.skyblue));
            p_all_evaluate.setText("未评价");
        }
        //评价
        if (managementMemberEntity != null && managementMemberEntity.getmUser() != null) {
            mobileUser = managementMemberEntity.getmUser();
            if (managementMemberEntity.getEvaluateCreator() != null && managementMemberEntity.getEvaluateCreator().getRealName() != null) {
                creator_eva.setText("评估人:" + managementMemberEntity.getEvaluateCreator().getRealName());
                creator_eva.setVisibility(View.VISIBLE);
            }
            if (mobileUser.getRealName() != null) {
                p_name.setText(mobileUser.getRealName());
            }
            String point = String.valueOf(managementMemberEntity.getPoint());

            p_score.setText(point);
            if (managementMemberEntity.getEvaluate() != null) {
                String evaluate = managementMemberEntity.getEvaluate();

                if (evaluate.equals("excellent")) {
                    p_evaluate.setText("优秀");
                    p_evaluate.setTextColor(context.getResources().getColor(R.color.orange));
                } else if (evaluate.equals("qualified")) {
                    p_evaluate.setText("合格");
                    p_evaluate.setTextColor(context.getResources().getColor(R.color.defaultColor));
                } else if (evaluate.equals("fail")) {
                    p_evaluate.setText("未达标");
                    p_evaluate.setTextColor(context.getResources().getColor(R.color.lightpink));
                }

            } else {
                p_evaluate.setTextColor(context.getResources().getColor(R.color.skyblue));
                p_evaluate.setText("未评价");
            }

        }
        ll_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p_check.isChecked()) {
                    p_check.setChecked(false);
                } else {
                    p_check.setChecked(true);
                }

                if (managementMemberEntity != null && managementMemberEntity.getId() != null) {
                    evaluateBack.putEveluateId(managementMemberEntity.getId(), p_check.isChecked());

                }
            }
        });


    }

    @Override
    public int bindView(int viewtype) {
        return R.layout.student_assignment_item;
    }

    public EvaluateBack evaluateBack;

    public interface EvaluateBack {
        void putEveluateId(String id, boolean isCheck);
    }

    public void setEvaluateBack(EvaluateBack mEvaluateBack) {
        this.evaluateBack = mEvaluateBack;
    }
}
