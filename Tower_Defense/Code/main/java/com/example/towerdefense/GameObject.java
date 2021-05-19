package com.example.towerdefense;

import android.graphics.Point;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class GameObject
{
    //Class variables
    Point mLocation;
    private int mAttributeSize;
    Bitmap mBitmap;
    private boolean sizeSet=false;

    //Default draw method
    public void draw (Canvas canvas, Paint paint)
    {
        canvas.drawBitmap(mBitmap, mLocation.x-(mBitmap.getWidth()/2), mLocation.y-(mBitmap.getHeight()/2), null);
    }

    //Getters
    public Point getLocation() { return mLocation; }
    public int getAttributeSize(){ return mAttributeSize; }

    public void setAttributeSize(int size)
    {
        if (!sizeSet)
        {
            mAttributeSize = size;
            sizeSet = true;
        }
    }
}
