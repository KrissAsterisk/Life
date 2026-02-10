package Ents;

import java.util.ArrayList;
import java.util.Random;

public enum EnemyTypes {
    GOBLIN("Goblin", 0, 150, 50, 50, true, true, false),
    DWARF("Dwarf", 1, 50, 100, 25, false, true, false),
    MONGREL("Mongrel", 2, 100, 1, 100, false, false, false),
    ASSASSIN("Assassin", 3, 25, 200, 30, false, true, false),
    NEANDERTHAL("Neanderthal", 4, 200, 100, 25, false, true, false),
    WEREWOLF("Werewolf", 5, 250, 300, 10, false, false, true),
    ROBOT("Robot", 6, 400, 30, 5, false, true, true),
    FLESHWALL("Flesh Wall", 7, 20, 0, 0, false, false, true);

    private final String namedEnemy;
    private final int position;
    private final double maxHealth, maxEnergy, minDamage;
    public final boolean isDefault, isCommon, isRare;

    EnemyTypes(String name, int position, double maxHealth, double maxEnergy, double minDamage, boolean isDefault, boolean isCommon, boolean isRare) {
        this.namedEnemy = name;
        this.position = position;
        this.maxHealth = maxHealth;
        this.maxEnergy = maxEnergy;
        this.minDamage = minDamage;
        this.isDefault = isDefault;
        this.isCommon = isCommon;
        this.isRare = isRare;
    }

    public static ArrayList<EnemyTypes> getEnemyType(CheckTrait checker) {
        ArrayList<EnemyTypes> soughtTypes = new ArrayList<>();
        for (var enemy : EnemyTypes.values()) {
            if (checker.checkTrait(enemy)) {
                soughtTypes.add(enemy);
            }
        }
        return soughtTypes;
    }

    public Object[] getData() {
        return new Object[]{namedEnemy, maxHealth, maxEnergy, minDamage};
    }

    public static EnemyTypes randomizeEncounter() throws RuntimeException {
        var randomVariable = new Random().nextInt(0, EnemyTypes.values().length);
        for (var enemy : EnemyTypes.values()) {
            if (randomVariable == enemy.position) {
                return enemy;
            }
        }
        throw new RuntimeException();
    }
}

