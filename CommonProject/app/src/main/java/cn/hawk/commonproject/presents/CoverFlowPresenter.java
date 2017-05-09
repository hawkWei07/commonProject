package cn.hawk.commonproject.presents;

import android.content.Context;

import cn.hawk.commonlib.base.CommonPresenter;
import cn.hawk.commonproject.contracts.CoverFlowContract;

/**
 * Created by kehaowei on 2017/5/8.
 */

public class CoverFlowPresenter extends CommonPresenter implements CoverFlowContract.Presenter {

    private Context context;
    private CoverFlowContract.View mView;

    public CoverFlowPresenter(Context context, CoverFlowContract.View mView) {
        this.context = context;
        this.mView = mView;
    }

    @Override
    public void start() {

    }
}
