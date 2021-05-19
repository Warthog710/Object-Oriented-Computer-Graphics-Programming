package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Iterator;

public class GameMap {
    private final int pathWidth = 50;
    Bitmap base;
    Point size;
    Path path;
    ArrayList<PathPoints> mPathCords = new ArrayList<>();
    private level currentLevel;
    private int pathOffset = 0;

    //GameMap constructor
    public GameMap(Context context, Point size) {
        this.base = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.space_base), size.x / 5, size.y / 2, true);
        this.size = size;
    }

    //Converts the defined path into an ArrayList of points
    private void calculatePathCords() {
        //Method variables.
        PathMeasure pm = new PathMeasure(path, false);
        float length = pm.getLength();
        float step = 1;
        float counter = 0;
        float[] cords = new float[2];
        float[] tangent = new float[2];

        while (true) {
            //While not at the end of the path element, execute the following...
            while (counter < length) {
                pm.getPosTan(counter, cords, tangent);
                double angle = Math.atan2(tangent[0], tangent[1]);
                angle = angle * (180 / Math.PI);
                mPathCords.add(new PathPoints(new Point((int) cords[0], (int) cords[1]), (int) angle));
                counter += step;
            }

            //If there is another path element...
            if (pm.nextContour()) {
                //Get the new length and loadLevel counter to zero.
                length = pm.getLength();
                counter = 0;
            }

            //Else, end of path reached. Break out of loop.
            else
                break;
        }
    }

    //Draw the map
    void draw(Canvas canvas, Paint paint, BitMapContainer mBitmaps) {
        //Draw the background...
        canvas.drawBitmap(mBitmaps.getBackground(getCurrentLevel() - 1), 0, 0, null);

        //Draw the base...
        canvas.drawBitmap(base, size.x - base.getWidth(), (size.y / 2) - (base.getHeight() / 2) + pathOffset, null);
    }

    //Returns the hitbox of the base. Used to determine if the aliens intersect.
    public Rect getBaseCords() {
        return new Rect(size.x - base.getWidth() + size.x / 50, (size.y / 2) - (base.getHeight() / 2) + pathOffset, size.x, (size.y / 2) + (base.getHeight() / 2) + pathOffset);
    }

    public ArrayList<PathPoints> getPathCords() {
        return mPathCords;
    }

    //Returns true if the points location is on or near the path or base
    public boolean inPath(Point location) {
        boolean inpath = false;
        if (mPathCords != null) {
            Iterator<PathPoints> pathIterator = mPathCords.iterator();
            while (pathIterator.hasNext()) {
                PathPoints pathPoint = pathIterator.next();
                if (location.x < pathPoint.getPath().x + pathWidth / 2 + Tower.towerSize / 4 && location.x > pathPoint.getPath().x - pathWidth / 4 - Tower.towerSize / 2
                        && location.y < pathPoint.getPath().y + pathWidth / 2 + Tower.towerSize / 4 && location.y > pathPoint.getPath().y - pathWidth / 4 - Tower.towerSize / 2) {
                    inpath = true;
                    break;
                }
            }
        }
        if (getBaseCords().contains(location.x, location.y)) {
            inpath = true;
        }
        return inpath;
    }

    //Change the current level
    public void changeLevel(GameMap.level level) {
        LevelPath temp = new LevelPath(level, this.size);
        this.path = temp.getPath();
        this.pathOffset = temp.getPathOffset();
        mPathCords.clear();
        calculatePathCords();
        this.currentLevel = level;
    }

    public int getCurrentLevel() { //return the current level
        int level;
        switch (currentLevel) {

            case level1:
                level = 1;
                break;
            case level2:
                level = 2;
                break;
            case level3:
                level = 3;
                break;
            default:
                level = 1;
                break;
        }
        return level;
    }

    enum level {level1, level2, level3}

}
