package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;
/*
Factory for building towers
 */

public class TowerFactory { //factory for building towers

    public Tower getTower(Tower.TowerType towerType, Context context, Point mLocation){
        Tower tower=null;
        switch(towerType)
        {
            case PLASMA: //make a plasma tower
                tower = new PlasmaTower(context, mLocation);
                break;
            case LASER: //make a plasma tower
                tower = new LaserTower(context, mLocation);
                break;
            case ROCKET: //make a plasma tower
                tower = new RocketTower(context, mLocation);
                break;
        }
        return tower;
    }
}
