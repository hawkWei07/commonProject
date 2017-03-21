package cn.hawk.commonproject.ui.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.hawk.commonlib.base.BaseFragment;
import cn.hawk.commonlib.utils.ImageUtils;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.contracts.ImageHandleContract;

/**
 * Created by kehaowei on 2017/3/21.
 */

public class ImageHandleFragment extends BaseFragment implements ImageHandleContract.View {
    private ImageHandleContract.Presenter mPresenter;

    private ImageView ivTest;
    private int maxSize;
    private int minWidth;
    private int minHeight;

    public static ImageHandleFragment newInstance() {
        Bundle args = new Bundle();
        ImageHandleFragment fragment = new ImageHandleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(ImageHandleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_image_handle;
    }

    @Override
    protected void bindView() {
        super.bindView();
        ivTest = (ImageView) getView().findViewById(R.id.iv_test);
    }

    @Override
    protected void initData() {
        super.initData();
        maxSize = getActivity().getResources().getDimensionPixelSize(R.dimen.img_max_size);
        minWidth = getActivity().getResources().getDimensionPixelSize(R.dimen.img_min_width);
        minHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.img_min_height);
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
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showImage(Bitmap bitmap) {
        int[] size = ImageUtils.cauculateSize(bitmap.getWidth(), bitmap.getHeight(), minWidth, minHeight, maxSize);
        ViewGroup.LayoutParams layoutParams = ivTest.getLayoutParams();
        layoutParams.width = size[0];
        layoutParams.height = size[1];
        ivTest.setLayoutParams(layoutParams);
        ivTest.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ivTest.setImageBitmap(bitmap);
    }
}
