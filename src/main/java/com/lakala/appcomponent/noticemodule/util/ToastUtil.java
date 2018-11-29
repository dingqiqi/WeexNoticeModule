package com.lakala.appcomponent.noticemodule.util;

import android.content.Context;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * ToastUtil
 * Created by dingqq on 2018/4/2.
 */

public class ToastUtil {

    private Toast mToast;

    private Toast mLongToast;

    private Timer mShowTimer = new Timer();

    private Timer mCancelTimer = new Timer();

    private static Context mContext;

    public static ToastUtil getInstance(Context context) {
        mContext = context.getApplicationContext();
        return ToastInstance.mInstance;
    }

    private static class ToastInstance {
        private static final ToastUtil mInstance = new ToastUtil();
    }

    private ToastUtil() {
    }

    public void toast(String msg) {

        if (mToast == null) {
            mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }

        mToast.show();
    }

    public void longToast(String msg) {

        if (mLongToast == null) {
            mLongToast = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
        } else {
            mLongToast.setText(msg);
        }

        mLongToast.show();
    }

    /**
     * 自定义时间toast
     *
     * @param msg  内容
     * @param time 时间
     */
    public void customTimeToast(String msg, int time) {
        Toast toast = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);

        showToast(toast, time);
    }

    private void showToast(final Toast toast, int time) {

        mShowTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3500);

        mCancelTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                mShowTimer.cancel();
            }
        }, time);
    }

}
