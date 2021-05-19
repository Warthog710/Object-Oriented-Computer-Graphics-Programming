package com.example.towerdefense;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Iterator;

class GameView
{
    private Context context;
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;
    private Point size;
    private BitMapContainer mBitmaps;

    GameView(SurfaceView surfaceView, Context context, Point size)
    {
        this.context = context;
        mSurfaceHolder = surfaceView.getHolder();
        mPaint = new Paint();
        this.size = size;
        mBitmaps = new BitMapContainer(context, size);
    }

    void draw(GameWorld gameWorld, HUD mHUD, long FPS)
    {
        if(mSurfaceHolder.getSurface().isValid())
        {
            mCanvas = mSurfaceHolder.lockCanvas();

            if (gameWorld.getGameRunning())
            {

                //Draw game background
                gameWorld.mMap.draw(mCanvas, mPaint, mBitmaps);

                //If currently drawing...
                if (gameWorld.getDrawing())
                {

                    //Draw projectiles
                    if (gameWorld.mProjectiles != null)
                    {
                        Iterator<Projectile> projectileIterator = gameWorld.mProjectiles.iterator();
                        while (projectileIterator.hasNext())
                        {
                            projectileIterator.next().draw(mCanvas, mPaint);
                        }
                    }

                    //Draw towers
                    if (gameWorld.mTowers != null)
                    {
                        for (int count = 0; count < gameWorld.mTowers.size(); count++)
                        {
                            gameWorld.mTowers.get(count).draw(mCanvas, mPaint);
                        }
                    }

                    //Draw aliens
                    if (gameWorld.mAliens != null)
                    {
                        Iterator<Alien> alienIterator = gameWorld.mAliens.iterator();

                        while (alienIterator.hasNext())
                        {
                            (alienIterator.next()).draw(mCanvas, mPaint, mBitmaps, gameWorld.getPaused());
                        }
                    }
                }

                //Draw HUD
                mHUD.draw(mCanvas, mPaint, gameWorld, FPS);

            }

            else if (!gameWorld.getGameRunning())
            {
                gameWorld.startScreen.draw(mCanvas, mPaint, gameWorld.mSound.getSound());
            }

            //Unlock and post
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}
