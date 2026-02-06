package Mine;

import static Mine.Colours.AnsiCodes.*;
import static java.lang.System.out;

public enum PlayerState {
    DEAD{}, CRITICAL_CONDITION{}, EXIT_GAME{}, ALIVE {},
    FIGHT_WIN{};

    public static int moveLogic(){
        int addMoves = (int) (Math.random() * 10) + 5; // at least 5 moves
        ANSI_RED.printCode();
        ANSI_HIGH_INTENSITY.printCode();
        out.println("You have gained " + addMoves + " moves for destroying your foe in battle!");
        Colours.clear();
        return addMoves;
    }

}
