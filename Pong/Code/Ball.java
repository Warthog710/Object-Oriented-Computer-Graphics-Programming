package com.example.pongactivity;

import android.graphics.RectF;

//Changed to extend RectF so it inherits such methods as intersects(). The reasoning behind this change was to
//simplify the detectCollision method in PongGame.
class Ball extends RectF {

    //Member variables for the class.
    private float mXVelocity;
    private float mYVelocity;
    private float mBallWidth;
    private float mBallHeight;

    //Constructor takes a ScreenInfo object and sets the balls width and height.
    Ball(ScreenInfo screen)
    {
        // Make the Ball square and 1% of screen width.
        mBallWidth = screen.GetWidth() / 100;
        mBallHeight = screen.GetWidth() / 100;
    }

    //Called to Update the balls location. Called each frame/loop.
    void Update(long fps)
    {

        //The Ball is moved based on the set horizonal (mXVelocity) and veritcal (mYVelocity)
        //speed dividing by the current fps.
        left += (mXVelocity / fps);
        top += (mYVelocity / fps);

        // Match up the bottom right corner based on the size of the Ball
        right = left + mBallWidth;
        bottom = top + mBallHeight;
    }

    //Reverse the vertical direction of travel
    void ReverseYVelocity()
    {
        mYVelocity = -mYVelocity;
    }

    //Reverse the horizontal direction of travel
    void ReverseXVelocity()
    {
        mXVelocity = -mXVelocity;
    }

    //This method was changed to accept a ScreenInfo object.
    void Reset(ScreenInfo screen)
    {
        //Set the four initial points of the Ball.
        left = screen.GetWidth() / 2;
        top = 0;
        right = screen.GetWidth() / 2 + mBallWidth;
        bottom = mBallHeight;

        //Set the current velocity (both X and Y) of the Ball.
        mYVelocity = -(screen.GetWidth() / 3);
        mXVelocity = (screen.GetWidth() / 3);
    }

    //This method is called to increase the speed of the Ball.
    //Each call is an increase of 10%.
    void IncreaseVelocity()
    {
        mXVelocity = mXVelocity * 1.1f;
        mYVelocity = mYVelocity * 1.1f;
    }


    //Bounces the Ball back when it is determined that Bat and Ball have collided.
    //A Bat object is treated as a RectF as only its coordinates are required.
    void BatBounce(RectF batPosition)
    {
        //Detecting the center of the Bat.
        float batCenter = batPosition.left + (batPosition.width() / 2);

        //Detecting the center of the Ball.
        float ballCenter = left + (mBallWidth / 2);

        //Locating Ball hit location on Bat.
        float relativeIntersect = (batCenter - ballCenter);

        //Selecting a bounce direction.
        if(relativeIntersect < 0)
        {
            //Bouncing right.
            mXVelocity = Math.abs(mXVelocity);

        }
        else
        {
            //Bouncing left.
            mXVelocity = -Math.abs(mXVelocity);
        }

        //Reverse the Ball's direction so that it does not continue through the Bat
        //and instead heads back up.
        ReverseYVelocity();
    }
}