package cn.hawk.commonproject.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.hawk.commonlib.interfaces.OnItemClickListener;
import cn.hawk.commonlib.utils.LogUtils;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.models.PoetryItemBean;

/**
 * Created by kehaowei on 2017/4/11.
 */

public class PoetryListAdapter extends RecyclerView.Adapter<PoetryListAdapter.ViewHolder> {
    private static final String TAG = PoetryListAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<PoetryItemBean> mData = new ArrayList<>();
    private OnItemClickListener mListener;

    public PoetryListAdapter(Context context, ArrayList<PoetryItemBean> mData) {
        this.context = context;
        this.mData = mData;
    }

    public void setmListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public void refresh(ArrayList<PoetryItemBean> data) {
        if (null != data && data.size() > 0)
            mData = data;
        else
            mData.clear();
        notifyDataSetChanged();
    }

    public void addData(int position, PoetryItemBean data) {
        mData.add(position, data);
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        if (position < 0 || position >= mData.size())
            return;
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void updateData(int position, PoetryItemBean data) {
        if (position < 0 || position >= mData.size())
            return;
        mData.set(position, data);
        notifyItemChanged(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_item_poetry_info, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PoetryItemBean item = mData.get(position);
        holder.title.setText(item.getTitle());
        holder.time.setText(item.getTime());
        holder.author.setText(item.getAuthor());
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
        TextView title;
        TextView time;
        TextView author;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.poetry_title);
            time = (TextView) itemView.findViewById(R.id.poetry_time);
            author = (TextView) itemView.findViewById(R.id.poetry_author);
        }
    }
}
