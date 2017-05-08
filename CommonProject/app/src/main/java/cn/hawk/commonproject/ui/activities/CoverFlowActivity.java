package cn.hawk.commonproject.ui.activities;

import cn.hawk.commonlib.base.BaseActivity;
import cn.hawk.commonlib.utils.ActivityUtils;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.contracts.CoverFlowContract;
import cn.hawk.commonproject.presents.CoverFlowPresenter;
import cn.hawk.commonproject.ui.fragments.CoverFlowFragment;

/**
 * Created by kehaowei on 2017/5/8.
 */

public class CoverFlowActivity extends BaseActivity {
    private CoverFlowContract.Presenter mPresenter;
    private CoverFlowFragment mFragment;

    @Override
    protected int getContentId() {
        return R.layout.activity_cover_flow;
    }

    @Override
    protected void bindView() {
        super.bindView();
        mFragment = (CoverFlowFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (null == mFragment) {
            mFragment = CoverFlowFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragment, R.id.contentFrame);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new CoverFlowPresenter(this, mFragment);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }
}
