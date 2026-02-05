package Mine;


import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import static Mine.Colours.AnsiCodes.*;
import java.time.*;

import static java.lang.System.*;
import static java.lang.Thread.sleep;


public class Life {
    private static final int  FIGHT = 1, SLEEP = 2, DRINK = 3, EAT = 4, CONDITION = 5, QUIT = 6;
    private static final int STARTING_MOVES = 50;

    final record Player(String name, Scanner reader){
        Player { // compact constructor!!
            if (name.equals("Asterisk")) {
                ANSI_RED.printCode(); ANSI_HIGH_INTENSITY.printCode();
                out.println("The name " + name + " is reserved.");
                Colours.clear();
                startGame(reader);
            }
            else {
                out.println(ANSI_GREEN.colourCode()+ "Welcome to the game, " + ANSI_YELLOW.colourCode() + name + ANSI_GREEN.colourCode() + "!"); // welcome the player anytime they "sign in"
                Colours.clear(); // less boilerplate yuppie
                nameChange(reader);
            }
        }
    }

    static void nameChange(Scanner reader){
        out.println("\nWould you like to change your name?");
        if (reader.nextLine().replaceAll(" ", "").toLowerCase().contains("yes")) {
            out.println("Input your new name: ");
            new Player(reader.nextLine(), reader); // create the record name anew, keeping it immutable
            Colours.clear();
        } else {
            out.println(ANSI_RED.colourCode() + "Name locked in.\n");
            Colours.clear();
        }
    }

    static String startGame(Scanner reader){
        out.println("Player1, your name: ");
        var player1 = new Player(reader.nextLine(), reader);
        return player1.name();
    }

    public static void main(String[] args) throws NoSuchElementException, IOException {
        Actions actions = new Actions();
        Colours.clear(); // initialize enum
        MADD madd = new MADD();
        int playerState, finalMoves = 0, movesLeft = STARTING_MOVES, misinputCounter = 0;
        var reader = new Scanner(in);
        actions.getpName(startGame(reader)); // game starts here - maybe pass the name onto actions' methods?
        Actions.showChoices(STARTING_MOVES, 0);
        var choice = 0;
        var gameStartTime = Instant.now();
        for (int totalMoves = 1; totalMoves <= movesLeft; totalMoves++) {
            try {
                choice = reader.nextInt();
                if (choice > 6 || choice < 1) {
                    out.println("Choose a number between 0 and 7. You lose a move every time you mistype.");
                    totalMoves--;
                    movesLeft--; // don't count for final score.
                }
                else switch (choice) {
                    case FIGHT -> {
                        movesLeft += actions.fight();
                        if (movesLeft < 0) {
                            continue;
                        }
                    }
                    case SLEEP -> {
                        if ((playerState = actions.sleep()) < 0) {
                            movesLeft = playerState; // runs status check which on top of giving current stats, check if you're dead TODO: run the "death check" on a separate thread at all times
                            continue;
                        }
                    }
                    case DRINK -> {
                        if ((playerState = actions.drinkWater()) < 0) {
                            movesLeft = playerState;
                            continue; // runs the for loop condition again, in case the player died, to exit it by setting available moves to a non positive int
                        }
                    }
                    case EAT -> {
                        if ((playerState = actions.eatFood()) < 0) {
                            movesLeft = playerState;
                            continue;
                        }
                    }
                    case CONDITION -> {
                        actions.statusReport();
                        totalMoves--;
                        movesLeft--; // don't count towards the final score but still uses a move
                    }
                    case QUIT -> {
                        if ((actions.endGame(reader)) == 1) { // needs to be in catch block
                            totalMoves--; // dont count towards total moves
                            Actions.showChoices(movesLeft, totalMoves);
                        } else {
                            //out.println("In total you've had " + finalMoves + " moves!");
                            //HighScores highScores = new HighScores(); // TODO: rework this mb with inheritance
                            //highScores.filePrintHS(player1.name(), temp);
                            //exit(0);
                            movesLeft = -2;
                        }
                        continue;
                    }
                }
                Actions.showChoices(movesLeft, totalMoves);
                if (movesLeft - totalMoves == 5) {
                    out.printf("%s%s+----------------------+%s\n%s%s|      %s %s%sWARNING!%s %s      |%s\n%s%s|You have 5 moves left!|%s\n%s%s+----------------------+%s%n", Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW.colourCode(), ANSI_RESET.colourCode(), Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW.colourCode(), Colours.ANSI_RED_BACKGROUND, Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW.colourCode(), Colours.ANSI_RED_BACKGROUND, Colours.ANSI_BLACK_BACKGROUND, ANSI_RESET.colourCode(), Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW.colourCode(), ANSI_RESET.colourCode(), Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW.colourCode(), ANSI_RESET.colourCode());
                    try {
                        sleep(2000);
                        for (int s = 0; s < 25; s++) {
                            madd.rngC(20);
                            sleep(100);
                        }
                        Actions.showChoices(movesLeft, totalMoves);
                    } catch (InterruptedException e) {
                        out.println("PATIENCE!");
                    }
                }
                finalMoves = totalMoves; // set it after every move for quit
            }
            catch (InputMismatchException e) {
                ANSI_RED.printCode();
                if (misinputCounter <= 5) {
                    out.println("Please input a number and nothing else.");
                    reader = new Scanner(in); // mandatory
                    Colours.clear();
                    misinputCounter++;
                } else {
                    out.println("Refrain from spamming non-integers.");
                    Colours.clear();
                    exit(-2);
                }
            }
        }
        finalMoves++; // count the last move before death
        if(movesLeft < -2) {
            ANSI_HIGH_INTENSITY.printCode();
            ANSI_RED.printCode();
            out.println("You died! Game over!");
            Colours.clear();
        }
        out.println("In total you've had " + finalMoves + " moves!");
        var gameOverTime = Instant.now();
        out.println("You lasted: " + ANSI_HIGH_INTENSITY.colourCode() + (ChronoUnit.MINUTES.between(gameStartTime, gameOverTime)) + " minutes and " + (ChronoUnit.SECONDS.between(gameStartTime, gameOverTime)) +" seconds.");
        HighScores highScores = new HighScores();
        Colours.clear();
        //highScores.filePrintHS(player1.name(), temp); TODO:fix the sorting
        reader.close();
        exit(0);
    }
}

