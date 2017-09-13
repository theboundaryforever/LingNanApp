package com.haoyu.app.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haoyu.app.adapter.CourseSectionAdapter;
import com.haoyu.app.base.BaseActivity;
import com.haoyu.app.dialog.FileUploadDialog;
import com.haoyu.app.dialog.MaterialDialog;
import com.haoyu.app.entity.CourseChildSectionEntity;
import com.haoyu.app.entity.CourseSectionEntity;
import com.haoyu.app.entity.CourseSectionResult;
import com.haoyu.app.entity.FileUploadDataResult;
import com.haoyu.app.entity.FileUploadResult;
import com.haoyu.app.entity.MFileInfo;
import com.haoyu.app.entity.MultiItemEntity;
import com.haoyu.app.gdei.teacher.R;
import com.haoyu.app.pickerlib.MediaOption;
import com.haoyu.app.pickerlib.MediaPicker;
import com.haoyu.app.rxBus.MessageEvent;
import com.haoyu.app.rxBus.RxBus;
import com.haoyu.app.utils.Action;
import com.haoyu.app.utils.Common;
import com.haoyu.app.utils.Constants;
import com.haoyu.app.utils.NetStatusUtil;
import com.haoyu.app.utils.OkHttpClientManager;
import com.haoyu.app.utils.ScreenUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.Request;

/**
 * 创建日期：2017/2/8 on 14:18
 * 描述:课程资源上传
 * 作者:马飞奔 Administrator
 */
public class ResourcesUploadActivity extends BaseActivity implements View.OnClickListener {
    private ResourcesUploadActivity context = this;
    private String courseId;
    @BindView(R.id.iv_back)
    View iv_back;
    @BindView(R.id.tv_upload)
    View tv_upload;
    @BindView(R.id.et_resourceName)
    EditText et_resourceName;
    @BindView(R.id.tv_section)
    TextView tv_section;
    @BindView(R.id.bt_select)
    ImageView bt_select;
    @BindView(R.id.overlay)
    ImageView thumbnail;
    @BindView(R.id.iv_result)
    ImageView iv_result;
    @BindView(R.id.iv_delete)
    ImageView iv_delete;
    private CourseChildSectionEntity sectionEntity;
    private List<CourseSectionEntity> courseSections;
    private File mFile;
    private boolean isImage, isVideo;
    private FileUploadResult uploadResult;
    private String resourceName;

    @Override
    public int setLayoutResID() {
        return R.layout.activity_resource_upload;
    }

    @Override
    public void initView() {
        courseId = getIntent().getStringExtra("courseId");
    }

    /**
     * 获取章节列表
     */
    public void loadData() {
        String url = Constants.OUTRT_NET + "/" + courseId + "/teach/m/course/" + courseId + "/teach";
        addSubscription(OkHttpClientManager.getAsyn(context, url, new OkHttpClientManager.ResultCallback<CourseSectionResult>() {
            @Override
            public void onBefore(Request request) {
                showTipDialog();
            }

            @Override
            public void onError(Request request, Exception e) {
                hideTipDialog();
            }

            @Override
            public void onResponse(CourseSectionResult response) {
                hideTipDialog();
                if (response != null && response.getResponseData() != null && response.getResponseData().getmCourse() != null
                        && response.getResponseData().getmCourse().getmSections() != null
                        && response.getResponseData().getmCourse().getmSections().size() > 0) {
                    courseSections = new ArrayList<>();
                    courseSections.addAll(response.getResponseData().getmCourse().getmSections());
                    showSectionDialog();
                }
            }
        }));
    }


    private void showSectionDialog() {
        Drawable shouqi = ContextCompat.getDrawable(context, R.drawable.course_dictionary_shouqi);
        shouqi.setBounds(0, 0, shouqi.getMinimumWidth(), shouqi.getMinimumHeight());
        final Drawable zhankai = ContextCompat.getDrawable(context, R.drawable.course_dictionary_xiala);
        zhankai.setBounds(0, 0, zhankai.getMinimumWidth(), zhankai.getMinimumHeight());
        tv_section.setCompoundDrawables(null, null, shouqi, null);
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setBackgroundColor(ContextCompat.getColor(context, R.color.spaceColor));
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        final PopupWindow popupWindow = new PopupWindow(recyclerView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        List<MultiItemEntity> mDatas = new ArrayList<>();
        for (int i = 0; i < courseSections.size(); i++) {
            CourseSectionEntity entity = courseSections.get(i);
            mDatas.add(entity);
            if (entity.getChildSections() != null && entity.getChildSections().size() > 0)
                for (int j = 0; j < entity.getChildSections().size(); j++) {
                    mDatas.add(entity.getChildSections().get(j));
                }
        }
        CourseSectionAdapter sectionAdapter = new CourseSectionAdapter(mDatas);
        recyclerView.setAdapter(sectionAdapter);
        sectionAdapter.setOnSectionClickListener(new CourseSectionAdapter.OnSectionClickListener() {
            @Override
            public void onSectionSelected(CourseChildSectionEntity entity) {
                sectionEntity = entity;
                tv_section.setText(sectionEntity.getTitle());
                popupWindow.dismiss();
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tv_section.setCompoundDrawables(null, null, zhankai, null);
            }
        });
        popupWindow.showAsDropDown(tv_section);
    }

    @Override
    public void setListener() {
        iv_back.setOnClickListener(context);
        tv_upload.setOnClickListener(context);
        tv_section.setOnClickListener(context);
        bt_select.setOnClickListener(context);
        iv_delete.setOnClickListener(context);
        iv_result.setOnClickListener(context);
    }

    @Override
    public void onClick(View v) {
        Common.hideSoftInput(context, et_resourceName);
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_upload:
                resourceName = et_resourceName.getText().toString().trim();
                if (resourceName.length() == 0) {
                    showMaterialDialog("提示", "请输入资源名称");
                } else if (sectionEntity == null) {
                    showMaterialDialog("提示", "请选择所属章节");
                } else if (mFile == null) {
                    showMaterialDialog("提示", "请选择上传的文件");
                } else {
                    if (NetStatusUtil.isConnected(context)) {
                        if (NetStatusUtil.isWifi(context))
                            upload();
                        else
                            netWorkDialog();
                    } else
                        showMaterialDialog("网络提醒", "当前网络不可用");
                }
                break;
            case R.id.tv_section:
                if (courseSections == null) {
                    loadData();
                } else {
                    showSectionDialog();
                }
                break;
            case R.id.bt_select:
                showSubmitDialog();
                break;
            case R.id.iv_delete:
                mFile = null;
                uploadResult = null;
                bt_select.setVisibility(View.VISIBLE);
                iv_result.setVisibility(View.GONE);
                thumbnail.setVisibility(View.GONE);
                iv_delete.setVisibility(View.GONE);
                break;
            case R.id.iv_result:
                if (isImage) {
                    ArrayList<String> imgList = new ArrayList<>();
                    imgList.add(mFile.getAbsolutePath());
                    Intent intent = new Intent(context, AppMultiImageShowActivity.class);
                    intent.putStringArrayListExtra("photos", imgList);
                    intent.putExtra("position", 0);
                    context.startActivity(intent);
                    context.overridePendingTransition(R.anim.zoom_in, 0);
                } else if (isVideo) {
                    Common.openFile(context, mFile);
                }
                break;
        }
    }

    private void netWorkDialog() {
        MaterialDialog dialog = new MaterialDialog(context);
        dialog.setTitle("网络提醒");
        dialog.setMessage("使用2G/3G/4G网络上传会消耗较多流量。确定要执行本次操作吗？");
        dialog.setNegativeTextColor(ContextCompat.getColor(context, R.color.defaultColor));
        dialog.setPositiveTextColor(ContextCompat.getColor(context, R.color.gray));
        dialog.setNegativeButton("取消", null);
        dialog.setPositiveButton("继续上传", new MaterialDialog.ButtonClickListener() {
            @Override
            public void onClick(View v, AlertDialog dialog) {
                upload();
            }
        });
        dialog.show();
    }

    private void upload() {
        if (mFile != null && mFile.exists()) {
            if (uploadResult != null) {
                lastCommit(uploadResult.getResponseData());
            } else {
                String url = Constants.OUTRT_NET + "/m/file/uploadTemp";
                final FileUploadDialog uploadDialog = new FileUploadDialog(context, mFile.getName(), "正在上传");
                uploadDialog.setCancelable(false);
                uploadDialog.setCanceledOnTouchOutside(false);
                final Disposable mSubscription = OkHttpClientManager.postAsyn(context, url, new OkHttpClientManager.ResultCallback<FileUploadResult>() {

                    @Override
                    public void onBefore(Request request) {
                        uploadDialog.show();
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        e.printStackTrace();
                        uploadDialog.dismiss();
                        showErrorDialog();
                    }

                    @Override
                    public void onResponse(FileUploadResult response) {
                        uploadDialog.dismiss();
                        if (response != null && response.getResponseData() != null) {
                            uploadResult = response;
                            lastCommit(uploadResult.getResponseData());
                        } else {
                            showErrorDialog();
                        }
                    }
                }, mFile, mFile.getName(), new OkHttpClientManager.ProgressListener() {
                    @Override
                    public void onProgress(long totalBytes, long remainingBytes, boolean done, File file) {
                        Flowable.just(new long[]{totalBytes, remainingBytes}).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<long[]>() {
                                    @Override
                                    public void accept(long[] params) throws Exception {
                                        uploadDialog.setUploadProgressBar(params[0], params[1]);
                                        uploadDialog.setUploadText(params[0], params[1]);
                                    }
                                });
                    }
                });
                uploadDialog.setCancelListener(new FileUploadDialog.CancelListener() {
                    @Override
                    public void cancel() {
                        showCancelDialog(mSubscription, uploadDialog);
                    }
                });
            }
        } else {
            showMaterialDialog("提示", "上传的文件不存在，请重新选择文件");
        }
    }

    private void lastCommit(MFileInfo fileInfo) {
        String url = Constants.OUTRT_NET + "/m/resource";
        Map<String, String> map = new HashMap<>();
        map.put("title", resourceName);
        map.put("resourceRelations[0].relation.id", sectionEntity.getId());
        map.put("resourceRelations[0].relation.type", "section");
        map.put("belong", "personal");
        map.put("fileInfos[0].id", fileInfo.getId());
        map.put("fileInfos[0].url", fileInfo.getUrl());
        map.put("fileInfos[0].fileName", fileInfo.getFileName());
        OkHttpClientManager.postAsyn(context, url, new OkHttpClientManager.ResultCallback<FileUploadDataResult>() {
            @Override
            public void onBefore(Request request) {
                showTipDialog();
            }

            @Override
            public void onError(Request request, Exception e) {
                hideTipDialog();
            }

            @Override
            public void onResponse(FileUploadDataResult response) {
                hideTipDialog();
                if (response != null && response.getResponseData() != null) {
                    MessageEvent event = new MessageEvent();
                    event.action = Action.UPLOAD_RESOURCES;
                    event.obj = response.getResponseData();
                    RxBus.getDefault().post(event);
                    toastFullScreen("上传成功", true);
                    finish();
                } else {
                    showErrorDialog();
                }
            }
        }, map);
    }

    /*上传失败显示dialog*/
    private void showErrorDialog() {
        MaterialDialog dialog = new MaterialDialog(context);
        dialog.setTitle("上传结果");
        dialog.setMessage("由于网络问题上传资源失败，您可以点击重新上传再次上传");
        dialog.setNegativeTextColor(ContextCompat.getColor(context, R.color.gray));
        dialog.setPositiveTextColor(ContextCompat.getColor(context, R.color.defaultColor));
        dialog.setNegativeButton("取消", null);
        dialog.setPositiveButton("重新上传", new MaterialDialog.ButtonClickListener() {
            @Override
            public void onClick(View v, AlertDialog dialog) {
                upload();
            }
        });
        dialog.show();
    }

    /*取消上传显示dialog*/
    private void showCancelDialog(final Disposable mSubscription, final FileUploadDialog uploadDialog) {
        MaterialDialog dialog = new MaterialDialog(context);
        dialog.setTitle("提示");
        dialog.setMessage("你确定取消本次上传吗？");
        dialog.setPositiveTextColor(ContextCompat.getColor(context, R.color.defaultColor));
        dialog.setPositiveButton("确定", new MaterialDialog.ButtonClickListener() {
            @Override
            public void onClick(View v, AlertDialog dialog) {
                dialog.dismiss();
                mSubscription.dispose();
            }
        });
        dialog.setNegativeButton("关闭", new MaterialDialog.ButtonClickListener() {
            @Override
            public void onClick(View v, AlertDialog dialog) {
                dialog.dismiss();
                if (uploadDialog != null && !uploadDialog.isShowing()) {
                    uploadDialog.show();
                }
            }
        });
        dialog.show();
    }

    private void showSubmitDialog() {
        View view = getLayoutInflater().inflate(
                R.layout.resource_upload_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.dialog)
                .create();
        Button bt_picture = view.findViewById(R.id.bt_picture);
        Button bt_video = view.findViewById(R.id.bt_video);
        bt_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                pickerPicture();
            }
        });
        bt_video.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                picketVideo();
            }
        });
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setCancelable(true);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setLayout(ScreenUtils.getScreenWidth(context),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setWindowAnimations(R.style.dialogWindowAnim);
        window.setContentView(view);
        window.setGravity(Gravity.BOTTOM);
    }

    private void pickerPicture() {
        isImage = true;
        isVideo = false;
        MediaOption option = new MediaOption.Builder()
                .setSelectType(MediaOption.TYPE_IMAGE)
                .setShowCamera(true)
                .build();
        MediaPicker.getInstance().init(option).selectMedia(context, callBack);
    }

    private void picketVideo() {
        isImage = false;
        isVideo = true;
        MediaOption option = new MediaOption.Builder()
                .setSelectType(MediaOption.TYPE_VIDEO)
                .setShowCamera(true)
                .build();
        MediaPicker.getInstance().init(option).selectMedia(context, callBack);
    }

    private MediaPicker.onSelectMediaCallBack callBack = new MediaPicker.onSelectMediaCallBack() {
        @Override
        public void onSelected(String path) {
            iv_result.setVisibility(View.VISIBLE);
            mFile = new File(path);
            Glide.with(context).load(path).into(iv_result);
            if (isVideo) {
                thumbnail.setVisibility(View.VISIBLE);
            } else {
                thumbnail.setVisibility(View.GONE);
            }
            iv_delete.setVisibility(View.VISIBLE);
        }
    };
}