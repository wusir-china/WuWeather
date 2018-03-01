package com.wusir.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zy on 2018/2/9.
 */

public class MultiEleProgress extends View {
    private Paint paint;
    private float strokeWidth=20f;
    public MultiEleProgress(Context context) {
        super(context);
    }

    public MultiEleProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiEleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint=new Paint();
        paint.setStrokeWidth(20f);

    }
}
