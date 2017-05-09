package cn.hawk.commonproject.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.hawk.commonlib.base.BaseActivity;
import cn.hawk.commonlib.utils.ActivityUtils;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.presents.MainPresnter;
import cn.hawk.commonproject.ui.fragments.MainFragment;

public class MainActivity extends BaseActivity {

    private MainFragment mainFragment;

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindView() {
        super.bindView();
        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (null == mainFragment) {
            mainFragment = MainFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mainFragment, R.id.contentFrame);
        }
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected void initView() {
        super.initView();
    }
}
