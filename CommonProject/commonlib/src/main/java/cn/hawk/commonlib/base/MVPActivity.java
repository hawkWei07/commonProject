package cn.hawk.commonlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by kehaowei on 2017/5/9.
 */

public abstract class MVPActivity<T extends CommonPresenter> extends BaseActivity {
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (null != mPresenter)
            mPresenter.attachView();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter)
            mPresenter.detachView();
    }

    public abstract T createPresenter();
}
