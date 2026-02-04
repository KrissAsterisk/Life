
package Mine;


import java.util.Random;

import static java.lang.System.*;

final class MADD implements Colours {



    public void defaultOption(){ // overridden default option
        out.println("not anymore");
    }

    public void inheritMe(){
        out.println("I'm forced :(");
    }


    public void rngC() {
        Random rand = new Random();
        defaultOption();
        String[] array = new String[8];
        array[0] = ANSI_BLACK_BACKGROUND;
        array[1] = ANSI_WHITE_BACKGROUND;
        array[2] = ANSI_PURPLE_BACKGROUND;
        array[3] = ANSI_GREEN_BACKGROUND;
        array[4] = ANSI_CYAN_BACKGROUND;
        array[5] = ANSI_BLUE_BACKGROUND;
        array[6] = ANSI_YELLOW_BACKGROUND;
        array[7] = ANSI_RED_BACKGROUND;
        for (int i = 0; i < 3; i++) {
            int j = rand.nextInt(8) - 1;
            if (j < 0) j++;
            out.println(array[j]+"\n");
        }
        Colours.clear();

    }
    public void rngCConst(){
        Random rand = new Random();
        String[] array = new String[8];
        array[0] = ANSI_BLACK_BACKGROUND;
        array[1] = ANSI_WHITE_BACKGROUND;
        array[2] = ANSI_PURPLE_BACKGROUND;
        array[3] = ANSI_GREEN_BACKGROUND;
        array[4] = ANSI_CYAN_BACKGROUND;
        array[5] = ANSI_BLUE_BACKGROUND;
        array[6] = ANSI_YELLOW_BACKGROUND;
        array[7] = ANSI_RED_BACKGROUND;
        for (int i = 0; i < 5; i++) {
            int j = rand.nextInt(8) - 1;
            if (j < 0) j++;
            out.println(array[j]+"\n");
        }
        Colours.clear();

    }
}
