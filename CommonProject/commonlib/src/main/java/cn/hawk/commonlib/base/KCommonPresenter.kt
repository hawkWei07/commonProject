package cn.hawk.commonlib.base

import rx.subscriptions.CompositeSubscription

/**
 * Created by kehaowei on 2017/5/25.
 */
open class KCommonPresenter {
    var disposable: CompositeSubscription? = null;

    fun attachView() {
        disposable = CompositeSubscription();
    }

    fun detachView() {
        disposable?.clear();
    }
}