package cn.hawk.commonproject.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import cn.hawk.commonlib.base.CoverFlowAdapter;
import cn.hawk.commonproject.R;

/**
 * Created by kehaowei on 2017/5/8.
 */

public class MyCoverFlowAdapter extends CoverFlowAdapter {
    private boolean dataChanged;

    private Bitmap image1 = null;

    private Bitmap image2 = null;

    public MyCoverFlowAdapter(Context context) {
        image1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.poetry_bg);
        image2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_mini);
    }

    public void changeBitmap() {
        dataChanged = true;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataChanged ? 3 : 8;
    }

    @Override
    public Bitmap getImage(int position) {
        return (dataChanged && position == 0) ? image2 : image1;
    }
}
