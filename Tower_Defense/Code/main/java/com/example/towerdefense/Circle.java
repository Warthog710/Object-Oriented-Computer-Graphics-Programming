package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Circle
{
    public Point mLocation;
    private int radius;

    //Set circle color
    private final int LIGHT_GREY = Color.argb(50, 161, 161, 161);

    //Create the circle
    public Circle(Point mLocation, int radius)
    {
        this.mLocation=mLocation;
        this.radius=radius;
    }

    //Draw the circle
    public void draw(Canvas canvas, Paint paint)
    {
        paint.setStrokeWidth(10F);
        paint.setColor(LIGHT_GREY);
        canvas.drawCircle(mLocation.x, mLocation.y, radius, paint);
    }
}
