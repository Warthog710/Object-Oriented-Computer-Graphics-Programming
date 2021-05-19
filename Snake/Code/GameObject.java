package com.example.snake;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class GameObject
{
    //Location of the object.
    Point mLocation;

    //The object sprite
    Bitmap mGraphic;

    //Default draw method.
    void draw(Canvas canvas, Paint paint, ScreenInfo screen)
    {
        canvas.drawBitmap(mGraphic,
                mLocation.x
                        * screen.getmSegmentSize(),
                mLocation.y
                        * screen.getmSegmentSize(), paint);
    }
}
