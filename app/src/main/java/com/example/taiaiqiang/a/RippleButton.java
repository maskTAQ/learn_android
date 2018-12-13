package com.example.taiaiqiang.a;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/*
知识点
1.dispatchDraw 重写绘制方法 https://blog.csdn.net/scorplopan/article/details/6302827
2.postInvalidate 界面刷新http://www.cnblogs.com/newcaoguo/p/6053288.html
3.Log.i 日志输出
4.onSizeChange onTouchEvent 获取视图宽高 处理touch事件
 */


public class RippleButton extends AppCompatButton {
    //每次刷新的时间间隔
    private static final int INVALIDATE_DURATION = 15;
    private int pointX, pointY;                          //控件原点坐标（左上角）
    //背景和水波纹画笔
    private Paint bgPaint, ripplePaint;
    //长按时间
    private static int LONG_PRESS_TIMEOUT;
    //按下的时间
    private long downTime = 0;
    //按下的坐标位置
    private int eventX, eventY;
    //按钮的宽高
    private int buttonWidth, buttonHeight;
    //扩散的最大半径
    private int maxRadio;
    //按钮是否被按下
    private boolean isButtonPress;
    private static int DIFFUSE_GAP = 10;                  //扩散半径增量
    private int shaderRadio;                        //扩散的半径

    public RippleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        System.out.print("pointX");
        LONG_PRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();

    }

    //初始化画笔
    private void initPaint() {
        bgPaint = new Paint();
        ripplePaint = new Paint();

        bgPaint.setColor(getResources().getColor(R.color.rippleButtonBg));
        ripplePaint.setColor(getResources().getColor(R.color.rippleButtonRipple));

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (downTime == 0) {
                    downTime = SystemClock.elapsedRealtime();
                }
                eventX = (int) event.getX();
                eventY = (int) event.getY();
                countMaxRadio();
                isButtonPress = true;
                postInvalidateDelayed(INVALIDATE_DURATION);
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (SystemClock.elapsedRealtime() - downTime < LONG_PRESS_TIMEOUT) {
                    DIFFUSE_GAP = 30;
                    postInvalidate();
                } else {
                    clearData();
                }
                break;

        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!isButtonPress) return;
        Log.i("RippleButton", "pointX:" + pointX);
        //绘制按下后的整个背景
        canvas.drawRect(pointX, pointY, pointX + buttonWidth, pointY + buttonHeight, bgPaint);
        canvas.save();
        //绘制扩散圆形背景
        canvas.clipRect(pointX, pointY, pointX + buttonWidth, pointY + buttonHeight);
        canvas.drawCircle(eventX, eventY, shaderRadio, ripplePaint);
        canvas.restore();

        //直到半径等于最大半径
        if (shaderRadio < maxRadio) {
            postInvalidateDelayed(INVALIDATE_DURATION,
                    pointX, pointY, pointX + buttonWidth, pointY + buttonHeight);
            shaderRadio += DIFFUSE_GAP;
        } else {
            clearData();
        }
    }

    /*
     * 重置数据的方法
     * */
    private void clearData() {
        downTime = 0;
        DIFFUSE_GAP = 10;
        isButtonPress = false;
        shaderRadio = 0;
        postInvalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        buttonWidth = w;
        buttonHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void countMaxRadio() {
        if (buttonWidth > buttonHeight) {
            if (eventX < buttonWidth / 2) {
                maxRadio = buttonWidth - eventX;
            } else {
                maxRadio = buttonWidth / 2 + eventX;
            }
        } else {
            if (eventY < buttonHeight / 2) {
                maxRadio = buttonHeight - eventY;
            } else {
                maxRadio = buttonHeight / 2 + eventY;
            }
        }
    }
}
