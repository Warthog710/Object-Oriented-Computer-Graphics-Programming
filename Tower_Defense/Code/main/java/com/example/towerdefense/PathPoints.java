package com.example.towerdefense;

import android.graphics.Point;

//Holds a defined point and heading
public class PathPoints
{
    private Point path;
    private int angle;

    //Constructor...
    public PathPoints(Point path, int angle)
    {
        this.path = path;
        this.angle = angle;
    }

    //Getters...
    public Point getPath() { return this.path; }

}
