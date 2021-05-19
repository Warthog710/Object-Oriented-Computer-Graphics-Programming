package com.example.towerdefense;

import android.graphics.Point;
import java.util.ArrayList;

//Basic movement strategy for the Drone
public class DroneMovementStrategy implements EnemyMovable
{
    private int mSpeed = 6;
    private int currentIndex = 0;
    private Point mLocation;

    public DroneMovementStrategy(Point mLocation)
    {
        this.mLocation = mLocation;
    }

    public void move(ArrayList<PathPoints> path)
    {
        //Increment current index by speed and retrieve the next point.
        this.currentIndex += this.mSpeed;
        this.mLocation = path.get(this.currentIndex).getPath();

    }

    public Point getLocation() { return mLocation; }
}
