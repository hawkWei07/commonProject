package cn.hawk.commonproject.ui.fragments;

import android.os.Bundle;

import java.util.ArrayList;

import cn.hawk.commonlib.base.BaseFragment;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.contracts.RecyclerViewContract;

/**
 * Created by kehaowei on 2017/3/24.
 */

public class RecyclerViewFragment extends BaseFragment implements RecyclerViewContract.View {

    private RecyclerViewContract.Presenter mPresenter;

    public static RecyclerViewFragment newInstance() {
        Bundle args = new Bundle();
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    public void setPresenter(RecyclerViewContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshList(ArrayList<String> infos) {

    }
}
