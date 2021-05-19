package com.example.snake;

import android.graphics.Point;

//abstract class for all movable objects
public abstract class Movable extends GameObject
{
    public void follow(Point location){ //all objects will be able to go to a point
        mLocation=location;
    }

    //some objects can move without having a point
    public abstract void move();
}