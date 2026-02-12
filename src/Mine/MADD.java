package Mine;

import static Acts.RandomGenerator.rand;
import static java.lang.System.*;

final class MADD implements Colours {

    public void rngC(int duration) { // TODO look at enum in interface mb
        String[] array = new String[8];
        array[0] = ANSI_BLACK_BACKGROUND;
        array[1] = ANSI_WHITE_BACKGROUND;
        array[2] = ANSI_PURPLE_BACKGROUND;
        array[3] = ANSI_GREEN_BACKGROUND;
        array[4] = ANSI_CYAN_BACKGROUND;
        array[5] = ANSI_BLUE_BACKGROUND;
        array[6] = ANSI_YELLOW_BACKGROUND;
        array[7] = ANSI_RED_BACKGROUND;
        for (int i = 0; i < duration; i++) {
            int j = rand.nextInt(array.length-1);
            out.println(array[j]+"\n");
        }
        Colours.clear();

    }
}
