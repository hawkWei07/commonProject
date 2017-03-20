package cn.hawk.commonlib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ExifInterface;

import java.io.IOException;

import cn.hawk.commonlib.R;

/**
 * Created by kehaowei on 2017/3/20.
 */

public class ImageUtils {
    public static Bitmap getBubbleShapeBitmap(Context context, Bitmap bitmap, int bubbleResId) {
        Bitmap mask = BitmapFactory.decodeResource(context.getResources(), bubbleResId);
        Bitmap result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        NinePatch np = new NinePatch(mask, mask.getNinePatchChunk(), null);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        np.draw(mCanvas, rect, null);
        mCanvas.drawBitmap(bitmap, 0, 0, paint);
        return result;
    }

    public static Bitmap decodeScaleImage(Context context, String path) {
        BitmapFactory.Options bitmapOptions = getBitmapOptions(path);
        float sampleSize = calculateInSampleSize(context, bitmapOptions);
        bitmapOptions.inSampleSize = (int) Math.ceil(sampleSize);
        bitmapOptions.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, bitmapOptions);
        int degree = readPictureDegree(path);
        Bitmap bitmap1 = null;
        if (bitmap != null && degree != 0) {
            bitmap1 = rotaingImageView(degree, bitmap);
            bitmap.recycle();
            bitmap = null;
            return bitmap1;
        } else {
            return bitmap;
        }
    }

    public static Bitmap decodeScaleImage(Context context, int resId) {
        BitmapFactory.Options bitmapOptions = getBitmapOptions(context, resId);
        float sampleSize = calculateInSampleSize(context, bitmapOptions);
        bitmapOptions.inSampleSize = (int) Math.ceil(sampleSize);
        bitmapOptions.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId, bitmapOptions);
        return bitmap;
    }

    public static float calculateInSampleSize(Context context, BitmapFactory.Options options) {
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        int minWidth = context.getResources().getDimensionPixelSize(R.dimen.img_min_width);
        int minHeight = context.getResources().getDimensionPixelSize(R.dimen.img_min_height);
        int maxSize = context.getResources().getDimensionPixelSize(R.dimen.img_max_size);
        float result = 1;
        if (outHeight == outWidth) {
            if (outHeight > maxSize)
                result = (float) outHeight / (float) maxSize;
        } else if (outHeight > outWidth) {
            if (outWidth > minWidth || outHeight > maxSize) {
                float scaleWidth = (float) outWidth / (float) minWidth;
                float scaleHeight = (float) outHeight / (float) maxSize;
                result = scaleWidth > scaleHeight ? scaleWidth : scaleHeight;
            }
        } else {
            if (outHeight > minHeight || outWidth > maxSize) {
                float scaleWidth = (float) outWidth / (float) maxSize;
                float scaleHeight = (float) outHeight / (float) minHeight;
                result = scaleWidth > scaleHeight ? scaleWidth : scaleHeight;
            }
        }
        return result;
    }


    public static int[] cauculateSize(int width, int height, int minWidth, int minHeight, int maxSize) {
        int[] result = new int[2];
        float scale = (float) width / (float) height;
        if (width == height) {
            int size = Math.min(width, maxSize);
            size = Math.max(size, Math.min(minWidth, minHeight));
            result[0] = size;
            result[1] = size;
        } else if (width > height) {
            result[0] = maxSize;
            result[1] = (int) (maxSize / scale);
        } else {
            result[0] = (int) (maxSize * scale);
            result[1] = maxSize;
        }
        return result;
    }

    public static BitmapFactory.Options getBitmapOptions(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        return options;
    }

    public static BitmapFactory.Options getBitmapOptions(Context context, int res) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), res, options);
        return options;
    }

    public static int readPictureDegree(String path) {
        short degree = 0;

        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt("Orientation", 1);
            switch (orientation) {
                case 3:
                    degree = 180;
                    break;
                case 6:
                    degree = 90;
                    break;
                case 8:
                    degree = 270;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return degree;
    }


    public static Bitmap rotaingImageView(int direction, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) direction);
        Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return result;
    }
}
