package cn.hawk.commonlib.base;

import android.app.Application;

import cn.hawk.commonlib.utils.LogUtils;

/**
 * Created by kehaowei on 2017/2/24.
 */

public abstract class BaseApplication extends Application {
    private static LogUtils logUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        logUtils = initLogUtil();
    }

    public LogUtils getLogUtils() {
        if (null == logUtils)
            logUtils = initLogUtil();
        return logUtils;
    }

    protected abstract LogUtils initLogUtil();

    public void logv(String tag, String msg) {
        getLogUtils().logv(tag, msg);
    }

    public void logi(String tag, String msg) {
        getLogUtils().logi(tag, msg);
    }

    public void logd(String tag, String msg) {
        getLogUtils().logd(tag, msg);
    }

    public void logw(String tag, String msg) {
        getLogUtils().logw(tag, msg);
    }

    public void loge(String tag, String msg) {
        getLogUtils().loge(tag, msg);
    }
}
