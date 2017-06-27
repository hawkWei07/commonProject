package cn.hawk.commonproject.contracts;

import android.graphics.Bitmap;
import android.os.Handler;

import com.google.zxing.Result;

import cn.hawk.commonlib.base.BasePresenter;
import cn.hawk.commonlib.base.BaseView;
import cn.hawk.commonproject.utils.zxing.view.ViewfinderView;

/**
 * Created by kehaowei on 2017/6/27.
 */

public interface CaptureContract {
    interface Presenter extends BasePresenter {
    }

    interface View extends BaseView<Presenter> {
        ViewfinderView getViewfinderView();

        void handleDecode(Result resultObj, Bitmap barcode);

        Handler getHandler();

        void drawViewfinder();
    }
}
