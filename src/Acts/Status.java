package Acts;

import Mine.Colours;


import static Mine.Colours.AnsiCodes.*;
import static java.lang.System.out;

public final class Status implements Actions{

    public void action(Entities entity){
        ANSI_HIGH_INTENSITY.printCode();
        out.println(entity.name() + "'s current state:");
        out.printf("%sHP: %f\n%sEP: %f\n%sWP: %f\n%sFP: %f", ANSI_RED.colourCode(), entity.health(), ANSI_YELLOW.colourCode(), entity.energy(), ANSI_CYAN.colourCode(), entity.water(), ANSI_GREEN.colourCode(), entity.food());
        Colours.clear();
    }
}
