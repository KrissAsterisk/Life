package Mine;

import static Shareables.Colours.AnsiCodes.*;
import static java.lang.System.out;

final class UserInterface {
     static void showChoices(int movesLeft, int totalMoves) {
        out.printf(ANSI_LOW_INTENSITY.toString() + ANSI_BLUE + "\nIt's time to choose:"+ ANSI_RESET +
                "\n1 - Fight\n2 - Sleep\n3 - Drink\n4 - Eat\n5 - Condition\n6 - Quit\n%d: Moves left\n", movesLeft - totalMoves);
    }

    static void lowMovesWarning(){ // this is a work of art
        out.printf("%s%s+----------------------+%s\n%s%s|      %s %s%sWARNING!%s %s      |%s\n%s%s|You have 5 moves left!|%s\n%s%s+----------------------+%s%n",
                ANSI_BLACK_BACKGROUND, ANSI_YELLOW, ANSI_RESET,
                ANSI_BLACK_BACKGROUND, ANSI_YELLOW,
                ANSI_RED_BACKGROUND,
                ANSI_BLACK_BACKGROUND, ANSI_YELLOW,
                ANSI_RED_BACKGROUND,
                ANSI_BLACK_BACKGROUND, ANSI_RESET,
                ANSI_BLACK_BACKGROUND, ANSI_YELLOW,
                ANSI_RESET,
                ANSI_BLACK_BACKGROUND, ANSI_YELLOW,
                ANSI_RESET
        );
    }
}
