package cn.hawk.commonproject.ui.activities;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hawk.commonlib.base.MVPActivity;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.contracts.CaptureContract;
import cn.hawk.commonproject.presents.CaptureActivityPresenter;
import cn.hawk.commonproject.utils.zxing.camera.CameraManager;
import cn.hawk.commonproject.utils.zxing.decoding.CaptureActivityHandler;
import cn.hawk.commonproject.utils.zxing.decoding.InactivityTimer;
import cn.hawk.commonproject.utils.zxing.view.ViewfinderView;

import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by kehaowei on 2017/6/27.
 */

public class CaptureActivity extends MVPActivity<CaptureActivityPresenter> implements CaptureContract.View, Callback {
    @BindView(R.id.sv_preview_view)
    SurfaceView svPreviewView;
    @BindView(R.id.vfv_view)
    ViewfinderView viewfinderView;

    private static final float BEEP_VOLUME = 0.10f;
    private static final long VIBRATE_DURATION = 200L;

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private boolean vibrate;

    @Override
    public CaptureActivityPresenter createPresenter() {
        return new CaptureActivityPresenter(this, this);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_capture;
    }

    @Override
    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    @Override
    public void handleDecode(Result resultObj, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = resultObj.getText();
        if (TextUtils.isEmpty(resultString)) {
            showToast("扫描失败");
        } else {
            showToast(resultString);
        }
        finish();
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(surfaceHolder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        hasSurface = false;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();
        CameraManager.init(getApplicationContext());
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceHolder surfaceHolder = svPreviewView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != handler) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        if (null != mediaPlayer) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder holder) {
        try {
            CameraManager.get().openDriver(holder);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (null == handler) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
                mediaPlayer = null;
            }
        }
    }

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }
}
