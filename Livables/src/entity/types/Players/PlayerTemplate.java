package entity.types.Players;

import Shareables.*;

import java.util.Optional;
import java.util.Scanner;

import static Shareables.Colours.AnsiCodes.*;
import static Shareables.RandomGenerator.rand;
import static Shareables.RandomGenerator.randomize;
import static java.lang.System.out;

// The point of this record is to make it clear that I do NOT want anything to change the default init values of a player
public record PlayerTemplate(String name, Scanner reader, double foodP, double waterP, double energyP, double healthP, float xp, double damage, byte level,
                             EntityState currentState) {

    public PlayerTemplate { // compact constructor!!
        out.println(ANSI_GREEN + "Welcome to the game, " + ANSI_YELLOW + name + ANSI_GREEN + "!"); // welcome the player anytime they "sign in"
        Colours.clear(); // less boilerplate yuppie

    }

    public static PlayerTemplate initPlayer(Scanner reader) {

        out.println("Player1, your name: ");
        String name = NormalizeStrings.normedUserName(reader).orElse("");

        return (new PlayerTemplate(nameChange(reader, name).orElse(DEFAULT_NAME), reader, DEFAULT_FOOD_POINTS, DEFAULT_WATER_POINTS, DEFAULT_ENERGY_POINTS, DEFAULT_HEALTH_POINTS, DEFAULT_XP, DEFAULT_DAMAGE, DEFAULT_STARTING_LEVEL, EntityState.ALIVE));

    }
    // im never using recursive functions like this ever again
    private static Optional<String> nameChange(Scanner reader, String name) {// makes me scream inside
        if (!name.matches("\\w+")) { // check for non-letters - conveniently also forbids the user from using empty space for their name IN GENERAL.
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
    private final static double DEFAULT_DAMAGE = randomize(rand.nextInt(11), 10.0); // at most 20 dmg
    private final static float DEFAULT_XP = 0f;
    private final static String DEFAULT_NAME = "BOZO";
    private final static byte DEFAULT_STARTING_LEVEL = 1;
}
