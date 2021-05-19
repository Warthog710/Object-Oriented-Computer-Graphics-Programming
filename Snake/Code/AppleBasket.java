package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//This class acts as a container for all the apples in play.
public class AppleBasket
{
    private Map<Point, MyApple> myAppleMap = new HashMap<>();

    //Class constructor builds the first apple on screen.
    public AppleBasket(ScreenInfo screen, Context context)
    {
        //Always start with a red apple.
        MyApple apple = new MyApple.AppleBuilder()
                .setColor("red_apple", screen, context)
                .setLocation(screen)
                .setPoints(1)
                .build();

        myAppleMap.put(apple.getLocation(), apple);
    }

    //Call to remove a given apple from the map.
    public void removeApple (Point point) { myAppleMap.remove(point); }

    //Call to get the current apple map.
    public Map getAppleMap() { return myAppleMap; }

    //Call to get the point value of a selected apple
    public int getPoints(Point point) { return myAppleMap.get(point).getPoints(); }

    //Spawn apples. This is called each time an apple is eaten.
    public void spawn(ScreenInfo screen, Context context, int score)
    {
        int temp = (score / 5) + 1;

        while (myAppleMap.size() < temp)
        {
            //Cap the number of apples on screen at 10.
            if (myAppleMap.size() >= 10)
                return;

            MyApple apple = createNewApple(screen, context);
            myAppleMap.put(apple.getLocation(), apple);
        }
    }

    //Constructs a new apple by randomly choosing the color and point value.
    private MyApple createNewApple (ScreenInfo screen, Context context)
    {
        Random random = new Random();
        int temp = random.nextInt(100), points;
        String color;

        //Create a bad apple. 20% chance
        if (temp < 20)
        {
            color = "bad_apple";
            points = -2;
        }

        //Create a red apple. 50% chance
        else if (temp < 70)
        {
            color = "red_apple";
            points = 1;
        }

        //Create a blue apple. 20% chance
        else if (temp < 90)
        {
            color = "blue_apple";
            points = 2;
        }

        //Create a gold apple. 10% chance
        else
        {
            color = "gold_apple";
            points = 3;
        }

        //Build a new apple with the selected options.
        MyApple apple = new MyApple.AppleBuilder()
                .setColor(color, screen, context)
                .setLocation(screen)
                .setPoints(points)
                .build();

        return apple;
    }

    //Draw the list of apples.
    void draw(Canvas canvas, Paint paint, ScreenInfo screen)
    {
        for(Map.Entry mapElement : myAppleMap.entrySet())
        {
            //Call each apples draw method inherited from GameObject
            MyApple temp = (MyApple)mapElement.getValue();
            temp.draw(canvas, paint, screen);
        }
    }
}
