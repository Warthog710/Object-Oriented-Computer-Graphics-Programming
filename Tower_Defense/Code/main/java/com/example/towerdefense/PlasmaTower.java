package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;

import java.util.Iterator;

/*
Plasma Tower
Set up the tower
 */

public class PlasmaTower extends Tower {

    public PlasmaTower(Context context, Point location) //create the plasma tower
    {
        setSize(); //set the size
        this.mDescription = "Plasma is fast but doesn't hurt much";
        this.mName = "Plasma Tower: Fires plasma at a fast speeds.";
        this.mLocation = location;
        this.mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.plasma_turret);
        this.mBitmap = Bitmap.createScaledBitmap(mBitmap, getAttributeSize(), getAttributeSize(), false);
        this.mOriginalBitMap = this.mBitmap;
        this.mProjectileBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_plasma);
        lastShot = System.currentTimeMillis();

        this.mDamage = 2;
        this.mRateOfFire = 4;
        this.mRange = 300;
        this.mUpgradeCost = 25;
    }

    @Override
    public void shoot(GameWorld gameWorld) //shoot at the first enemy it can
    {
        if (System.currentTimeMillis() - lastShot > (1000 / (mRateOfFire * gameWorld.getSpeed()))) { //can the tower fire?
            lastShot = System.currentTimeMillis();
            Iterator<Alien> alienIterator = gameWorld.mAliens.iterator();
            while (alienIterator.hasNext()) //loop though aliens
            {
                Alien alien = alienIterator.next();
                if (inRange(alien) && alien.getHealth() > 0) //if the alien is in range, shoot
                {
                    gameWorld.mSound.playPlasmaSound();
                    gameWorld.mProjectiles.add(new PlasmaProjectile(mProjectileBitmap, mLocation, alien, mDamage)); //create projectile
                    int angle = (int) Math.toDegrees(Math.atan2((alien.getLocation().y - mLocation.y), (alien.getLocation().x - mLocation.x))) + 90;
                    Matrix matrix = new Matrix();
                    matrix.postRotate(angle);
                    mBitmap = Bitmap //rotate bitmap to face enemy
                            .createBitmap(mOriginalBitMap,
                                    0, 0, getAttributeSize(), getAttributeSize(), matrix, true);


                    break; //stop looping after shooting at a alien
                }
            }
        }
    }

    @Override
    public void upgrade() { //upgrade plasma tower
        this.mDamage = (this.mDamage + 1);
        this.mRateOfFire = this.mRateOfFire + 1;
        this.mRange = (int) (this.mRange * 1.05);
    }

    @Override
    public int towerCost() {
        return Tower.PLASMA_COST;
    }

}
