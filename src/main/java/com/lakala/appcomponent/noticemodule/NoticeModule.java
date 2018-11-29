package com.lakala.appcomponent.noticemodule;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.lakala.appcomponent.noticemodule.util.DialogFactory;
import com.lakala.appcomponent.noticemodule.util.ToastUtil;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import org.json.JSONException;
import org.json.JSONObject;

public class NoticeModule extends WXModule implements INoticeModule {

    @JSMethod(uiThread = true)
    @Override
    public boolean showLoading(String params) {
        Activity activity = (Activity) mWXSDKInstance.getContext();

        if (activity == null) {
            return false;
        }

        try {
            JSONObject jsonObject = new JSONObject(params);

            String text = jsonObject.getString("message");

            DialogFactory.getInstance().showProgressDialog(activity, text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return true;
    }

    @JSMethod(uiThread = true)
    @Override
    public boolean hideLoading() {

        DialogFactory.getInstance().dismissProgress();

        return true;
    }

    @JSMethod(uiThread = true)
    @Override
    public void toast(String params) {
        Context context = mWXSDKInstance.getContext();

        if (context == null) {
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(params);

            String msg = jsonObject.getString("message");
            float time = 0;
            if (jsonObject.has("duration")) {
                time = jsonObject.getInt("duration");
            }

            if (time > 3) {
                ToastUtil.getInstance(context).longToast(msg);
            } else {
                ToastUtil.getInstance(context).toast(msg);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Dialog mDialog;

    @JSMethod(uiThread = true)
    @Override
    public void alert(String params, final JSCallback callback) {

        try {
            JSONObject jsonObject = new JSONObject(params);

            final String msg = jsonObject.getString("message");
            final String ok = jsonObject.getString("okTitle");

            Activity activity = (Activity) mWXSDKInstance.getContext();

            if (activity != null) {
                mDialog = DialogFactory.getInstance().showAlertDialog(activity, null, msg, null, ok, new DialogFactory.ClickCallback() {
                    @Override
                    public void onClick(boolean isOk) {
                        mDialog.dismiss();
                        if (callback != null) {
                            callback.invoke(isOk ? ok : null);
                        }
                    }
                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @JSMethod(uiThread = true)
    @Override
    public void confirm(String params, final JSCallback callback) {

        try {
            JSONObject jsonObject = new JSONObject(params);

            final String msg = jsonObject.getString("message");
            final String ok = jsonObject.getString("okTitle");
            final String cancel = jsonObject.getString("cancelTitle");

            Activity activity = (Activity) mWXSDKInstance.getContext();

            if (activity != null) {
                mDialog = DialogFactory.getInstance().showAlertDialog(activity, null, msg, cancel, ok, new DialogFactory.ClickCallback() {
                    @Override
                    public void onClick(boolean isOk) {
                        mDialog.dismiss();
                        if (callback != null) {
                            callback.invoke(isOk ? ok : cancel);
                        }
                    }
                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
