package Mine;

import Ents.EntityState;

import java.util.Scanner;

import static Mine.Colours.AnsiCodes.*;
import static Mine.Life.*;
import static java.lang.System.out;

// Point of this record is to make it clear that I do NOT want anything to change the default init values of a player
record Player(String name, Scanner reader, double foodP, double waterP, double energyP, double healthP,
              EntityState currentState) {

    Player { // compact constructor!!
        out.println(ANSI_GREEN.colourCode() + "Welcome to the game, " + ANSI_YELLOW.colourCode() + name + ANSI_GREEN.colourCode() + "!"); // welcome the player anytime they "sign in"
        Colours.clear(); // less boilerplate yuppie

    }


    static Player initPlayer(Scanner reader) {
        out.println("Player1, your name: ");
        String name = reader.nextLine();
        if (name.equals("Asterisk")) {
            ANSI_RED.printCode();
            ANSI_HIGH_INTENSITY.printCode();
            out.println("The name " + name + " is reserved.");
            Colours.clear();
            return initPlayer(reader);
        }

        return new Player(nameChange(reader, name), reader, DEFAULT_FOOD_POINTS, DEFAULT_WATER_POINTS, DEFAULT_ENERGY_POINTS, DEFAULT_HEALTH_POINTS, EntityState.ALIVE);

    }


    private static String nameChange(Scanner reader, String name) {
        out.println("\nWould you like to change your name?");
        if (reader.nextLine().replaceAll(" ", "").toLowerCase().contains("yes")) {
            out.println("Input your new name: ");
            Colours.clear();
            name = reader.nextLine();
            nameChange(reader, name);
            return name;
        } else {
            out.println(ANSI_RED.colourCode() + "Name locked in.\n");
            Colours.clear();
            return name;
        }
    }
}
