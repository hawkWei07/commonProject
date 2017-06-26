package cn.hawk.commonproject.contracts;

import android.graphics.Bitmap;

import cn.hawk.commonlib.base.BasePresenter;
import cn.hawk.commonlib.base.BaseView;

/**
 * Created by kehaowei on 2017/6/26.
 */

public interface WriteQRCodeContract {
    interface Presenter extends BasePresenter {
        void generateQRCode(String content);
    }

    interface View extends BaseView<Presenter> {
        void onCodeGenerateSuccess(Bitmap bitmap);

        void onCodeGenerateFailed(String msg);
    }
}
