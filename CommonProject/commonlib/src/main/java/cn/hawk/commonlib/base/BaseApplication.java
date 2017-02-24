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
        logUtils.setDebug(true);
    }

    public LogUtils getLogUtils() {
        return logUtils;
    }

    protected abstract LogUtils initLogUtil();

    public void logv(String tag, String msg) {
        logUtils.logv(tag, msg);
    }

    public void logi(String tag, String msg) {
        logUtils.logi(tag, msg);
    }

    public void logd(String tag, String msg) {
        logUtils.logd(tag, msg);
    }

    public void logw(String tag, String msg) {
        logUtils.logw(tag, msg);
    }

    public void loge(String tag, String msg) {
        logUtils.loge(tag, msg);
    }
}
