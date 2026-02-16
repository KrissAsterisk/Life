package Mine;

import Ents.EntityState;

import java.util.Arrays;
import java.util.Scanner;

import static Mine.Colours.AnsiCodes.*;
import static java.lang.System.out;

// The point of this record is to make it clear that I do NOT want anything to change the default init values of a player
public record Player(String name, Scanner reader, double foodP, double waterP, double energyP, double healthP,
                     EntityState currentState) {

    public Player { // compact constructor!!
        out.println(ANSI_GREEN.colourCode() + "Welcome to the game, " + ANSI_YELLOW.colourCode() + name + ANSI_GREEN.colourCode() + "!"); // welcome the player anytime they "sign in"
        Colours.clear(); // less boilerplate yuppie

    }


    private static boolean nameLimiter(String name) {
        ANSI_RED.printCode();
        ANSI_HIGH_INTENSITY.printCode();
        if (name.contains("Asterisk")) {
            out.println("The name " + name + " is reserved.");
            Colours.clear();
            return true; // Limiter triggered
        }
        if(name.replaceAll("\\D+", "").matches("\\d+")){ // remove any non-digits - if there are any digits, trigger flag
            out.println("Your name cannot contain any numbers.");
            Colours.clear();
            return true;
        }
        Colours.clear();
        return false;
    }


    static Player initPlayer(Scanner reader) {
        out.println("Player1, your name: ");
        String name = reader.nextLine();
        if (nameLimiter(name)) {

            return initPlayer(reader);
        }


        return new Player(nameChange(reader, name), reader, DEFAULT_FOOD_POINTS, DEFAULT_WATER_POINTS, DEFAULT_ENERGY_POINTS, DEFAULT_HEALTH_POINTS, EntityState.ALIVE);

    }


    private static String nameChange(Scanner reader, String name) { // makes me scream inside
        if (name == null) {
            out.println("Please choose another name: ");
            name = reader.nextLine();
            if(nameLimiter(name)){
                name = null;
                return nameChange(reader, name);
            }
        }
        out.println("Are you sure " + name + " is your name?");
        if (NormalizeStrings.normalize(reader.nextLine()).contains("y")) {
            out.println(ANSI_RED.colourCode() + "Name locked in.\nGood Luck.");
            Colours.clear();
            return name;
        } else {
            out.println("Input your new name: ");
            Colours.clear();
            name = reader.nextLine();
            if (nameLimiter(name)) {
                name = null;
                return nameChange(reader, name);
            }
            nameChange(reader, name);
        }
        return name;
    }

    private final static double DEFAULT_FOOD_POINTS = 100.0, DEFAULT_WATER_POINTS = 100.0, DEFAULT_ENERGY_POINTS = 100.0, DEFAULT_HEALTH_POINTS = 100.0;
}