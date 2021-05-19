package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.view.MotionEvent;
import java.util.ArrayList;

public class Head extends Movable
{

    private enum Heading
    {
        UP, RIGHT, DOWN, LEFT
    }

    //start the heading to the right
    private Heading heading = Heading.RIGHT;

    Head(Context context, ScreenInfo screen)
    {
        //get the head asset
        mGraphic = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.head);
        //scale it
        mGraphic = Bitmap.createScaledBitmap(mGraphic, screen.getmSegmentSize(), screen.getmSegmentSize(), false);

        //add the initial location off screen
        mLocation = new Point((screen.getmNumberOfBlocksWide() / 2), (screen.getmNumberOfBlocksHigh() / 2));

    }

    //update the direction the head is facing.
    public void updateHeading(MotionEvent motionEvent, ScreenInfo screen)
    {
        Matrix matrix = new Matrix();

        //rotate left
        if (motionEvent.getX() < screen.getHalfWayPoint())
        {
            matrix.postRotate(-90);
            rotateHeadLeft();
        }
        //rotate right
        else
        {
            matrix.postRotate(90);
            rotateHeadRight();
        }

        //rotate the head
        mGraphic = Bitmap
                .createBitmap(mGraphic,
                        0, 0, screen.getmSegmentSize(), screen.getmSegmentSize(), matrix, true);
    }


    //change the direction if the head was rotated left
    private void rotateHeadLeft()
    {
        switch (heading)
        {
            case UP:
                heading = Heading.LEFT;
                break;
            case LEFT:
                heading = Heading.DOWN;
                break;
            case DOWN:
                heading = Heading.RIGHT;
                break;
            case RIGHT:
                heading = Heading.UP;
                break;
        }
    }

    //change the direction if the head was rotated right
    private void rotateHeadRight()
    {
        switch (heading)
        {
            // Rotate right
            case UP:
                heading = Heading.RIGHT;
                break;
            case RIGHT:
                heading = Heading.DOWN;
                break;
            case DOWN:
                heading = Heading.LEFT;
                break;
            case LEFT:
                heading = Heading.UP;
                break;
        }
    }

    //move the head on its own
    public void move()
    {
        switch (heading)
        {
            case UP:
                mLocation.y--;
                break;
            case RIGHT:
                mLocation.x++;
                break;
            case DOWN:
                mLocation.y++;
                break;
            case LEFT:
                mLocation.x--;
                break;
        }
    }
}
