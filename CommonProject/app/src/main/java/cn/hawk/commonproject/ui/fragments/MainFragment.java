package cn.hawk.commonproject.ui.fragments;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.hawk.commonlib.base.MVPFragment;
import cn.hawk.commonlib.utils.PermissionUtils;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.common.Constants;
import cn.hawk.commonproject.contracts.MainContract;
import cn.hawk.commonproject.presents.MainPresnter;
import cn.hawk.commonproject.services.FloatWindowService;
import cn.hawk.commonproject.ui.activities.CaptureActivity;
import cn.hawk.commonproject.ui.activities.CardDisplayActivity;
import cn.hawk.commonproject.ui.activities.CoverFlowActivity;
import cn.hawk.commonproject.ui.activities.HLActivity;
import cn.hawk.commonproject.ui.activities.ImageHandleActivity;
import cn.hawk.commonproject.ui.activities.MainActivity;
import cn.hawk.commonproject.ui.activities.RecyclerViewActivity;
import cn.hawk.commonproject.ui.activities.ViewFlipperActivity;
import cn.hawk.commonproject.ui.activities.WifiInfoActivity;
import cn.hawk.commonproject.ui.activities.WriteQRCodeActivity;

/**
 * Created by kehaowei on 2017/2/22.
 */

public class MainFragment extends MVPFragment<MainPresnter> implements MainContract.View, View.OnClickListener {
    @BindView(R.id.output)
    TextView output;
    @BindView(R.id.go_image)
    Button goImage;
    @BindView(R.id.go_recycler_view)
    Button goRecyclerView;
    @BindView(R.id.go_card_display)
    Button goCardDisplay;
    @BindView(R.id.go_cover_flow)
    Button goCoverFlow;
    @BindView(R.id.btn_float_window)
    Button btnFloatWindow;
    @BindView(R.id.btn_flipper_view)
    Button btnFlipperView;
    @BindView(R.id.btn_write_qrcode)
    Button btnWriteQrcode;
    @BindView(R.id.btn_go_capture)
    Button btnGoCapture;
    @BindView(R.id.btn_go_hl)
    Button btnGoHL;
    @BindView(R.id.btn_go_wifi)
    Button btnGoWifi;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void bindView() {
        super.bindView();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        goImage.setOnClickListener(this);
        goRecyclerView.setOnClickListener(this);
        goCardDisplay.setOnClickListener(this);
        goCoverFlow.setOnClickListener(this);
        btnFloatWindow.setOnClickListener(this);
        btnFlipperView.setOnClickListener(this);
        btnWriteQrcode.setOnClickListener(this);
        btnGoCapture.setOnClickListener(this);
        btnGoHL.setOnClickListener(this);
        btnGoWifi.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        goFlowWindow(true, false);
    }

    @Override
    public void showOutput(String output) {
        if (TextUtils.isEmpty(output))
            return;
        this.output.setText(output);
    }

    private void goImageHandle() {
        startActivity(new Intent(getActivity(), ImageHandleActivity.class));
    }

    private void goRecyclerView() {
        startActivity(new Intent(getActivity(), RecyclerViewActivity.class));
    }

    private void goCardDisplay() {
        startActivity(new Intent(getActivity(), CardDisplayActivity.class));
    }

    private void goCoverFlow() {
        startActivity(new Intent(getActivity(), CoverFlowActivity.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_image:
                goImageHandle();
                goFlowWindow(false, true);
                break;
            case R.id.go_recycler_view:
                goRecyclerView();
                goFlowWindow(false, true);
                break;
            case R.id.go_card_display:
                goCardDisplay();
                goFlowWindow(false, true);
                break;
            case R.id.go_cover_flow:
                goCoverFlow();
                goFlowWindow(false, true);
                break;
            case R.id.btn_float_window:
                goFlowWindow(false, false);
                break;
            case R.id.btn_flipper_view:
                goFlipperView();
                break;
            case R.id.btn_write_qrcode:
                goWriteQRCode();
                break;
            case R.id.btn_go_capture:
                if (checkCapturePermission())
                    goCapture();
                break;
            case R.id.btn_go_hl:
                if (checkSDCardPermission())
                    goHardLoad();
                break;
            case R.id.btn_go_wifi:
                goWifi();
                break;
        }
    }

    private void goFlowWindow(boolean forceHide, boolean forceShow) {
        if (checkOverdrawSettings()) {
            showFloatWindow(forceHide, forceShow);
        } /*else {
            requestOverdrawPermission();
        }*/
    }

    private void goFlipperView() {
        startActivity(new Intent(getActivity(), ViewFlipperActivity.class));
    }

    private void goWriteQRCode() {
        startActivity(new Intent(getActivity(), WriteQRCodeActivity.class));
    }

    public void goCapture() {
        startActivity(new Intent(getActivity(), CaptureActivity.class));
    }

    public void goHardLoad() {
        Log.d("Hawk", "goHardLoad");
        startActivity(new Intent(getActivity(), HLActivity.class));
    }

    public void goWifi() {
        startActivity(new Intent(getActivity(), WifiInfoActivity.class));
    }

    private boolean checkFloatWindowPermission() {
        return PermissionUtils.checkPermission(
                getActivity(),
                new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW},
                getString(R.string.permission_float_window),
                MainActivity.CODE_PERMISSION_FLOAT_WINDOW);
    }

    private boolean checkCapturePermission() {
        return PermissionUtils.checkPermission(getActivity(),
                new String[]{
                        Manifest.permission.CAMERA
                },
                getString(R.string.permission_capture),
                MainActivity.CODE_PERMISSION_CAPTURE);
    }

    private boolean checkSDCardPermission() {
        return PermissionUtils.checkPermission(getActivity(),
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                getString(R.string.permission_sdcard),
                MainActivity.CODE_PERMISSION_SDCARD);
    }

    private boolean checkOverdrawSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(getActivity());
        } else
            return true;
    }

    public void showFloatWindow(boolean forceHide, boolean forceShow) {
        getActivity().startService(new Intent(getActivity(), FloatWindowService.class)
                .putExtra(Constants.EXTRA_KEY_FORCE_HIDE, forceHide)
                .putExtra(Constants.EXTRA_KEY_FORCE_SHOW, forceShow));
    }

    @Override
    public MainPresnter createPresenter() {
        return new MainPresnter(this);
    }

}
