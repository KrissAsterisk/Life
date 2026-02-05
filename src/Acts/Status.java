package Acts;

import Mine.Colours;

import java.util.Scanner;

import static Mine.Colours.AnsiCodes.*;
import static java.lang.System.out;

public final class Status extends Actions{
    public int act(Scanner reader){
        ANSI_HIGH_INTENSITY.printCode();
        out.println(pName + "'s current state:");
        out.printf("%sHP: %f\n%sEP: %f\n%sWP: %f\n%sFP: %f", ANSI_RED.colourCode(), healthP, ANSI_YELLOW.colourCode(), energyP, ANSI_CYAN.colourCode(), waterP, ANSI_GREEN.colourCode(), foodP);
        Colours.clear();
        super.act(reader);
        return 0;
    }
}
