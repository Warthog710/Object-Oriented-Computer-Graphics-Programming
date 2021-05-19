package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/*
The laser projectile
draws a green line between tower and alien
 */

public class LaserProjectile implements Projectile {
    final static long TIME = 100;

    private long startingTime;
    private Point mLocation;
    private Point mDestination;


    public LaserProjectile(Point mLocation, Alien mTarget) //create laser beam
    {
        this.mLocation = mLocation;
        this.mDestination = mTarget.getLocation();
        this.startingTime = System.currentTimeMillis();
    }

    @Override
    public void move() {
        //Do nothing
    }

    @Override
    public boolean remove(GameWorld gameWorld) //check if the beam has timed out
    {
        return (System.currentTimeMillis() >= startingTime + TIME);
    }

    public void draw(Canvas canvas, Paint paint) //draw the beam
    {
        paint.setColor(Color.argb(255, 34, 107, 59));
        paint.setStrokeWidth(10);
        canvas.drawLine(mLocation.x, mLocation.y, mDestination.x, mDestination.y, paint);
        paint.setColor(Color.argb(255, 12, 237, 87));
        paint.setStrokeWidth(4);
        canvas.drawLine(mLocation.x, mLocation.y, mDestination.x, mDestination.y, paint);
        paint.setColor(GameWorld.white);


    }
}
