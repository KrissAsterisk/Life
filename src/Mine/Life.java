package Mine;

import Acts.*;
import Ents.*;
import Ents.EnemyTypes;

import java.time.*;
import java.time.temporal.ChronoUnit;

import java.util.*;

import static Mine.Colours.AnsiCodes.*;
import static Ents.EntityState.*;
import static java.lang.System.*;


public class Life implements UserInterface, Constants {

    static int startGame(Scanner reader, Players player, MADD madd) {
        int finalMoves = 0, movesLeft = STARTING_MOVES;
        for (int totalMoves = 1; totalMoves <= movesLeft; totalMoves++) {
                var choice = reader.nextLine().replaceAll(" ", "").toLowerCase();

                switch (PossibleMoves.checkInput(choice)) {
                    case FIGHT -> {
                        //new Fight().action(player);
                        var enemy  = new Enemies(EnemyTypes.randomizeEncounter());
                        new Fight().attack(player, enemy ,reader);
                        if (player.state() == FIGHT_WIN) {
                            out.println("You've defeated the " + enemy.getName() + "!\nYou live for now...");
                            movesLeft += moveLogic();
                            player.setState(ALIVE); // TODO !set back to alive if fled
                        }

                    }
                    case SLEEP -> new Sleep().action(player);
                    case DRINK -> new Drink().action(player);
                    case EAT -> new Eat().action(player);
                    case CONDITION -> { new Status().action(player);
                        totalMoves--;
                        movesLeft--; // don't count towards the final score but still uses a move
                    }
                    case QUIT -> { new Quit().action(player, reader); // needs to be in catch block
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
                UserInterface.showChoices(movesLeft, totalMoves); // show choices after every move
                if (movesLeft - totalMoves == 5) {
                    UserInterface.lowMovesWarning(madd, movesLeft, totalMoves);
                }
                finalMoves = totalMoves; // set it after every move for the quit
        }
        finalMoves++;       // count the last move before death
        return finalMoves;
    }

    static void endGame(Players player, int totalMoves) {
        if (player.state() != EXIT_GAME) {
            ANSI_HIGH_INTENSITY.printCode();
            ANSI_RED.printCode();
            out.println("You died! Game over!");
            Colours.clear();
        }
        out.println("In total you've had " + totalMoves + " moves!");
    }

    public static void main() throws NoSuchElementException {
        Colours.clear(); // initialize enum
        MADD madd = new MADD();
        var reader = new Scanner(in);
        var player = new Players(Player.initPlayer(reader).name(), ALIVE, DEFAULT_FOOD_POINTS, DEFAULT_WATER_POINTS, DEFAULT_ENERGY_POINTS, DEFAULT_HEALTH_POINTS);
        UserInterface.showChoices(STARTING_MOVES, 0);
        var gameStartTime = Instant.now();
        endGame(player, startGame(reader, player, madd)); // start the game
        var gameOverTime = Instant.now();
        out.println("You lasted: " + ANSI_HIGH_INTENSITY.colourCode() + (ChronoUnit.MINUTES.between(gameStartTime, gameOverTime)) + " minutes and " + (ChronoUnit.SECONDS.between(gameStartTime, gameOverTime)) + " seconds.");
        //HighScores highScores = new HighScores();
        Colours.clear();
        //highScores.filePrintHS(player.getName(), temp); TODO:fix the sorting
        reader.close();
        exit(0);
    }
}

