package com.example.guswn_000.mengmo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by guswn_000 on 2017-06-08.
 */

public class Mypainter extends View
{
    int oldX,oldY = -1;
    Canvas mcanvas;
    Bitmap mbitmap;
    Paint mpaint = new Paint();

    public Mypainter(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mpaint.setColor(Color.BLACK);
        mpaint.setStrokeWidth(5);
        mpaint.setStrokeJoin(Paint.Join.BEVEL);
        mpaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mbitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        mcanvas = new Canvas();
        //빗맵과 캔버스 연결
        mcanvas.setBitmap(mbitmap);
        mcanvas.drawColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if(mbitmap != null)
        {
            canvas.drawBitmap(mbitmap,0,0,null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int X = (int)event.getX();
        int Y = (int)event.getY();
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            oldX = X; oldY = Y;
        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE)
        {
            if(oldX != -1)
            {
                mcanvas.drawLine(oldX,oldY,X,Y,mpaint);
            }
            invalidate();
            oldX = X; oldY = Y;
        }
        else if (event.getAction() == MotionEvent.ACTION_UP)
        {
            if(oldX != -1)
            {
                mcanvas.drawLine(oldX,oldY,X,Y,mpaint);
            }
            invalidate();
            oldX = -1; oldY = -1;
        }
        return true;
    }

//    public void penRed()
//    {
//        mpaint.setColor(Color.parseColor("e90000"));
//    }
//    public void penOrange()
//    {
//        mpaint.setColor(Color.parseColor("fc801c"));
//    }

    public void changewidth(int width)
    {
        switch (width)
        {
            case 5:
                mpaint.setStrokeWidth(5);
                break;
            case 10:
                mpaint.setStrokeWidth(10);
                break;
            case 20:
                mpaint.setStrokeWidth(20);
                break;
            case 30:
                mpaint.setStrokeWidth(30);
                break;
        }
    }

    public void changecolor(String color)
    {
        switch (color)
        {
            case "RED":
                mpaint.setColor(Color.parseColor("#e90000"));
                break;
            case "ORANGE":
                mpaint.setColor(Color.parseColor("#fc801c"));
                break;
            case "YELLOW":
                mpaint.setColor(Color.parseColor("#fccf1c"));
                break;
            case "GREEN":
                mpaint.setColor(Color.parseColor("#72db1c"));
                break;
            case "BLUE":
                mpaint.setColor(Color.parseColor("#11c7f4"));
                break;
            case "NAVY":
                mpaint.setColor(Color.parseColor("#123ddb"));
                break;
            case "PURPLE":
                mpaint.setColor(Color.parseColor("#a32af8"));
                break;
            case "PINK":
                mpaint.setColor(Color.parseColor("#f980ec"));
                break;
            case "BLACK":
                mpaint.setColor(Color.BLACK);
                break;
            case "WHITE":
                mpaint.setColor(Color.WHITE);
                break;
        }
    }

    public void eraseAll()
    {
        mbitmap.eraseColor(Color.WHITE);
        invalidate();
    }






}
