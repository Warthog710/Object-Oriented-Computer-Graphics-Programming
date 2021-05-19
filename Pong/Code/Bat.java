package com.example.pongactivity;

import android.graphics.RectF;

//Changed to extend RectF so it inherits such methods as intersects(). The reasoning behind this change was to
//simplify the detectCollision method in PongGame.
class Bat extends RectF
{
    //Member variables for the class.
    private float mXCoord;

    //These constant variables keep track of if the Bat is stopped, moving left, or right.
    final String STOPPED = "stopped";
    final String LEFT = "left";
    final String RIGHT = "right";

    //Start the Bat stopped.
    private String mBatMoving = STOPPED;

    //Bat's Constructor, changed to take a ScreenInfo object instead of an X and Y location.
    Bat(ScreenInfo screen)
    {
        //Configure the starting location of the Bat in the middle of the screen.
        mXCoord = (screen.GetWidth() / 2);

        //Initialize the four points of the Bat.
        left = (screen.GetWidth() / 2);
        top = (screen.GetHeight() - (screen.GetHeight() / 40));
        right = (screen.GetWidth() / 2) + (screen.GetWidth() / 8);
        bottom = (screen.GetHeight() - (screen.GetHeight() / 40)) + (screen.GetHeight() / 40);
    }

    //Update the current state of the Bat. Passed in by the onTouchEvent method of PongGame
    void SetMovementState(String state)
    {
        mBatMoving = state;
    }

    //Update the location of the Bat. ScreenInfo object is passed to eliminate reliance on storing redundant information.
    void Update(long fps, ScreenInfo screen)
    {
        //Move the Bat based on the mBatMoving variable and the speed of the previous frame
        if(mBatMoving == LEFT)
        {
            mXCoord = mXCoord - screen.GetWidth() / fps;
        }
        if(mBatMoving == RIGHT)
        {
            mXCoord = mXCoord + screen.GetWidth() / fps;
        }
        //Stop the Bat going off the screen
        if(mXCoord < 0)
        {
            mXCoord = 0;
        }
        else if(mXCoord + (screen.GetWidth() / 8) > screen.GetWidth())
        {
            mXCoord = screen.GetWidth() - (screen.GetWidth() / 8);
        }

        //Update the location of the Bat based on the state determined.
        left = mXCoord;
        right = mXCoord + (screen.GetWidth() / 8);
    }
}