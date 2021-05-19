package com.example.snake;

import android.graphics.Color;

public class ScreenInfo
{
    //Class member variables. Note, set them to private as there is no need to change them once they are created.
    private int mNumberOfBlocksHigh;
    private int mNumberOfBlocksWide;
    private int mSegmentSize;
    private final int BLUE_COLOR = Color.argb(255, 26, 128, 182);
    private final int WHITE_COLOR = Color.argb(255, 255, 255, 255);
    private int halfWayPoint;

    //The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;


    //Class constructor, takes an X and Y location saves them, and sets the font, margin, and debug sizes.
    ScreenInfo(int x, int y)
    {
        this.mSegmentSize = x / NUM_BLOCKS_WIDE;
        this.mNumberOfBlocksWide= x /this.mSegmentSize;
        this.mNumberOfBlocksHigh= y /this.mSegmentSize;
        this.halfWayPoint= x /2;
    }

    //Getter methods for our variables. Note, setters are not required because we don't intend to change these values once they are
    //set by the constructor.
    int getmNumberOfBlocksHigh() { return mNumberOfBlocksHigh; }
    int getmNumberOfBlocksWide() { return mNumberOfBlocksWide; }
    int getmSegmentSize() { return mSegmentSize;}
    public int getBLUE_COLOR() { return BLUE_COLOR; }
    public int getWHITE_COLOR() { return WHITE_COLOR; }
    public int getHalfWayPoint(){ return halfWayPoint;}
}
