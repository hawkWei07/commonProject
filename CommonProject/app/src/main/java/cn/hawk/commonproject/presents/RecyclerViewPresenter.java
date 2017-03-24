package cn.hawk.commonproject.presents;

import android.content.Context;

import cn.hawk.commonproject.contracts.RecyclerViewContract;

/**
 * Created by kehaowei on 2017/3/24.
 */

public class RecyclerViewPresenter implements RecyclerViewContract.Presenter {
    private static final String TAG = RecyclerViewPresenter.class.getSimpleName();
    private Context context;
    private RecyclerViewContract.View mView;

    public RecyclerViewPresenter(Context context, RecyclerViewContract.View mView) {
        this.context = context;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
