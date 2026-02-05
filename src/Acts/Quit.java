package Acts;

import Mine.Colours;

import java.util.Scanner;

import static Mine.Colours.AnsiCodes.ANSI_RED;
import static java.lang.System.out;

public final class Quit extends Actions{

    public int act(Scanner reader){
        out.println("You've chosen to quit the game. Are you sure? (Y/N)");
        String b = reader.next();
        if (b.equalsIgnoreCase("y")) {
            out.println("Goodbye!");
            return 0;
        } else if (b.equalsIgnoreCase("n")) {
            return 1;
        } else {
            ANSI_RED.printCode();
            out.println("Please input only 1 character. (Y/N)");
            Colours.clear();
            act(reader);
        }
        return 1; // TODO:replace with boolean
    }
}
