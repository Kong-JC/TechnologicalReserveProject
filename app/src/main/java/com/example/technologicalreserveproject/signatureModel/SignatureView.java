package com.example.technologicalreserveproject.signatureModel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SignatureView extends View {

    Paint paint;
    Path path;
    List<Stroke> strokeList;
    List<Path> pathList;

    private boolean isSignature;

    public SignatureView(Context context) {
        super(context);
        initView();
    }

    public SignatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView() {
        setBackgroundColor(Color.WHITE);
        strokeList = new ArrayList<>();
        pathList = new ArrayList<>();

        paint = new Paint();
        paint.setColor(Color.BLACK);
//        paint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, STROKE_WIDTH, getResources().getDisplayMetrics()));
        paint.setStyle(Paint.Style.STROKE);//设置画笔空心
        paint.setAntiAlias(true);//消除锯齿
        path = new Path();
    }

    private Canvas canvas;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        if (path != null) canvas.drawPath(path, paint);
        for (Path p : pathList) canvas.drawPath(p, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (isSignature) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    canvas.save();
                    path.moveTo(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    path.lineTo(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    pathList.add(path);
                    Log.d("SignatureView", " -=-=- onTouchEvent: pathList:" + pathList);
                    path = null;
                    path = new Path();
                    break;
            }
        } else {

        }
//        path.reset();
        invalidate();
        return true;
    }

    class Stroke {
        public float x;
        public float y;

        public Stroke(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public void pathClose() {
//        path.close();
        pathList.clear();
        invalidate();
    }

    public void changeModel(boolean isSignature) {
        this.isSignature = isSignature;
    }

}
