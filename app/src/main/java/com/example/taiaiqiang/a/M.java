package com.example.taiaiqiang.a;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class M extends View {
    public float x;
    public float y;

    public M(Context context) {
        super(context);
        x = 0;
        y = 200;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.img);
        canvas.drawBitmap(bitmap, x, y, paint);
        //判断图片是否回收,木有回收的话强制收回图片
        if (bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
}
