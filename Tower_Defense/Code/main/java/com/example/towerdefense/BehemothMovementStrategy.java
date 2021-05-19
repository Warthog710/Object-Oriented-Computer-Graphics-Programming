package com.example.towerdefense;

import android.graphics.Point;
import java.util.ArrayList;

//Basic movement strategy for the Soldier
public class BehemothMovementStrategy implements EnemyMovable
{
    //Class variables
    private int mSpeed = 2;
    private int currentIndex = 0;
    private Point mLocation;

    public BehemothMovementStrategy(Point mLocation)
    {
        this.mLocation = mLocation;
    }

    public void move(ArrayList<PathPoints> path)
    {
        //Increment current index by speed and retrieve the next point.
        this.currentIndex += this.mSpeed;
        this.mLocation = path.get(this.currentIndex).getPath();
    }

    //Getter methods
    public Point getLocation() { return mLocation; }
}