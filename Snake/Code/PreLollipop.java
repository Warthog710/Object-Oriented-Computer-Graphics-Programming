package com.example.snake;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import java.io.IOException;

public class PreLollipop implements ISound
{
    private SoundPool mSP;
    private int mEat_ID, mCrashID;

    //Initialize the SoundPool
    PreLollipop(Context context)
    {
        mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        loadAssets(context);
    }

    //Load the sound assets into SoundPool
    private void loadAssets(Context context)
    {
        try
        {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            // Prepare the sounds in memory
            descriptor = assetManager.openFd("get_apple.ogg");
            mEat_ID = mSP.load(descriptor, 0);
            descriptor = assetManager.openFd("snake_death.ogg");
            mCrashID = mSP.load(descriptor, 0);
        }
        catch (IOException e)
        {
            System.out.println("Failed to load sound assets.");
        }
    }

    //Play the sound.
    @Override
    public void playGetApple()
    {
        mSP.play(mEat_ID, 1, 1, 0, 0, 1);
    }

    @Override
    public void playSnakeDeath()
    {
        mSP.play(mCrashID, 1, 1, 0, 0, 1);
    }
}
