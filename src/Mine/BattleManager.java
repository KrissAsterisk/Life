package Mine;

import Acts.PlayerActions.Fight;
import Shareables.Colours;
import entity.types.Enemies.Enemies;
import entity.types.Players.Players;

import java.util.Scanner;

import static Shareables.Colours.AnsiCodes.*;
import static Shareables.EntityState.*;
import static Shareables.RandomGenerator.randomize;
import static entity.types.Enemies.EnemyTypes.*;
import static java.lang.System.out;


/**
 * The BattleManager class is responsible for managing battles between players and enemies.
 * It handles enemy generation, battle interactions, and the resulting outcomes (e.g., win, retreat, or loss).
 * The class operates primarily through interaction with a {@link Players} object and user input via a {@link Scanner}.
 */
public final class BattleManager {
    private BattleManager(){}
    public static int startFight(Players player, Scanner reader) {
        var fight = new Fight();
        Enemies enemy;
        try {
            enemy = new Enemies(randomizeEncounter());
        } catch (RuntimeException e) {
            out.println(ANSI_RED + "Failed generating enemy, using default." + ANSI_RESET);
            enemy = new Enemies(getEnemyRarity(enemyTypes -> enemyTypes.isDefault).getFirst());
        }
        enemy.printEnemyName();
        debug(enemy);
        fight.attack(player, enemy, reader);
        if (player.state() == FIGHT_WIN) {
            out.println("You've defeated the " + enemy.getName() + "!\nYou live for now...");
            player.setState(RESET);
            return calculateMovesGained();
        }
        if (player.state() == COWARD) {
            player.setState(RESET);
            var movesToLose = randomize(5, 15);
            out.println("You've lost " + movesToLose + " moves trying to get away!");
            return -movesToLose;
        }
        return 0;
    }

    private static int calculateMovesGained() {
        int addMoves = randomize(5, 15);
        ANSI_HIGH_INTENSITY.printCode();
        out.println("You have gained " + ANSI_PURPLE + addMoves + ANSI_RESET + " moves for destroying your foe in battle!");
        Colours.clear();
        return addMoves;
    }

    private static void debug(Enemies enemy) {
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
