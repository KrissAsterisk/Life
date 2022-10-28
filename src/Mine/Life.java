package Mine;


import java.io.IOException;
import java.util.*;

import static java.lang.System.*;
import static java.lang.Thread.sleep;


public class Life implements Colours {
    public String pName;
    public final MADD madd = new MADD();  //TODO keep for fun later on ;)

    Life(String pName) {
        this.pName = pName;
    }


    public String toString() {
        return pName;
    }





    public static void main(String[] args) throws NoSuchElementException, IOException {
        Actions actions = new Actions();
        MADD madd = new MADD();
        int j = 0;

        int k = 0;
        do{
            try{
                    int l = 50;
                    int temp = 0;
                    int checkInt;
                    Scanner reader = new Scanner(in);
                        out.println("Player1, your name: ");//TODO nuttin persunel kid
                        String pName = reader.nextLine();
                        Life player1 = new Life(pName);
                        out.println("Welcome to the game, " + player1 + "!");
                        out.println("You have only " + l + " moves.");
                        out.println("\nIf you would like to change your name, type 7.\nIf not, enter a number, in order to continue.");
                        Scanner tempScanner = new Scanner(in);
                        int tempInt = tempScanner.nextInt();
                        if (tempInt == 7) {
                            pName = actions.pNameChange();
                            player1.pName = pName;
                        }
                        actions.showChoices(l, 0);
                        actions.getpName(pName);
                              for (int i = 1; i <= l; i++) {
                        int x = reader.nextInt();
                        if (x > 6 || x < 1) {
                            out.println("Choose a number between 0 and 7. You lose a move every time you type otherwise.");
                        } else if (x == 1) {
                            //l += player1.fight();
                            l += actions.fightTEST();
                            if (l < 0) {
                                continue;

                            }
                        } else if (x == 2) {
                            //player1.sleep();
                           checkInt = actions.sleepTEST();
                            if (checkInt < 0){
                                l = checkInt;
                                continue;
                            }
                        } else if (x == 3) {
                            //player1.drinkWater();
                            checkInt = actions.drinkWaterTEST();
                            if (checkInt < 0){
                                l = checkInt;
                                continue;
                            }
                        } else if (x == 4) {
                            //player1.eatFood();
                            checkInt = actions.eatFoodTEST();
                            if (checkInt < 0){
                                l = checkInt;
                                continue;
                            }
                        } else if (x == 5) {
                            //player1.statusReport(); //TODO this is - switch maybe?
                            actions.statusReportTEST();
                        } else {
                            int result = actions.endGameTEST();
                            if (result == 1) {
                                actions.showChoices(l, i);
                                l+=1;
                                continue;
                            } else {
                                out.println("In total you've had " + temp + " moves!");
                                HighScores highScores = new HighScores();
                                highScores.filePrintHS(player1, temp);
                                System.exit(0);
                                }
                        }
                        //out.printf("\n1 - Fight\n2 - Sleep\n3 - Drink\n4 - Eat\n5 - Condition\n6 - Quit\n%d: Moves left\n", l - i);
                        actions.showChoices(l, i);
                        if (l - i == 5) {

                            out.printf("%s%s+----------------------+%s\n%s%s|      %s %s%sWARNING!%s %s      |%s\n%s%s|You have 5 moves left!|%s\n%s%s+----------------------+%s%n", ANSI_BLACK_BACKGROUND, ANSI_YELLOW, ANSI_RESET, ANSI_BLACK_BACKGROUND, ANSI_YELLOW, ANSI_RED_BACKGROUND, ANSI_BLACK_BACKGROUND, ANSI_YELLOW, ANSI_RED_BACKGROUND, ANSI_BLACK_BACKGROUND, ANSI_RESET, ANSI_BLACK_BACKGROUND, ANSI_YELLOW, ANSI_RESET, ANSI_BLACK_BACKGROUND, ANSI_YELLOW, ANSI_RESET);

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
                        temp = i - 1;
                        temp ++;

                    }
                    madd.printHIGH();
                    madd.printR();
                    out.println("You died! Game over!");
                    madd.clearBG();
                    out.println("In total you've had " + temp + " moves!");
                    HighScores highScores = new HighScores();
                    highScores.filePrintHS(player1, temp);
                    tempScanner.close();
                    reader.close();
                    System.exit(0);
                } catch (InputMismatchException e) {
                madd.printR();
                if (j <= 5) {
                    out.println("Please input a number and nothing else.");
                        madd.clearBG();
                        j++;

                    } else {
                    out.println("Refrain from spamming non-integers.");
                        madd.clearBG();
                        exit(-2);
                    }
                }

            } while (true); //TODO interface ||
        }
    }

