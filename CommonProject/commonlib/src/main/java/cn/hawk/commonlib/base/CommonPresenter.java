package cn.hawk.commonlib.base;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by kehaowei on 2017/5/9.
 */

public class CommonPresenter {
    protected CompositeSubscription disposable;

    public void attachView() {
        disposable = new CompositeSubscription();
    }

    public void detachView() {
        disposable.clear();
    }
}
