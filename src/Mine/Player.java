package Mine;

import Ents.EntityState;

import java.util.Optional;
import java.util.Scanner;

import static Mine.Colours.AnsiCodes.*;
import static java.lang.System.out;

// The point of this record is to make it clear that I do NOT want anything to change the default init values of a player
public record Player(String name, Scanner reader, double foodP, double waterP, double energyP, double healthP, float xp,
                     EntityState currentState) {

    public Player { // compact constructor!!
        out.println(ANSI_GREEN + "Welcome to the game, " + ANSI_YELLOW + name + ANSI_GREEN + "!"); // welcome the player anytime they "sign in"
        Colours.clear(); // less boilerplate yuppie

    }

    static Player initPlayer(Scanner reader) {

        out.println("Player1, your name: ");
        String name = NormalizeStrings.normedUserName(reader).orElse("");

        return new Player(nameChange(reader, name).orElse(Constants.DEFAULT_NAME), reader, DEFAULT_FOOD_POINTS, DEFAULT_WATER_POINTS, DEFAULT_ENERGY_POINTS, DEFAULT_HEALTH_POINTS, DEFAULT_XP, EntityState.ALIVE);

    }
    // im never using recursive functions like this ever again
    private static Optional<String> nameChange(Scanner reader, String name) {// makes me scream inside
        if (!name.matches("\\w+")) {
            out.println("Please choose another name: ");
            return nameChange(reader, NormalizeStrings.normedUserName(reader).orElse(""));
        }
            out.println("Are you sure " + name + " is your name?");
        if (NormalizeStrings.normalize(reader).contains("y")) {
            out.println(ANSI_RED + "Name locked in.\nGood Luck.");
            Colours.clear();
            return Optional.of(name);
        } else {
            out.println("Input your new name: ");
            Colours.clear();
            name = NormalizeStrings.normedUserName(reader).orElse("");
            return nameChange(reader, name);
        }
    }

    private final static double DEFAULT_FOOD_POINTS = 100.0, DEFAULT_WATER_POINTS = 100.0, DEFAULT_ENERGY_POINTS = 100.0, DEFAULT_HEALTH_POINTS = 100.0;
    private final static float DEFAULT_XP = 0f;
}