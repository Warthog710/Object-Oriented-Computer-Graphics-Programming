package com.example.towerdefense;

public class DmgDealt
{
    private float plasmaDmg;
    private float laserDmg;
    private float rocketDmg;

    //Constructor
    public DmgDealt()
    {
        this.plasmaDmg = 0;
        this.laserDmg = 0;
        this.rocketDmg = 0;
    }

    //Called to record the dmg dealt to an enemy
    public void recordDmg(GameEngine.dmgType dmgType, float dmg)
    {
        switch (dmgType)
        {
            case plasma:
                plasmaDmg += dmg;
                break;

            case laser:
                laserDmg += dmg;
                break;

            case rocket:
                rocketDmg += dmg;
                break;
        }
    }

    public GameEngine.dmgType getMaxType()
    {
        if (plasmaDmg > laserDmg && plasmaDmg > rocketDmg)
            return GameEngine.dmgType.plasma;

        else if (laserDmg > plasmaDmg && laserDmg > rocketDmg)
            return GameEngine.dmgType.laser;

        else
            return GameEngine.dmgType.rocket;
    }

    //Getters
    public float getPlasmaDmg() { return plasmaDmg; }
    public float getLaserDmg() { return laserDmg; }
    public float getRocketDmg() { return rocketDmg; }
}
