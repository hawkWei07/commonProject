package cn.hawk.commonlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by kehaowei on 2017/5/9.
 */

public abstract class MVPFragment<T extends CommonPresenter> extends BaseFragment {
    protected T mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (null != mPresenter) {
            mPresenter.attachView();
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mPresenter)
            mPresenter.detachView();
    }

    public abstract T createPresenter();
}
