//package com.haoyu.app.imageloader;
//
//import android.app.Activity;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//import com.haoyu.app.gdei.teacher.R;
//
///**
// * 创建日期：2017/3/17 on 10:23
// * 描述:
// * 作者:马飞奔 Administrator
// */
//public class UILImageLoader implements com.lqr.imagepicker.loader.ImageLoader {
//    @Override
//    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
//        Glide.with(activity)
//                .load(path)
//                .placeholder(R.drawable.global_img_default)
//                .centerCrop()
//                .into(imageView);
//    }
//
//    @Override
//    public void clearMemoryCache() {
//
//    }
//}
