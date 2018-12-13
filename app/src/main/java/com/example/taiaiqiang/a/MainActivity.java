package com.example.taiaiqiang.a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout frame = (FrameLayout) findViewById(R.id.myframe);
        final M mzi = new M(MainActivity.this);
        final TextView locationTextView  = (TextView) findViewById(R.id.locationText);
        mzi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Float x = event.getX();
                Float y = event.getY();
                mzi.x = x;
                mzi.y = y;
                mzi.invalidate();

                locationTextView.setText("坐标地址:"+x.toString()+y.toString());
                return true;
            }
        });
        frame.addView(mzi);
    }
}
