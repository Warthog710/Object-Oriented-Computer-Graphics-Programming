package com.example.towerdefense;

/*
Silent strategy, do nothing when the play sound method is called.
 */
public class SilentStrategy implements ISound
{
    @Override
    public void playLaser() {

    }

    @Override
    public void playRocket() {

    }

    @Override
    public void playPlasma() {

    }

    @Override
    public void playExplosion() {

    }
}
