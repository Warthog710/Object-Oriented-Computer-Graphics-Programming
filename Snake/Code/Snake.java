package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.ListIterator;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Snake extends Movable
{
    //a list of body parts
    private ArrayList<Movable> mBody;

    //the head
    private Movable mHead;

    //whether the snake has a body of less than 0
    private Boolean mStarved;

    Snake(Context context, ScreenInfo screen)
    {
        mBody=new ArrayList();
        mHead= new Head(context, screen);
        mStarved =false;
    }

    //move the head and the rest of the body
    public void move()
    {
        ListIterator<Movable> iterator = mBody.listIterator();
        Point lastLocation = new Point(mHead.mLocation.x, mHead.mLocation.y);
        mHead.move();

        while (iterator.hasNext())
        {
            Movable temp = iterator.next();
            Point temp2 = new Point(temp.mLocation.x, temp.mLocation.y);
            temp.follow(lastLocation);
            lastLocation = temp2;
        }
    }

    //draw the whole snake
    public void draw(Canvas mCanvas, Paint mPaint, ScreenInfo screen)
    {
        mHead.draw(mCanvas, mPaint, screen);
        ListIterator<Movable> iterator = mBody.listIterator();

        while (iterator.hasNext())
        {
            iterator.next().draw(mCanvas, mPaint, screen);
        }
    }

    //reset the snake
    public void reset(ScreenInfo screen)
    {
        mBody.clear();
        mStarved =false;
        mHead.mLocation=(new Point((screen.getmNumberOfBlocksWide() / 2), (screen.getmNumberOfBlocksHigh() / 2)));

    }

    //add a body part
    public void addBody(Context context, ScreenInfo screen)
    {
        mBody.add(new Body(context, screen));
    }

    //lose a body part
    public void loseBody()
    {
        if(mBody.size()==0)
            mStarved =true;

        else
            mBody.remove(mBody.size() - 1);
    }


    //check if the snake ate an apple
    public Point checkApple(AppleBasket appleMap, Context context, ScreenInfo screen) {

        //If the apple map contains the current location as a key an apple must be there.
        if (appleMap.getAppleMap().containsKey(mHead.mLocation))
        {
            //If a badApple, remove a body segment.
            if (appleMap.getPoints(mHead.mLocation) < 0 )
            {
                for (int subtractTail = 0; subtractTail > appleMap.getPoints(mHead.mLocation); subtractTail--)
                    loseBody();
            }

            //Add body parts equal to the point value of the apple.
            for(int addTail = 0; addTail < appleMap.getPoints(mHead.mLocation); addTail++)
                addBody(context, screen);

            return mHead.mLocation;
        }
        //If no apple is found at the snakes location, return null.
        return null;
    }


    //check if the snake died
    public boolean detectDeath(ScreenInfo screen)
    {
        if (leftScreen(screen) || ateSelf() || mStarved)
            return TRUE;

        return FALSE;
    }

    //check if the snake left the screen
    private boolean leftScreen(ScreenInfo screen)
    {
        return mHead.mLocation.x > screen.getmNumberOfBlocksWide() || mHead.mLocation.x < 0
                || mHead.mLocation.y > screen.getmNumberOfBlocksHigh() || mHead.mLocation.y < 0;
    }

    //check if the snake ate it's self
    private boolean ateSelf()
    {
        boolean tailEaten = FALSE;
        ListIterator<Movable> iterator = mBody.listIterator();

        while (iterator.hasNext())
        {
            if (mHead.mLocation.equals(iterator.next().mLocation))
                tailEaten = TRUE;
        }
        return tailEaten;
    }

    //tell the head to update itself
    public void updateHeading(MotionEvent motionEvent,ScreenInfo screen)
    {
        ((Head) mHead).updateHeading(motionEvent, screen);
    }
}
