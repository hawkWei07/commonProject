package cn.hawk.commonproject.ui.activities;

import cn.hawk.commonlib.base.BaseActivity;
import cn.hawk.commonlib.utils.ActivityUtils;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.presents.ImageHandlePresenter;
import cn.hawk.commonproject.ui.fragments.ImageHandleFragment;

/**
 * Created by kehaowei on 2017/3/21.
 */

public class ImageHandleActivity extends BaseActivity {

    private ImageHandlePresenter mPresenter;

    private ImageHandleFragment mFragment;

    @Override
    protected int getContentId() {
        return R.layout.activity_image_handle;
    }

    @Override
    protected void bindView() {
        super.bindView();
        mFragment = (ImageHandleFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (null == mFragment) {
            mFragment = ImageHandleFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragment, R.id.contentFrame);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new ImageHandlePresenter(this, mFragment);
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
