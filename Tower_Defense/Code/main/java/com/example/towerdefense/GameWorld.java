package com.example.towerdefense;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class GameWorld
{
    private static volatile boolean mThreadRunning = false;
    private static volatile boolean mPaused = true;
    private static volatile boolean mGameOver = true;
    private static volatile boolean mDrawing = false;
    private static volatile boolean mPlacing = false;
    private static volatile boolean mGameWon = false;
    private static volatile boolean mGameRunning = false;
    private static volatile boolean mReadyForNewGame = false;
    private Tower.TowerType mTowerType;

    static final int white = Color.argb(255,255,255,255);
    static final int black = Color.argb(255,0,0,0);
    static final int red = Color.argb(255,255,0,0);
    static final int grey = Color.argb(255,161,161,161);

    final static long BASE_TICKS_PER_SECOND=40, FPS=30;

    public String errorMessage;
    private final int SPEED_MULTIPLIER=5;

    public long errorTime;

    private boolean fastGame;

    //Record dmg dealt per wave
    private DmgDealt dmgDealt;

    public enum error{MONEY, PLACEMENT}

    //Create enemy spawner
    LevelSpawning enemySpawner;

    //Create sound manager.
    public GameSound mSound;

    //Collection of game objects go here...
     ArrayList<Projectile> mProjectiles;
     ArrayList<Tower> mTowers;
     ArrayList<Alien> mAliens;
     GameMap mMap;
     StartScreen startScreen;



    //Game variables
    private int mLives;
    private int mCash;

    //Class constructor
    GameWorld(Context context, Point size)
    {
        this.startScreen=new StartScreen(context, size);
        fastGame=false;
        mSound = new GameSound(context);
        mTowers = new ArrayList<>();
        mProjectiles = new ArrayList<>();
        mAliens = new ArrayList<>();
        mMap = new GameMap(context, size);

        //Initialize dmg dealt
        dmgDealt = new DmgDealt();
    }

    public void endGame()
    {
        mGameOver = true;
        mPaused = true;
        //mGameRunning = false;

        //If you still have lives, you won!
        if (mLives > 0)
            mGameWon = true;
    }
    public void error(GameWorld.error error){
        errorTime=1000+System.currentTimeMillis();
        switch (error){
            case MONEY:
                errorMessage="Not enough money";
                break;
            case PLACEMENT:
                errorMessage="Cannot place a tower here";
                break;
        }

    }

    void startGame()
    {
        //Don't want to draw while updating game field
        stopDrawing();

        resume(); //unpause the game

        //Start drawing again
        startDrawing();
    }

    //Collection of various getter and setters.
    void pause() { mPaused = true; }
    void resume() { mGameOver = false; mPaused = false; }
    void startThread() { mThreadRunning = true; }
    boolean getThreadRunning() { return mThreadRunning; }
    boolean getDrawing() { return mDrawing; }
    boolean getPaused() {return mPaused; }
    boolean getGameOver() { return mGameOver; }
    private void stopDrawing() { mDrawing = false; }
    private void startDrawing() { mDrawing = true; }
    void setmPlacing() { mPlacing=true;}
    void closemPlacing(){ mPlacing=false;}
    void setTowerType(Tower.TowerType type){mTowerType=type;}
    Tower.TowerType getTowerType(){ return mTowerType;}
    boolean getmPlacing(){ return mPlacing;}
    boolean getmGameWon() { return mGameWon; }
    boolean getGameRunning(){ return mGameRunning;}
    void setGameRunningOff(){
        mGameRunning=false;
    }
    void addTower(Tower tower)
    {
        mTowers.add(tower);
    }
    int getLives() {
        return mLives;
    }
    void setLives(){
        mLives=20;
    }
    int getCash(){ return mCash;}
    void resetCash(){this.mCash=150;}
    void loseCash(int amount){this.mCash-=amount;}
    public LevelSpawning getSpawner() { return this.enemySpawner; }

    void stopEverything()
    {
        mPaused = true;
        mGameOver = true;
        mThreadRunning = false;
    }

    void loseLife()
    {
        mLives -= 1;

        //Check for death
        if (mLives <= 0)
            endGame();
    }

    void changeSpeed()
    {
        if(!fastGame)
            fastGame=true;

        else
            fastGame=false;
    }

    int getSpeed()
    {
        if(fastGame)
            return SPEED_MULTIPLIER;
        else
            return 1;
    }

    private void resetSpeed(){
        fastGame=false;
    }
    private void addCashAmount(int mCash)
    {
        this.mCash+=mCash;
    }

    //Returns the tower that is situated at that point
    public Tower getTower(Point location)
    {
        Tower tower=null;
        if(mTowers != null)
        {
            Iterator<Tower> towerIterator = mTowers.iterator();
            while(towerIterator.hasNext())
            {
                Tower currentTower = towerIterator.next();
                if (currentTower.contains(location))
                {
                    tower=currentTower;
                    break;
                }
            }
        }
        return tower;
    }

    //Returns true if the point is located on a tower.
    public boolean overTower(Point location)
    {
        Tower temp = getTower(location);
        return (temp != null);
    }

    //Load the current level
    public void loadLevel(GameMap.level level)
    {
        mMap.changeLevel(level);
        enemySpawner = new LevelSpawning(level);
        resetCash();
        setLives();
        mAliens = new ArrayList<>();
        mTowers = new ArrayList<>();
        mProjectiles = new ArrayList<>();
        resetSpeed();
        mReadyForNewGame=true;
        mGameRunning=true;
        mGameWon=false;
        mPlacing=false;
    }

    public void setReadyForNewGameFalse(){
        mReadyForNewGame=false;
    }
    public boolean getReadyForNewGame(){
        return mReadyForNewGame;
    }

    public int getCurrentTowerCost()
    {
        int cost=0;

        if(mPlacing && mTowerType==Tower.TowerType.LASER)
            cost = Tower.LASER_COST;

        else if(mPlacing && mTowerType==Tower.TowerType.PLASMA)
            cost = Tower.PLASMA_COST;

        else if(mPlacing && mTowerType==Tower.TowerType.ROCKET)
            cost = Tower.ROCKET_COST;

        return cost;
    }

    public void addDmgDealt(DmgDealt enemyDmg)
    {
        this.dmgDealt.recordDmg(GameEngine.dmgType.plasma, enemyDmg.getPlasmaDmg());
        this.dmgDealt.recordDmg(GameEngine.dmgType.laser, enemyDmg.getLaserDmg());
        this.dmgDealt.recordDmg(GameEngine.dmgType.rocket, enemyDmg.getRocketDmg());
    }

    public void spawn()
    {
        this.enemySpawner.spawn(mAliens, mMap.getPathCords().get(0), dmgDealt);
    }

    public boolean checkWave()
    {
        if (enemySpawner.getCurrentWave() >= enemySpawner.getWaveCount())
            return true;

        return false;
    }

    public void updateAliens()
    {
        Iterator<Alien> alienIterator = mAliens.iterator();

        while (alienIterator.hasNext())
        {
            Alien temp = alienIterator.next();

            //Check for dead aliens
            if (temp.getStatus())
            {
                alienIterator.remove();
                addCashAmount(temp.getMoney());
                addDmgDealt(temp.getDmgDealt());
                mSound.playExplosionSound();
            }

            //Check for aliens that hit the base
            else if (temp.checkCollision(mMap.getBaseCords()))
            {
                //Kill enemy
                temp.kill();
                alienIterator.remove();
                loseLife();
            }

            //Else, move the enemy
            else
                mAliens.get(mAliens.indexOf(temp)).move(mMap.getPathCords());
        }
    }

    public void updateProjectiles()
    {
        ArrayList<Projectile> projectilesToRemove = new ArrayList<>();

        Iterator<Projectile> projectileIterator = mProjectiles.iterator();

        while (projectileIterator.hasNext())
        {
            Projectile temp = projectileIterator.next();

            //Move
            temp.move();

            //Check if the projectile has hit something
            if (temp.remove(this))
            {
                projectilesToRemove.add(temp);
            }
        }

        projectileIterator = projectilesToRemove.iterator();

        while (projectileIterator.hasNext())
        {
            mProjectiles.remove(projectileIterator.next());
        }
    }

    public void fireTowers()
    {
        for (int count = 0; count < mTowers.size(); count++)
        {
            mTowers.get(count).shoot(this);
        }
    }
}
