package cn.hawk.commonproject.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.hawk.commonlib.base.BaseFragment;
import cn.hawk.commonlib.base.MVPFragment;
import cn.hawk.commonlib.widgets.CoverFlowView;
import cn.hawk.commonproject.AppContext;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.adapters.MyCoverFlowAdapter;
import cn.hawk.commonproject.contracts.CoverFlowContract;
import cn.hawk.commonproject.presents.CoverFlowPresenter;

/**
 * Created by kehaowei on 2017/5/8.
 */

public class CoverFlowFragment extends MVPFragment<CoverFlowPresenter> implements
        CoverFlowContract.View,
        CoverFlowView.CoverFlowListener<MyCoverFlowAdapter>,
        CoverFlowView.TopImageLongClickListener {

    protected static final String VIEW_LOG_TAG = "CoverFlowDemo";

    private CoverFlowView<MyCoverFlowAdapter> mCoverFlowView;
    private MyCoverFlowAdapter mAdapter;
    private Button btnChangeImage;

    @Override
    protected int getContentId() {
        return R.layout.fragment_cover_flow;
    }

    public static CoverFlowFragment newInstance() {
        Bundle args = new Bundle();
        CoverFlowFragment fragment = new CoverFlowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void bindView() {
        super.bindView();
        mCoverFlowView = (CoverFlowView<MyCoverFlowAdapter>) getView().findViewById(R.id.coverflow);
        btnChangeImage = (Button) getView().findViewById(R.id.change_bitmap_button);
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter = new MyCoverFlowAdapter(getActivity());
    }

    @Override
    protected void initView() {
        super.initView();
        initCoverFlowView();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.changeBitmap();
            }
        });
        mCoverFlowView.setCoverFlowListener(this);
        mCoverFlowView.setTopImageLongClickListener(this);
    }

    private void initCoverFlowView() {
        mCoverFlowView.setAdapter(mAdapter);
    }

    @Override
    public void imageOnTop(CoverFlowView<MyCoverFlowAdapter> coverFlowView, int position, float left, float top, float right, float bottom) {
        AppContext.getInstance().logd(VIEW_LOG_TAG, position + "on Top !");
    }

    @Override
    public void topImageClicked(CoverFlowView<MyCoverFlowAdapter> coverFlowView, int position) {
        AppContext.getInstance().logd(VIEW_LOG_TAG, position + " clicked!");
    }

    @Override
    public void invalidationCompleted() {

    }

    @Override
    public void onLongClick(int position) {
        AppContext.getInstance().logd(VIEW_LOG_TAG, "top image long clicked ==> " + position);
    }

    @Override
    public CoverFlowPresenter createPresenter() {
        return new CoverFlowPresenter(getActivity(), this);
    }
}
