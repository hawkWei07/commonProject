package cn.hawk.commonproject.presents;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;

import cn.hawk.commonlib.base.CommonPresenter;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.contracts.RecyclerViewContract;

/**
 * Created by kehaowei on 2017/3/24.
 */

public class RecyclerViewPresenter extends CommonPresenter implements RecyclerViewContract.Presenter {
    private static final String TAG = RecyclerViewPresenter.class.getSimpleName();
    private Context context;
    private RecyclerViewContract.View mView;

    public RecyclerViewPresenter(Context context, RecyclerViewContract.View mView) {
        this.context = context;
        this.mView = mView;
    }

    @Override
    public void start() {
        ArrayList<String> data = new ArrayList<>();
        data.addAll(Arrays.asList(context.getResources().getStringArray(R.array.recycler_view_demo_content)));
        mView.refreshList(data);
    }
}
