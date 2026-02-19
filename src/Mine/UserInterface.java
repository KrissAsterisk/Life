package Mine;

import static Mine.Colours.AnsiCodes.*;
import static java.lang.System.out;
import static java.lang.Thread.sleep;

interface UserInterface {
     static void showChoices(int movesLeft, int totalMoves) {
        out.printf(ANSI_LOW_INTENSITY.toString() + ANSI_BLUE + "\nIt's time to choose:"+ ANSI_RESET +
                "\n1 - Fight\n2 - Sleep\n3 - Drink\n4 - Eat\n5 - Condition\n6 - Quit\n%d: Moves left\n", movesLeft - totalMoves);
    }

    static void lowMovesWarning(MADD madd, int movesLeft, int totalMoves){
        out.printf("%s%s+----------------------+%s\n%s%s|      %s %s%sWARNING!%s %s      |%s\n%s%s|You have 5 moves left!|%s\n%s%s+----------------------+%s%n",
                Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW, ANSI_RESET,
                Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW,
                Colours.ANSI_RED_BACKGROUND,
                Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW,
                Colours.ANSI_RED_BACKGROUND,
                Colours.ANSI_BLACK_BACKGROUND, ANSI_RESET,
                Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW,
                ANSI_RESET,
                Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW,
                ANSI_RESET
        );
//        try {
//            sleep(2000);
//            for (int s = 0; s < 25; s++) {
//                madd.rngC(20);
//                sleep(200);
//            }
//        } catch (InterruptedException e) {  // this catch is currently not working
//            out.println("PATIENCE!");
//        }
    }
}
