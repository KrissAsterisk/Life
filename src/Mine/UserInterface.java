package Mine;

import static Mine.Colours.AnsiCodes.*;
import static java.lang.System.out;
import static java.lang.Thread.sleep;

interface UserInterface {
     static void showChoices(int movesLeft, int totalMoves) {
        out.printf(ANSI_LOW_INTENSITY.colourCode() + ANSI_BLUE.colourCode() + "\nIt's time to choose:"+ ANSI_RESET.colourCode() +
                "\n1 - Fight\n2 - Sleep\n3 - Drink\n4 - Eat\n5 - Condition\n6 - Quit\n%d: Moves left\n", movesLeft - totalMoves);
    }

    static void lowMovesWarning(MADD madd, int movesLeft, int totalMoves){
        out.printf("%s%s+----------------------+%s\n%s%s|      %s %s%sWARNING!%s %s      |%s\n%s%s|You have 5 moves left!|%s\n%s%s+----------------------+%s%n",
                Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW.colourCode(), ANSI_RESET.colourCode(),
                Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW.colourCode(),
                Colours.ANSI_RED_BACKGROUND,
                Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW.colourCode(),
                Colours.ANSI_RED_BACKGROUND,
                Colours.ANSI_BLACK_BACKGROUND, ANSI_RESET.colourCode(),
                Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW.colourCode(),
                ANSI_RESET.colourCode(),
                Colours.ANSI_BLACK_BACKGROUND, ANSI_YELLOW.colourCode(),
                ANSI_RESET.colourCode()
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
