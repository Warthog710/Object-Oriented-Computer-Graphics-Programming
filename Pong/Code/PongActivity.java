package com.example.pongactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.graphics.Point;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class PongActivity extends AppCompatActivity
{
    //Create a new pongGame object.
    private PongGame mPongGame;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Calling the parents constructor.
        super.onCreate(savedInstanceState);

        //Getting the display object.
        Display display = getWindowManager().getDefaultDisplay();

        //Code added to allow successful run in our emulators.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Added code end.

        //Getting point object and display size.
        Point size = new Point();
        display.getSize(size);

        //Creating a screenSize object and initializing it.
        ScreenInfo screen = new ScreenInfo(size.x, size.y);

        //Starting game!
        mPongGame = new PongGame(this, screen);
        setContentView(mPongGame);
    }

    //Called when the game is currently paused and needs to Resume.
    @Override
    protected void onResume()
    {
        super.onResume();
        mPongGame.Resume();
    }

    //Called to Pause the game.
    @Override
    protected void onPause()
    {
        super.onPause();
        mPongGame.Pause();
    }
}
