package cn.hawk.commonproject.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import cn.hawk.commonproject.AppContext;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.common.Constants;

/**
 * Created by kehaowei on 2017/5/12.
 */

public class FloatWindowService extends Service implements View.OnTouchListener, View.OnClickListener {
    private static final String TAG = FloatWindowService.class.getSimpleName();
    Context context;

    private View floatWindowView;

    private boolean isShown;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private static final int MIN_DELTA = 5;

    private float xInScreen;
    private float yInScreen;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean forceShow = intent.getBooleanExtra(Constants.EXTRA_KEY_FORCE_SHOW, false);
        boolean forceHide = intent.getBooleanExtra(Constants.EXTRA_KEY_FORCE_HIDE, false);
        if (!isShown) {
            if (!forceHide)
                showFloatWindow();
        } else {
            if (!forceShow)
                hideFloatWindow();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideFloatWindow();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void init() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        floatWindowView = LayoutInflater.from(context).inflate(R.layout.view_float_window, null);
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.RIGHT | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = context.getResources().getDimensionPixelSize(R.dimen.float_window_width);
        layoutParams.height = context.getResources().getDimensionPixelSize(R.dimen.float_window_height);
        layoutParams.x = 0;
        layoutParams.y = 0;
        isShown = false;
        floatWindowView.setOnTouchListener(this);
        floatWindowView.findViewById(R.id.btn_exit).setOnClickListener(this);
    }

    private void showFloatWindow() {
        isShown = true;
        try {
            windowManager.addView(floatWindowView, layoutParams);
        } catch (Exception e) {
            AppContext.getInstance().loge(TAG, e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private void hideFloatWindow() {
        isShown = false;
        if (null != windowManager && null != floatWindowView) {
            try {
                windowManager.removeView(floatWindowView);
            } catch (Exception e) {
                AppContext.getInstance().loge(TAG, e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }

    private void updateFloatWindow(int dx, int dy) {
        layoutParams.x = layoutParams.x + dx;
        layoutParams.y = layoutParams.y + dy;
        if (layoutParams.x < 0)
            layoutParams.x = 0;
        if (layoutParams.y < 0)
            layoutParams.y = 0;
        if (null != windowManager && null != floatWindowView) {
            try {
                windowManager.updateViewLayout(floatWindowView, layoutParams);
            } catch (Exception e) {
                AppContext.getInstance().loge(TAG, e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xInScreen = motionEvent.getRawX();
                yInScreen = motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = motionEvent.getRawX();
                float y = motionEvent.getRawY();
                int dx = (int) (x - xInScreen);
                int dy = (int) (y - yInScreen);
                if (Math.abs(dx) >= MIN_DELTA || Math.abs(dy) >= MIN_DELTA) {
                    updateFloatWindow(-dx, dy);
                    xInScreen = x;
                    yInScreen = y;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_exit:
                hideFloatWindow();
                break;
        }
    }
}
