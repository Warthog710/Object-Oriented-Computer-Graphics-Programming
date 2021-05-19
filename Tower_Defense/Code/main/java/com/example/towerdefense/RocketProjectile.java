package com.example.towerdefense;


import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.Iterator;
/*
Rocket Projectile:
Pass it a target which it follows.
 */

public class RocketProjectile extends Movable implements Projectile {

    public final int mSize = 25;
    public int mDamage;
    private Alien mTarget;
    private Bitmap mOriginalBitmap;

    public RocketProjectile(Bitmap mBitMap, Point mLocation, Alien mTarget, int mDamage) { //set up rocket projectile
        this.mSpeed = (int) (300 / GameWorld.BASE_TICKS_PER_SECOND);
        this.setAttributeSize(mSize);
        this.mLocation = mLocation;
        this.mDamage = mDamage;
        this.mTarget = mTarget;
        this.mBitmap = mBitMap;
        this.mBitmap = Bitmap.createScaledBitmap(mBitmap, getAttributeSize(), getAttributeSize(), false);
        this.mOriginalBitmap = mBitMap;
        this.mOriginalBitmap = Bitmap.createScaledBitmap(mOriginalBitmap, getAttributeSize(), getAttributeSize(), false);
        this.mHeading = new Angle();
        mHeading.setAngle((int) Math.toDegrees(Math.atan2((mTarget.getLocation().y - mLocation.y), (mTarget.getLocation().x - mLocation.x))) + 90);
        Matrix matrix = new Matrix();
        matrix.postRotate(mHeading.getAngle());
        mBitmap = Bitmap
                .createBitmap(mBitmap,
                        0, 0, getAttributeSize(), getAttributeSize(), matrix, true);
    }

    @Override
    public void move() { //move the projectile
        if (this.mTarget.getHealth() > 0 && this.mTarget != null) { //if the target is alive
            mHeading.setAngle((int) Math.toDegrees(Math.atan2((mTarget.getLocation().y - mLocation.y), (mTarget.getLocation().x - mLocation.x))) + 90);
            Matrix matrix = new Matrix();
            matrix.postRotate(mHeading.getAngle());
            mBitmap = Bitmap
                    .createBitmap(mOriginalBitmap,
                            0, 0, getAttributeSize(), getAttributeSize(), matrix, true);
        }
        int xSpeed = ((int) (Math.cos(Math.toRadians(mHeading.getAngle() - 90)) * mSpeed));
        int ySpeed = ((int) (Math.sin(Math.toRadians(mHeading.getAngle() - 90)) * mSpeed));
        mLocation = new Point(mLocation.x + xSpeed, mLocation.y + ySpeed);
    }

    @Override
    public boolean remove(GameWorld gameWorld) { //remove it it has hit a alien or has left the screen
        Rect hitbox = new Rect(mLocation.x - getAttributeSize() / 2, mLocation.y - getAttributeSize() / 2,
                mLocation.x + getAttributeSize() / 2, mLocation.y + getAttributeSize() / 2);
        boolean value = false;
        Iterator<Alien> alienIterator = gameWorld.mAliens.iterator();
        while (alienIterator.hasNext()) {
            Alien alien = alienIterator.next();
            if (alien.checkCollision(hitbox)) { //see if the projectile hit the alien
                alien.onHit(mDamage, GameEngine.dmgType.rocket);
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
