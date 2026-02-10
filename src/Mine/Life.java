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
import static Mine.NormalizeStrings.normalizeString;


public class Life implements UserInterface, Constants {

    static int startGame(Scanner reader, Players player, MADD madd) {
        Actions sleep = Sleep::new, status = Status::new, eat = Eat::new, drink = Drink::new; // method references
        Quit quit = new Quit(player);
        Fight fight = new Fight();
        int finalMoves = 0, movesLeft = STARTING_MOVES;
        for (int totalMoves = 1; totalMoves <= movesLeft; totalMoves++) {
            var choice = normalizeString(reader.nextLine());
            switch (PossibleMoves.checkInput(choice)) {
                case FIGHT -> {
                    Enemies enemy; // declare enemy in bigger scope so it can be resigned in the try catch block
                    try {
                        enemy = new Enemies(EnemyTypes.randomizeEncounter());
                        if (normalizeString(Arrays.toString(EnemyTypes.getEnemyType(a -> a.isRare).toArray()))
                                .contains(normalizeString(enemy.getName()))) {
                            out.println("rare spotted");
                        }
                        if(normalizeString(Arrays.toString(EnemyTypes.getEnemyType(a->!a.isRare).toArray())).
                                contains(normalizeString(enemy.getName()))){
                            out.println("not rare");
                        }
                    } catch (RuntimeException e) {
                        out.println("Failed generating enemy, using default");
                        enemy = new Enemies(EnemyTypes.getEnemyType(a -> a.isDefault).getFirst());
                    }
                    //new Fight().attack(player, enemy, reader, status);
                    fight.attack(player, enemy, reader, status);
                    if (player.state() == FIGHT_WIN) {
                        out.println("You've defeated the " + enemy.getName() + "!\nYou live for now...");
                        movesLeft += moveLogic();
                        player.setState(RESET);
                    }
                }
                case SLEEP -> sleep.action(player);
                case DRINK -> drink.action(player);
                case EAT ->  eat.action(player);
                case CONDITION -> {
                    status.action(player);
                    totalMoves--;
                    movesLeft--; // don't count towards the final score but still uses a move
                }
                case QUIT -> {
                   // new Quit(null).action(player, reader); // care
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

