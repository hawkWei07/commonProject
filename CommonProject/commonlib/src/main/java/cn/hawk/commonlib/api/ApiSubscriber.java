package cn.hawk.commonlib.api;

import rx.Subscriber;

/**
 * Created by kehaowei on 2017/5/15.
 */

public class ApiSubscriber<T> extends Subscriber<ResponseBean<T>> {
    public static final int UNKNOWN_CODE = -1;
    private ApiCallBack<T> apiCallBack;

    public ApiSubscriber(ApiCallBack<T> apiCallBack) {
        this.apiCallBack = apiCallBack;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (null == apiCallBack)
            return;
    }

    @Override
    public void onCompleted() {
        if (null == apiCallBack)
            return;
        apiCallBack.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (null == apiCallBack)
            return;
        apiCallBack.onFailure(UNKNOWN_CODE, e.getMessage());
    }

    @Override
    public void onNext(ResponseBean<T> tResponseBean) {
        if (null == apiCallBack)
            return;
        if (tResponseBean.isSuccess()) {
            apiCallBack.onSuccess(tResponseBean.getData());
        } else if (tResponseBean.isTokenError()) {
            apiCallBack.onTokenError(tResponseBean.getCode(), tResponseBean.getMessage());
        } else {
            apiCallBack.onFailure(tResponseBean.getCode(), tResponseBean.getMessage());
        }
    }
}
