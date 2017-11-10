package com.example.huoapp.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by tinle on 2017/11/10.
 */

@SuppressLint("AppCompatCustomView")
public class ClipZoomImageView extends ImageView implements
        ScaleGestureDetector.OnScaleGestureListener,
        View.OnClickListener,
        ViewTreeObserver.OnGlobalLayoutListener{
    private static final String TAG = ClipZoomImageView.class.getSimpleName();
    private final float SCALE_MAX = 4.0f;
    private final float SCALE_MID = 2.0f;

    /**缩放的手势检测*/
    private ScaleGestureDetector mScaleGestureDetector = null;
    private final Matrix mScaleMatrix = new Matrix();

    /**用于双击检测*/
    private GestureDetector mGestureDetector;
    private boolean isAutoScale;


    public ClipZoomImageView(Context context) {
        this(context, null);
    }

    public ClipZoomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClipZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setScaleType(ScaleType.MATRIX);

    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onGlobalLayout() {

    }

    /**
     * 获得当前的缩放比例
     * @return
     */
    public final float getScale(){
        return 0;
    }
}
