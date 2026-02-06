package Acts;


import Mine.Colours;
import Mine.PlayerState;

import javax.swing.text.html.parser.Entity;

import static Mine.Colours.AnsiCodes.*;

import static java.lang.System.*;


public interface Actions { // G O D  C L A S S

    void action(Entities entity);


    public static void deathCheck(Entities entity){
        double meanWarning = ((entity.food() + entity.water() + entity.energy() + entity.health()) / 4);

        if (entity.food() < -10 || entity.water() < -10 || entity.energy() < -10 || entity.health() < -10) {
            ANSI_HIGH_INTENSITY.printCode();

            out.println(entity.name() + "'s current state:");
            out.printf("%sHP: %f\n%sEP: %f\n%sWP: %f\n%sFP: %f\n%s", ANSI_RED.colourCode(), entity.health(), ANSI_YELLOW.colourCode(), entity.energy(), ANSI_CYAN.colourCode(), entity.water(), ANSI_GREEN.colourCode(), entity.food(), ANSI_RESET.colourCode());
            Colours.clear();
            entity.setState(PlayerState.DEAD);
        } else if (meanWarning < 50.00 && meanWarning > -10.00) {
            out.printf("%n%s%sWatch it!%s Your state is in critical condition!\nYou will lose the game if one of your points go below -10!%n", ANSI_RED.colourCode(), ANSI_HIGH_INTENSITY.colourCode(), ANSI_RESET.colourCode());
            Colours.clear();

            entity.setState(PlayerState.ALIVE); // redundant but its ok
        }
    }
}
