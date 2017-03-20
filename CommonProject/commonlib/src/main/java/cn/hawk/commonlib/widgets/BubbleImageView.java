package cn.hawk.commonlib.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by kehaowei on 2017/3/20.
 */

public class BubbleImageView extends android.support.v7.widget.AppCompatImageView {


    public BubbleImageView(Context context) {
        super(context);
    }

    public BubbleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BubbleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
