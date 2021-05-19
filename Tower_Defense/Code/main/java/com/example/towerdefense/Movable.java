package com.example.towerdefense;

//Movable for gameObjects
public abstract class Movable extends GameObject
{
    //Angle object is heading and the speed.
    Angle mHeading;
    int mSpeed;

    //How it moves
    abstract void move();
}
