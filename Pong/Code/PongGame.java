package com.example.pongactivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


class PongGame extends SurfaceView implements Runnable
{
    //Creating necessary drawing objects.
    private SurfaceHolder mOurHolder;
    private Canvas mCanvas;
    private Paint mPaint;
    /*
    Change the blue and white color to variables.
    This allows us to know exactly color we are using when we pass it to the canvas.
    Also it allows us to reuse these colors in the future.
     */
    private final int blueColor = Color.argb(255, 26, 128, 182);
    private final int whiteColor = Color.argb(255, 255, 255, 255);


    //This variable will be used to track the current FPS.
    private long mFPS;

    //Constant int, used to perform FPS calculations.
    private final int MILLIS_IN_SECOND = 1000;

    //Creating the game objects.
    private Bat mBat;
    private Ball mBall;

    //These variables hold the current number of lives and the current score.
    private int mScore;
    private int mLives;

    //This object holds the screen size and font size/margin information.
    private ScreenInfo screen;

    //Creating the Thread and two control variables
    private Thread mGameThread = null;

    //These volatile variables can be accessed from inside and outside the thread.
    private volatile boolean mPlaying;
    private boolean mPaused = true;

    //manages all the audio for game
    private AudioManager audio;

    //PongGame constructor. Called only from PongActivity
    public PongGame(Context context, ScreenInfo screen)
    {
        //Calling the parent constructor.
        super(context);

        //Copying the passed screenObject into this class's instance.
        this.screen = screen;

        //Initialize the objects to get ready for drawing.
        //Note, getHolder is a method of SurfaceView.
        mOurHolder = getHolder();
        mPaint = new Paint();

        //Initialize the Bat and Ball.
        //Note, previously screen coordinates were sent. Instead a screen object is now passed which
        //each object uses to set the sizes and locations of their items.
        mBall = new Ball(screen);
        mBat = new Bat(screen);

        audio= new AudioManager(context);

        //Start the game!
        StartNewGame();
    }

    //This method is called when the player is starting their first game or has just lost.
    private void StartNewGame()
    {
        //Put the Ball back to the starting position
        mBall.Reset(screen);

        //Reset the score and the player's chances
        mScore = 0;
        mLives = 3;
    }

    //When we start the thread with: mGameThread.start(); the run method is continuously
    //called by Android because we implemented the Runnable interface
    //Calling mGameThread.join(); will stop the thread.
    @Override
    public void run()
    {
        //While playing the game, this loop executes.
        while (mPlaying)
        {
            //Record current time at loop start.
            long frameStartTime = System.currentTimeMillis();

            //This only executes if the game is NOT paused.
            if(!mPaused)
            {
                //Call the Update method.
                Update();

                //Check for any new collisions.
                DetectCollisions();
            }

            //Draw the scene.
            draw();

            //Calculate and store the length of time needed for this loop.
            long timeThisFrame = System.currentTimeMillis() - frameStartTime;

            //Making sure that the number is greater than zero to avoid a divide by zero error.
            if (timeThisFrame > 0)
            {
                //Store the number as FPS in mFPS.
                mFPS = MILLIS_IN_SECOND / timeThisFrame;
            }
        }
    }

    //This method is called to Update the Bat and Ball game objects.
    private void Update()
    {
        //Update the Bat and the Ball by calling each objects Update method.
        mBall.Update(mFPS);

        //Note, changed to accept a ScreenInfo object as screen cords are required since
        //they are no longer saved in the class itself.
        mBat.Update(mFPS, screen);
    }

    private void DetectCollisions()
    {
        //Detect if the Bat has hit the Ball.
        //Since both Bat and Ball are children of RectF. Just call the intersects() method they inherited.
        if(mBat.intersects(mBat, mBall))
        {
            //Bounce the Ball.
            mBall.BatBounce(mBat);

            //Increase the velocity of the Ball to make the game harder.
            mBall.IncreaseVelocity();

            //Increment the player's score.
            mScore++;

            //Play the associated sound.
            audio.PlayBeep();
        }

        //Detect if the Ball has hit the bottom of the screen.
        if(mBall.bottom > screen.GetHeight())
        {
            //The Ball has hit the bottom of the screen and missed the Bat.
            mBall.ReverseYVelocity();
            mLives--;
            audio.PlayMiss();

            //If the number of lives is zero, Pause the game and start a new one.
            if(mLives == 0)
            {
                mPaused = true;
                StartNewGame();
            }
        }

        //Detect if the Ball has hit the top of the screen.
        if(mBall.top < 0)
        {
            //The Ball has hit the top, bounce it back.
            mBall.ReverseYVelocity();
            audio.PlayBoop();
        }

        //Detect if the Ball has hit the left or right of the screen.
        if(mBall.left < 0 || mBall.right > screen.GetWidth())
        {
            //The Ball has hit the left, bounce in back.
            mBall.ReverseXVelocity();
            audio.PlayBop();
        }

    }

    //This method is called when the player minimizes or quits the game.
    public void Pause()
    {
        //Set mPlaying to false, this stops the run
        // method loop.
        mPlaying = false;

        //Attempt to stop the thread.
        try
        {
            mGameThread.join();
        }
        //Catch and log any errors.
        catch (InterruptedException e)
        {
            Log.e("Error:", "joining thread");
        }
    }

    //Called when the player begins to play.
    public void Resume()
    {
        //Set mPlaying to true to represent that the player is playing.
        mPlaying = true;

        //Initialize the instance of Thread and start it.
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    //Called to draw the game objects and HUD.
    private void draw()
    {
        if (mOurHolder.getSurface().isValid())
        {
            //Lock the canvas, ready to draw.
            mCanvas = mOurHolder.lockCanvas();

            //Set background color.
            mCanvas.drawColor(blueColor);

            //Set color to paint with.
            mPaint.setColor(whiteColor);

            //Draw the Bat and Ball
            mCanvas.drawRect(mBall, mPaint);
            mCanvas.drawRect(mBat, mPaint);

            //Choose the font size
            mPaint.setTextSize(screen.GetFontSize());
            //Draw the HUD
            mCanvas.drawText("Score: " + mScore + " Lives: " + mLives, screen.GetFontMargin() , screen.GetFontSize(), mPaint);

            //add names to the upper right hand side of the screen
            mCanvas.drawText("Logan Hollmer", 2*screen.GetWidth()/3, screen.GetHeight()/20, mPaint);
            mCanvas.drawText("Quinn Roemer", 2*screen.GetWidth()/3, (3)*screen.GetHeight()/20, mPaint);

            //We have no way of changing debugging, thus it is always true
            //No need to check for debugging, just always show the FPS
            //Show FPS
            mPaint.setTextSize(screen.GetDebugSize()); //set FPS text sizw
            mCanvas.drawText("FPS: " + mFPS , 10, screen.GetDebugLocation() + screen.GetDebugSize(), mPaint); //display FPS

            // Display the drawing on screen by unlocking and posting.
            mOurHolder.unlockCanvasAndPost(mCanvas);
        }
    }


    //This method handles all the screen touches.
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        //Switch throught the actions.
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK)
        {
            //The player has put the finger on the screen.
            case MotionEvent.ACTION_DOWN:
                //Unpause the game.
                mPaused = false;

                //Determine if the touch happened on the left or right hand side of the screen.
                if(motionEvent.getX() > screen.GetWidth() / 2)
                {
                    //On the right, set Bat to move right.
                    mBat.SetMovementState(mBat.RIGHT);
                }
                else
                {
                    //On the left, set Bat to move left.
                    mBat.SetMovementState(mBat.LEFT);
                }
                break;

             //The player has no fingers on the screen.
            case MotionEvent.ACTION_UP:
                //Set the Bat to stop.
                mBat.SetMovementState(mBat.STOPPED);
                break;
        }
        return true;
    }
}
