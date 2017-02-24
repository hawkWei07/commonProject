package cn.hawk.commonlib.utils;

import android.util.Log;

/**
 * Created by kehaowei on 2017/2/24.
 */

public class LogUtils {
    private String TAG = "DEBUG_LOG";

    private boolean debug = true;

    public LogUtils(String TAG) {
        this.TAG = TAG;
    }

    public LogUtils() {
    }

    public static LogUtils getInstance() {
        return new LogUtils();
    }

    public static LogUtils getInstance(String tag) {
        return new LogUtils(tag);
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public void logv(String tag, String msg) {
        if (debug)
            Log.v(TAG, tag + "==>" + msg);
    }

    public void logi(String tag, String msg) {
        if (debug)
            Log.i(TAG, tag + "==>" + msg);
    }

    public void logd(String tag, String msg) {
        Log.d(TAG, tag + "==>" + msg);
    }

    public void logw(String tag, String msg) {
        if (debug)
            Log.w(TAG, tag + "==>" + msg);
    }

    public void loge(String tag, String msg) {
        Log.e(TAG, tag + "==>" + msg);
    }
}
