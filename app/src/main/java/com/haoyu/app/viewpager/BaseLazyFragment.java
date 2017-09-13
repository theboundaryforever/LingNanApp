package com.haoyu.app.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haoyu.app.gdei.teacher.R;
import com.haoyu.app.rxBus.MessageEvent;
import com.haoyu.app.utils.PixelFormat;

/**
 * 懒加载fragment基类
 * <p>
 * 创建日期：2016/11/14 on 16:53
 * 描述:
 * 作者:马飞奔 Administrator
 */
public abstract class BaseLazyFragment extends Fragment {
    /**
     * 懒加载过
     */
    private boolean isLazyLoaded;
    private boolean isInitView;
    private boolean isPrepared;
    private boolean isVisible;
    private View mRootView;
    public Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        //只有Fragment onCreateView好了，
        //另外这里调用一次lazyLoad(）
        lazyLoad();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = createView(inflater, getLayoutId());
            initView(mRootView);
            isInitView = true;
            lazyLoad();
            setListener();
        }
        ViewGroup viewGroup = (ViewGroup) mRootView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(mRootView);
        }
        return mRootView;
    }

    public void customToast(String chapterName, String operationTips) {
        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.public_toast_layout, null);
        TextView tv_chapterName = (TextView) view
                .findViewById(R.id.chapterName);
        TextView tv_operationTips = (TextView) view
                .findViewById(R.id.operationTips);
        tv_chapterName.setText(chapterName);
        if (operationTips != null && !TextUtils.isEmpty(operationTips)) {
            tv_operationTips.setText(operationTips);
            tv_operationTips.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, R.id.chapterName);
            tv_operationTips.setLayoutParams(params);
        } else {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            tv_chapterName.setLayoutParams(layoutParams);
        }
        Toast toast = new Toast(getActivity());
        toast.setGravity(Gravity.BOTTOM, 0,
                PixelFormat.formatDipToPx(getActivity(), 70));
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public View createView(LayoutInflater inflater, int layoutId) {
        return inflater.inflate(layoutId, null);
    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initView(View view);

    public abstract void setListener();


    /**
     * 调用懒加载
     */
    private void lazyLoad() {
        if (isVisible && isPrepared && isInitView && !isLazyLoaded) {
            initData();
            isLazyLoaded = true;
        }
    }

    public void onConnectError() {
        if (getActivity() == null) {
            return;
        }
        customToast(
                getActivity().getResources().getString(R.string.connect_error),
                null);
    }

    public void onJsonParseError() {
        if (getActivity() == null) {
            return;
        }
        customToast(getActivity().getResources()
                .getString(R.string.parse_error), null);
    }

    public void onNetWorkError() {
        if (getActivity() == null) {
            return;
        }
        customToast(
                getActivity().getResources().getString(R.string.network_error),
                null);
    }

    public void onResponseError(int errorCode) {
        if (getActivity() == null) {
            return;
        }
        Log.e("错误状态相应码为:", String.valueOf(errorCode));
    }

    public void toast(String text) {
        if (getActivity() == null) {
            return;
        }
        customToast(text, null);
    }

    public abstract void onEvent(MessageEvent event);

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
