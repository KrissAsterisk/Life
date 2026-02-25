package Mine;

import Acts.*;

import Acts.PlayerActions.*;
import entity.types.Enemies.Enemies;


import entity.types.Players.Players;
import Shareables.*;

import java.io.IOException;
import java.util.Scanner;

import static Shareables.Colours.AnsiCodes.*;
import static Mine.Constants.*;
import static Shareables.NormalizeStrings.normalize;
import static Shareables.EntityState.*;
import static entity.types.Enemies.EnemyTypes.*;
import static java.lang.System.*;

interface GameStatus { // this interface is WAY too important - needs to be split up

    private static int fightLogic(Fight fight, Players player, Scanner reader, Actions status) { // refactor this
        Enemies enemy; // declare enemy in bigger scope so it can be reassigned in the try catch block
        try {
            enemy = new Enemies(randomizeEncounter()); // returns an enemy type
        } catch (RuntimeException e) {
            out.println("Failed generating enemy, using default");
            enemy = new Enemies(getEnemyRarity(enemyTypes -> enemyTypes.isDefault).getFirst()); // overloaded func, if you pass it just the boolean to look for, it will return the 1st available bool
        }
/**
 *      these methods here were used to understand functional interfaces - they dont really achieve much, since most ops can be done by the classes themselves
 */
        var checkHealthType = getEnemyData(enemyTypes -> enemyTypes.maxHealth, enemy); // TODO: make it so it expected a pred or func
        if(checkHealthType.isPresent()) out.println(checkHealthType.get() + " found. It contains MaxHealth."); // checks if any values in an enemy, match that of energy from the enums and checks if the found matches names also match -- maybe use this to check matches against players?
        else out.println("Type not found. How did you pull that off?");
        if (getEnemyRarity(enemyTypes -> enemyTypes.isRare, enemy))
            out.println(enemy.getName() + " is Rare!");// look for any rare enemyTypes and see if the new generated one is Rare or not based on its declaration in the Enum;
        if (getEnemyRarity(enemyTypes -> enemyTypes.isCommon, enemy))
            out.println(enemy.getName() + " is Common!");


//        if (Arrays.toString(getEnemyData(type -> type.maxEnergy).toArray()) != null) { //useless until we can use keys later on
//            out.println(Arrays.toString(getEnemyData(type -> type.maxEnergy).toArray())); // prints all possible values for the type specified
//        }

        fight.attack(player, enemy, reader, status);
        if (player.state() == FIGHT_WIN) {
            out.println("You've defeated the " + enemy.getName() + "!\nYou live for now...");
            player.setState(RESET);
            return moveLogic();
        }
        return 0;
    }


    static int startGame(Scanner reader, Players player) {
        Actions sleep = Sleep::new, status = Status::new, eat = Eat::new, drink = Drink::new; // method references
        Quit quit = new Quit();
        Fight fight = new Fight();
        int finalMoves = 0, movesLeft = STARTING_MOVES; //TODO make moves static and part of player class?
        for (int totalMoves = 1; totalMoves <= movesLeft; totalMoves++) {
            player.levelUpCheck(player);
            var choice = normalize(reader);
            if (movesLeft - totalMoves == 5) {
                UserInterface.lowMovesWarning(movesLeft, totalMoves);
            }
            switch (PossibleMoves.checkInput(choice)) {
                case FIGHT -> // dont get a move for retreating
                        movesLeft += fightLogic(fight, player, reader, status);// the lambdas don't really do anything right now; I have no idea how to actually make them useful YET

                case SLEEP -> sleep.action(player);
                case DRINK -> drink.action(player);
                case EAT -> eat.action(player);
                case CONDITION -> {
                    status.action(player);
                    totalMoves--;
                    movesLeft--; // don't count towards the final score but still uses a move
                }
                case QUIT -> {
                    quit.action(player, reader);
                    totalMoves--; // dont count towards total moves
                }
                case null -> {
                    out.println("Choose a number between 0 and 7. You lose a move every time you mistype.");
                    totalMoves--;
                    movesLeft--; // don't count for the final score.
                }
            }
            if (player.state() == DEAD || player.state() == EXIT_GAME) {
                //TODO: run the "status check" on a separate thread when main game loop is running
                break;
            }
            if(movesLeft - totalMoves != 0) UserInterface.showChoices(movesLeft, totalMoves); // show choices after every move except the last one
            finalMoves = totalMoves; // set it after every move for the quit
        }
        finalMoves++;       // count the last move before death
        return finalMoves;
    }

    static int endGame(Players player, int totalMoves) {
        if (player.state() != ALIVE & player.state() != EXIT_GAME & player.state() != RESET & player.state() != COWARD) { //mb make it so that if reset player = alive?
            ANSI_HIGH_INTENSITY.printCode();
            ANSI_RED.printCode();
            out.println("You died! Game over!");
            Colours.clear();
        }
        out.println("In total you've had " + totalMoves + " moves!");
        return totalMoves;
    }

    static void retryGame(Scanner reader) throws Exception{ // move this somewhere else
        out.println("Would you like to try again?");
        String ch = NormalizeStrings.normalize(reader);
        if (ch.contains("y")) {
            Life.main(); // TODO: care
        } else if (ch.contains("n")) {
            out.println("See ya!");
            reader.close();
            exit(0);
        } else {
            ANSI_RED.printCode();
            out.println("Please type in only Yes or No");
            Colours.clear();
            retryGame(reader);
        }
    }
}
