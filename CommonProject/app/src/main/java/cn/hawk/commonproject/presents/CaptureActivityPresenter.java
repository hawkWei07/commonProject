package cn.hawk.commonproject.presents;

import android.content.Context;

import cn.hawk.commonlib.base.CommonPresenter;
import cn.hawk.commonproject.contracts.CaptureContract;

/**
 * Created by kehaowei on 2017/6/27.
 */

public class CaptureActivityPresenter extends CommonPresenter implements CaptureContract.Presenter {
    private static final String TAG = CaptureActivityPresenter.class.getSimpleName();

    private Context mContext;
    private CaptureContract.View mView;

    public CaptureActivityPresenter(Context mContext, CaptureContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void start() {

    }
}
