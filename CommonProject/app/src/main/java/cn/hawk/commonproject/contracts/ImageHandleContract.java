package cn.hawk.commonproject.contracts;

import android.graphics.Bitmap;

import cn.hawk.commonlib.base.BasePresenter;
import cn.hawk.commonlib.base.BaseView;

/**
 * Created by kehaowei on 2017/3/21.
 */

public interface ImageHandleContract {
    interface View extends BaseView<ImageHandleContract.Presenter> {
        void showImage(Bitmap bitmap);
    }

    interface Presenter extends BasePresenter {

    }
}
