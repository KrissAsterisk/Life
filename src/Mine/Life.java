package Mine;


import java.io.IOException;
import java.util.*;
import static Mine.Colours.AnsiCodes.*;

import static java.lang.System.*;
//import static java.lang.Thread.sleep;


public class Life {
    public String pName;

    //public final MADD madd = new MADD();  //TODO keep for fun later on ;) - replaced with interface static methods 2/3/26

    Life(String pName) {
        this.pName = pName;
    }


    public String toString() {
        return pName;
    }

    private static final int NAME_CHANGE = 7, FIGHT = 1, SLEEP = 2, DRINK = 3, EAT = 4, CONDITION = 5, QUIT = 6;



    public static void main(String[] args) throws NoSuchElementException, IOException {
        Actions actions = new Actions();
        Colours.clear(); // initialize enum
        //MADD Colours = new MADD();
        int j = 0;
        do{
            try{
                    int l = 50;
                    int temp = 0;
                    int checkInt;
                    Scanner reader = new Scanner(in);
                        //out.println(Colours.AnsiCodes.ANSI_RESET);
                        out.println("Player1, your name: ");//TODO nuttin persunel kid
                        String pName = reader.nextLine();
                        Life player1 = new Life(pName);
                        out.println("Welcome to the game, " + player1 + "!");
                        out.println("You have only " + l + " moves.");
                        out.println("\nIf you would like to change your name, type 7.\nIf not, enter a number, in order to continue.");
                        Scanner tempScanner = new Scanner(in);
                        int tempInt = tempScanner.nextInt();
                        if (tempInt == NAME_CHANGE) {
                            pName = actions.pNameChange();
                            player1.pName = pName;
                        }
                        actions.showChoices(l, 0);
                        actions.getpName(pName);
                        for (int i = 1; i <= l; i++) {
                            int moveChoice = reader.nextInt();
                            if (moveChoice > 6 || moveChoice < 1) {
                                out.println("Choose a number between 0 and 7. You lose a move every time you type otherwise.");
                                i--;
                                l--; // don't count for final score.
                            } else switch (moveChoice){
                                case FIGHT -> {
                                    l+=actions.fightTEST();
                                    if(l < 0){ continue; }
                                }
                                case SLEEP -> {
                                    if ((checkInt = actions.sleepTEST()) < 0) {
                                        l = checkInt; // runs status check which on top of giving current stats, check if you're dead TODO: run the "death check" on a separate thread at all times
                                        continue;
                                    }
                                }
                                case DRINK -> {
                                    if((checkInt = actions.drinkWaterTEST()) < 0) {
                                        l = checkInt;
                                        continue;
                                    }
                                }
                                case EAT -> {
                                    if( (checkInt = actions.eatFoodTEST()) < 0){
                                        l = checkInt;
                                        continue;
                                    }
                                }
                                case CONDITION -> {
                                    actions.statusReportTEST();
                                    i--;
                                    l--; // don't count towards the final score but still uses a move
                                }
                                case QUIT -> {
                                    if( (actions.endGameTEST()) == 1){
                                        i--;
                                        actions.showChoices(l, i);
                                        continue;
                                    } else {
                                        out.println("In total you've had " + temp + " moves!");
                                        HighScores highScores = new HighScores();
                                        highScores.filePrintHS(player1, temp);
                                        System.exit(0);
                                    }
                                }
                            }
                            actions.showChoices(l, i);
                            if (l - i == 5) {

                                out.printf("%s%s+----------------------+%s\n%s%s|      %s %s%sWARNING!%s %s      |%s\n%s%s|You have 5 moves left!|%s\n%s%s+----------------------+%s%n", Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW.colourCode(), ANSI_RESET.colourCode(), Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW.colourCode(), Colours.ANSI_RED_BACKGROUND, Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW.colourCode(), Colours.ANSI_RED_BACKGROUND, Colours.ANSI_BLACK_BACKGROUND, ANSI_RESET.colourCode(), Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW.colourCode(), ANSI_RESET.colourCode(), Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW.colourCode(), ANSI_RESET.colourCode());

                                /*try {
                                    sleep(2000);
                                    for (int s = 0; s < 25; s++) {
                                        madd.rngCConst();
                                        sleep(100);
                                    }
                                    actions.showChoices(l, i);
                                } catch (InterruptedException e) {
                                    out.println("PATIENCE!");
                                } //TODO turn on for a little bit of fun ;) */
                            }
                            temp = i;
                        }
                    temp++; // count the last move before death
                    ANSI_HIGH_INTENSITY.printCode();
                    ANSI_RED.printCode();
                    out.println("You died! Game over!");
                    Colours.clear();
                    out.println("In total you've had " + temp + " moves!");
                    HighScores highScores = new HighScores();
                    highScores.filePrintHS(player1, temp);
                    tempScanner.close();
                    reader.close();
                    System.exit(0);
                } catch (InputMismatchException e) { //TODO:doesnt work as intended
                ANSI_RED.printCode();
                if (j <= 5) {
                    out.println("Please input a number and nothing else.");
                        Colours.clear();
                        j++;

                    } else {
                    out.println("Refrain from spamming non-integers.");
                        Colours.clear();
                        exit(-2);
                    }
                }

            } while (true); //TODO interface ||
        }
    }

