package cn.hawk.commonproject.ui.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import butterknife.BindView;
import cn.hawk.commonlib.base.MVPActivity;
import cn.hawk.commonproject.AppContext;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.contracts.ViewFlipperContract;
import cn.hawk.commonproject.presents.ViewFlipperActivityPresenter;

/**
 * Created by kehaowei on 2017/6/1.
 */

public class ViewFlipperActivity extends MVPActivity<ViewFlipperActivityPresenter> implements ViewFlipperContract.View {
    private static final String TAG = ViewFlipperActivity.class.getSimpleName();
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;

    @Override
    public ViewFlipperActivityPresenter createPresenter() {
        return new ViewFlipperActivityPresenter(this, this);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_view_flipper;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();
        for (int i = 0; i < 5; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.view_flipper_item, null);
            TextView pre = (TextView) view.findViewById(R.id.tv_pre);
            pre.setText("pre" + i);
            TextView content = (TextView) view.findViewById(R.id.tv_content);
            content.setText("content" + i);
            TextView end = (TextView) view.findViewById(R.id.tv_end);
            end.setText("end" + i);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppContext.getInstance().logd(TAG, "onClicked : " + finalI);
                }
            });
            viewFlipper.addView(view);
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }
}
