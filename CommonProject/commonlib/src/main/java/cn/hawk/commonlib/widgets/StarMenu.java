package cn.hawk.commonlib.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import cn.hawk.commonlib.R;

/**
 * Created by kehaowei on 2017/5/16.
 */

public class StarMenu extends ViewGroup implements View.OnClickListener {
    private static final String TAG = "StarMenu";
    private OnMenuItemClickListener menuItemClickListener;
    private Position mPosition = Position.RIGHT_BOTTOM;

    private int mRadius;
    private int mDuration;

    private View mCenterView;

    private Status mStatus = Status.CLOSE;

    public StarMenu(Context context) {
        super(context);
        mRadius = context.getResources().getDimensionPixelSize(R.dimen.star_menu_radius_default);
        mDuration = 300;
    }

    public StarMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StarMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StarMenu);
        mRadius = a.getDimensionPixelSize(R.styleable.StarMenu_radius, 100);
        mPosition = Position.values()[a.getInt(R.styleable.StarMenu_position, Position.RIGHT_BOTTOM.ordinal())];
        mDuration = a.getInteger(R.styleable.StarMenu_duration, 300);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        if (count > 0)
            layoutCenterView();
        if (count > 1) {
            for (int i = 1; i < count; i++) {
                layoutChild(getChildAt(i), i - 1, count - 1);
            }
        }
    }

    protected void layoutCenterView() {
        mCenterView = getChildAt(0);
        mCenterView.setOnClickListener(this);
        int l = 0;
        int t = 0;
        int width = mCenterView.getMeasuredWidth();
        int height = mCenterView.getMeasuredHeight();

        switch (mPosition) {
            case LEFT_TOP:
                l = 0;
                t = 0;
                break;
            case LEFT_CENTER:
                l = 0;
                t = (getMeasuredHeight() - height) / 2;
                break;
            case LEFT_BOTTOM:
                l = 0;
                t = getMeasuredHeight() - height;
                break;
            case RIGHT_TOP:
                l = getMeasuredWidth() - width;
                t = 0;
                break;
            case RIGHT_CENTER:
                l = getMeasuredWidth() - width;
                t = (getMeasuredHeight() - height) / 2;
                break;
            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - width;
                t = getMeasuredHeight() - height;
                break;
            case TOP:
                l = (getMeasuredWidth() - width) / 2;
                t = 0;
                break;
            case BOTTOM:
                l = (getMeasuredWidth() - width) / 2;
                t = getMeasuredHeight() - height;
                break;
        }
        mCenterView.layout(l, t, l + width, t + height);
    }

    protected void layoutChild(View child, int index, int childCount) {
        int l = 0;
        int t = 0;
        int width = child.getMeasuredWidth();
        int height = child.getMeasuredHeight();
        double rec = 0.5 * Math.PI / (childCount + 1) * (index + 1);
        switch (mPosition) {
            case LEFT_TOP:
                l = (int) (mRadius * Math.cos(rec));
                t = (int) (mRadius * Math.sin(rec));
                break;
            case LEFT_CENTER:
                rec = rec * 2;
                l = (int) (mRadius * Math.sin(rec));
                t = (int) (getMeasuredHeight() / 2 - mRadius * Math.cos(rec) - height / 2);
                break;
            case LEFT_BOTTOM:
                l = (int) (mRadius * Math.sin(rec));
                t = (int) (getMeasuredHeight() - mRadius * Math.cos(rec) - height);
                break;
            case RIGHT_TOP:
                l = (int) (getMeasuredWidth() - width - mRadius * Math.cos(rec));
                t = (int) (mRadius * Math.sin(rec));
                break;
            case RIGHT_CENTER:
                rec = rec * 2;
                l = (int) (getMeasuredWidth() - width - mRadius * Math.sin(rec));
                t = (int) (getMeasuredHeight() / 2 - mRadius * Math.cos(rec) - height / 2);
                break;
            case RIGHT_BOTTOM:
                l = (int) (getMeasuredWidth() - width - mRadius * Math.sin(rec));
                t = (int) (getMeasuredHeight() - mRadius * Math.cos(rec) - height);
                break;
            case TOP:
                rec = rec * 2;
                l = (int) (getMeasuredWidth() / 2 - mRadius * Math.cos(rec) - width / 2);
                t = (int) (mRadius * Math.sin(rec));
                break;
            case BOTTOM:
                rec = rec * 2;
                l = (int) (getMeasuredWidth() / 2 - mRadius * Math.cos(rec) - width / 2);
                t = (int) (getMeasuredHeight() - mRadius * Math.sin(rec) - height);
                break;
        }
        child.layout(l, t, l + width, t + height);
    }

    protected void rotateCenterView(View view, float start, float end, int duration) {
        RotateAnimation animation = new RotateAnimation(
                start, end,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

    protected void toggleMenu(int duration) {
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            final View childView = getChildAt(i + 1);
            childView.setVisibility(VISIBLE);

            int dx = getChildTransferX(i, count - 1);
            int dy = getChildTransferY(i, count - 1);

            AnimationSet animationSet = new AnimationSet(true);
            Animation transAnim = null;
            if (mStatus == Status.CLOSE) {
                transAnim = new TranslateAnimation(dx, 0, dy, 0);
                childView.setClickable(true);
                childView.setFocusable(true);
            } else {
                transAnim = new TranslateAnimation(0, dx, 0, dy);
                childView.setClickable(false);
                childView.setFocusable(false);
            }
            transAnim.setFillAfter(true);
            transAnim.setDuration(duration);
            transAnim.setStartOffset((i * 100) / (count - 1));

            transAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mStatus == Status.CLOSE) {
                        childView.setVisibility(GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            RotateAnimation rotateAnimation = new RotateAnimation(0, 720,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(duration);
            rotateAnimation.setFillAfter(true);

            animationSet.addAnimation(rotateAnimation);
            animationSet.addAnimation(transAnim);
            childView.startAnimation(animationSet);

            final int pos = i + 1;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != menuItemClickListener) {
                        menuItemClickListener.onClick(childView, pos);
                    }
                    menuItemAnim(pos - 1);
                    changeStatus();
                }
            });
        }
        changeStatus();
    }

    private void changeStatus() {
        mStatus = mStatus == Status.CLOSE ? Status.OPEN : Status.CLOSE;
    }

    protected void menuItemAnim(int pos) {
        for (int i = 0; i < getChildCount() - 1; i++) {
            View childView = getChildAt(i + 1);
            if (i == pos) {
                childView.startAnimation(scaleBigAnim(mDuration));
            } else {
                childView.startAnimation(scaleSmallAnim(mDuration));
            }
            childView.setClickable(false);
            childView.setFocusable(false);
        }
    }

    protected Animation scaleSmallAnim(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.0f);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    protected Animation scaleBigAnim(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.0f);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    private int getChildTransferX(int index, int childCount) {
        double rec = 0.5 * Math.PI / (childCount + 1) * (index + 1);
        if (mPosition == Position.BOTTOM
                || mPosition == Position.TOP
                || mPosition == Position.LEFT_CENTER
                || mPosition == Position.RIGHT_CENTER) {
            rec = rec * 2;
        }
        int x = (int) (mRadius * Math.cos(rec));
        if (mPosition == Position.LEFT_CENTER
                || mPosition == Position.LEFT_BOTTOM
                || mPosition == Position.RIGHT_CENTER
                || mPosition == Position.RIGHT_BOTTOM) {
            x = (int) (mRadius * Math.sin(rec));
        }
        int xFlag = 1;
        if (mPosition == Position.LEFT_TOP
                || mPosition == Position.LEFT_CENTER
                || mPosition == Position.LEFT_BOTTOM) {
            xFlag = -1;
        }
        return x * xFlag;
    }

    private int getChildTransferY(int index, int childCount) {
        double rec = 0.5 * Math.PI / (childCount + 1) * (index + 1);
        if (mPosition == Position.BOTTOM
                || mPosition == Position.TOP
                || mPosition == Position.LEFT_CENTER
                || mPosition == Position.RIGHT_CENTER) {
            rec = rec * 2;
        }
        int y = (int) (mRadius * Math.sin(rec));
        if (mPosition == Position.LEFT_CENTER
                || mPosition == Position.LEFT_BOTTOM
                || mPosition == Position.RIGHT_CENTER
                || mPosition == Position.RIGHT_BOTTOM) {
            y = (int) (mRadius * Math.cos(rec));
        }
        int yFlag = 1;
        if (mPosition == Position.LEFT_TOP
                || mPosition == Position.TOP
                || mPosition == Position.RIGHT_TOP) {
            yFlag = -1;
        }
        return y * yFlag;
    }

    @Override
    public void onClick(View view) {
        centerViewOnClicked(view);
    }

    protected void centerViewOnClicked(View view) {
        toggleMenu(mDuration);
        rotateCenterView(view, 0, 360, mDuration);
    }

    private enum Position {
        LEFT_TOP, LEFT_CENTER, LEFT_BOTTOM,
        RIGHT_TOP, RIGHT_CENTER, RIGHT_BOTTOM,
        TOP, BOTTOM
    }

    private enum Status {OPEN, CLOSE}

    public void setMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
        this.menuItemClickListener = menuItemClickListener;
    }

    public interface OnMenuItemClickListener {
        void onClick(View view, int pos);
    }
}
