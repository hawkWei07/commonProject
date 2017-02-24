package cn.hawk.commonproject;

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
    }

    public static AppContext getInstance() {
        return instance;
    }

    @Override
    protected LogUtils initLogUtil() {
        return LogUtils.getInstance("CommonProject");
    }
}
