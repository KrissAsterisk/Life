package Ents;

import java.util.Random;

public enum EnemyTypes {
    GOBLIN("Goblin", 0, 150, 50, 50),
    DWARF("Dwarf", 1, 50, 100, 25),
    MONGREL("Mongrel", 2, 100, 1, 100),
    ASSASSIN("Assassin", 3, 25, 200, 30),
    NEANDERTHAL("Neanderthal", 4, 200, 100, 25),
    WEREWOLF("Werewolf", 5, 250, 300, 10),
    ROBOT("Robot", 6, 400, 30, 5),
    FLESHWALL("Flesh Wall", 7, 20, 0, 0);

    public final String namedEnemy;
    public final int position;
    public final double maxHealth, maxEnergy, minDamage;

    EnemyTypes(String name, int position, double maxHealth, double maxEnergy, double minDamage){
        this.namedEnemy = name;
        this.position = position;
        this.maxHealth = maxHealth;
        this.maxEnergy = maxEnergy;
        this.minDamage = minDamage;
    }

    public String getEnemyName(){
        return namedEnemy;
    }

    public Object[] getData(){
        return new Object[]{namedEnemy, maxHealth, maxEnergy, minDamage};
    }

    public static EnemyTypes randomizeEncounter(){
        var randomVariable = new Random().nextInt(0, EnemyTypes.values().length);
        for(var enemy : EnemyTypes.values()){
            if(randomVariable == enemy.position){
                return enemy;
            }
        }
        return randomizeEncounter(); // uhhhhhhhhhhhhh
    }
}

