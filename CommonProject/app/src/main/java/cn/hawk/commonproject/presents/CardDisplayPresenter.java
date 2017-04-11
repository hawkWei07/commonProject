package cn.hawk.commonproject.presents;

import android.content.Context;

import java.util.ArrayList;

import cn.hawk.commonproject.contracts.CardDisplayContract;
import cn.hawk.commonproject.models.PoetriesListBean;
import cn.hawk.commonproject.models.PoetryItemBean;
import cn.hawk.commonproject.utils.PoetryUtils;

/**
 * Created by kehaowei on 2017/4/10.
 */

public class CardDisplayPresenter implements CardDisplayContract.Presenter {
    private static final String TAG = CardDisplayPresenter.class.getSimpleName();

    Context context;
    private final CardDisplayContract.View mView;

    public CardDisplayPresenter(Context context, CardDisplayContract.View mView) {
        this.context = context;
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {
        PoetriesListBean body = PoetryUtils.getAllPoetries(context);
        if (null == body)
            return;
        ArrayList<PoetryItemBean> infos = body.getPoetries();
        if (null == infos || infos.size() == 0)
            return;
        mView.showPoetries(infos);
    }
}
