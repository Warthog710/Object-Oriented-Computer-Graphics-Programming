package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameEngine extends SurfaceView implements Runnable, GameEngineBroadcaster
{
    //Class Variables
    private GameView mGameView;
    private GameWorld mGameWorld;
    private HUD mHUD;
    private Context context;
    private Thread mThread = null;
    private long mNextFrameTime, mLastFrameTime, mNextDraw, mFPS;
    private ArrayList<InputObserver> inputObservers = new ArrayList();
    UIController mUIController;
    final long MILLIS_PER_SECOND = 1000;

    enum dmgType {plasma, laser, rocket}

    //Class constructor
    public GameEngine(Context context, Point size)
    {
        super(context);
        mNextFrameTime=mNextDraw=System.currentTimeMillis();
        this.context = context;
        mGameWorld = new GameWorld(this.context, size);
        mGameView = new GameView(this, this.context, size);
        mHUD= new HUD(size);
        mHUD.setGraphics(context);
        mUIController = new UIController(this, context);
    }

    @Override
    public void run()
    {
        while (mGameWorld.getThreadRunning())
        {
            if (updateRequired() && !mGameWorld.getPaused())
            {
                //Called to update all gameObjects
                update();

                //If the game is running
                if (mGameWorld.getGameRunning())
                {
                    //Check to see if the last wave has occurred. If so, end the game.
                    if (mGameWorld.checkWave())
                    {
                        mGameWorld.endGame();
                    }
                }
            }

            if(mNextDraw <= System.currentTimeMillis())
            {
                //Measure FPS
                long time = System.currentTimeMillis() - mLastFrameTime;

                if(time >= 1)
                {
                    mFPS = MILLIS_PER_SECOND / (time);
                    mLastFrameTime=System.currentTimeMillis();
                }

                mNextDraw =System.currentTimeMillis()
                        + MILLIS_PER_SECOND / GameWorld.FPS;

                //Draw all game objects
                mGameView.draw(mGameWorld, mHUD, mFPS);
            }
        }
    }

    //Updates all the game objects in play
    private void update()
    {
        //Check if game is paused. If not, execute the following...
        if(!mGameWorld.getPaused())
        {
            //Update projectiles
            mGameWorld.updateProjectiles();

            //Update aliens
            mGameWorld.updateAliens();

            //Fire the towers
            mGameWorld.fireTowers();

            //Spawn more enemies if needed.
            mGameWorld.spawn();
        }
    }

    //Method is used to check when an update is required to the GameView
    public boolean updateRequired()
    {
        //Are we due to update the frame?
        if(mNextFrameTime<= System.currentTimeMillis())
        {
            mNextFrameTime =System.currentTimeMillis()
                    + MILLIS_PER_SECOND / (GameWorld.BASE_TICKS_PER_SECOND*mGameWorld.getSpeed());

            //Return true - update required
            return true;
        }

        //Else, no update is required.
        return false;
    }

    //Handles player input
    @Override
    public boolean onTouchEvent (MotionEvent motionEvent)
    {
        for (InputObserver o : inputObservers)
        {
            o.handleInput(motionEvent, mGameWorld, mHUD);
        }

        return true;
    }

    public void stopThread()
    {
        mGameWorld.stopEverything();

        try
        {
            mThread.join();
        }
        catch (InterruptedException e)
        {
            Log.e("Exception", "stopThread()" + e.getMessage());
        }
    }

    public void startThread()
    {
        mGameWorld.startThread();
        mNextFrameTime = System.currentTimeMillis();

        mThread = new Thread(this);
        mThread.start();
    }

    public void addObserver(InputObserver o)
    {
        inputObservers.add(o);
    }
}
