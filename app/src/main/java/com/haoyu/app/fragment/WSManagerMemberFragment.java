package com.haoyu.app.fragment;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoyu.app.adapter.ManagementMemberAdapter;
import com.haoyu.app.base.BaseFragment;
import com.haoyu.app.base.BaseResponseResult;
import com.haoyu.app.entity.ManagementMemberEntity;
import com.haoyu.app.entity.ManagementMemberResult;
import com.haoyu.app.entity.MobileUser;
import com.haoyu.app.gdei.teacher.R;
import com.haoyu.app.rxBus.MessageEvent;
import com.haoyu.app.utils.Constants;
import com.haoyu.app.utils.OkHttpClientManager;
import com.haoyu.app.view.LoadFailView;
import com.haoyu.app.view.LoadingView;
import com.haoyu.app.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by acer1 on 2017/6/28.
 */
public class WSManagerMemberFragment extends BaseFragment implements View.OnClickListener, XRecyclerView.LoadingListener {
    private Activity context;
    private TextView member_count;//人数
    private XRecyclerView xRecyclerView;
    private TextView warn_msg;
    private String workshopId;
    private int page = 1;
    private boolean isRefresh, isLoadMore;
    private ManagementMemberAdapter adapter;
    private List<ManagementMemberEntity> mobileUserList = new ArrayList<>();
    private LinearLayout ll_show;
    private LoadingView loadingView;
    private LoadFailView loadFailView;
    private String searchName;

    @Override
    public void obBusEvent(MessageEvent event) {
        String action = event.getAction();
        if (action.equals("nameSearch")) {
            searchName = event.obj.toString();
            searchByName(searchName);
        }
    }


    @Override
    public int createView() {
        return R.layout.fragment_manager_member;
    }

    @Override
    public void initData() {

        String url = Constants.OUTRT_NET + "/master_" + workshopId + "/m/workshop_user/" + workshopId + "/members?page=" + page;
        OkHttpClientManager.getAsyn(context, url, new OkHttpClientManager.ResultCallback<ManagementMemberResult>() {

            @Override
            public void onError(Request request, Exception e) {
                loadingView.setVisibility(View.GONE);
                loadFailView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onResponse(ManagementMemberResult response) {
                loadingView.setVisibility(View.GONE);
                ll_show.setVisibility(View.VISIBLE);
                if (response != null && response.getResponseData() != null && response.getResponseData().getWorkshopUsers() != null) {
                    member_count.setText("共有成员" + String.valueOf(response.getResponseData().getWorkshopUsers().size()) + "人");
                    if (isRefresh) {
                        mobileUserList.clear();
                        xRecyclerView.refreshComplete(true);
                    } else if (isLoadMore) {
                        xRecyclerView.loadMoreComplete(true);
                    }
                    if (response.getResponseData().getWorkshopUsers().size() == 0) {
                        warn_msg.setVisibility(View.VISIBLE);
                    }
                    if (response.getResponseData().getPaginator() != null && response.getResponseData().getPaginator().getHasNextPage()) {
                        xRecyclerView.setLoadingMoreEnabled(true);
                    } else {
                        xRecyclerView.setLoadingMoreEnabled(false);
                    }
                    ll_show.setVisibility(View.VISIBLE);
                    mobileUserList.addAll(response.getResponseData().getWorkshopUsers());
                    adapter.notifyDataSetChanged();
                    loadFailView.setVisibility(View.GONE);

                } else {
                    loadFailView.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    @Override
    public void initView(View view) {
        context = getActivity();
        workshopId = getArguments().getString("workshopId");
        loadFailView = view.findViewById(R.id.loadFailView);
        xRecyclerView = view.findViewById(R.id.xRecyclerView);
        ll_show = view.findViewById(R.id.ll_show);
        member_count = view.findViewById(R.id.member_count);
        loadingView = view.findViewById(R.id.loadingView);
        warn_msg = view.findViewById(R.id.warn_msg);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        adapter = new ManagementMemberAdapter(context, mobileUserList);
        xRecyclerView.setArrowImageView(R.drawable.refresh_arrow);
        xRecyclerView.setAdapter(adapter);
        xRecyclerView.setLoadingListener(this);
    }

    @Override
    public void setListener() {
        loadFailView.setOnRetryListener(new LoadFailView.OnRetryListener() {
            @Override
            public void onRetry(View v) {
                initData();
            }
        });
        adapter.setDisposeCallBack(new ManagementMemberAdapter.onDisposeCallBack() {
            @Override
            public void onAlter(int position, MobileUser entity) {
            }

            @Override
            public void onDelete(int position, MobileUser entity) {
                String id = mobileUserList.get(position).getId();
                //删除成员
                String url = Constants.OUTRT_NET + "/master_" + workshopId + "/m/workshop_user/" + id;
                Map<String, String> map = new HashMap<>();
                map.put("_method", "delete");

                OkHttpClientManager.postAsyn(context, url, new OkHttpClientManager.ResultCallback<BaseResponseResult>() {
                    @Override
                    public void onBefore(Request request) {
                        showTipDialog();
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        hideTipDialog();
                        onNetWorkError();
                    }

                    @Override
                    public void onResponse(BaseResponseResult response) {
                        hideTipDialog();
                        if (response != null && response.getSuccess() == true) {
                            mobileUserList.clear();
                            initData();
                            toast("删除成功");
                        } else {
                            toast("删除失败,请稍后尝试");
                        }
                    }
                }, map);

            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private void searchByName(String searchName) {
        mobileUserList.clear();
        ll_show.setVisibility(View.GONE);
        page = 1;
        String url = Constants.OUTRT_NET + "/master_" + workshopId + "/m/workshop_user/" + workshopId + "/members?page=" + page + "&realName=" + searchName;
        OkHttpClientManager.getAsyn(context, url, new OkHttpClientManager.ResultCallback<ManagementMemberResult>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);

                loadingView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Request request, Exception e) {
                loadingView.setVisibility(View.GONE);
                loadFailView.setVisibility(View.VISIBLE);
                ll_show.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(ManagementMemberResult response) {

                loadingView.setVisibility(View.GONE);
                ll_show.setVisibility(View.VISIBLE);
                if (response != null && response.getResponseData() != null && response.getResponseData().getWorkshopUsers() != null) {
                    member_count.setText("共有成员" + response.getResponseData().getWorkshopUsers().size() + "人");
                    if (isRefresh) {
                        mobileUserList.clear();
                        xRecyclerView.refreshComplete(true);
                    } else if (isLoadMore) {
                        xRecyclerView.loadMoreComplete(true);
                    }
                    if (response.getResponseData().getPaginator() != null && response.getResponseData().getPaginator().getHasNextPage()) {
                        xRecyclerView.setLoadingMoreEnabled(true);
                    } else {
                        xRecyclerView.setLoadingMoreEnabled(false);
                    }
                    mobileUserList.addAll(response.getResponseData().getWorkshopUsers());
                    adapter.notifyDataSetChanged();
                } else {
                    loadFailView.setVisibility(View.VISIBLE);
                    ll_show.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        isLoadMore = false;
        page = 1;
        initData();
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        isLoadMore = true;
        page += 1;
        initData();
    }
}
