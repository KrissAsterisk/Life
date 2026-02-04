package Mine;

import static java.lang.System.out;

sealed interface Colours permits MADD { // actually this is package access abstract
    String ANSI_BLACK = "\u001B[30m";
    String ANSI_BLUE = "\u001B[34m";
    String ANSI_PURPLE = "\u001B[35m";
    String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    String ANSI_RED_BACKGROUND = "\u001B[41m";
    String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    // used in formatted strings
    public static enum AnsiCodes{
        ANSI_RESET("\u001B[0m"){}, //
        ANSI_BLUE("\u001B[34m"), ANSI_RED("\u001B[31m"), ANSI_GREEN("\u001B[32m"), ANSI_YELLOW("\u001B[33m"), ANSI_CYAN("\u001B[36m"),
        ANSI_HIGH_INTENSITY("\u001B[1m"), ANSI_LOW_INTENSITY("\u001B[2m"), ;

        public String colourCode(){
            return code;
        }
        public void printCode(){
            out.print(code);
        }

        private final String code;

        private AnsiCodes(String code){
            this.code = code;
            System.out.println("\u001B[0m" + "ENUM: " + name() + code + " -- INITIALIZED");
        }

    }
    // methods to be called at random
    static void clear() {
        out.print(AnsiCodes.ANSI_RESET.code);
    } // just in case :)

    void rngC(int duration);


}
