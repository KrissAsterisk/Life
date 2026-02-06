package Acts;

import Mine.Colours;
import Mine.PlayerState;

import java.util.Scanner;

import static Mine.Colours.AnsiCodes.ANSI_RED;
import static java.lang.System.out;

public final class Quit extends Actions{

    public Quit(Scanner reader){

        out.println("You've chosen to quit the game. Are you sure? (Y/N)");
        String b = reader.next();
        if (b.equalsIgnoreCase("y")) {
            out.println("Goodbye!");
            playerState = PlayerState.EXIT_GAME;
        } else if (b.equalsIgnoreCase("n")) {
            // nothing yet
        } else {
            ANSI_RED.printCode();
            out.println("Please input only 1 character. (Y/N)");
            Colours.clear();
        }

    }




}
