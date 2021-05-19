package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;

/*
Projectile interface
*/

public interface Projectile
{
    void move();
    boolean remove(GameWorld gameWorld);
    void draw(Canvas canvas, Paint paint);
}
