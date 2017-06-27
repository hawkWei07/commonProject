package cn.hawk.commonlib.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * Created by kehaowei on 2017/6/26.
 */

public class QRUtils {
    public static Bitmap encodeAsBitmap(String content) {
        Bitmap bitmap = null;
        BitMatrix result = null;
        if (TextUtils.isEmpty(content))
            return null;
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            result = writer.encode(content, BarcodeFormat.QR_CODE, 200, 200);
            int w = result.getWidth();
            int h = result.getHeight();
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                int offset = y * w;
                for (int x = 0; x < w; x++) {
                    pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
