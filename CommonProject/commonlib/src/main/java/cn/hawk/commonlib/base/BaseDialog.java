package cn.hawk.commonlib.base;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by kehaowei on 16/6/8.
 */
public abstract class BaseDialog {
    protected Dialog mDialog;
    protected Context mContext;

    public abstract int getLayoutId();

    public BaseDialog(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(getLayoutId(), null);
        initLayoutParams(context, view);
    }

    public abstract void initLayoutParams(Context context, View view);

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }
}
