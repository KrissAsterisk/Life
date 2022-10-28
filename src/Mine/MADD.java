
package Mine;


import java.util.Random;

import static java.lang.System.*;

class MADD implements Colours {

    void printR() {
        out.print(ANSI_RED);
    }
    void printC(){
        out.print(ANSI_CYAN);
    }
    void printY(){
        out.print(ANSI_YELLOW);
    }
    void printG(){
        out.print(ANSI_GREEN);
    }
    void printHIGH(){
        out.print(HIGH_INTENSITY);
    }
    void printLOW(){
        out.print(LOW_INTENSITY);
    }
    void clearBG() {
        out.println(ANSI_RESET);
    }

    void rngC() {
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
        for (int i = 0; i < 3; i++) {
            int j = rand.nextInt(8) - 1;
            if (j < 0) j++;
            out.println(array[j]+"\n");
        }
        out.println(ANSI_RESET);

    }
    void rngCConst(){
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
        out.println(ANSI_RESET);

    }
}
