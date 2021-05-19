package com.example.towerdefense;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

/*
abstract class for all towers
 */
public abstract class Tower extends GameObject {
    public long lastShot; //time that the last shot was taken
    public int mRange; //range in pixels
    public float mRateOfFire; //shots per second
    public int mDamage; //amount of damage each projectile does
    public Bitmap mProjectileBitmap;
    public Bitmap mOriginalBitMap;
    public String mDescription;
    public String mName;
    public int mUpgradeCost;
    final static int towerSize = 150; //size of all the towers
    final static int ROCKET_COST = 200; //cost of rocket
    final static int PLASMA_COST = 50; //cost of plasma
    final static int LASER_COST = 100; //cost of laser

    enum TowerType {PLASMA, LASER, ROCKET} //types of towers

    public abstract void shoot(GameWorld gameWorld); //method that calls the tower to shoot

    public abstract void upgrade(); //upgrades the tower;

    public abstract int towerCost();

    public void setSize() {
        setAttributeSize(towerSize);
    } //set the size of the tower

    public boolean contains(Point point) { //check to see if a point exists on the tower
        Rect hitbox = new Rect(mLocation.x - getAttributeSize() / 2, mLocation.y - getAttributeSize() / 2,
                mLocation.x + getAttributeSize() / 2, mLocation.y + getAttributeSize() / 2);
        return hitbox.contains(point.x, point.y);
    }

    public boolean inRange(Alien alien) //check to see if an alien is in range
    {
        int x = mLocation.x - alien.getLocation().x;
        int y = mLocation.y - alien.getLocation().y;
        return Math.sqrt(x * x + y * y) <= mRange;
    }
}
