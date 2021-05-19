package com.example.snake;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.content.Context;
import java.io.IOException;

//Set target API to 21 (Post Lollipop)
@TargetApi(21)
public class PostLollipop implements ISound
{
    private SoundPool mSP;
    private int mEat_ID, mCrashID;

    //Initialize SoundPool
    PostLollipop(Context context)
    {
        AudioAttributes audioAttributes =
                new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes
                                .CONTENT_TYPE_SONIFICATION)
                        .build();
        mSP = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();

        loadAssets(context);
    }

    //Load sound assets into SoundPool
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
