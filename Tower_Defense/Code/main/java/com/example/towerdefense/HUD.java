package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

/*
Deals with all the buttons and titles for when the game is running
 */

class HUD {
    static int PAUSE = 0;
    static int SPEEDUP = 1;
    static int PlasmaTower = 2;
    static int LaserTower = 3;
    static int RocketTower = 4;
    public InfoContainer infoContainer;
    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;
    private ArrayList<Rect> controls;
    private Bitmap mPauseBitMap, mPlasmaTower, mLaserTower, mRocketTower, mSpeedUpBitMap, mWonSplashScreen, mLostSplashScreen;
    private Bitmap mPausedSplashScreen, mLevel1SplashScreen, mLevel2SplashScreen, mLevel3SplashScreen;
    private int buttonWidth;
    private int buttonHeight;
    private Point size;
    private int mSplashScreenSize = 600;

    HUD(Point size) {
        this.size = size;
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;
        buttonWidth = mScreenWidth / 14;
        buttonHeight = mScreenHeight / 12;
        controls = new ArrayList<Rect>();
        prepareControls();
        infoContainer = new InfoContainer(size);
    }

    private void prepareControls() //creates all the buttons
    {
        int buttonPadding = mScreenWidth / 90;
        Rect pause = new Rect( //pause button
                mScreenWidth - buttonPadding - buttonWidth, buttonPadding,
                mScreenWidth - buttonPadding,
                buttonPadding + buttonHeight);
        Rect speedUp = new Rect( //tower button
                mScreenWidth - (buttonPadding * 2) - (buttonWidth * 2),
                buttonPadding,
                mScreenWidth - buttonPadding * 2 - buttonWidth,
                buttonPadding + buttonHeight);
        Rect plasmaTower = new Rect( //tower button
                mScreenWidth - (buttonPadding * 3) - (buttonWidth * 3),
                buttonPadding,
                mScreenWidth - buttonPadding * 3 - buttonWidth * 2,
                buttonPadding + buttonHeight);
        Rect laserTower = new Rect( //tower button
                mScreenWidth - (buttonPadding * 4) - (buttonWidth * 4),
                buttonPadding,
                mScreenWidth - buttonPadding * 4 - buttonWidth * 3,
                buttonPadding + buttonHeight);
        Rect rocketTower = new Rect( //tower button
                mScreenWidth - (buttonPadding * 5) - (buttonWidth * 5),
                buttonPadding,
                mScreenWidth - buttonPadding * 5 - buttonWidth * 4,
                buttonPadding + buttonHeight);

        controls.add(PAUSE, pause);
        controls.add(SPEEDUP, speedUp);
        controls.add(PlasmaTower, plasmaTower);
        controls.add(LaserTower, laserTower);
        controls.add(RocketTower, rocketTower);

    }

    void draw(Canvas canvas, Paint paint, GameWorld mGameWorld, long FPS) //draw the HUD
    {
        paint.setColor(GameWorld.black);
        paint.setTextSize(mTextFormatting);

        canvas.drawText("Lives: " + mGameWorld.getLives(), //draw lives
                mTextFormatting, mTextFormatting * 2, paint);
        canvas.drawText("Cash: " + mGameWorld.getCash(), //draw cash
                mTextFormatting * 6, mTextFormatting * 2, paint);

        int currentWave = mGameWorld.getSpawner().getCurrentWave();
        int waveCount = mGameWorld.getSpawner().getWaveCount();

        //Prevents zero display
        if (currentWave + 1 > waveCount)
            currentWave--;

        canvas.drawText("Wave: " + (currentWave + 1) + "/" + waveCount,
                mTextFormatting * 12, mTextFormatting * 2, paint);

        canvas.drawText("FPS: " + FPS, //draw FPS
                mTextFormatting * 18, mTextFormatting * 2, paint);
        paint.setColor(GameWorld.red);
        paint.setTextSize(mTextFormatting * 2);
        if (mGameWorld.errorTime >= System.currentTimeMillis()) {
            canvas.drawText(mGameWorld.errorMessage, //draw FPS
                    (mScreenWidth / 2) - mTextFormatting * 9, (mScreenHeight / 2) - mTextFormatting / 2, paint);
        }

        if (mGameWorld.getGameOver()) //if the game is over
        {
            //If you don't have any lives you must've lost
            if (mGameWorld.getLives() <= 0) {
                canvas.drawBitmap(mLostSplashScreen, (mScreenWidth / 2) - (mSplashScreenSize / 2), (mScreenHeight / 2) - (mSplashScreenSize / 2), null);

            }
            //If gameWon is true you must've won
            else if (mGameWorld.getmGameWon()) {
                canvas.drawBitmap(mWonSplashScreen, (mScreenWidth / 2) - (mSplashScreenSize / 2), (mScreenHeight / 2) - (mSplashScreenSize / 2), null);

            }
            //Must be a new game...
            else {
                switch (mGameWorld.mMap.getCurrentLevel()) {
                    case 1:
                        canvas.drawBitmap(mLevel1SplashScreen, (mScreenWidth / 2) - (mSplashScreenSize / 2), (mScreenHeight / 2) - (mSplashScreenSize / 2), null);
                        break;
                    case 2:
                        canvas.drawBitmap(mLevel2SplashScreen, (mScreenWidth / 2) - (mSplashScreenSize / 2), (mScreenHeight / 2) - (mSplashScreenSize / 2), null);
                        break;
                    case 3:
                        canvas.drawBitmap(mLevel3SplashScreen, (mScreenWidth / 2) - (mSplashScreenSize / 2), (mScreenHeight / 2) - (mSplashScreenSize / 2), null);
                        break;

                }
            }
        }

        if (mGameWorld.getPaused() && !mGameWorld.getGameOver()) //if the game is paused
        {
            canvas.drawBitmap(mPausedSplashScreen, (mScreenWidth / 2) - (mSplashScreenSize / 2), (mScreenHeight / 2) - (mSplashScreenSize / 2), null);

        }

        drawControls(canvas, paint); //draw the buttons
        drawGraphics(canvas); //draw the graphics over the buttons
        infoContainer.draw(canvas, paint);
    }

    private void drawControls(Canvas c, Paint p) {
        p.setColor(GameWorld.black); //set color to black
        for (Rect r : controls) {
            c.drawRect(r.left, r.top, r.right, r.bottom, p);
        }

        // Set the color to white
        p.setColor(GameWorld.white);
    }

    ArrayList<Rect> getControls() {
        return controls;
    }

    public void setGraphics(Context context) //create all the graphics
    {
        HelperFactory helperFactory = new HelperFactory(context);
        this.mPauseBitMap = helperFactory.bitmapMaker(R.drawable.play, buttonWidth, buttonHeight);
        this.mSpeedUpBitMap = helperFactory.bitmapMaker(R.drawable.speed_up, buttonWidth, buttonHeight);
        this.mPlasmaTower = helperFactory.bitmapMaker(R.drawable.plasma_turret, buttonWidth, buttonHeight);
        this.mLaserTower = helperFactory.bitmapMaker(R.drawable.laser_turret, buttonWidth, buttonHeight);
        this.mRocketTower = helperFactory.bitmapMaker(R.drawable.rocket_turret, buttonWidth, buttonHeight);
        this.mLevel1SplashScreen = helperFactory.bitmapMaker(R.drawable.level1_splash_screen, mSplashScreenSize, mSplashScreenSize);
        this.mLevel2SplashScreen = helperFactory.bitmapMaker(R.drawable.level2_splash_screen, mSplashScreenSize, mSplashScreenSize);
        this.mLevel3SplashScreen = helperFactory.bitmapMaker(R.drawable.level3_splash_screen, mSplashScreenSize, mSplashScreenSize);
        this.mWonSplashScreen = helperFactory.bitmapMaker(R.drawable.won_splash_screen, mSplashScreenSize, mSplashScreenSize);
        this.mLostSplashScreen = helperFactory.bitmapMaker(R.drawable.lost_splash_screen, mSplashScreenSize, mSplashScreenSize);
        this.mPausedSplashScreen = helperFactory.bitmapMaker(R.drawable.paused_splash_screen, mSplashScreenSize, mSplashScreenSize);
    }

    private void drawGraphics(Canvas canvas) //draw the graphics for the buttons
    {
        canvas.drawBitmap(mPauseBitMap, controls.get(PAUSE).left, controls.get(PAUSE).top, null);
        canvas.drawBitmap(mSpeedUpBitMap, controls.get(SPEEDUP).left, controls.get(SPEEDUP).top, null);
        canvas.drawBitmap(mPlasmaTower, controls.get(PlasmaTower).left, controls.get(PlasmaTower).top, null);
        canvas.drawBitmap(mLaserTower, controls.get(LaserTower).left, controls.get(LaserTower).top, null);
        canvas.drawBitmap(mRocketTower, controls.get(RocketTower).left, controls.get(RocketTower).top, null);
    }


    public boolean onButton(Point point) { //return true if the point is on a button
        boolean collision = false;
        for (Rect r : controls) {
            if (r.contains(point.x, point.y))
                collision = true;
        }
        return collision;
    }
}