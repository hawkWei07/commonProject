package cn.hawk.commonproject.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.hawk.commonlib.base.BaseFragment;
import cn.hawk.commonlib.utils.ImageUtils;
import cn.hawk.commonlib.utils.LogUtils;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.contracts.MainContract;

/**
 * Created by kehaowei on 2017/2/22.
 */

public class MainFragment extends BaseFragment implements MainContract.View {
    private MainContract.Presenter mPresenter;

    private TextView tvOutPut;
    private ImageView ivTest;
    private Bitmap btTest;
    private int maxSize;
    private int minWidth;
    private int minHeight;

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
        long startTime = System.currentTimeMillis();
        Bitmap bitmap = ImageUtils.decodeScaleImage(getActivity(), R.drawable.test);
        int[] size = ImageUtils.cauculateSize(bitmap.getWidth(), bitmap.getHeight(), minWidth, minHeight, maxSize);
        btTest = ImageUtils.getBubbleShapeBitmap(getActivity(), bitmap, R.drawable.output_bg_single);
        ViewGroup.LayoutParams layoutParams = ivTest.getLayoutParams();
        layoutParams.width = size[0];
        layoutParams.height = size[1];
        ivTest.setLayoutParams(layoutParams);
        ivTest.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ivTest.setImageBitmap(btTest);
        LogUtils.getInstance().logd("Hawk", "cost time : " + (System.currentTimeMillis() - startTime));
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
