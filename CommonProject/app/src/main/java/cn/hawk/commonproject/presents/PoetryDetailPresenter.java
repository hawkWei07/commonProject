package cn.hawk.commonproject.presents;

import android.content.Context;

import cn.hawk.commonproject.contracts.PoetryDetailContract;
import cn.hawk.commonproject.models.PoetryItemBean;
import cn.hawk.commonproject.utils.PoetryUtils;

/**
 * Created by kehaowei on 2017/4/12.
 */

public class PoetryDetailPresenter implements PoetryDetailContract.Presenter {
    private static final String TAG = PoetryDetailPresenter.class.getSimpleName();

    private Context context;
    private PoetryDetailContract.View mView;
    private int poetryId;

    public PoetryDetailPresenter(Context context, PoetryDetailContract.View mView, int poetryId) {
        this.context = context;
        this.mView = mView;
        this.poetryId = poetryId;
    }

    @Override
    public void start() {
        PoetryItemBean item = PoetryUtils.getPoetryById(context, poetryId);
        if (null != item)
            mView.showPoetryContent(item.getContent());
    }
}
