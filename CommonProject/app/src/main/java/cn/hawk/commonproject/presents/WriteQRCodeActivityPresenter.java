package cn.hawk.commonproject.presents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import cn.hawk.commonlib.base.CommonPresenter;
import cn.hawk.commonlib.utils.QRUtils;
import cn.hawk.commonproject.AppContext;
import cn.hawk.commonproject.contracts.WriteQRCodeContract;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * Created by kehaowei on 2017/6/26.
 */

public class WriteQRCodeActivityPresenter extends CommonPresenter implements WriteQRCodeContract.Presenter {
    private static final String TAG = WriteQRCodeActivityPresenter.class.getSimpleName();
    private Context mContext;
    private WriteQRCodeContract.View mView;
    private Bitmap bitmap;

    public WriteQRCodeActivityPresenter(Context mContext, WriteQRCodeContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void start() {

    }

    @Override
    public void generateQRCode(final String content) {
        if (TextUtils.isEmpty(content))
            mView.onCodeGenerateFailed("生成失败，输入位空");
        bitmap = null;
        disposable.add(generateBitmapObservable(content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        if (TextUtils.isEmpty(s)) {
                            if (null != bitmap)
                                mView.onCodeGenerateSuccess(bitmap);
                        } else {
                            mView.onCodeGenerateFailed(s);
                        }
                    }
                }));
    }

    Observable<String> generateBitmapObservable(final String content) {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.just(generateBitmap(content));
            }
        });
    }


    private String generateBitmap(String content) {
        AppContext.getInstance().logd(TAG, "start to generate : " + content);
        String resultStr = "";
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            result = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400);
            int w = result.getWidth();
            int h = result.getHeight();
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                int offset = y * w;
                for (int x = 0; x < w; x++) {
                    pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
                }
            }
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        } catch (Exception e) {
            e.printStackTrace();
            resultStr = e.getLocalizedMessage();
            AppContext.getInstance().logd(TAG, "generate failed : " + resultStr);
        }
        AppContext.getInstance().logd(TAG, "generate end : " + bitmap);
        return resultStr;
    }
}
