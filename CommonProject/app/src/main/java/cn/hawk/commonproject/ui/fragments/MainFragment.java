package cn.hawk.commonproject.ui.fragments;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
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
import cn.hawk.commonproject.AppContext;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.common.Constants;
import cn.hawk.commonproject.contracts.MainContract;
import cn.hawk.commonproject.presents.MainPresnter;
import cn.hawk.commonproject.services.FloatWindowService;
import cn.hawk.commonproject.ui.activities.CardDisplayActivity;
import cn.hawk.commonproject.ui.activities.CoverFlowActivity;
import cn.hawk.commonproject.ui.activities.ImageHandleActivity;
import cn.hawk.commonproject.ui.activities.MainActivity;
import cn.hawk.commonproject.ui.activities.RecyclerViewActivity;

/**
 * Created by kehaowei on 2017/2/22.
 */

public class MainFragment extends MVPFragment<MainPresnter> implements MainContract.View, View.OnClickListener {
    Button btnFloatWindow;
    private TextView tvOutPut;
    private Button btnGoImage;
    private Button btnGoRecycler;
    private Button btnGoCardDisplay;
    private Button btnGoCoverFlow;

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
        tvOutPut = (TextView) getView().findViewById(R.id.output);
        btnGoImage = (Button) getView().findViewById(R.id.go_image);
        btnGoRecycler = (Button) getView().findViewById(R.id.go_recycler_view);
        btnGoCardDisplay = (Button) getView().findViewById(R.id.go_card_display);
        btnGoCoverFlow = (Button) getView().findViewById(R.id.go_cover_flow);
        btnFloatWindow = (Button) getView().findViewById(R.id.btn_float_window);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btnGoImage.setOnClickListener(this);
        btnGoRecycler.setOnClickListener(this);
        btnGoCardDisplay.setOnClickListener(this);
        btnGoCoverFlow.setOnClickListener(this);
        btnFloatWindow.setOnClickListener(this);
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
        tvOutPut.setText(output);
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
        }
    }

    private void goFlowWindow(boolean forceHide, boolean forceShow) {
        if (checkOverdrawSettings()) {
            showFloatWindow(forceHide, forceShow);
        } /*else {
            requestOverdrawPermission();
        }*/
    }

    private boolean checkFloatWindowPermission() {
        return PermissionUtils.checkPermission(
                getActivity(),
                new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW},
                getString(R.string.permission_float_window),
                MainActivity.CODE_PERMISSION_FLOAT_WINDOW);
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
