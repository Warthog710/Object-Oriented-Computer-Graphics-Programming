package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

/*
Set up and display the "start" or home screen
 */

public class StartScreen
{
    //Class Variables
    private Bitmap background, level1, level2, level3, rules, title, back, soundOn, soundOff, oneBack, twoBack, threeBack;
    private int levelButtonTopPadding, infoButtonPadding, infoButtonHeight, levelButtonPadding;
    private int mTextFormatting, mScreenHeight, mScreenWidth, levelButtonWidth;

    //Button List
    private ArrayList<Rect> buttons;
    static int LEVEL1 = 0;
    static int LEVEL2 = 1;
    static int LEVEL3 = 2;
    static int RULES = 3;
    static int SOUND = 4;

    private boolean showingRules;

    //Set up start screen
    public StartScreen(Context context, Point size)
    {
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;
        levelButtonWidth = (size.x - (mScreenWidth / 90) * 4) / 3;
        int infoButtonWidth = mScreenHeight / 6;
        levelButtonTopPadding = (size.y) / 5;
        levelButtonPadding = infoButtonPadding = mScreenWidth / 90;
        infoButtonHeight = mScreenHeight / 12;
        buttons = new ArrayList<>();
        HelperFactory helperFactory = new HelperFactory(context);
        this.oneBack = helperFactory.bitmapMaker(R.drawable.one_back, levelButtonWidth);
        this.twoBack = helperFactory.bitmapMaker(R.drawable.two_back, levelButtonWidth);
        this.threeBack = helperFactory.bitmapMaker(R.drawable.three_back, levelButtonWidth);
        this.level1 = helperFactory.bitmapMaker(R.drawable.level1, levelButtonWidth);
        this.level2 = helperFactory.bitmapMaker(R.drawable.level2, levelButtonWidth);
        this.level3 = helperFactory.bitmapMaker(R.drawable.level3, levelButtonWidth);
        this.rules = helperFactory.bitmapMaker(R.drawable.rules, infoButtonWidth);
        this.back = helperFactory.bitmapMaker(R.drawable.back, infoButtonWidth);
        this.soundOff = helperFactory.bitmapMaker(R.drawable.sound_off, infoButtonWidth);
        this.soundOn = helperFactory.bitmapMaker(R.drawable.sound_on, infoButtonWidth);

        this.background = BitmapFactory.decodeResource(context.getResources(), R.drawable.start_page);
        this.title = BitmapFactory.decodeResource(context.getResources(), R.drawable.title);
        title = Bitmap.createScaledBitmap(title, mScreenWidth, levelButtonTopPadding, false);
        makeButtons();
    }

    //Make all the buttons
    private void makeButtons() {
        //Lvl 1
        Rect level1 = new Rect(
                levelButtonPadding,
                levelButtonTopPadding,
                levelButtonPadding + levelButtonWidth,
                levelButtonTopPadding + levelButtonWidth);

        //Lvl 2
        Rect level2 = new Rect(
                levelButtonPadding * 2 + levelButtonWidth,
                levelButtonTopPadding,
                levelButtonPadding * 2 + levelButtonWidth * 2,
                levelButtonTopPadding + levelButtonWidth);

        //Lvl 3
        Rect level3 = new Rect(
                levelButtonPadding * 3 + levelButtonWidth * 2,
                levelButtonTopPadding,
                levelButtonPadding * 2 + levelButtonWidth * 3,
                levelButtonTopPadding + levelButtonWidth);

        //Game rules
        Rect gameInfo = new Rect(
                mScreenWidth - levelButtonPadding - infoButtonHeight * 2,
                mScreenHeight - infoButtonPadding - infoButtonHeight * 2,
                mScreenWidth - levelButtonPadding,
                mScreenHeight - infoButtonPadding);

        //Sound on/off
        Rect sound = new Rect(
                levelButtonPadding,
                mScreenHeight - infoButtonPadding - infoButtonHeight * 2,
                infoButtonHeight * 2 + levelButtonPadding,
                mScreenHeight - infoButtonPadding);

        //Add all the buttons to the list
        buttons.add(LEVEL1, level1);
        buttons.add(LEVEL2, level2);
        buttons.add(LEVEL3, level3);
        buttons.add(RULES, gameInfo);
        buttons.add(SOUND, sound);
    }

    //Display the start screen.
    void draw(Canvas canvas, Paint paint, boolean sound)
    {
        canvas.drawBitmap(background, 0, 0, null);

        //If the rules should be shown...
        if (showingRules)
        {
            //Set color to white
            paint.setColor(GameWorld.white);
            paint.setTextSize(mTextFormatting);

            //Display all the rules
            canvas.drawText("You are the space commander charged with protecting planets from alien invasion. Your mission is to keep the",
                    0, buttons.get(RULES).top - (mTextFormatting + 2) * 16, paint);
            canvas.drawText("aliens from reaching your base. You must survive 5 waves until reinforcements arrive. If 20 aliens reach",
                    0, buttons.get(RULES).top - (mTextFormatting + 2) * 14, paint);
            canvas.drawText("your headquarters they will over run you and you will lose the game. At your disposal are 3 types of towers:",
                    0, buttons.get(RULES).top - (mTextFormatting + 2) * 12, paint);
            canvas.drawText("a plasma tower that shoots very quickly with low damage, a laser tower that deals medium damage with an ",
                    0, buttons.get(RULES).top - (mTextFormatting + 2) * 10, paint);
            canvas.drawText("average rate of fire, and finally a rocket tower that deals massive damage with its heat seeking rockets, ",
                    0, buttons.get(RULES).top - (mTextFormatting + 2) * 8, paint);
            canvas.drawText("however it has an incredibly low rate of fire. Your foes, are smart, over time they will build a resistance ",
                    0, buttons.get(RULES).top - (mTextFormatting + 2) * 6, paint);
            canvas.drawText("to whichever tower they are taking the most damage from. Prepare accordingly. Finally, your towers will target ",
                    0, buttons.get(RULES).top - (mTextFormatting + 2) * 4, paint);
            canvas.drawText("the strongest enemy in range, use this to your advantage. Best of luck commander.", //rules
                    0, buttons.get(RULES).top - (mTextFormatting + 2) * 2, paint);

            //Set color to grey
            paint.setColor(GameWorld.grey);
            paint.setAlpha(0xBF);
            canvas.drawRect(buttons.get(RULES), paint);
            canvas.drawBitmap(back, buttons.get(RULES).left, buttons.get(RULES).top, null);

        }

        //Else, draw the main menu
        else
        {
            paint.setColor(GameWorld.grey);
            paint.setAlpha(0xBF);

            //Draw a button...
            canvas.drawRect(buttons.get(RULES), paint);

            //Draw the button backgrounds
            canvas.drawBitmap(oneBack, buttons.get(LEVEL1).left, buttons.get(LEVEL1).top, paint);
            canvas.drawBitmap(twoBack, buttons.get(LEVEL2).left, buttons.get(LEVEL2).top, paint);
            canvas.drawBitmap(threeBack, buttons.get(LEVEL3).left, buttons.get(LEVEL3).top, paint);

            //Draw all the bitmaps
            canvas.drawBitmap(level1, buttons.get(LEVEL1).left, buttons.get(LEVEL1).top, null);
            canvas.drawBitmap(level2, buttons.get(LEVEL2).left, buttons.get(LEVEL2).top, null);
            canvas.drawBitmap(level3, buttons.get(LEVEL3).left, buttons.get(LEVEL3).top, null);
            canvas.drawBitmap(rules, buttons.get(RULES).left, buttons.get(RULES).top, null);
            canvas.drawBitmap(title, 0, 0, null);
        }

        //Draw sound button
        canvas.drawRect(buttons.get(SOUND), paint);

        //If the sounds is on...
        if (sound)
            canvas.drawBitmap(soundOn, buttons.get(SOUND).left, buttons.get(SOUND).top, null);

        //Else the sound is off...
        else
            canvas.drawBitmap(soundOff, buttons.get(SOUND).left, buttons.get(SOUND).top, null);
    }

    //Return a list of the buttons
    public ArrayList<Rect> getButtons()
    {
        return buttons;
    }

    //Cycle showing the rules
    public void rulesOnOff()
    {
        showingRules = !showingRules;
    }
}
