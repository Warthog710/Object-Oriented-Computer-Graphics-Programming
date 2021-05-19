package com.example.towerdefense;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

//This class is the context for the sound strategy
public class GameSound
{
    //Holds the selected strategy.
    private ISound strategy;

    //Holds the strategy version.
    private ISound version_strategy;


    private boolean soundPlaying;

    //Selects the necessary strategy on creation.
    GameSound(Context context)
    {
        soundPlaying=true;

        //Post Lollipop
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            this.strategy = this.version_strategy= new PostLollipop(context);
        }

        //Pre Lollipop
        else
        {
            this.strategy= this.version_strategy = new PreLollipop(context);
        }
    }

    //Play the chosen strategies sounds.
    public void playLaserSound()
    {
        strategy.playLaser();
    }

    public void playPlasmaSound()
    {
        strategy.playPlasma();
    }

    public void playRocketSound()
    {
        strategy.playRocket();
    }

    public void playExplosionSound() { strategy.playExplosion(); }

    public void soundOff(){
        this.strategy=new SilentStrategy();
        soundPlaying=false;
    }

    public void soundOn() {
        this.strategy=this.version_strategy;
        soundPlaying=true;

    }

    public boolean getSound(){
        return soundPlaying;
    }

    public void changeSound(){
        if (soundPlaying){
            soundOff();
        }else{
            soundOn();
        }
    }

}
