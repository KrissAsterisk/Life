package Mine.Engine;

import Acts.PlayerActions.Explore;
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

public final class Manager {
    private Manager(){}
    public static int startFight(Players player, Scanner reader) {
        new Explore().explore(player, reader);
        if (player.state() == FIGHT_WIN) {
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
        int movesToAdd = randomize(5, 15);
        ANSI_HIGH_INTENSITY.printCode();
        out.println("You have gained " + ANSI_PURPLE + movesToAdd + ANSI_RESET + " moves for destroying your foe in battle!");
        Colours.clear();
        return movesToAdd;
    }

}
