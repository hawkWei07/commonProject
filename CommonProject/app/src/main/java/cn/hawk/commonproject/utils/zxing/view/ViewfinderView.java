/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.hawk.commonproject.utils.zxing.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.zxing.ResultPoint;

import java.util.Collection;
import java.util.HashSet;

import cn.hawk.commonproject.R;
import cn.hawk.commonproject.utils.zxing.camera.CameraManager;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points.
 */
public final class ViewfinderView extends View {
    private static final String TAG = "ViewfinderView";
    /**
     * 刷新界面的时间
     */
    private static final long ANIMATION_DELAY = 10L;
    private static final int OPAQUE = 0xFF;
    /**
     * 四个绿色边角对应的宽度
     */
    private static final int CORNER_WIDTH = 10;
    /**
     * 扫描框中的中间线的宽度
     */
    private static final int MIDDLE_LINE_WIDTH = 20;
    /**
     * 扫描框中的中间线的与扫描框左右的间隙
     */
    private static final int MIDDLE_LINE_PADDING = 5;
    /**
     * 中间那条线每次刷新移动的距离
     */
    private static final int SPEEN_DISTANCE = 5;
    /**
     * 字体大小
     */
    private static final int TEXT_SIZE = 10;
    /**
     * 字体距离扫描框下面的距离
     */
    private static final int TEXT_PADDING_TOP = 30;
    /**
     * 手机的屏幕密度
     */
    private static float density;
    boolean isFirst;
    /**
     * 四个绿色边角对应的长度
     */
    private int ScreenRate;
    /**
     * 画笔对象的引用
     */
    private Paint paint;
    /**
     * 中间滑动线的最顶端位置
     */
    private int slideTop;
//    /**
//     * 中间滑动线的最底端位置
//     */
//    private int slideBottom;

    // private Resources mResources;
    private Bitmap resultBitmap;
    private Collection<ResultPoint> possibleResultPoints;
    private Collection<ResultPoint> lastPossibleResultPoints;

    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        density = context.getResources().getDisplayMetrics().density;
        // 将像素转换成dp
        ScreenRate = (int) (20 * density);

        paint = new Paint();

        possibleResultPoints = new HashSet<ResultPoint>(5);
    }

    @Override
    public void onDraw(Canvas canvas) {
        // 中间的扫描框，你要修改扫描框的大小，去CameraManager里面修改
        Log.i(TAG, "onDraw");
        Rect frame = null;
        try {
            frame = CameraManager.get().getFramingRect();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (frame == null) {
            return;
        }

        // 初始化中间线滑动的最上边和最下边
        if (!isFirst) {
            isFirst = true;
            slideTop = frame.top;
//            slideBottom = frame.bottom;
        }

        // 获取屏幕的宽和高

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // paint.setColor(resultBitmap != null ? resultColor : maskColor);
        paint.setColor(Color.parseColor("#000000"));
        paint.setAlpha(150);
        // 画出扫描框外面的阴影部分，共四个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面
        // 扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
                paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(OPAQUE);
            canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
        } else {

            // 画扫描框边上的角，总共8个部分
            paint.setColor(Color.parseColor("#F4FBFF"));
            // 左上右
            canvas.drawRect(frame.left - CORNER_WIDTH,
                    frame.top - CORNER_WIDTH, frame.left + ScreenRate,
                    frame.top, paint);

            // 左上左
            canvas.drawRect(frame.left - CORNER_WIDTH, frame.top, frame.left,
                    frame.top + ScreenRate, paint);

            canvas.drawRect(frame.left - CORNER_WIDTH, frame.bottom, frame.left
                    + ScreenRate, frame.bottom + CORNER_WIDTH, paint);

            canvas.drawRect(frame.left - CORNER_WIDTH, frame.bottom
                    - ScreenRate, frame.left, frame.bottom, paint);

            canvas.drawRect(frame.right, frame.top, frame.right + CORNER_WIDTH,
                    frame.top + ScreenRate, paint);
            canvas.drawRect(frame.right - ScreenRate, frame.top - CORNER_WIDTH,
                    frame.right + CORNER_WIDTH, frame.top, paint);

            canvas.drawRect(frame.right - ScreenRate, frame.bottom, frame.right
                    + CORNER_WIDTH, frame.bottom + CORNER_WIDTH, paint);
            canvas.drawRect(frame.right, frame.bottom - ScreenRate, frame.right
                    + CORNER_WIDTH, frame.bottom, paint);
            // 画出4条1像素的描边
            // 左
            canvas.drawRect(frame.left, frame.top, frame.left + 1, frame.bottom
                    - ScreenRate, paint);
            // 上
            canvas.drawRect(frame.left, frame.top, frame.right - ScreenRate,
                    frame.top + 1, paint);
            // 右
            canvas.drawRect(frame.right, frame.top, frame.right + 1,
                    frame.bottom - ScreenRate, paint);
            // 下
            canvas.drawRect(frame.left, frame.bottom, frame.right - ScreenRate,
                    frame.bottom + 1, paint);

            // 绘制中间的线,每次刷新界面，中间的线往下移动SPEEN_DISTANCE
            slideTop += SPEEN_DISTANCE;
            if (slideTop >= frame.bottom) {
                slideTop = frame.top;
            }

            RectF dst = new RectF(frame.left + MIDDLE_LINE_PADDING, slideTop
                    - MIDDLE_LINE_WIDTH / 2, frame.right - MIDDLE_LINE_PADDING,
                    slideTop + MIDDLE_LINE_WIDTH / 2);
            // 画扫描线
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.img_saomiao_line2);
            canvas.drawBitmap(bitmap, null, dst, paint);
            // 画扫描框下的图
            Bitmap mtBitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.img_scan_tip);
            canvas.drawBitmap(mtBitmap, frame.left + 80, frame.bottom + 150,
                    paint);
            // // 画扫描框下面的字(3个提示)
            // paint.setColor(Color.WHITE);
            // paint.setTextSize(TEXT_SIZE * density);
            // paint.setAlpha(0x40);
            // paint.setTypeface(Typeface.create("System", Typeface.BOLD));
            //
            // String tishi = getResources()
            // .getString(R.string.scan_text_tishione);
            // int index = tishi.indexOf(",");
            // canvas.drawText(
            // tishi,
            // frame.left + 260,
            // (float) (frame.bottom + (float) TEXT_PADDING_TOP * density),
            // paint);
            // String tishi1 = getResources().getString(
            // R.string.scan_text_tishitwo);
            //
            // canvas.drawText(
            // tishi1,
            // frame.left + 260,
            // (float) (frame.bottom + (float) TEXT_PADDING_TOP * density) +
            // 100,
            // paint);
            // String tishi2 = getResources().getString(
            // R.string.scan_text_tishithree);
            //
            // canvas.drawText(
            // tishi2,
            // frame.left + 260,
            // (float) (frame.bottom + (float) TEXT_PADDING_TOP * density) +
            // 200,
            // paint);

            // canvas.drawText(
            // tishi.substring(0, index),
            // frame.left,
            // (float) (frame.bottom + (float) TEXT_PADDING_TOP * density),
            // paint);
            // canvas.drawText(tishi.substring(index + 1), frame.left,
            // (float) (frame.bottom + (float) TEXT_PADDING_TOP * 2
            // * density), paint);
            Collection<ResultPoint> currentPossible = possibleResultPoints;
            Collection<ResultPoint> currentLast = lastPossibleResultPoints;
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null;
            } else {
                possibleResultPoints = new HashSet<ResultPoint>(5);
                lastPossibleResultPoints = currentPossible;
                paint.setAlpha(OPAQUE);
                paint.setColor(Color.RED);
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle(frame.left + point.getX(), frame.top
                            + point.getY(), 6.0f, paint);
                }
            }
            if (currentLast != null) {
                paint.setAlpha(OPAQUE / 2);
                paint.setColor(Color.RED);
                for (ResultPoint point : currentLast) {
                    canvas.drawCircle(frame.left + point.getX(), frame.top
                            + point.getY(), 3.0f, paint);
                }
            }

            // 只刷新扫描框的内容，其他地方不刷新
            postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
                    frame.right, frame.bottom);

        }
    }

    public void drawViewfinder() {
        resultBitmap = null;
        invalidate();
    }

    /**
     * Draw a bitmap with the result points highlighted instead of the live
     * scanning display.
     *
     * @param barcode An image of the decoded barcode.
     */
    public void drawResultBitmap(Bitmap barcode) {
        resultBitmap = barcode;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        possibleResultPoints.add(point);
    }
}
