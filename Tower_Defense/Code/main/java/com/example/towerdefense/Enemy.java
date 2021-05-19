package com.example.towerdefense;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
public class Enemy extends GameObject implements Alien
{
    private EnemyMovable movementStrategy;
    private AlienHealthBar mHealthBar;
    private int animationIndex = 0;
    private int enemyType;
    private float health;
    private String mInfo;
    private int money;

    //Used to record the dmg dealt to this enemy in its "short" life
    private DmgDealt dmgDealt;

    //Used to hold the current resistances for this enemy
    private Resistances resistances;

    //Has the enemy demised?
    private boolean isDead = false;

    //Private class constructor
    private Enemy(EnemyBuilder builder)
    {
        //Initialize variables
        this.health = builder.health;
        this.money = builder.money;
        this.movementStrategy = builder.movementStrategy;
        this.mHealthBar = builder.mHealthbar;
        this.mInfo = builder.mInfo;
        this.mLocation = builder.mLocation;
        this.enemyType = builder.enemyType;
        this.setAttributeSize(builder.attributeSize);
        this.resistances = builder.resistances;

        //Initialize dmgdealt
        dmgDealt = new DmgDealt();
    }

    //Call onHit to deal dmg
    public void onHit(float dmg, GameEngine.dmgType dmgType)
    {
        health = health - (dmg / resistances.getResistances(dmgType));

        //Prevents the healthbar from looking weird...
        if (health <= 0)
        {
            health = 0;

            //Explode the enemy
            enemyType = 4;
            animationIndex = 0;
        }

        //Record dmg dealt with resistance
        dmgDealt.recordDmg(dmgType, dmg / resistances.getResistances(dmgType));
    }

    //Calls move() in the movement strategy to move the enemy along the path
    public void move(ArrayList<PathPoints> path)
    {
        if (enemyType != 4)
            movementStrategy.move(path);
    }

    //Return a hitbox for the current location of the drone
    public Rect getHitbox()
    {
        return new Rect(movementStrategy.getLocation().x - (getAttributeSize() / 2),
                movementStrategy.getLocation().y - (getAttributeSize() / 2),
                movementStrategy.getLocation().x + (getAttributeSize() / 2),
                movementStrategy.getLocation().y + (getAttributeSize() / 2));
    }

    @Override
    public String getResistance()
    {
        return "Resistance | Plasma: "+resistances.getResistances(GameEngine.dmgType.plasma)+
                " | Rockets: "+resistances.getResistances(GameEngine.dmgType.rocket)+
                " | Lasers: "+resistances.getResistances(GameEngine.dmgType.laser);
    }

    //Call to check collision with another hitbox.
    public boolean checkCollision(Rect mBase)
    {
        //If the drone collides with the base
        if (getHitbox().intersects(getHitbox(), mBase))
            return true;

        return false;
    }

    //Custom drawing method for enemies
    public void draw(Canvas canvas, Paint paint, BitMapContainer mBitmaps, boolean isPaused)
    {
        //Draw enemy
        if (enemyType <= 3)
        {
            canvas.drawBitmap(mBitmaps.getEnemyBitmap(animationIndex, enemyType), movementStrategy.getLocation().x - getAttributeSize() / 2, movementStrategy.getLocation().y - getAttributeSize() / 2, null);

            //Draw healthbar
            mHealthBar.draw(canvas, paint, this.health, movementStrategy.getLocation());

            if (!isPaused)
                animationIndex = mBitmaps.getNextEnemyIndex(animationIndex, enemyType);
        }

        //Enemy is type dead and will explode.
        else
        {
            canvas.drawBitmap(mBitmaps.getEnemyBitmap(animationIndex, enemyType), movementStrategy.getLocation().x - getAttributeSize() / 2, movementStrategy.getLocation().y - getAttributeSize() / 2, null);

            if (!isPaused)
                animationIndex = mBitmaps.getNextEnemyIndex(animationIndex, enemyType);

            if (animationIndex >= 4)
            {
                isDead = true;
            }
        }
    }

    //Getter methods.
    @Override
    public Point getLocation() { return movementStrategy.getLocation(); }
    public DmgDealt getDmgDealt()
    {
        return this.dmgDealt;
    }
    public boolean getStatus()
    {
        return this.isDead;
    }
    public float getHealth() { return this.health; }
    public String getInfo() {
        return mInfo;
    }
    public int getMoney() { return money; }

    //Call to instantly kill the enemy
    public void kill() {
        health = 0;
    }

    //Inline builder class
    public static class EnemyBuilder
    {
        //Necessary variables
        private float health;
        private int attributeSize, enemyType, money;
        private EnemyMovable movementStrategy;
        private AlienHealthBar mHealthbar;
        private Point mLocation;
        private String mInfo;
        private Resistances resistances;

        //Set spawn location
        public EnemyBuilder setLocation(PathPoints start)
        {
            this.mLocation = start.getPath();

            return this;
        }

        //Set enemy health
        public EnemyBuilder setHealth(int health)
        {
            this.health = health;

            return this;
        }

        //Set enemy resistance
        public EnemyBuilder setResist(Resistances resist)
        {
            this.resistances = resist;

            return this;
        }

        public EnemyBuilder setMoney(int money)
        {
            this.money = money;

            return this;
        }

        //Set enemy attribute size
        public EnemyBuilder setAttributeSize(int attributeSize)
        {
            this.attributeSize = attributeSize;

            return this;
        }

        //Set enemy attribute size
        public EnemyBuilder setType(AlienFactory.enemyType enemyType)
        {
            switch(enemyType)
            {
                case soldier:
                    this.enemyType = 2;
                    break;

                case behemoth:
                    this.enemyType = 3;
                    break;

                default:
                    this.enemyType = 1;
                    break;
            }
            return this;
        }

        //Attach a healthbar. Height defines the height of the healthbar off the enemy.
        public EnemyBuilder attachHealthBar(int height)
        {
            this.mHealthbar = new AlienHealthBar(height, attributeSize, health);
            return this;
        }

        //Attach a movement strategy
        public EnemyBuilder attachMovementStrategy(AlienFactory.enemyType enemyType)
        {
            switch (enemyType)
            {
                case soldier:
                    this.movementStrategy = new SoldierMovementStrategy(mLocation);
                    break;

                case behemoth:
                    this.movementStrategy = new BehemothMovementStrategy(mLocation);
                    break;

                default:
                    this.movementStrategy = new DroneMovementStrategy(mLocation);
                    break;
            }
            return this;
        }

        //Set info string
        public EnemyBuilder setInfo(String info)
        {
            this.mInfo = info;
            return this;
        }

        //Build the enemy!
        public Enemy build()
        {
            return new Enemy(this);
        }
    }
}
