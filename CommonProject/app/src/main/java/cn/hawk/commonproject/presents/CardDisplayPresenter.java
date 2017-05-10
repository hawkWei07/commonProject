package cn.hawk.commonproject.presents;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;

import cn.hawk.commonlib.base.CommonPresenter;
import cn.hawk.commonlib.utils.LogUtils;
import cn.hawk.commonproject.AppContext;
import cn.hawk.commonproject.contracts.CardDisplayContract;
import cn.hawk.commonproject.models.PoetriesListBean;
import cn.hawk.commonproject.models.PoetryItemBean;
import cn.hawk.commonproject.utils.PoetryUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kehaowei on 2017/4/10.
 */

public class CardDisplayPresenter extends CommonPresenter implements CardDisplayContract.Presenter {
    private static final String TAG = CardDisplayPresenter.class.getSimpleName();

    Context context;
    private final CardDisplayContract.View mView;
    private ArrayList<PoetryItemBean> infos = new ArrayList<>();

    public CardDisplayPresenter(Context context, CardDisplayContract.View mView) {
        this.context = context;
        this.mView = mView;
    }

    @Override
    public void start() {
        disposable.add(loadPoetryListObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(@NonNull String s) {
                        if (TextUtils.isEmpty(s))
                            mView.showPoetries(infos);
                        else
                            AppContext.getInstance().logd(TAG, s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        AppContext.getInstance().loge(TAG, e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void refreshPoetries() {
        disposable.add(refreshSampleObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(@NonNull String s) {
                        if (TextUtils.isEmpty(s))
                            mView.showPoetries(infos);
                        else
                            AppContext.getInstance().logd(TAG, s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        AppContext.getInstance().loge(TAG, e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        mView.dismissRefresh();
                    }
                }));
    }


    @Override
    public PoetryItemBean getPoetryByPosition(int position) {
        if (null == infos || infos.size() == 0)
            return null;
        if (position < 0 || position >= infos.size())
            return null;
        return infos.get(position);
    }

    private String loadPoetryListImp() {
        PoetriesListBean body = PoetryUtils.getAllPoetries(context);
        if (null == body)
            return "Poetry list is null";
        ArrayList<PoetryItemBean> infos = body.getPoetries();
        if (null == infos || infos.size() == 0)
            return "Poetry list is empty";
        this.infos = infos;
        return "";
    }

    Observable<String> loadPoetryListObservable() {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                String result = loadPoetryListImp();
                return Observable.just(result);
            }
        });
    }

    Observable<String> refreshSampleObservable() {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                String result = "";
                AppContext.getInstance().logd(TAG, "act as refresh here");
                SystemClock.sleep(1000);
                PoetriesListBean body = PoetryUtils.getAllPoetries(context);
                if (null == body)
                    result = "Poetry list is null";
                else {
                    ArrayList<PoetryItemBean> infos = body.getPoetries();
                    if (null != infos && infos.size() > 0) {
                        Collections.shuffle(infos);
                        CardDisplayPresenter.this.infos = infos;
                    } else
                        result = "Poetry list is empty";
                }
                return Observable.just(result);
            }
        });
    }
}
