package Shareables;

import static java.lang.System.out;

public final class Colours { // util class
    private Colours(){}
    public enum AnsiCodes {
        ANSI_RESET("\u001B[0m"), //
        ANSI_BLUE("\u001B[34m"), ANSI_RED("\u001B[31m"), ANSI_GREEN("\u001B[32m"), ANSI_YELLOW("\u001B[33m"), ANSI_CYAN("\u001B[36m"),
        ANSI_HIGH_INTENSITY("\u001B[1m"), ANSI_LOW_INTENSITY("\u001B[2m"), ANSI_PURPLE("\u001B[35m"),
        ANSI_RED_BACKGROUND("\u001B[41m"), ANSI_BLACK_BACKGROUND("\u001B[40m"), ANSI_CLEAR("\u001b[3J\u001b[2J\u001b[H");

        public String toString(){
            return code;
        }
        public void printCode(){
            out.print(code);
        }

        private final String code;

        AnsiCodes(String code){
            this.code = code;
            System.out.println("\u001B[0m" + "ENUM: " + name() + code + " -- INITIALIZED");
        }

    }
    // methods to be called at random
    public static void clear() {
        out.print(AnsiCodes.ANSI_RESET);
    } // just in case :)

}
