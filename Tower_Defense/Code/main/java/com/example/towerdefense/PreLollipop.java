package com.example.towerdefense;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.io.IOException;

/*
Strategy for sound in post lollipop api
 */
public class PreLollipop implements ISound
{
    private SoundPool mSP;
    private int mLaser_ID, mPlasma_ID, mRocket_ID, mExplosion_ID;
    private MediaPlayer mediaPlayer;
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
            descriptor = assetManager.openFd("laser_sound.ogg");
            mLaser_ID = mSP.load(descriptor, 0);
            descriptor = assetManager.openFd("rocket_sound.ogg");
            mRocket_ID = mSP.load(descriptor, 0);
            descriptor = assetManager.openFd("plasma_sound.ogg");
            mPlasma_ID = mSP.load(descriptor, 0);
            descriptor = assetManager.openFd("explosion.ogg");
            mExplosion_ID = mSP.load(descriptor, 0);
        }
        catch (IOException e)
        {
            System.out.println("Failed to load sound assets.");
        }
    }

    //Play the sound.
    @Override
    public void playLaser() {
        mSP.play(mLaser_ID, 1, 1, 0, 0, 1);
    }

    @Override
    public void playRocket() {
        mSP.play(mRocket_ID, 1, 1, 0, 0, 1);
    }

    @Override
    public void playPlasma() {
        mSP.play(mPlasma_ID, 1, 1, 0, 0, 1);
    }

    @Override
    public void playExplosion() {
        mSP.play(mExplosion_ID, 1, 1, 0, 0, 1);
    }

}
