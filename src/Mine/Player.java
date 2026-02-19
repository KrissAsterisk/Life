package Mine;

import Ents.EntityState;

import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Supplier;

import static Mine.Colours.AnsiCodes.*;
import static java.lang.System.out;

// The point of this record is to make it clear that I do NOT want anything to change the default init values of a player
public record Player(String name, Scanner reader, double foodP, double waterP, double energyP, double healthP, float xp,
                     EntityState currentState) {

    public Player { // compact constructor!!
        out.println(ANSI_GREEN + "Welcome to the game, " + ANSI_YELLOW + name + ANSI_GREEN + "!"); // welcome the player anytime they "sign in"
        Colours.clear(); // less boilerplate yuppie

    }


    private static boolean nameLimiter(Scanner reader, Optional<String> name) {
        ANSI_RED.printCode();
        ANSI_HIGH_INTENSITY.printCode();

        if ((name = name.orElseGet(reader::nextLine).describeConstable()).isPresent()) { // this is forced on purpose
            if (name.get().contains("Asterisk")) {
                out.println("The name " + name.get() + " is reserved.");
                Colours.clear();
                return true; // Limiter triggered
            }
            if (name.get().replaceAll("\\D+", "").matches("\\d+")) { // remove any non-digits - if there are any digits, trigger flag
                out.println("Your name cannot contain any numbers.");
                Colours.clear();
                return true;
            }
        }

        Colours.clear();
        return false;
    }


    static Player initPlayer(Scanner reader) {

        out.println("Player1, your name: ");
        Optional<String> name = reader.nextLine().describeConstable();
        if (nameLimiter(reader, name)) {

            return initPlayer(reader);
        }


        return new Player(nameChange(reader, name), reader, DEFAULT_FOOD_POINTS, DEFAULT_WATER_POINTS, DEFAULT_ENERGY_POINTS, DEFAULT_HEALTH_POINTS, DEFAULT_XP, EntityState.ALIVE);

    }

    @SuppressWarnings("all") // name is checked - might not handle all edge cases but i dont need all that yellow rn
    private static String nameChange(Scanner reader, Optional<String> name) {// makes me scream inside
        if (name.isEmpty()) {
            out.println("Please choose another name: ");
            name = reader.nextLine().describeConstable();
            if (nameLimiter(reader, name)) {
                name = Optional.empty();
                return nameChange(reader, name);
            }
        }
        out.println("Are you sure " + name.get() + " is your name?");
        if (NormalizeStrings.normalize(reader.nextLine()).contains("y")) {
            out.println(ANSI_RED + "Name locked in.\nGood Luck.");
            Colours.clear();
            return name.get();
        } else {
            out.println("Input your new name: ");
            Colours.clear();
            name = reader.nextLine().describeConstable();
            if (nameLimiter(reader, name)) {
                name = Optional.empty();
                return nameChange(reader, name);
            }
            nameChange(reader, name);
        }
        return name.get();
    }

    private final static double DEFAULT_FOOD_POINTS = 100.0, DEFAULT_WATER_POINTS = 100.0, DEFAULT_ENERGY_POINTS = 100.0, DEFAULT_HEALTH_POINTS = 100.0;
    private final static float DEFAULT_XP = 0f;
}