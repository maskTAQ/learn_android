package com.example.taiaiqiang.a;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.graphics.Path;

public class RoundImageView extends AppCompatImageView {
    private Bitmap bitmap;
    private PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();
    private Rect rect = new Rect();
    private Paint paint = new Paint();
        public  RoundImageView(Context context, AttributeSet attrs){
            super(context,attrs);
            init();
        }
        public  void  init(){
            //设置画笔样式 仅描边
            paint.setStyle(Paint.Style.STROKE);
            //抗锯齿标志
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            //抗锯齿
            paint.setAntiAlias(true);
        }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bitmap == null){
            return;
        }
        int imageWidth = getWidth();
        int imageHeight = getHeight();
        rect.set(0,0,imageWidth,imageHeight);
        // 从canvas层面去除绘制时锯齿
        canvas.setDrawFilter(pdf);

        path.addCircle(imageWidth/2,imageWidth/2,imageHeight/2,Path.Direction.CCW);
        canvas.clipPath(path, Region.Op.REPLACE);
        canvas.drawBitmap(bitmap,null,rect,paint);
        canvas.restore();
    }
}
