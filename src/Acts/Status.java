package Acts;

import Ents.Enemies;
import Ents.Entities;
import Ents.Players;
import Mine.Colours;


import static Mine.Colours.AnsiCodes.*;
import static java.lang.System.out;

public final class Status implements Actions{

    public Status(Entities entities) {
        action(entities);
    }

    public void action(Entities entity){
        ANSI_HIGH_INTENSITY.printCode();
        out.println(entity.getName() + "'s current state:");
        if(entity.getClass() == Players.class) out.printf("%sHP: %f\n%sEP: %f\n%sWP: %f\n%sFP: %f%n", ANSI_RED.colourCode(), entity.health(), ANSI_YELLOW.colourCode(), entity.energy(), ANSI_CYAN.colourCode(), entity.water(), ANSI_GREEN.colourCode(), entity.food());
        if(entity.getClass() == Enemies.class) out.printf("%sHP: %f\n%sEP: %f\n", ANSI_RED.colourCode(), entity.health(), ANSI_YELLOW.colourCode(), entity.energy());
        Colours.clear();
    }
}
