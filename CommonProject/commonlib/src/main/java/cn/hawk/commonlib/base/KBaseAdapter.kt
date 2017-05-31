package cn.hawk.commonlib.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by kehaowei on 2017/5/31.
 */
class KBaseAdapter<T>(val context: Context, val layoutResourceId: Int, var mData: ArrayList<T>, val init: (View, T) -> Unit)
    : RecyclerView.Adapter<KBaseAdapter.ViewHolder<T>>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder<T> {
        val view = LayoutInflater.from(context).inflate(layoutResourceId, parent, false)
        return ViewHolder(view, init)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder<T>?, position: Int) {
        holder?.bindForecast(mData[position])
    }

    class ViewHolder<in T>(view: View, val init: (View, T) -> Unit) : RecyclerView.ViewHolder(view) {
        fun bindForecast(item: T) {
            with(item) {
                init(itemView, item)
            }
        }
    }
}