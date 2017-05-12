package cn.hawk.commonlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by kehaowei on 2017/2/22.
 */

public abstract class BaseActivity extends AppCompatActivity {
    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentId());
        unbinder = ButterKnife.bind(this);
        bindView();
        initData();
        initView();
        initEvent();
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
