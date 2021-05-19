package com.example.towerdefense;

public class Resistances
{
    private int plasmaResistance;
    private int laserResistance;
    private int rocketResistance;

    public Resistances()
    {
        this.plasmaResistance = 1;
        this.laserResistance = 1;
        this.rocketResistance = 1;
    }

    public void modifyResistances(GameEngine.dmgType dmgType)
    {
        switch (dmgType)
        {
            case plasma:
                plasmaResistance *= 2;
                break;

            case laser:
                laserResistance *= 2;
                break;

            case rocket:
                rocketResistance *= 2;
                break;
        }
    }

    public int getResistances(GameEngine.dmgType dmgType)
    {
        switch (dmgType)
        {
            case plasma:
                return plasmaResistance;

            case laser:
                return laserResistance;

            case rocket:
                return rocketResistance;
        }

        //Default
        return 1;
    }
}
