package Shareables;

import static Shareables.Colours.AnsiCodes.*;
import static java.lang.System.out;

public enum EntityState {
    EXIT_GAME {},
    DEAD {}, COWARD {}, ALIVE {},
    FIGHT_WIN {}, VULNERABLE,
    RESET;

    public static int moveLogic() {
        int addMoves = RandomGenerator.randomize(5, 16); // at least 5 moves
        ANSI_RED.printCode();
        ANSI_HIGH_INTENSITY.printCode();
        out.println("You have gained " + addMoves + " moves for destroying your foe in battle!");
        Colours.clear();
        return addMoves;
    }

}
