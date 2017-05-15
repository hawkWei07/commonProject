package cn.hawk.commonproject.presents;

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;

import cn.hawk.commonlib.base.CommonPresenter;
import cn.hawk.commonproject.AppContext;
import cn.hawk.commonproject.contracts.CardDisplayContract;
import cn.hawk.commonproject.models.PoetriesListBean;
import cn.hawk.commonproject.models.PoetryItemBean;
import cn.hawk.commonproject.utils.PoetryUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

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
                .subscribe(new Subscriber<String>() {
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
                    public void onCompleted() {

                    }
                }));
    }

    @Override
    public void refreshPoetries() {
        disposable.add(refreshSampleObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
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
                    public void onCompleted() {
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
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                String result = loadPoetryListImp();
                return Observable.just(result);
            }
        });
    }

    Observable<String> refreshSampleObservable() {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
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
