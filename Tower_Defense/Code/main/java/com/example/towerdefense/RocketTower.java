package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;

import java.util.Iterator;

/*
Rocket tower
 */

public class RocketTower extends Tower {
    public RocketTower(Context context, Point location) //create the rocket tower
    {
        setSize(); //set the size
        this.mDescription = "Rockets will follow their target until they hit something";
        this.mName = "Rocket Tower: Fires rockets at a slow speed.";
        this.mLocation = location;
        this.mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket_turret);
        this.mBitmap = Bitmap.createScaledBitmap(mBitmap, getAttributeSize(), getAttributeSize(), false);
        this.mOriginalBitMap = this.mBitmap;
        this.mProjectileBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket);
        lastShot = System.currentTimeMillis();

        this.mDamage = 50;
        this.mRateOfFire = 0.25f;
        this.mRange = 500;
        this.mUpgradeCost = 100;
    }

    @Override
    public void shoot(GameWorld gameWorld) //shoot at the first enemy it can
    {
        if (System.currentTimeMillis() - lastShot > (1000 / (mRateOfFire * gameWorld.getSpeed()))) { //can the tower fire?
            lastShot = System.currentTimeMillis();
            Iterator<Alien> alienIterator = gameWorld.mAliens.iterator();
            while (alienIterator.hasNext()) { //loop through the aliens
                Alien alien = alienIterator.next();
                if (inRange(alien) && alien.getHealth() > 0) {//if the alien is in range
                    gameWorld.mSound.playRocketSound();
                    gameWorld.mProjectiles.add(new RocketProjectile(mProjectileBitmap, mLocation, alien, mDamage)); //create projectile
                    int angle = (int) Math.toDegrees(Math.atan2((alien.getLocation().y - mLocation.y), (alien.getLocation().x - mLocation.x))) + 90;
                    Matrix matrix = new Matrix();
                    matrix.postRotate(angle);
                    mBitmap = Bitmap //rotate bitmap to face enemy
                            .createBitmap(mOriginalBitMap,
                                    0, 0, getAttributeSize(), getAttributeSize(), matrix, true);
                    break; //stop looping if a enemy has been found.
                }
            }
        }
    }

    @Override
    public void upgrade() { //upgrade the tower
        this.mDamage = (this.mDamage + 10);
        this.mRateOfFire = this.mRateOfFire + 0.1f;
        this.mRange = (int) (this.mRange * 1.05);
    }

    @Override
    public int towerCost() {
        return Tower.ROCKET_COST;
    }
}
