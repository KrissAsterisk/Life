package Acts;


import Mine.Colours;

import java.util.*;
import static Mine.Colours.AnsiCodes.*;

import static java.lang.System.*;
//@SuppressWarnings("")
public sealed class Actions permits Fight, Sleep, Eat, Drink, Quit, Status { // G O D  C L A S S
    // TODO IDEA: make this into an abstract class. split eat, food etc into children of it that override a "do()" method

    protected static double foodP, waterP, energyP, healthP; //used static here to keep the variable consisted between instances
    protected static String pName; // they now belong to the class

    protected Actions(){
    }

    public Actions(String pName, double foodP, double waterP, double energyP, double healthP){
        Actions.foodP = foodP;
        Actions.waterP = waterP;
        Actions.energyP = energyP;
        Actions.healthP = healthP;
        Actions.pName = pName;
    }

    protected int act(Scanner reader) {
        double meanWarning = ((foodP + waterP + energyP + healthP) / 4);
        if (energyP < -10 || waterP < -10 || foodP < -10 || healthP < -10) {
            ANSI_HIGH_INTENSITY.printCode();

            out.println(pName + "'s current state:");
            out.printf("%sHP: %f\n%sEP: %f\n%sWP: %f\n%sFP: %f\n%s", ANSI_RED.colourCode(), healthP, ANSI_YELLOW.colourCode(), energyP, ANSI_CYAN.colourCode(), waterP, ANSI_GREEN.colourCode(), foodP, ANSI_RESET.colourCode());
            Colours.clear();
            return -999999999; // doing this makes it harder to read and understand the code, would be better to perhaps call a function to "kill" the player instead of exiting the for loop like this
        } else if (meanWarning < 50.00 && meanWarning > -10.00) {
            out.printf("%n%s%sWatch it!%s Your state is in critical condition!\nYou will lose the game if one of your points go below -10!", ANSI_RED.colourCode(), ANSI_HIGH_INTENSITY.colourCode(), ANSI_RESET.colourCode());
            Colours.clear();

            return 0;
        } else return 21; // 21 the funny number; no code uses >0 return value YET

    }

    public static void showChoices(int movesLeft, int totalMoves) {
        out.printf(ANSI_LOW_INTENSITY.colourCode() + ANSI_BLUE.colourCode() + "\nIt's time to choose:"+ ANSI_RESET.colourCode() +"\n1 - Fight\n2 - Sleep\n3 - Drink\n4 - Eat\n5 - Condition\n6 - Quit\n%d: Moves left\n", movesLeft - totalMoves);
    } // put into an interface?

}
