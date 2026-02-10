package Ents;

import Mine.Colours;

import java.util.Random;

import static Mine.Colours.AnsiCodes.*;
import static java.lang.System.out;

public enum EntityState {
    DEAD{}, COWARD {}, EXIT_GAME{}, ALIVE {},
    FIGHT_WIN{};

    public static int moveLogic(){
        int addMoves = new Random().nextInt(5, 16); // at least 5 moves
        ANSI_RED.printCode();
        ANSI_HIGH_INTENSITY.printCode();
        out.println("You have gained " + addMoves + " moves for destroying your foe in battle!");
        Colours.clear();
        return addMoves;
    }

}
