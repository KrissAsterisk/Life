package Shareables;

import static Shareables.Colours.AnsiCodes.*;
import static java.lang.System.out;

public enum EntityState {
    EXIT_GAME {},
    DEAD {}, COWARD {}, ALIVE {},
    FIGHT_WIN {}, VULNERABLE, DODGING,
    RESET;
}
