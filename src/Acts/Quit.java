package Acts;

import Ents.Entities;
import Ents.Players;
import Mine.Colours;
import Ents.EntityState;

import java.util.Scanner;

import static Mine.Colours.AnsiCodes.ANSI_RED;
import static java.lang.System.out;

public final class Quit{


    public void action(Players player, Scanner reader){

        out.println("You've chosen to quit the game. Are you sure? (Y/N)");
        String b = reader.nextLine();
        if (b.equalsIgnoreCase("y")) {
            out.println("Goodbye!");
            //reader.close();
            player.setState(EntityState.EXIT_GAME);
        } else if (b.equalsIgnoreCase("n")) {
            // nothing yet
        } else {
            ANSI_RED.printCode();
            out.println("Please input only 1 character. (Y/N)");
            Colours.clear();
            action(player, reader); // come back to this method
        }
    }
}
