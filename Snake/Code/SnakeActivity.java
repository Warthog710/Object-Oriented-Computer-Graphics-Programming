package com.example.snake;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class SnakeActivity extends AppCompatActivity
{
    SnakeGame mSnakeGame;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Code added to allow code to successfully run in our emulators.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        //Get the pixel dimensions of the screen
        Display display = getWindowManager().getDefaultDisplay();

        //Initialize the result into a Point object
        Point size = new Point();
        display.getSize(size);

        //Save screen info into ScreenInfo object.
        ScreenInfo screen = new ScreenInfo(size.x, size.y);

        //Create a new instance of the snakeGame class
        mSnakeGame = new SnakeGame(this, screen);

        //Make snakeGame the view of the Activity
        setContentView(mSnakeGame);
    }
    //Start the thread in snakeGame
    @Override
    protected void onResume()
    {
        super.onResume();
        mSnakeGame.resume();
    }

    //Stop the thread in snakeGame
    @Override
    protected void onPause()
    {
        super.onPause();
        mSnakeGame.pause();
    }
}
