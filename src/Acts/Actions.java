package Acts;


import Mine.Colours;
import Mine.PlayerState;

import java.util.*;
import static Mine.Colours.AnsiCodes.*;

import static java.lang.System.*;
//@SuppressWarnings("")
public sealed class Actions permits Fight, Sleep, Eat, Drink, Quit, Status { // G O D  C L A S S

    protected static double foodP, waterP, energyP, healthP; //used static here to keep the variable consisted between instances
    public static PlayerState playerState;
    protected static String pName; // they now belong to the class

    protected Actions(){
        double meanWarning = ((foodP + waterP + energyP + healthP) / 4);

        if (energyP < -10 || waterP < -10 || foodP < -10 || healthP < -10) {
            ANSI_HIGH_INTENSITY.printCode();

            out.println(pName + "'s current state:");
            out.printf("%sHP: %f\n%sEP: %f\n%sWP: %f\n%sFP: %f\n%s", ANSI_RED.colourCode(), healthP, ANSI_YELLOW.colourCode(), energyP, ANSI_CYAN.colourCode(), waterP, ANSI_GREEN.colourCode(), foodP, ANSI_RESET.colourCode());
            Colours.clear();
            playerState = PlayerState.DEAD;
        } else if (meanWarning < 50.00 && meanWarning > -10.00) {
            out.printf("%n%s%sWatch it!%s Your state is in critical condition!\nYou will lose the game if one of your points go below -10!%n", ANSI_RED.colourCode(), ANSI_HIGH_INTENSITY.colourCode(), ANSI_RESET.colourCode());
            Colours.clear();

            playerState = PlayerState.ALIVE;
        }
    }

    public Actions(String pName, double foodP, double waterP, double energyP, double healthP, PlayerState playerState){ // TODO: put in separate class
        Actions.foodP = foodP;
        Actions.waterP = waterP;
        Actions.energyP = energyP;
        Actions.healthP = healthP;
        Actions.pName = pName;
        Actions.playerState = playerState;
    }

}
