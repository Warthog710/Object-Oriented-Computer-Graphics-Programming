package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.ArrayList;

public class BitMapContainer
{
    private ArrayList<Bitmap> drone = new ArrayList<>();
    private ArrayList<Bitmap> soldier = new ArrayList<>();;
    private ArrayList<Bitmap> behemoth = new ArrayList<>();;
    private ArrayList<Bitmap> explosion = new ArrayList<>();;
    private ArrayList<Bitmap> backgrounds = new ArrayList<>();;

    private Bitmap base;

    public BitMapContainer(Context context, Point size)
    {
        //Create drone list
        drone.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.drone_1), size.x/40, size.x/40, true));
        drone.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.drone_2), size.x/40, size.x/40, true));

        //Create soldier list
        soldier.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.soldier_1), size.x/40, size.x/40, true));
        soldier.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.soldier_2), size.x/40, size.x/40, true));
        soldier.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.soldier_3), size.x/40, size.x/40, true));
        soldier.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.soldier_4), size.x/40, size.x/40, true));

        //Create behemoth list
        behemoth.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.behemoth_1), size.x/40, size.x/40, true));
        behemoth.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.behemoth_2), size.x/40, size.x/40, true));

        //Create explosion list
        explosion.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_1), size.x/40, size.x/40, true));
        explosion.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_2), size.x/40, size.x/40, true));
        explosion.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_3), size.x/40, size.x/40, true));
        explosion.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_4), size.x/40, size.x/40, true));
        explosion.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_5), size.x/40, size.x/40, true));

        //Create background list
        backgrounds.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.background_1), size.x, size.y, true));
        backgrounds.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.background_2), size.x, size.y, true));
        backgrounds.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.background_3), size.x, size.y, true));
    }

    //Returns the enemies bitmap based on type and index.
    public Bitmap getEnemyBitmap(int index, int type)
    {
        switch (type)
        {
            case 2:
                return soldier.get(index);

            case 3:
                return behemoth.get(index);

            case 4:
                return explosion.get(index);

            default:
                return drone.get(index);
        }
    }

    //Returns the next index of the enemies animation loop
    public int getNextEnemyIndex(int index, int type)
    {
        switch (type)
        {
            case 2:
                if (index >= soldier.size() - 1)
                    index = 0;
                else
                    index++;
                break;

            case 3:
                if (index >= behemoth.size() - 1)
                    index = 0;
                else
                    index++;
                break;

            case 4:
                if (index >= explosion.size() - 1)
                    index = 0;
                else
                    index++;
                break;

            default:
                if (index >= drone.size() - 1)
                    index = 0;
                else
                    index++;
                break;
        }
        return index;
    }

    public Bitmap getBackground(int index)
    {
        return backgrounds.get(index);
    }
}
