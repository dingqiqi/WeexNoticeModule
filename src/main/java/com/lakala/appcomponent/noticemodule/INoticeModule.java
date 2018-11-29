package com.lakala.appcomponent.noticemodule;

import com.taobao.weex.bridge.JSCallback;

public interface INoticeModule {

    boolean showLoading(String message);

    boolean hideLoading();

    void toast(String params);

    void alert(String params, JSCallback callback);

    void confirm(String params, JSCallback callback);
}
