package Acts;

import entity.types.Enemies.Enemies;

import static Shareables.Colours.AnsiCodes.*;
import static Shareables.RandomGenerator.rand;
import static Shareables.RandomGenerator.randomize;
import static entity.types.Enemies.EnemyTypes.getEnemyRarity;
import static entity.types.Enemies.EnemyTypes.getNumericEnemyData;
import static java.lang.System.out;

public final class Constants {
    Constants(){}
    public final static double MIN_XP_GAIN = 10;
    public final static double MIN_ENERGY_COST = 10;
    public final static double VULNERABILITY_DEBUFF = 2.25;
    public static final double COST = (randomize(rand.nextDouble(5.2364), MIN_ENERGY_COST));
    public final static int GAUNTLET_ENERGY_CONSUMPTION = randomize(1,5);
    public final static int GAUNTLET_HEALTH_CONSUMPTION = randomize(1, 10);
    public final static int GAUNTLET_WIN_THRESHOLD = 1300;

    public static void debug(Enemies enemy) {
        var checkHealthType = getNumericEnemyData(enemyData -> enemyData.maxHealth, enemy);
        checkHealthType.ifPresent(map ->
                out.println(ANSI_PURPLE.toString() + map.keySet() + ANSI_RESET + " found. " +
                        "It contains " + ANSI_RED + map.values() + ANSI_RESET + " MaxHealth and " +
                        "can deal at least " + ANSI_BLUE + map.keySet().stream().findFirst().get().minDamage + ANSI_RESET + " damage."));
        if (getEnemyRarity(enemyType -> enemyType.isRare, enemy))
            out.println(enemy.getName() + " is Rare!");
        if (getEnemyRarity(enemyType -> enemyType.isCommon, enemy))
            out.println(enemy.getName() + " is Common!");
    }
}
