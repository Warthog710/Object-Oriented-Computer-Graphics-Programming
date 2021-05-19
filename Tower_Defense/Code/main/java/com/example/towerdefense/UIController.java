package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Iterator;
/*
Handles all user input
 */

public class UIController implements InputObserver {
    Context context; //hold onto the context for later use

    public UIController(GameEngineBroadcaster b, Context context) //make the controller
    {
        b.addObserver(this); //add it as an observer
        this.context = context;
    }

    @Override
    public void handleInput(MotionEvent event, GameWorld gameState, HUD mHud) //handles a input
    {
        ArrayList<Rect> buttons = mHud.getControls();
        int i = event.getActionIndex();
        int x = (int) event.getX(i); //get the x location
        int y = (int) event.getY(i); //get the y location
        int eventType = event.getAction() & MotionEvent.ACTION_MASK;
        TowerFactory towerFactory = new TowerFactory(); //make a tower factory
        if (eventType == MotionEvent.ACTION_UP ||
                eventType == MotionEvent.ACTION_POINTER_UP) {

            if (gameState.getGameRunning()) { //if the is a active game running (game screen)

                //code for taking the player back to the main screen after a game is iver
                if (gameState.getPaused() && gameState.getGameOver() && !gameState.getReadyForNewGame()) {
                    gameState.setGameRunningOff();
                    gameState.setReadyForNewGameFalse();
                    mHud.infoContainer.hideInfo();
                }
                //code for starting the game once the player sees the map
                else if (gameState.getPaused() && gameState.getGameOver() && gameState.getReadyForNewGame()) {
                    gameState.startGame();
                    gameState.setReadyForNewGameFalse();
                    mHud.infoContainer.hideInfo();
                } else if (buttons.get(HUD.PAUSE).contains(x, y) && !gameState.getGameOver()) //the player has hit the pause button
                {
                    // If the game is not paused
                    if (!gameState.getPaused()) {
                        // Pause the game
                        gameState.pause();
                    }

                    //Paused and not game over
                    else if (gameState.getPaused()) {
                        gameState.resume();
                    }
                }
                //if the player hits the plasma tower and the game is not paused
                else if (buttons.get(HUD.PlasmaTower).contains(x, y) && !gameState.getPaused() && !gameState.getmPlacing()) {
                    gameState.setmPlacing();
                    gameState.setTowerType(Tower.TowerType.PLASMA);
                    mHud.infoContainer.setInfo(Tower.TowerType.PLASMA);
                }
                //laser tower
                else if (buttons.get(HUD.LaserTower).contains(x, y) && !gameState.getPaused() && !gameState.getmPlacing()) {
                    gameState.setmPlacing();
                    gameState.setTowerType(Tower.TowerType.LASER);
                    mHud.infoContainer.setInfo(Tower.TowerType.LASER);
                }
                //rocket tower
                else if (buttons.get(HUD.RocketTower).contains(x, y) && !gameState.getPaused() && !gameState.getmPlacing()) {
                    gameState.setmPlacing();
                    gameState.setTowerType(Tower.TowerType.ROCKET);
                    mHud.infoContainer.setInfo(Tower.TowerType.ROCKET);
                } else if (buttons.get(HUD.SPEEDUP).contains(x, y) && !gameState.getPaused() && !gameState.getmPlacing()) //speed up
                {
                    gameState.changeSpeed();
                } else if (mHud.infoContainer.upgradeButton.contains(x, y)) { //hit the upgrade button
                    mHud.infoContainer.buttonClick(gameState);
                    gameState.closemPlacing();
                }
                //if the player has a tower to place and the game is not paused and the location is not on the path
                else if (gameState.getmPlacing() && !gameState.getPaused()) { //place tower
                    //cannot place tower there
                    if (gameState.overTower(new Point(x, y)) || gameState.mMap.inPath(new Point(x, y)) || mHud.onButton(new Point(x, y))) { //on tower
                        gameState.error(GameWorld.error.PLACEMENT);
                    } else if (gameState.getCash() >= gameState.getCurrentTowerCost()) {
                        gameState.loseCash(gameState.getCurrentTowerCost());
                        gameState.closemPlacing();
                        gameState.addTower(towerFactory.getTower(gameState.getTowerType(), context, new Point(x, y)));
                        mHud.infoContainer.hideInfo();
                    } else { //not enough cash
                        gameState.error(GameWorld.error.MONEY);
                    }
                } else if (gameState.mMap.inPath(new Point(x, y)) && !gameState.getmPlacing()) { //the player has clicked the path (trying to click a enemy)
                    Iterator<Alien> alienIterator = gameState.mAliens.iterator();
                    while (alienIterator.hasNext()) {
                        Alien currentAlien = alienIterator.next();
                        if (currentAlien.getHitbox().contains(x, y)) {
                            mHud.infoContainer.setInfo(currentAlien);
                            break;
                        }
                    }
                }

                //if the player has anywhere on the screen, check if they clicked a tower
                else if (gameState.mTowers != null && !gameState.getmPlacing() && gameState.overTower(new Point(x, y))) {
                    Tower tower = gameState.getTower(new Point(x, y));
                    if (tower != null) {
                        mHud.infoContainer.setInfo(tower);
                    }
                } else {
                    mHud.infoContainer.hideInfo();
                }
            } else { //we are on the start screen
                //the player has clicked level 1
                if (gameState.startScreen.getButtons().get(StartScreen.LEVEL1).contains(x, y)) {
                    gameState.mMap.changeLevel(GameMap.level.level1);
                    gameState.loadLevel(GameMap.level.level1);

                }
                //The player has clicked level 2
                else if (gameState.startScreen.getButtons().get(StartScreen.LEVEL2).contains(x, y)) {
                    gameState.mMap.changeLevel(GameMap.level.level2);
                    gameState.loadLevel(GameMap.level.level2);
                }
                //the player has clicked level 3
                else if (gameState.startScreen.getButtons().get(StartScreen.LEVEL3).contains(x, y)) {
                    gameState.mMap.changeLevel(GameMap.level.level3);
                    gameState.loadLevel(GameMap.level.level3);
                }
                //the player has clicked the rules or the back button
                else if (gameState.startScreen.getButtons().get(StartScreen.RULES).contains(x, y)) {
                    gameState.startScreen.rulesOnOff();

                }
                //the player has clicked the volume button
                else if (gameState.startScreen.getButtons().get(StartScreen.SOUND).contains(x, y)) {
                    gameState.mSound.changeSound();

                }
            }
        }
    }

}