package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;


public class AlienHealthBar
{
    private int width;
    private int height;
    private float maxHealth;

    //Class constructor
    public AlienHealthBar(int height, int width, float maxHealth)
    {
        this.height = height;
        this.width = width;
        this.maxHealth = maxHealth;
    }

    //Draws the two rectangles that compose the health bar
    public void draw(Canvas canvas, Paint paint, float health, Point mLocation)
    {
        //Draw back of health bar...
        paint.setColor(Color.RED);
        canvas.drawRect(mLocation.x - (width / 2), mLocation.y - (width / 2) - height, mLocation.x + (width / 2), mLocation.y - (width / 2), paint);

        //Draw front of health bar...
        paint.setColor(Color.GREEN);
        canvas.drawRect(mLocation.x - (width / 2), mLocation.y - (width / 2) - height, (mLocation.x + (width / 2)) - getGoodHealthWidth(health), mLocation.y - (width / 2), paint);

    }

    //Returns the width of the "current health" section of the health bar
    private float getGoodHealthWidth(float health)
    {
        float healthWidth = width - ((this.width / maxHealth) * health);
        return healthWidth;
    }

}
