package cn.hawk.commonproject.ui.activities;

import android.support.v7.widget.Toolbar;

import cn.hawk.commonlib.base.BaseActivity;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.contracts.CardDisplayContract;
import cn.hawk.commonproject.presents.CardDisplayPresenter;

/**
 * Created by kehaowei on 2017/4/10.
 */

public class CardDisplayActivity extends BaseActivity implements CardDisplayContract.View {
    Toolbar toolbar;
    private CardDisplayContract.Presenter mPresenter;

    @Override
    protected int getContentId() {
        return R.layout.activity_base;
    }

    @Override
    protected void bindView() {
        super.bindView();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new CardDisplayPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    public void setPresenter(CardDisplayContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }
}
