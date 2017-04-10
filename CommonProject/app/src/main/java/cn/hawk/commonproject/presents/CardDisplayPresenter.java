package cn.hawk.commonproject.presents;

import cn.hawk.commonproject.contracts.CardDisplayContract;

/**
 * Created by kehaowei on 2017/4/10.
 */

public class CardDisplayPresenter implements CardDisplayContract.Presenter {
    private static final String TAG = CardDisplayPresenter.class.getSimpleName();

    private final CardDisplayContract.View mView;

    public CardDisplayPresenter(CardDisplayContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
