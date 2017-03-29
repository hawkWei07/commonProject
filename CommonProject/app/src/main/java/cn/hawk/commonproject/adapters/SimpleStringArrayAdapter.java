package cn.hawk.commonproject.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.hawk.commonlib.interfaces.OnItemClickListener;
import cn.hawk.commonproject.R;

/**
 * Created by kehaowei on 2017/3/27.
 */

public class SimpleStringArrayAdapter extends RecyclerView.Adapter<SimpleStringArrayAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> mData = new ArrayList<>();
    private OnItemClickListener mListener;

    public SimpleStringArrayAdapter(Context context, ArrayList<String> mData) {
        this.context = context;
        this.mData = mData;
    }

    public void setmListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public void refresh(ArrayList<String> data) {
        if (null != data && data.size() > 0)
            mData = data;
        else
            mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_item_text, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv.setText(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == mListener)
                    return;
                int pos = holder.getLayoutPosition();
                mListener.onItemClick(view, pos);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (null != mListener) {
                    int pos = holder.getLayoutPosition();
                    mListener.onItemLongClick(view, pos);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_text);
        }
    }

    public void addData(int position, String data) {
        mData.add(position, data);
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        if (position < 0 || position >= mData.size())
            return;
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void updateData(int position, String data) {
        if (position < 0 || position >= mData.size())
            return;
        mData.set(position, data);
        notifyItemChanged(position);
    }
}
