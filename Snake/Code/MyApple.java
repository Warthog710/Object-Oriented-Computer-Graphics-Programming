package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import java.util.Random;

//Apple inherits from game object.
public class MyApple extends GameObject
{
    private int mPoints;

    //Private constructor.
    private MyApple(AppleBuilder builder)
    {
        //mLocation & mGraphic inherited from GameObject.
        this.mLocation = builder.mLocation;
        this.mGraphic = builder.mBitmapApple;

        //Grab builder attributes.
        this.mPoints = builder.mPoints;
    }

    //Return the apples location.
    public Point getLocation(){ return mLocation; }

    //Return the apples point value.
    public int getPoints() { return mPoints; }

    //Inline builder class
    public static class AppleBuilder
    {
        private Point mLocation = new Point();
        private Bitmap mBitmapApple;
        private int mPoints;

        //This method chooses which color apple to create based on the passed color.
        public AppleBuilder setColor(String color, ScreenInfo screen, Context context)
        {
            switch (color)
            {
                case "red_apple":
                    return redApple(screen, context);

                case "blue_apple":
                    return blueApple(screen, context);

                case "gold_apple":
                    return goldApple(screen, context);

                case "bad_apple":
                    return badApple(screen, context);
            }
            //Default
            return redApple(screen, context).setPoints(1);
        }

        //Sets the apple to a random location on the screen.
        public AppleBuilder setLocation(ScreenInfo screen)
        {
            //Set a random location on the map
            Random random = new Random();
            mLocation.x = random.nextInt(screen.getmNumberOfBlocksWide()) + 1;
            mLocation.y = random.nextInt(screen.getmNumberOfBlocksHigh() - 1) + 1;

            return this;
        }

        //Set the number of points the apple is worth.
        public AppleBuilder setPoints(int points)
        {
            this.mPoints = points;
            return this;
        }

        //Create a red apple.
        private AppleBuilder redApple(ScreenInfo screen, Context context)
        {
            mBitmapApple = BitmapFactory
                    .decodeResource(context.getResources(),
                            R.drawable.red_apple);
            // Resize the bitmap
            mBitmapApple = Bitmap
                    .createScaledBitmap(mBitmapApple, screen.getmSegmentSize(), screen.getmSegmentSize(), false);

            return this;
        }

        //Create a gold apple.
        private AppleBuilder goldApple(ScreenInfo screen, Context context)
        {
            mBitmapApple = BitmapFactory
                    .decodeResource(context.getResources(),
                            R.drawable.gold_apple);
            // Resize the bitmap
            mBitmapApple = Bitmap
                    .createScaledBitmap(mBitmapApple, screen.getmSegmentSize(), screen.getmSegmentSize(), false);

            return this;
        }

        //Create a blue apple.
        private AppleBuilder blueApple(ScreenInfo screen, Context context)
        {
            mBitmapApple = BitmapFactory
                    .decodeResource(context.getResources(),
                            R.drawable.blue_apple);
            // Resize the bitmap
            mBitmapApple = Bitmap
                    .createScaledBitmap(mBitmapApple, screen.getmSegmentSize(), screen.getmSegmentSize(), false);

            return this;
        }

        //Create a bad apple.
        private AppleBuilder badApple(ScreenInfo screen, Context context)
        {
            mBitmapApple = BitmapFactory
                    .decodeResource(context.getResources(),
                            R.drawable.bad_apple);
            // Resize the bitmap
            mBitmapApple = Bitmap
                    .createScaledBitmap(mBitmapApple, screen.getmSegmentSize(), screen.getmSegmentSize(), false);

            return this;
        }

        //Build the apple!
        public MyApple build()
        {
            return new MyApple(this);
        }
    }
}


