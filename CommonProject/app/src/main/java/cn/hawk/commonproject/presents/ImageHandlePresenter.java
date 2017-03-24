package cn.hawk.commonproject.presents;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import cn.hawk.commonlib.utils.ImageUtils;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.contracts.ImageHandleContract;

/**
 * Created by kehaowei on 2017/3/21.
 */

public class ImageHandlePresenter implements ImageHandleContract.Presenter {
    private static final String TAG = ImageHandlePresenter.class.getSimpleName();
    private final ImageHandleContract.View mView;

    private Context context;

    private int maxSize;
    private int minWidth;
    private int minHeight;

    public ImageHandlePresenter(@NonNull Context context, @NonNull ImageHandleContract.View mainView) {
        mView = mainView;
        mView.setPresenter(this);
        this.context = context;
        maxSize = context.getResources().getDimensionPixelSize(R.dimen.img_max_size);
        minWidth = context.getResources().getDimensionPixelSize(R.dimen.img_min_width);
        minHeight = context.getResources().getDimensionPixelSize(R.dimen.img_min_height);
    }

    @Override
    public void start() {
        Bitmap original = ImageUtils.decodeScaleImage(context, R.drawable.test);
        int[] size = ImageUtils.cauculateSize(original.getWidth(), original.getHeight(), minWidth, minHeight, maxSize);
        Bitmap bubbleBitmap = ImageUtils.getBubbleShapeBitmap(context, original, R.drawable.input_bg_single);
        mView.showImage(bubbleBitmap);
    }
}
