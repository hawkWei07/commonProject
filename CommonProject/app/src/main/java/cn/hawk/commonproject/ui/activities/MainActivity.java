package cn.hawk.commonproject.ui.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import cn.hawk.commonlib.base.BaseActivity;
import cn.hawk.commonlib.utils.ActivityUtils;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.presents.MainPresnter;
import cn.hawk.commonproject.ui.fragments.MainFragment;

public class MainActivity extends BaseActivity {

    public static final int CODE_PERMISSION_FLOAT_WINDOW = 1;
    public static final int CODE_PERMISSION_CAPTURE = 2;

    public static final int CODE_REQUEST_OVER_DRAW = 1;

    private MainFragment mainFragment;

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindView() {
        super.bindView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this))
                requestOverdrawPermission();
        }
        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (null == mainFragment) {
            mainFragment = MainFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mainFragment, R.id.contentFrame);
        }
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


    private void requestOverdrawPermission() {
        startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())),
                MainActivity.CODE_REQUEST_OVER_DRAW);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean failed = false;
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                failed = true;
                break;
            }
        }
        if (!failed && null != mainFragment) {
            switch (requestCode) {
                case CODE_PERMISSION_FLOAT_WINDOW:
                              /*mainFragment.showFloatWindow(false, false);*/
                    break;
                case CODE_PERMISSION_CAPTURE:
                    mainFragment.goCapture();
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CODE_REQUEST_OVER_DRAW:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.canDrawOverlays(this)) {
                       /* if (null != mainFragment)
                            mainFragment.showFloatWindow(false, false);*/
                    } else {
                        Toast.makeText(this, "权限获取失败，无法显示悬浮框", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
