package com.example.towerdefense;

import android.view.MotionEvent;
//observer interface
public interface InputObserver
{
    void handleInput(MotionEvent event, GameWorld gameState, HUD mHud);
}
