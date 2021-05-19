package com.example.pongactivity;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import java.io.IOException;
/*
Create a class to manage the audio rather than having it in the "PongGame" file.
We decided to make this class to help encapsulate the data and variables that are necessary for playing sound.
We also wanted to abstract the process of initialising the sounds and playing them.
This class will set-up all the audio necessary for the game.
Also, we can simply class this class and ask it to play a sound like "boop"
where as before we would need to know the sounds id, and specify the strength of the left and right audio.
 */

class AudioManager {
    //We set these variables to private since we do not want some other class interacting with them
    //these variables are only needed by this class
    private SoundPool mSP;
    private int mBeepID;
    private int mBoopID;
    private int mBopID;
    private int mMissID;

    AudioManager(Context context)
    {
        //Prepare the SoundPool instance. Depending upon the version of Android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            AudioAttributes audioAttributes =
                    new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build();
            mSP = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }
        else
        {
            mSP = new SoundPool(5, android.media.AudioManager.STREAM_MUSIC, 0);
        }

        //Open each of the sound files. If the file cannot be loaded. The error is logged.
        try
        {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("beep.ogg");
            mBeepID = mSP.load(descriptor, 0);
            descriptor = assetManager.openFd("boop.ogg");
            mBoopID = mSP.load(descriptor, 0);
            descriptor = assetManager.openFd("bop.ogg");
            mBopID = mSP.load(descriptor, 0);
            descriptor = assetManager.openFd("miss.ogg");
            mMissID = mSP.load(descriptor, 0);
        }
        catch(IOException e)
        {
            Log.d("error", "failed to load sound files");
        }
    }
    //make a method to play a beep and set it to package private
    void PlayBeep(){
        mSP.play(mBeepID, 1, 1, 0, 0, 1);
    }
    //make a method to play a miss and set it to package private
    void PlayMiss(){
        mSP.play(mMissID, 1, 1, 0, 0, 1);
    }
    //make a method to play a boop and set it to package private
    void PlayBoop(){
        mSP.play(mBoopID, 1, 1, 0, 0, 1);
    }
    //make a method to play a bop and set it to package private
    void PlayBop(){
        mSP.play(mBopID, 1, 1, 0, 0, 1);
    }
}
