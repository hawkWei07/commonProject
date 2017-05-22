package cn.hawk.commonlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.hawk.commonlib.widgets.LoadingDialog;

/**
 * Created by kehaowei on 2017/2/22.
 */

public abstract class BaseActivity extends AppCompatActivity {
    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(getContentId());
        unbinder = ButterKnife.bind(this);
        bindView();
        initData();
        initView();
        initEvent();
    }

    private LoadingDialog mLoadingDialog;

    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    public void dismissLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    protected void initWindow() {
    }

    protected abstract int getContentId();

    protected void bindView() {
    }

    protected void initData() {
    }

    protected void initView() {
    }

    protected void initEvent() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
