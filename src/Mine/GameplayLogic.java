package Mine;

import Acts.Actions;
import Acts.PlayerActions.Fight;
import Shareables.Colours;
import Shareables.RandomGenerator;
import entity.types.Enemies.Enemies;
import entity.types.Players.Players;

import java.util.Scanner;

import static Shareables.Colours.AnsiCodes.*;
import static Shareables.EntityState.FIGHT_WIN;
import static Shareables.EntityState.RESET;
import static entity.types.Enemies.EnemyTypes.*;
import static java.lang.System.out;

public interface GameplayLogic {
    private static int moveLogic() {
        int addMoves = RandomGenerator.randomize(5, 15); // at least 5 moves
        ANSI_HIGH_INTENSITY.printCode();
        out.println("You have gained " + ANSI_PURPLE + addMoves + ANSI_RESET + " moves for destroying your foe in battle!");
        Colours.clear();
        return addMoves;
    }

    static int fightLogic(Fight fight, Players player, Scanner reader) { // refactor this
        Enemies enemy; // declare enemy in bigger scope so it can be reassigned in the try catch block
        try {
            enemy = new Enemies(randomizeEncounter()); // returns an enemy type
        } catch (RuntimeException e) {
            out.println(ANSI_RED + "Failed generating enemy, using default." + ANSI_RESET);
            enemy = new Enemies(getEnemyRarity(enemyTypes -> enemyTypes.isDefault).getFirst()); // overloaded func, if you pass it just the boolean to look for, it will return the 1st available bool
        }
        debug(enemy);
        fight.attack(player, enemy, reader);
        if (player.state() == FIGHT_WIN) {
            out.println("You've defeated the " + enemy.getName() + "!\nYou live for now...");
            player.setState(RESET);
            return moveLogic();
        }
        return 0;
    }

    /**
     * these methods here were used to understand functional interfaces - they dont really achieve much, since most ops can be done by the classes themselves.
     * cant check for damage, since its randomized whenever a new enemy is created, resulting in a discrepancy between the enum and the actual enemy
     */
    private static void debug(Enemies enemy) {
        var checkHealthType = getNumericEnemyData(enemyData -> enemyData.maxHealth, enemy); // TODO: make it so it expected a pred or func
        checkHealthType.ifPresent(map ->
                out.println(ANSI_PURPLE.toString() + map.keySet() + ANSI_RESET + " found. " +
                        "It contains " + ANSI_RED + map.values() + ANSI_RESET + " MaxHealth and " +
                        "can deal at least " + ANSI_BLUE + map.keySet().stream().findFirst().get().minDamage + ANSI_RESET + " damage."));// checks if any values in an enemy, match that of health from the enums and checks if the found matches names also match -- maybe use this to check matches against players?\
        if (getEnemyRarity(enemyType -> enemyType.isRare, enemy))
            out.println(enemy.getName() + " is Rare!");// look for any rare enemyTypes and see if the new generated one is Rare or not based on its declaration in the Enum;
        if (getEnemyRarity(enemyType -> enemyType.isCommon, enemy))
            out.println(enemy.getName() + " is Common!");
    }
}
