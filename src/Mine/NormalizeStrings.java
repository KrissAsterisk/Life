package Mine;

import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

import static Mine.Colours.AnsiCodes.*;
import static java.lang.System.out;

public interface NormalizeStrings {


    /**
     * Normalize user input to lowercase no blanks for user input
     * @param reader
     * @return
     */
    public static String normalize(Scanner reader){
        Function<String, String> normedString = x->x.trim().toLowerCase().replaceAll(" ", "");
        return normedString.apply(reader.nextLine());
    }

    /**
     * Normalize string to lowercase no blanks
     * @param input
     * @return
     */
    public static String normalize(String input){
        Function<String, String> normedString = x->x.trim().toLowerCase().replaceAll(" ", "");
        return normedString.apply(input);
    }

    public static Optional<String> normedUserName(Scanner reader){
        Predicate<String> checkIfNumber = x->x.matches("\\d+");
        Predicate<String> checkIfReserved = x->x.matches("asterisk"); // add more names from created file
        Optional<String> finalUserInput = reader.nextLine().describeConstable();
        if(finalUserInput.isEmpty()){
            System.out.println(ANSI_HIGH_INTENSITY.toString() + ANSI_RED + "What did you even input???" + ANSI_RESET);
            return Optional.empty();
        }
        if(checkIfNumber.test(normalize(finalUserInput.get()).replaceAll("\\D+", ""))){
            System.out.println(ANSI_HIGH_INTENSITY.toString() + ANSI_RED + "You may not use numbers here." + ANSI_RESET);
            return Optional.empty();
        }
        if(checkIfReserved.test(normalize(finalUserInput.get()))){
            System.out.println(ANSI_HIGH_INTENSITY.toString() + ANSI_RED + "This name is reserved." + ANSI_RESET);
            return Optional.empty();
        }
        return finalUserInput;
    }
    /**
     * Apply any other normalization method to the string declared in params
     * @param normedString
     * @param input
     * @return
     */
    public static String normalize(Function<String, String> normedString, String input){
        return normedString.apply(input);
    }

}
