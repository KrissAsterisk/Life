package Mine;

import java.util.Scanner;
import java.util.function.Function;

public interface NormalizeStrings {


    /**
     * Normalize string to lowercase no blanks for user input
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
