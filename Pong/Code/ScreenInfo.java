package com.example.pongactivity;

//This simple class was added to encapsulate the screen size. Instead of passing around int's an object
//would server our purpose better. In addition, it calculates and hold the fontSize and Margin based on the
//screen information that is passed during creation.
class ScreenInfo
{
    //Class member variables. Note, set them to private as there is no need to change them once they are created.
    private int height;
    private int width;
    private int fontSize;
    private int fontMargin;
    private int debugSize;
    private final int debugLocation = 150;

    //Class constructor, takes an X and Y location saves them, and sets the font, margin, and debug sizes.
    ScreenInfo(int x, int y)
    {
        this.height = y;
        this.width = x;
        this.fontSize = this.width / 20;
        this.fontMargin = this.width / 75;
        this.debugSize = this.width / 40;
    }

    //Getter methods for our variables. Note, setters are not required because we don't intend to change these values once they are
    //set by the constructor.
    int GetHeight() { return height; }
    int GetWidth() { return width; }
    int GetFontSize() { return fontSize; }
    int GetFontMargin() { return fontMargin; }
    int GetDebugSize() { return debugSize; }
    int GetDebugLocation() { return debugLocation; }
}
