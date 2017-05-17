package cn.hawk.commonlib.widgets;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import cn.hawk.commonlib.R;
import cn.hawk.commonlib.base.BaseDialog;


/**
 * Created by kehaowei on 16/6/8.
 */
public class LoadingDialog extends BaseDialog {

    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_dialog_loading;
    }

    @Override
    public void initLayoutParams(Context context, View view) {
        mDialog = new Dialog(context, R.style.BaseDialog);
        mDialog.setContentView(view,
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                        , LinearLayout.LayoutParams.MATCH_PARENT));
    }
}
