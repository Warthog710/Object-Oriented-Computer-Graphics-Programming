package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;


public class Body extends Movable
{

    Body(Context context, ScreenInfo screen)
    {
        //set up the image
        mGraphic = BitmapFactory
                .decodeResource(context.getResources(), R.drawable.body);

        mGraphic = Bitmap.createScaledBitmap(mGraphic, screen.getmSegmentSize(), screen.getmSegmentSize(), false);

        //Add the initial location off screen
        mLocation = new Point(-10, -10);
    }

    @Override
    public void move() { //if the individual move is called, print something to the terminal and do nothing
        System.out.println("Body cannot move independently");
    }
}
