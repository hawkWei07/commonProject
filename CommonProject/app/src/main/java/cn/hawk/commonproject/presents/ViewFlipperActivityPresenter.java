package cn.hawk.commonproject.presents;

import android.content.Context;

import cn.hawk.commonlib.base.CommonPresenter;
import cn.hawk.commonproject.contracts.ViewFlipperContract;

/**
 * Created by kehaowei on 2017/6/1.
 */

public class ViewFlipperActivityPresenter extends CommonPresenter implements ViewFlipperContract.Presenter {
    private static final String TAG = ViewFlipperActivityPresenter.class.getSimpleName();

    private Context context;
    private ViewFlipperContract.View mView;

    public ViewFlipperActivityPresenter(Context context, ViewFlipperContract.View mView) {
        this.context = context;
        this.mView = mView;
    }

    @Override
    public void start() {

    }
}
