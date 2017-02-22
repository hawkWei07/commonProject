package cn.hawk.commonlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by kehaowei on 2017/2/22.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentId());
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
}
