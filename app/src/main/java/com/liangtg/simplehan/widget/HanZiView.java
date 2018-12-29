package com.liangtg.simplehan.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @ProjectName: simplehan
 * @ClassName: HanZiView
 * @Description: java类作用描述
 * @Author: liangtg
 * @CreateDate: 18-12-29 下午12:11
 * @UpdateUser: 更新者
 * @UpdateDate: 18-12-29 下午12:11
 * @UpdateRemark: 更新说明
 */
public class HanZiView extends View {
    private String text;
    private Paint.FontMetricsInt fontMetrics = new Paint.FontMetricsInt();
    private TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private Rect bounds = new Rect();

    public HanZiView(Context context) {
        this(context, null);
    }

    public HanZiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HanZiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.text}, defStyleAttr, 0);
        setText(array.getString(0));
        array.recycle();
        init();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        if (null == text) {
            this.text = "";
        }
        if (this.text.length() > 1) this.text = this.text.substring(0, 1);
        invalidate();
    }

    private void init() {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setDither(true);
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "pzhxs1.2.ttf"));
        paint.setColor(0xFF000000);
        paint.setStrokeWidth(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        if (w > 0) {
            setMeasuredDimension(w, w);
        } else if (h > 0) {
            setMeasuredDimension(h, h);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int size = Math.min(getWidth(), getHeight());
        paint.setTextSize(size);
        paint.getFontMetricsInt(fontMetrics);
        int height = fontMetrics.bottom - fontMetrics.top;
        while (height > getHeight()) {
            paint.setTextSize(--size);
            paint.getFontMetricsInt(fontMetrics);
            height = fontMetrics.bottom - fontMetrics.top;
            Log.d("t", String.format("%d\t-\t%d", size, height));
        }
        Log.d("t", String.format("%d\t-\t%d, view height: %d", size, height, getHeight()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine(0, 0, getWidth(), getHeight(), paint);
        canvas.drawLine(getWidth(), 0, 0, getHeight(), paint);
        if (!"".equals(text)) drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        paint.getTextBounds(text, 0, 1, bounds);
        int x = getWidth() / 2 - bounds.width() / 2 - bounds.left;
        int y = getHeight() / 2 - bounds.height() / 2 - bounds.top;
        canvas.drawText(text, x, y, paint);
        paint.setStyle(Paint.Style.STROKE);
        bounds.offsetTo(x + bounds.left, (int) y + bounds.top);
        canvas.drawRect(bounds, paint);
    }
}
