package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;

import java.util.Iterator;

/*
Laser Tower
 */

public class LaserTower extends Tower {
    public LaserTower(Context context, Point location) {
        //Set the size.
        setSize();
        this.mDescription = "Lasers deal medium damage instantly to a single target";
        this.mName = "Laser Tower: Fires a laser at medium speeds.";
        this.mLocation = location;
        this.mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.laser_turret);
        this.mBitmap = Bitmap.createScaledBitmap(mBitmap, getAttributeSize(), getAttributeSize(), false);
        this.mOriginalBitMap = this.mBitmap;
        this.mProjectileBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.blank_image);
        lastShot = System.currentTimeMillis();

        this.mDamage = 10;
        this.mRateOfFire = 0.5f;
        this.mRange = 400;
        this.mUpgradeCost = 50;
    }

    @Override
    public void shoot(GameWorld gameWorld) //shoot at the first enemy it can
    {
        //If the tower can fire.
        if (System.currentTimeMillis() - lastShot > (1000 / (mRateOfFire * gameWorld.getSpeed()))) {
            lastShot = System.currentTimeMillis();
            Iterator<Alien> alienIterator = gameWorld.mAliens.iterator();

            while (alienIterator.hasNext()) //loop through the
            {
                Alien alien = alienIterator.next();

                //If an enemy is in range
                if (inRange(alien) && alien.getHealth() > 0) {
                    //play the sound
                    gameWorld.mSound.playLaserSound();
                    //Create new projectile
                    gameWorld.mProjectiles.add(new LaserProjectile(mLocation, alien));

                    //Calculate damage
                    alien.onHit(mDamage, GameEngine.dmgType.laser);

                    //Calculate angle and rotate tower
                    int angle = (int) Math.toDegrees(Math.atan2((alien.getLocation().y - mLocation.y), (alien.getLocation().x - mLocation.x))) + 90;
                    Matrix matrix = new Matrix();
                    matrix.postRotate(angle);
                    mBitmap = Bitmap //rotate bitmap to face enemy
                            .createBitmap(mOriginalBitMap,
                                    0, 0, getAttributeSize(), getAttributeSize(), matrix, true);
                    break;
                }
            }
        }
    }

    @Override
    public void upgrade() { //upgrade the laser towers
        this.mDamage = (this.mDamage + 2);
        this.mRateOfFire = this.mRateOfFire + 0.5f;
        this.mRange = (int) (this.mRange * 1.01);
    }

    @Override
    public int towerCost() {
        return Tower.LASER_COST;
    }
}
