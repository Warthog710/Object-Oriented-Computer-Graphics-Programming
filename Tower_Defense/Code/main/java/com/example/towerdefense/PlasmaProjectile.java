package com.example.towerdefense;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.Iterator;
/*
projectile from the plasma tower.
 */

public class PlasmaProjectile extends Movable implements Projectile {
    public final int mSize = 25; //size of the projectile
    Point mDestination; //where the projectile is going to
    int mDamage; //amount of damage


    public PlasmaProjectile(Bitmap mBitMap, Point mLocation, Alien mTarget, int mDamage) //set up the projectile
    {
        this.mSpeed = (int) (2000 / GameWorld.BASE_TICKS_PER_SECOND);
        this.mDamage = mDamage; //set damage
        this.mDestination = mTarget.getLocation();
        this.mLocation = mLocation;
        this.setAttributeSize(mSize);
        this.mBitmap = mBitMap;
        this.mBitmap = Bitmap.createScaledBitmap(mBitmap, getAttributeSize(), getAttributeSize(), false);
        this.mHeading = new Angle();
        mHeading.setAngle((int) Math.toDegrees(Math.atan2((mDestination.y - mLocation.y), (mDestination.x - mLocation.x))) + 90);
        Matrix matrix = new Matrix();
        matrix.postRotate(mHeading.getAngle());
        mBitmap = Bitmap
                .createBitmap(mBitmap,
                        0, 0, getAttributeSize(), getAttributeSize(), matrix, true);
    }

    @Override
    public void move() //move to projectile
    {
        int xSpeed = ((int) (Math.cos(Math.toRadians(mHeading.getAngle() - 90)) * mSpeed));
        int ySpeed = ((int) (Math.sin(Math.toRadians(mHeading.getAngle() - 90)) * mSpeed));
        mLocation = new Point(mLocation.x + xSpeed, mLocation.y + ySpeed);

    }

    //check to see if the projectile should be remove
    //(it has hit a alien or it has left the field of view)
    @Override
    public boolean remove(GameWorld gameWorld) {
        Rect hitbox = new Rect(mLocation.x - getAttributeSize() / 2, mLocation.y - getAttributeSize() / 2,
                mLocation.x + getAttributeSize() / 2, mLocation.y + getAttributeSize() / 2);

        boolean value = false;
        Iterator<Alien> alienIterator = gameWorld.mAliens.iterator();

        while (alienIterator.hasNext()) {
            Alien alien = alienIterator.next();
            if (alien.checkCollision(hitbox)) { //see if the projectile hit the alien
                alien.onHit(mDamage, GameEngine.dmgType.plasma);
                value = true;
                break;
            }
        }

        if (mLocation.x > gameWorld.mMap.size.x || mLocation.y > gameWorld.mMap.size.y
                || mLocation.y < 0 || mLocation.x < 0) //if the projectile has left the screen
            value = true;

        return value;
    }

}
