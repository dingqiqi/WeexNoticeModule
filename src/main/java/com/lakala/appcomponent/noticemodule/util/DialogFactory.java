package com.lakala.appcomponent.noticemodule.util;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.lakala.appcomponent.noticemodule.R;

/**
 * 遮罩
 * Created by dingqq on 2018/4/25.
 */

public class DialogFactory {

    public static DialogFactory getInstance() {
        return DialogFactoryInstance.mFactory;
    }

    private static class DialogFactoryInstance {
        private static final DialogFactory mFactory = new DialogFactory();
    }

    private Dialog mProgressDialog;

    /**
     * js 调用的加载框(要使用全局的progress,需要调用取消方法)
     *
     * @param activity 活动
     * @param text     内容
     */
    public void showProgressDialog(Activity activity, String text) {
        dismissProgress();

        mProgressDialog = new Dialog(activity, R.style.DialogTheme);

        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_progress_layout, null, false);

        TextView mTvTitle = view.findViewById(R.id.tv_progress);

        if (TextUtils.isEmpty(text)) {
            mTvTitle.setVisibility(View.GONE);
        } else {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(text);
        }

        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setContentView(view);

        Window window = mProgressDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setWindowAnimations(R.style.DialogNoAnimalStyle);
        }

        if (!activity.isFinishing()) {
            mProgressDialog.show();
        }
    }

    public void dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    public Dialog showAlertDialog(Activity activity, String title, String text, final String cancel, final String ok, final ClickCallback callback) {
        return showAlertDialog(activity, title, text, cancel, ok, Gravity.CENTER_HORIZONTAL, false, callback);
    }

    /**
     * @param activity  活动
     * @param title     标题
     * @param text      内容
     * @param cancel    取消文字
     * @param ok        确定文子
     * @param gravity   内容对其
     * @param isDismiss 点击后是否关闭
     * @param callback  回调
     */
    public Dialog showAlertDialog(Activity activity, String title, String text, final String cancel, final String ok,
                                  int gravity, final boolean isDismiss, final ClickCallback callback) {
        final Dialog mAlertDialog = new Dialog(activity, R.style.DialogTheme);
        mAlertDialog.setContentView(R.layout.dialog_alert_layout);

        TextView tvTitle = mAlertDialog.findViewById(R.id.tv_title);
        TextView tvMsg = mAlertDialog.findViewById(R.id.tv_message);
        TextView tvCancel = mAlertDialog.findViewById(R.id.tv_cancel);
        TextView tvOk = mAlertDialog.findViewById(R.id.tv_ok);
        View line = mAlertDialog.findViewById(R.id.tv_line);

        tvMsg.setGravity(gravity);

        if (TextUtils.isEmpty(cancel)) {
            tvCancel.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        } else {
            tvCancel.setText(cancel);
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isDismiss) {
                        mAlertDialog.dismiss();
                    }
                    if (callback != null) {
                        callback.onClick(false);
                    }
                }
            });
        }

        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }

        tvMsg.setText(text);

        if (!TextUtils.isEmpty(ok)) {
            tvOk.setText(ok);
        }

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDismiss) {
                    mAlertDialog.dismiss();
                }
                if (callback != null) {
                    callback.onClick(true);
                }
            }
        });

        mAlertDialog.setCancelable(false);
        mAlertDialog.setCanceledOnTouchOutside(false);

        Window window = mAlertDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#40000000")));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setWindowAnimations(R.style.DialogNoAnimalStyle);
        }

        if (!activity.isFinishing()) {
            mAlertDialog.show();
        }

        return mAlertDialog;
    }

    /**
     * 显示加载框
     *
     * @param activity 活动
     * @param text     内容
     * @return Dialog
     */
    public Dialog showLoadingDialog(Activity activity, String text) {
        dismissProgress();

        Dialog mProgressDialog = new Dialog(activity, R.style.DialogTheme);

        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_progress_layout, null, false);

        TextView mTvTitle = view.findViewById(R.id.tv_progress);

        if (TextUtils.isEmpty(text)) {
            mTvTitle.setVisibility(View.GONE);
        } else {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(text);
        }

        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setContentView(view);

        Window window = mProgressDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setWindowAnimations(R.style.DialogNoAnimalStyle);
        }

        if (!activity.isFinishing()) {
            mProgressDialog.show();
        }

        return mProgressDialog;
    }

    public interface ClickCallback {
        void onClick(boolean isOk);
    }
}
