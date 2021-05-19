package com.example.towerdefense;

public class AlienFactory
{
    Alien alien;

    enum enemyType {drone, soldier, behemoth};

    //Select correct enemy to build, and the corresponding movement strategy.
    public AlienFactory(enemyType type, PathPoints start, Resistances resistances)
    {
        //Build a soldier
        if (type.equals(enemyType.soldier))
        {
            this.alien = new Enemy.EnemyBuilder()
                    .setHealth(50)
                    .setLocation(start)
                    .setAttributeSize(48)
                    .setResist(resistances)
                    .setMoney(25)
                    .setInfo("Soldier: medium speed with medium health")
                    .setResist(resistances)
                    .attachHealthBar(10)
                    .setType(type)
                    .attachMovementStrategy(type)
                    .build();
        }

        //Build a behemoth
        if (type.equals(enemyType.behemoth))
        {
            this.alien = new Enemy.EnemyBuilder()
                    .setHealth(150)
                    .setLocation(start)
                    .setAttributeSize(48)
                    .setResist(resistances)
                    .setMoney(50)
                    .setInfo("Behemoth: low speed with a large health pool")
                    .attachHealthBar(10)
                    .setType(type)
                    .attachMovementStrategy(type)
                    .build();
        }

        //Build a drone
        if (type.equals(enemyType.drone))
        {
            this.alien = new Enemy.EnemyBuilder()
                    .setHealth(10)
                    .setLocation(start)
                    .setAttributeSize(48)
                    .setResist(resistances)
                    .setMoney(10)
                    .setInfo("Drone: fast but with low health")
                    .attachHealthBar(10)
                    .setType(type)
                    .attachMovementStrategy(type)
                    .build();
        }
    }

    //Return the created alien
    public Alien getAlien()
    {
        return this.alien;
    }

}
