package com.example.snake;

//This strategy can only be chosen by setting the silent mode to true when the game sound object is
//created. Intended for debugging purposes.
public class SilentStrategy implements ISound
{
    //Play pseudo sounds.
    @Override
    public void playGetApple() { System.out.println("Playing get_apple.ogg in silent mode."); }

    @Override
    public void playSnakeDeath()
    {
        System.out.println("Playing snake_death.ogg in silent mode.");
    }
}
