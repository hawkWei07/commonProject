package cn.hawk.commonlib.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by kehaowei on 2017/5/31.
 */

public abstract class BaseAdapter<D, T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    protected Context context;
    protected ArrayList<D> mData = new ArrayList<>();

    public BaseAdapter(Context context, ArrayList<D> mData) {
        this.context = context;
        this.mData = mData;
    }

    public void refresh(ArrayList<D> data) {
        if (null == data || data.size() == 0) {
            mData.clear();
        } else {
            mData = data;
        }
        notifyDataSetChanged();
    }

    public void addData(int position, D data) {
        mData.add(position, data);
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        if (position < 0 || position >= mData.size())
            return;
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void updateData(int position, D data) {
        if (position < 0 || position >= mData.size())
            return;
        mData.set(position, data);
        notifyItemChanged(position);
    }

    protected abstract T getCustomHolder(ViewGroup parent, int viewType);

    protected abstract void setView(D item, T holder);


    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return getCustomHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        D item = mData.get(position);
        if (null != item) {
            setView(item, holder);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
