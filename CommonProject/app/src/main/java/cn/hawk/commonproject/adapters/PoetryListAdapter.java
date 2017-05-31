package cn.hawk.commonproject.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.hawk.commonlib.base.BaseAdapter;
import cn.hawk.commonlib.interfaces.OnItemClickListener;
import cn.hawk.commonlib.utils.LogUtils;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.models.PoetryItemBean;

/**
 * Created by kehaowei on 2017/4/11.
 */

public class PoetryListAdapter extends BaseAdapter<PoetryItemBean, PoetryListAdapter.ViewHolder> {
    private static final String TAG = PoetryListAdapter.class.getSimpleName();
    private OnItemClickListener mListener;

    public PoetryListAdapter(Context context, ArrayList<PoetryItemBean> mData) {
        super(context, mData);
    }


    public void setmListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }


    @Override
    protected ViewHolder getCustomHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_item_poetry_info, parent, false));
        return holder;
    }

    @Override
    protected void setView(PoetryItemBean item, final ViewHolder holder) {
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
