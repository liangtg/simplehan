package com.liangtg.simplehan.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.WeakHashMap;

import androidx.annotation.Nullable;

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
    private static WeakHashMap<String, Typeface> faceMap = new WeakHashMap<>();
    private static WeakHashMap<Integer, Integer> textSizeMap = new WeakHashMap<>();
    private static WeakHashMap<Integer, Integer> viewSizeMap = new WeakHashMap<>();
    private static WeakHashMap<String, Rect> boundMap = new WeakHashMap<>();
    private final float golden = 0.618f;
    private String text;
    private Paint.FontMetricsInt fontMetrics = new Paint.FontMetricsInt();
    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Rect textBounds = new Rect();
    private Rect lineBounds = new Rect();
    private Integer viewSize;
    private Path hPath = new Path();
    private Path vPath = new Path();
    private Path lPath = new Path();
    private Path rPath = new Path();
    private PointF textPoint = new PointF();

    public HanZiView(Context context) {
        this(context, null);
    }

    public HanZiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HanZiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray array = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.text}, defStyleAttr, 0);
        setText(array.getString(0));
        array.recycle();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        MethodTime m = MethodTime.obtain().tag("setText");
        this.text = text;
        if (TextUtils.isEmpty(text)) {
            this.text = "";
        } else {
            if (text.length() > 1) this.text = this.text.substring(0, 1);
            computeTextBounds();
        }
        invalidate();
        m.end();
    }

    private void computeTextBounds() {
        MethodTime m = MethodTime.obtain().tag("computeTextBounds");
        String key = text + textPaint.getTextSize();
        Rect bounds = boundMap.get(key);
        if (null == bounds) {
            if ("" != text) textPaint.getTextBounds(text, 0, 1, textBounds);
            boundMap.put(key, textBounds);
        } else {
            textBounds = bounds;
        }
        m.end();
    }

    private void init() {
        MethodTime m = MethodTime.obtain().tag("init");
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setDither(true);
        textPaint.setTypeface(getTypeFace("tyzks.ttf"));
        textPaint.setColor(0xFF000000);
        textPaint.setStrokeWidth(2);
        linePaint.setColor(0x88888888);
        linePaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 0.2f, getResources().getDisplayMetrics()));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setPathEffect(new DashPathEffect(new float[]{4, 4}, 0));
        m.end();
    }

    private Typeface getTypeFace(String key) {
        MethodTime m = MethodTime.obtain().tag("getTypeFace");
        Typeface typeface = faceMap.get(key);
        if (null == typeface) {
            typeface = Typeface.createFromAsset(getContext().getAssets(), key);
        }
        faceMap.put(key, typeface);
        m.end();
        return typeface;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        MethodTime m = MethodTime.obtain().tag("onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        if (w > 0) {
            setMeasuredDimension(w, w);
        } else if (h > 0) {
            setMeasuredDimension(h, h);
        }
        m.end();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        MethodTime m = MethodTime.obtain().tag("onLayout");
        super.onLayout(changed, left, top, right, bottom);
        int vsize = Math.min(getWidth(), getHeight());
        viewSize = textSizeMap.get(vsize);
        if (null != viewSize) {
            textPaint.setTextSize(viewSize);
            viewSize = viewSizeMap.get(vsize);
        } else {
            computeTextSize(vsize);
        }
        computeTextBounds();

        int w = (int) (vsize * golden);
        lineBounds.set(0, 0, w, w);
        int dx = (getWidth() - w) / 2;
        lineBounds.offset(dx, dx);

        hPath.reset();
        hPath.moveTo(0, getHeight() / 2);
        hPath.lineTo(getWidth(), getHeight() / 2);
        vPath.reset();
        vPath.moveTo(getWidth() / 2, 0);
        vPath.lineTo(getWidth() / 2, getHeight());
        lPath.reset();
        lPath.moveTo(0, 0);
        lPath.lineTo(getWidth(), getHeight());
        rPath.reset();
        rPath.moveTo(getWidth(), 0);
        rPath.lineTo(0, getHeight());
        textPoint.set(getWidth() / 2, getHeight() / 2);
        m.end();
    }

    private void computeTextSize(int vsize) {
        MethodTime m = MethodTime.obtain().tag("computeTextSize");
        textPaint.setTextSize(vsize);
        textPaint.getFontMetricsInt(fontMetrics);
        int height = fontMetrics.descent - fontMetrics.ascent;
        int size = vsize;
        int target = (int) (vsize * golden);
        while (height > target) {
            size = size - (height - target) / 2 - 1;
            textPaint.setTextSize(size);
            textPaint.getFontMetricsInt(fontMetrics);
            height = fontMetrics.descent - fontMetrics.ascent;
        }
        Integer key = Integer.valueOf(vsize);
        textSizeMap.put(key, size);
        viewSizeMap.put(key, key);
        m.end();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        MethodTime m = MethodTime.obtain().tag("onDraw");
        super.onDraw(canvas);
        canvas.drawPath(hPath, linePaint);
        canvas.drawPath(vPath, linePaint);
        canvas.drawPath(lPath, linePaint);
        canvas.drawPath(rPath, linePaint);
        canvas.drawRect(0, 0, getWidth(), getHeight(), linePaint);
        canvas.drawRect(lineBounds, linePaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() * golden / 2, linePaint);
        if (!"".equals(text)) drawText(canvas);
        m.end();
    }

    private void drawText(Canvas canvas) {
        MethodTime m = MethodTime.obtain().tag("drawText");
        float x = textPoint.x - textBounds.width() / 2 - textBounds.left;
        float y = textPoint.y - textBounds.height() / 2 - textBounds.top;
        canvas.drawText(text, textPoint.x, textPoint.y, textPaint);
        m.end();
    }
}
