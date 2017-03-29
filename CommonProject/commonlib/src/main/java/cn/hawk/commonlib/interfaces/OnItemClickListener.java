package cn.hawk.commonlib.interfaces;

import android.view.View;

/**
 * Created by kehaowei on 2017/3/29.
 */

public interface OnItemClickListener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);
}
