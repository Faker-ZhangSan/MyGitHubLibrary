package com.yongjie.jlibrary.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.yongjie.jlibrary.R;


public class ArcProgress extends View {
    //提示
    private TextPaint hintPaint;
    private int hintColor;
    private float hintSize;
    private String hint;
    //值
    private TextPaint valuePaint;
    private int valueColor;
    private float valueSize;
    private String value;
    //单位
    private TextPaint unitPaint;
    private int unitColor;
    private float unitSize;
    private String unit;
    //最大值、最小值
    private TextPaint minMaxPaint;
    private int minMaxColor;
    private float minMaxSize;
    private String min, max;
    //背景圆弧
    private Paint bgArcPaint;
    private int bgArcColor;
    private float bgArcWidht;
    private float bgArcStartAngle;
    private float bgArcSweepAngle;
    //进度圆弧
    private Paint arcPaint;
    private int arcColor;
    private float arcWidth;
    private float arcStartAngle;
    private float arcSweepAngle;

    private Context context;
    private boolean antiAlias;
    //属性动画
    private ValueAnimator mAnimator;
    private float mPercent;


    public ArcProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        antiAlias = true;
        initAttrs(attrs);
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        //hint
        hintColor = typedArray.getColor(R.styleable.MyView_hintColor, Color.BLACK);
        hintSize = typedArray.getDimension(R.styleable.MyView_hintSize, ViewConstant.DEFAULT_HINT_SIZE);
        hint = typedArray.getString(R.styleable.MyView_hint);
        //value
        valueColor = typedArray.getColor(R.styleable.MyView_valueColor, Color.BLACK);
        valueSize = typedArray.getDimension(R.styleable.MyView_valueSize, ViewConstant.DEFAULT_VALUE_SIZE);
        value = typedArray.getString(R.styleable.MyView_value);
        //unit
        unitColor = typedArray.getColor(R.styleable.MyView_unitColor, Color.BLACK);
        unitSize = typedArray.getDimension(R.styleable.MyView_unitSize, ViewConstant.DEFAULT_UNIT_SIZE);
        unit = typedArray.getString(R.styleable.MyView_unit);
        //min、max
        minMaxColor = typedArray.getColor(R.styleable.MyView_minMaxColor, Color.BLACK);
        minMaxSize = typedArray.getDimension(R.styleable.MyView_minMaxSize, ViewConstant.DEFAULT_MIN_MAX_SIZE);
        min = typedArray.getString(R.styleable.MyView_min);
        max = typedArray.getString(R.styleable.MyView_max);
        //bgArc
        bgArcColor = typedArray.getColor(R.styleable.MyView_bgArcColor, Color.BLACK);
        bgArcWidht = typedArray.getFloat(R.styleable.MyView_bgArcWidth, 15);
        bgArcStartAngle = typedArray.getFloat(R.styleable.MyView_bgArcStartAngle, 135);
        bgArcSweepAngle = typedArray.getFloat(R.styleable.MyView_bgArcSweepAngle, 270);
        //arc
        arcColor = typedArray.getColor(R.styleable.MyView_arcColor, Color.WHITE);
        arcWidth = typedArray.getFloat(R.styleable.MyView_arcWidth, 15);
        arcStartAngle = typedArray.getFloat(R.styleable.MyView_arcStartAngle, 135);
        arcSweepAngle = typedArray.getFloat(R.styleable.MyView_arcSweepAngle, 90);
    }

    private void initPaint() {
        //hint
        hintPaint = new TextPaint();
        hintPaint.setAntiAlias(antiAlias);
        hintPaint.setTextSize(hintSize);
        hintPaint.setColor(hintColor);
        hintPaint.setTextAlign(Paint.Align.CENTER);
        //value
        valuePaint = new TextPaint();
        valuePaint.setAntiAlias(antiAlias);
        valuePaint.setTextSize(valueSize);
        valuePaint.setColor(valueColor);
        valuePaint.setTextAlign(Paint.Align.CENTER);
        //unit
        unitPaint = new TextPaint();
        unitPaint.setAntiAlias(antiAlias);
        unitPaint.setTextSize(unitSize);
        unitPaint.setColor(unitColor);
        unitPaint.setTextAlign(Paint.Align.CENTER);
        //min、max
        minMaxPaint = new TextPaint();
        minMaxPaint.setAntiAlias(antiAlias);
        minMaxPaint.setTextSize(minMaxSize);
        minMaxPaint.setColor(minMaxColor);
        minMaxPaint.setTextAlign(Paint.Align.CENTER);
        //bgArc
        bgArcPaint = new Paint();
        bgArcPaint.setAntiAlias(antiAlias);
        bgArcPaint.setStyle(Paint.Style.STROKE);
        bgArcPaint.setStrokeCap(Paint.Cap.ROUND);
        bgArcPaint.setStrokeWidth(bgArcWidht);
        bgArcPaint.setColor(bgArcColor);
        //arc
        arcPaint = new Paint();
        arcPaint.setAntiAlias(antiAlias);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setStrokeWidth(arcWidth);
        arcPaint.setColor(arcColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF();
        float r = getWidth() / 2;
        rectF.left = arcWidth;
        rectF.top = arcWidth;
        rectF.right = r * 2 - arcWidth;
        rectF.bottom = r * 2 - arcWidth;
        drawArc(rectF, canvas);
        drawText(canvas);
    }


    /**
     * 画弧线
     *
     * @param rectF
     * @param canvas
     */
    private void drawArc(RectF rectF, Canvas canvas) {
        //bgArc
        canvas.drawArc(rectF, bgArcStartAngle, bgArcSweepAngle, false, bgArcPaint);
        //arc
        canvas.drawArc(rectF, arcStartAngle, arcSweepAngle, false, arcPaint);
    }


    private void drawText(Canvas canvas) {
        //最小值，最大值
        float x = getWidth() / 2 - arcWidth;
        float pointX = (float) (x + x * Math.cos(45));
        float pointX2 = (float) (x - x * Math.cos(45));
        float pointY = (float) (x + x * Math.sin(45));
        if (min != null && max != null) {
            canvas.drawText(min, pointX2, pointY, minMaxPaint);
            canvas.drawText(max, pointX, pointY, minMaxPaint);
        }
        //提示
        if (hint != null) {
            float hintY = x - x / 3;
            canvas.drawText(hint, x, hintY, hintPaint);
        }
        //数值
        if (value != null) {
            canvas.drawText(value, x, x, valuePaint);
        }
        //单位
        if (unit != null) {
            float unitY = x + x / 3;
            canvas.drawText(unit, x, unitY, unitPaint);
        }
    }

    private void startAnimator(float start,float end,long time){
        mAnimator=ValueAnimator.ofFloat(start,end);
        mAnimator.setDuration(time);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                mPercent= (float) animation.getAnimatedValue();
//                value=
            }
        });
    }
}
