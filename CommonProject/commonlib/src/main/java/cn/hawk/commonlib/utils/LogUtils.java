package cn.hawk.commonlib.utils;

import android.util.Log;

/**
 * Created by kehaowei on 2017/2/24.
 */

public class LogUtils {
    public static final int LOG_LEVEL_VIEW = 0;
    public static final int LOG_LEVEL_INFO = 1;
    public static final int LOG_LEVEL_DEBUG = 2;
    public static final int LOG_LEVEL_WARNNING = 3;
    public static final int LOG_LEVEL_ERROR = 4;

    private String TAG = "DEBUG_LOG";

    private boolean debug = true;

    private int logLevel = 0;

    public LogUtils(String TAG) {
        this.TAG = TAG;
    }

    public LogUtils() {
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    public static LogUtils getInstance() {
        return new LogUtils();
    }

    public static LogUtils getInstance(String tag) {
        return new LogUtils(tag);
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public void logv(String tag, String msg) {
        if (debug && logLevel <= LOG_LEVEL_VIEW)
            Log.v(TAG, tag + "==>" + msg);
    }

    public void logi(String tag, String msg) {
        if (debug && logLevel <= LOG_LEVEL_INFO)
            Log.i(TAG, tag + "==>" + msg);
    }

    public void logd(String tag, String msg) {
        if (debug && logLevel <= LOG_LEVEL_DEBUG)
            Log.d(TAG, tag + "==>" + msg);
    }

    public void logw(String tag, String msg) {
        if (logLevel <= LOG_LEVEL_WARNNING)
            Log.w(TAG, tag + "==>" + msg);
    }

    public void loge(String tag, String msg) {
        if (logLevel <= LOG_LEVEL_ERROR)
            Log.e(TAG, tag + "==>" + msg);
    }
}
