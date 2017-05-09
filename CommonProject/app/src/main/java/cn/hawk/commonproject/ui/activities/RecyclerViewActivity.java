package cn.hawk.commonproject.ui.activities;


import cn.hawk.commonlib.base.BaseActivity;
import cn.hawk.commonlib.utils.ActivityUtils;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.presents.RecyclerViewPresenter;
import cn.hawk.commonproject.ui.fragments.RecyclerViewFragment;

/**
 * Created by kehaowei on 2017/3/24.
 */

public class RecyclerViewActivity extends BaseActivity {
    private RecyclerViewFragment mFragment;

    @Override
    protected int getContentId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void bindView() {
        super.bindView();
        mFragment = (RecyclerViewFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (null == mFragment) {
            mFragment = RecyclerViewFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragment, R.id.contentFrame);
        }
    }

    @Override
    protected void initData() {
        super.initData();
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
