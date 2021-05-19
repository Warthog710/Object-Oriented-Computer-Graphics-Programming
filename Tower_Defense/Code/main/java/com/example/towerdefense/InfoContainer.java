package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
/*
Container to hold information about towers, aliens, etc
 */

public class InfoContainer {
    public Rect upgradeButton, background;
    private Tower mTower;
    private Alien mAlien;
    private Circle towerRange;
    private state currentState;
    private String line1, line2, line3, buttonLine1, buttonLine2;
    private boolean hideInfo;
    private int textStart;
    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;
    private int buttonWidth;
    private int buttonHeight;
    private int buttonPadding;
    //Create the info container and set size...
    public InfoContainer(Point size) {
        this.hideInfo = true;
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;
        buttonWidth = mScreenWidth / 14;
        buttonHeight = mScreenHeight / 9;
        buttonPadding = mScreenWidth / 90;

        //Tower Button
        upgradeButton = new Rect(
                mScreenWidth - buttonPadding - buttonWidth,
                mScreenHeight - buttonHeight - buttonPadding,
                mScreenWidth - buttonPadding,
                mScreenHeight - buttonPadding);

        textStart = mScreenWidth - buttonPadding - buttonWidth - mTextFormatting * 18;

        //Tower Button
        background = new Rect(
                textStart - buttonPadding,
                mScreenHeight - buttonHeight - buttonPadding * 2,
                mScreenWidth,
                mScreenHeight);
    }

    //Set the info if the data is a tower.
    public void setInfo(Tower tower) {
        this.hideInfo = false;
        this.mTower = tower;
        currentState = state.TOWER;
        line1 = mTower.mName;
        line2 = mTower.mDescription;
        line3 = "Damage: " + mTower.mDamage + " | Rate of Fire: " + mTower.mRateOfFire + " | Range: " + mTower.mRange;
        buttonLine1 = "Upgrade";
        buttonLine2 = "$" + mTower.mUpgradeCost;
        towerRange = new Circle(tower.getLocation(), tower.mRange);
    }

    //Set the info if the data is a tower type
    public void setInfo(Tower.TowerType towerType) {
        this.hideInfo = false;
        currentState = state.PLACING;
        buttonLine1 = "Cancel";
        buttonLine2 = "Placement";

        switch (towerType) {
            case LASER:
                line1 = "Touch to place a level 1 laser tower";
                line2 = "Laser towers fire at medium speed with decent damage";
                line3 = "Cost " + 100 + " | Damage: " + 15 + " | Rate of Fire: " + 1 + " | Range: " + 500;
                break;
            case PLASMA:
                line1 = "Touch to place a level 1 plasma tower";
                line2 = "Plasma towers fire quickly but have low damage";
                line3 = "Cost " + 50 + " | Damage: " + 5 + " | Rate of Fire: " + 4 + " | Range: " + 300;
                break;
            case ROCKET:
                line1 = "Touch to place a level 1 rocket tower";
                line2 = "Rocket towers fire slowly but deal huge damage";
                line3 = "Cost " + 200 + " | Damage: " + 50 + " | Rate of Fire: " + 0.5 + " | Range: " + 500;
                break;
        }
    }

    //Set the info if the data is an alien
    public void setInfo(Alien alien) {
        this.hideInfo = false;
        this.mAlien = alien;
        currentState = state.ALIEN;
        line1 = "";
        line2 = mAlien.getInfo();
        line3 = mAlien.getResistance();
        buttonLine1 = "";
        buttonLine2 = "";
    }

    //Draw the info
    public void draw(Canvas canvas, Paint paint) {
        if (!hideInfo) {
            paint.setColor(GameWorld.grey);
            canvas.drawRect(background, paint);

            if (currentState == state.TOWER) {
                //Draw range
                towerRange.draw(canvas, paint); //draw range
                line3 = "Damage: " + mTower.mDamage + " | Rate of Fire: " + mTower.mRateOfFire + " | Range: " + mTower.mRange; //update line 3
                buttonLine2 = "$" + mTower.mUpgradeCost; //update cost of upgrade
                towerRange.draw(canvas, paint); //draw range
            }
            paint.setColor(GameWorld.black); //set color to black
            if (currentState != state.ALIEN) { //if the info is not a alien, show the button
                canvas.drawRect(upgradeButton.left, upgradeButton.top, upgradeButton.right, upgradeButton.bottom, paint);
            }
            paint.setColor(GameWorld.white);
            paint.setTextSize(mTextFormatting / 1.5f);
            canvas.drawText(buttonLine1,
                    mScreenWidth - buttonPadding - buttonWidth, mScreenHeight - buttonHeight, paint);
            canvas.drawText(buttonLine2,
                    mScreenWidth - buttonPadding - buttonWidth, mScreenHeight - buttonHeight + mTextFormatting, paint);
            //draw stats
            paint.setColor(GameWorld.black);
            canvas.drawText(line1,
                    textStart, mScreenHeight - mTextFormatting * 3, paint);
            canvas.drawText(line2,
                    textStart, mScreenHeight - mTextFormatting * 2, paint);
            canvas.drawText(line3,
                    textStart, mScreenHeight - mTextFormatting, paint);
        }

    }

    public void buttonClick(GameWorld gameWorld) { //button has been clicked
        if (currentState == state.TOWER) { //tower is showing so upgrade button was hit
            towerUpgrade(gameWorld);
        } else { //cancel placement button so hide state
            hideInfo();

        }

    }

    public void hideInfo() { //hide the info
        hideInfo = true;
    }

    private void towerUpgrade(GameWorld gameWorld) { //upgrade the tower
        if (gameWorld.getCash() >= this.mTower.mUpgradeCost) {
            gameWorld.loseCash(this.mTower.mUpgradeCost);
            this.mTower.upgrade();
            this.towerRange = new Circle(mTower.mLocation, mTower.mRange);
            this.mTower.mUpgradeCost = (int) (this.mTower.mUpgradeCost * 1.3);
        } else {
            gameWorld.error(GameWorld.error.MONEY);
        }
    }

    private enum state {TOWER, PLACING, ALIEN}
}
