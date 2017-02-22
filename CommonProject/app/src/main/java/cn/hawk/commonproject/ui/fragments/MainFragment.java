package cn.hawk.commonproject.ui.fragments;

import android.text.TextUtils;
import android.widget.TextView;

import cn.hawk.commonlib.base.BaseFragment;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.contracts.MainContract;

/**
 * Created by kehaowei on 2017/2/22.
 */

public class MainFragment extends BaseFragment implements MainContract.View {
    private MainContract.Presenter mPresenter;

    private TextView tvOutPut;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void bindView() {
        super.bindView();
        tvOutPut = (TextView) getView().findViewById(R.id.output);
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

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showOutput(String output) {
        if (TextUtils.isEmpty(output))
            return;
        tvOutPut.setText(output);
    }
}
