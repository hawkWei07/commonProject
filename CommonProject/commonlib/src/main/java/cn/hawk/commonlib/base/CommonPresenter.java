package cn.hawk.commonlib.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by kehaowei on 2017/5/9.
 */

public class CommonPresenter {
    protected CompositeDisposable disposable;

    public void attachView() {
        disposable = new CompositeDisposable();
    }

    public void detachView() {
        disposable.clear();
    }
}
