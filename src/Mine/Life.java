package Mine;


import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import static Mine.Colours.AnsiCodes.*;
import java.time.*;

import static java.lang.System.*;
import static java.lang.Thread.sleep;


public class Life {

    //public final MADD madd = new MADD();  //TODO keep for fun later on ;) - replaced with interface static methods 2/3/26

    private static final int  FIGHT = 1, SLEEP = 2, DRINK = 3, EAT = 4, CONDITION = 5, QUIT = 6;
    private static final String NAME_CHANGE = "7";

    final record Player(String name){ } // new java thing :)


    public static void main(String[] args) throws NoSuchElementException, IOException {
        Actions actions = new Actions();
        Colours.clear(); // initialize enum
        MADD madd = new MADD();
        int misinputCounter = 0, movesLeft = 50, finalMoves = 0, playerState;
        var gameStartTime = Instant.now();
        do{
            try{
                var reader = new Scanner(in); // must be in the do while block to refresh the user input in case of error.
                out.println("Player1, your name: ");
                var player1 = new Player(reader.nextLine());
                out.println(ANSI_GREEN.colourCode()+"Welcome to the game, " + ANSI_YELLOW.colourCode() + player1.name() + ANSI_GREEN.colourCode() + "!");
                Colours.clear();
                out.println("You have only " + movesLeft + " moves.");
                out.println("\nWould you like to change your name?");
                if (reader.nextLine().replaceAll(" ", "").toLowerCase().contains("yes")) {
                    //pName = actions.pNameChange();
                    player1 = new Player(actions.pNameChange()); // create the record name anew, keeping it immutable
                    out.println(ANSI_RED.colourCode() + "New name registered successfully.\n\n" + ANSI_GREEN.colourCode() + "Welcome to the game, " + ANSI_YELLOW.colourCode() + player1.name() + ANSI_GREEN.colourCode() + "!");
                    Colours.clear();
                } else {
                    out.println(ANSI_RED.colourCode() + "Name locked in.");
                    out.println(ANSI_GREEN.colourCode() + "Welcome to the game, " + ANSI_YELLOW.colourCode() + player1.name() + ANSI_GREEN.colourCode() +"!");
                    Colours.clear();
                }
                Actions.showChoices(movesLeft, 0);
                actions.getpName(player1.name());
                for (int totalMoves = 1; totalMoves <= movesLeft; totalMoves++) {
                    int moveChoice = reader.nextInt();
                    if (moveChoice > 6 || moveChoice < 1 ) {
                        out.println("Choose a number between 0 and 7. You lose a move every time you type otherwise.");
                        totalMoves--;
                        movesLeft--; // don't count for final score.
                    } else switch (moveChoice){
                        case FIGHT -> {
                            movesLeft+=actions.fight();
                            if(movesLeft < 0){ continue; }
                        }
                        case SLEEP -> {
                            if ((playerState = actions.sleep()) < 0) {
                                movesLeft = playerState; // runs status check which on top of giving current stats, check if you're dead TODO: run the "death check" on a separate thread at all times
                                continue;
                            }
                        }
                        case DRINK -> {
                            if((playerState = actions.drinkWater()) < 0) {
                                movesLeft = playerState;
                                continue; // runs the for loop condition again, in case the player died, to exit it
                            }
                        }
                        case EAT -> {
                            if( (playerState = actions.eatFood()) < 0){
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
                            if( (actions.endGame()) == 1){
                                totalMoves--; // dont count towards total moves
                                Actions.showChoices(movesLeft, totalMoves);
                                continue;
                            } else {
                                //out.println("In total you've had " + finalMoves + " moves!");
                                //HighScores highScores = new HighScores(); // TODO: rework this mb with inheritance
                                //highScores.filePrintHS(player1.name(), temp);
                                //exit(0);
                                movesLeft = -999999999;
                                continue;

                            }
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
                            actions.showChoices(movesLeft, totalMoves);
                        } catch (InterruptedException e) {
                            out.println("PATIENCE!");
                        }
                    }
                    finalMoves = totalMoves; // set it after every move for quit
                }
                    finalMoves++; // count the last move before death
                    ANSI_HIGH_INTENSITY.printCode();
                    ANSI_RED.printCode();
                    out.println("You died! Game over!");
                    Colours.clear();
                    out.println("In total you've had " + finalMoves + " moves!");
                    var gameOverTime = Instant.now();
                    out.println("You lasted: " + ANSI_HIGH_INTENSITY.colourCode() + (ChronoUnit.MINUTES.between(gameStartTime, gameOverTime)) + " minutes and " + (ChronoUnit.SECONDS.between(gameStartTime, gameOverTime)) +" seconds.");
                    HighScores highScores = new HighScores();
                    Colours.clear();
                    //highScores.filePrintHS(player1.name(), temp); TODO:fix the sorting
                    reader.close();
                    exit(0);
                } catch (InputMismatchException e) { // fixed: now it simply looks for a yes in the answer instead of a number
                ANSI_RED.printCode();
                if (misinputCounter <= 5) {
                    out.println("Please input a number and nothing else.");
                        Colours.clear();
                        misinputCounter++;

                    } else {
                    out.println("Refrain from spamming non-integers.");
                        Colours.clear();
                        exit(-2);
                    }
                }

            } while (true);
        }
    }

