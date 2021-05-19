package com.example.snake;

import android.os.Build;
import android.content.Context;

//This class is the context for the sound strategy
public class GameSound
{
    //Holds the selected strategy.
    private ISound strategy;

    //Selects the necessary strategy on creation.
    GameSound(Context context, boolean silent)
    {
        //Silent mode is true
        if (silent)
        {
            this.strategy = new SilentStrategy();
        }

        //Post Lollipop
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            this.strategy = new PostLollipop(context);
        }

        //Pre Lollipop
        else
        {
            this.strategy = new PreLollipop(context);
        }
    }

    //Play the chosen strategies sounds.
    public void playGetApple()
    {
        strategy.playGetApple();
    }

    public void playSnakeDeath()
    {
        strategy.playSnakeDeath();
    }

}
