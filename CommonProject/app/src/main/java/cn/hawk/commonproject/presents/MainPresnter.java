package cn.hawk.commonproject.presents;

import android.support.annotation.NonNull;

import cn.hawk.commonproject.AppContext;
import cn.hawk.commonproject.contracts.MainContract;

/**
 * Created by kehaowei on 2017/2/22.
 */

public class MainPresnter implements MainContract.Presenter {
    private static final String TAG = MainPresnter.class.getSimpleName();
    private final MainContract.View mMainView;

    public MainPresnter(@NonNull MainContract.View mainView) {
        mMainView = mainView;
        mMainView.setPresenter(this);
    }

    @Override
    public void start() {
        mMainView.showOutput("Data bind successfully");
        AppContext.getInstance().logd(TAG, "Data bind successfully");
    }
}
