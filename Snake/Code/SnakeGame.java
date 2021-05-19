package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class SnakeGame extends SurfaceView implements Runnable
{
    // Objects for the game loop/thread
    private Thread mThread = null;

    //Control pausing between updates.
    private long mNextFrameTime;

    //Is the game currently playing and or paused?
    private volatile boolean mPlaying = false;
    private volatile boolean mPaused = true;

    //Keeps track of the player's score.
    private int mScore;

    //Objects for drawing.
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;
    private Context context;

    //Create snake.
    //private Head head;
    private Snake mSnake;
    private ScreenInfo screen;

    //Create apple map.
    private AppleBasket mApples;

    //Create sound manager.
    private GameSound mSound;

    //Class constructor.
    public SnakeGame(Context context, ScreenInfo screen)
    {
        super(context);
        this.screen = screen;
        this.context = context;

        //Initialize the sound, false = play sound, true = silent mode.
        mSound = new GameSound(context, false);

        //Initialize the drawing objects
        mSurfaceHolder = getHolder();
        mPaint = new Paint();

        //Initialize the apple(s) and snake.
        mApples = new AppleBasket(screen, context);
        //head = new Head(context, screen);
        mSnake=new Snake(context, screen);
    }

    //The game loop
    @Override
    public void run()
    {
        while (mPlaying)
        {
            if(!mPaused)
            {
                //Update 10 times a second
                if (updateRequired())
                {
                    update();
                }
            }
            draw();
        }
    }

    //Check to see if it is time for an update
    public boolean updateRequired()
    {
        //Run at 10 frames per second
        final long TARGET_FPS = 10;

        //There are 1000 milliseconds in a second
        final long MILLIS_PER_SECOND = 1000;

        //Are we due to update the frame
        if(mNextFrameTime<= System.currentTimeMillis())
        {
            mNextFrameTime =System.currentTimeMillis()
                    + MILLIS_PER_SECOND / TARGET_FPS;

            //Return true so that the update and draw
            return true;
        }

        //Else, no update is required.
        return false;
    }

    //Called to start a new game
    public void newGame()
    {
        //Reset the snake and apples.
        mApples = new AppleBasket(screen, context);
        mSnake.reset(screen);

        //Reset the mScore
        mScore = 0;

        //Setup mNextFrameTime so an update can triggered
        mNextFrameTime = System.currentTimeMillis();
    }

    //Update all the game objects
    public void update()
    {
        //Move the snake
        mSnake.move();


        //Did the head of the snake eat the Apple?
        Point temp = mSnake.checkApple(mApples, context, screen);
        if (temp != null)
        {
            //The snake hit an apple, add score, remove it, and spawn more.
            mScore += mApples.getPoints(temp);
            mApples.removeApple(temp);
            mSound.playGetApple();
            mApples.spawn(screen, context, mScore);
        }

        // Did the snake die or the score go below zero?
        if (mSnake.detectDeath(screen) || mScore < 0)
        {
            //Pause the game, get ready to play again.
            mSound.playSnakeDeath();
            mPaused =true;
        }
    }

    // Do all the drawing
    public void draw()
    {
        //Get a lock on the mCanvas
        if (mSurfaceHolder.getSurface().isValid())
        {
            mCanvas = mSurfaceHolder.lockCanvas();

            //Fill the screen with a color
            mCanvas.drawColor(screen.getBLUE_COLOR());

            //Set the size and color of the mPaint for the text
            mPaint.setColor(screen.getWHITE_COLOR());
            mPaint.setTextSize(120);

            //Draw the score
            mCanvas.drawText("" + mScore, 20, 120, mPaint);

            //Draw the apple(s) and the snake
            mApples.draw(mCanvas, mPaint, screen);
            mSnake.draw(mCanvas, mPaint, screen);

            //Draw some text while paused
            if(mPaused){
                //Set the size and color of mPaint for the text
                mPaint.setColor(screen.getWHITE_COLOR());
                mPaint.setTextSize(250);

                // Draw the message
                mCanvas.drawText("Tap To Play!", 200, 700, mPaint);
            }

            // Unlock the Canvas to show graphics for this frame
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        switch (motionEvent.getAction() &MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_UP:
                if (mPaused)
                {
                    //Game is paused, interpret press as un-pause.
                    mPaused = false;
                    newGame();
                    return true;
                }

                //Let the OldSnake class handle the input
                mSnake.updateHeading(motionEvent, screen);
                break;

            default:
                break;
        }
        return true;
    }

    //Stop the thread
    public void pause()
    {
        mPlaying = false;
        try
        {
            mThread.join();
        }
        catch (InterruptedException e)
        {
            System.out.println("Error joining thread.");
        }
    }

    // Start the thread
    public void resume()
    {
        mPlaying = true;

        //Start new thread.
        mThread = new Thread(this);
        mThread.start();
    }
}
