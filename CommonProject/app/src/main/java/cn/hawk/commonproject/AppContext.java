package cn.hawk.commonproject;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
//import com.tencent.bugly.beta.Beta;

import cn.hawk.commonlib.base.BaseApplication;
import cn.hawk.commonlib.utils.LogUtils;

/**
 * Created by kehaowei on 2017/2/24.
 */

public class AppContext extends BaseApplication {
    private static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(getApplicationContext());
        userStrategy.setAppChannel("cqtp");
        Bugly.init(this, "e047d34325", true, userStrategy);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
//        Beta.installTinker();
    }

    public static AppContext getInstance() {
        return instance;
    }

    @Override
    protected LogUtils initLogUtil() {
        LogUtils lu = LogUtils.getInstance("CommonProject");
        lu.setDebug(true);
        lu.setLogLevel(LogUtils.LOG_LEVEL_DEBUG);
        return lu;
    }
}
