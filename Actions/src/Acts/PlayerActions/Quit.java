package Acts.PlayerActions;

import entity.types.Players.Players;
import Shareables.Colours;
import Shareables.EntityState;

import java.util.Scanner;

import static Shareables.Colours.AnsiCodes.ANSI_RED;
import static java.lang.System.out;

public final class Quit {


    public void execute(Players player, Scanner reader) {

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
            execute(player, reader); // come back to this method
        }
    }
}
