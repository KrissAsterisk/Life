package Mine;



import java.util.*;
import static Mine.Colours.AnsiCodes.*;

import static java.lang.System.*;
//@SuppressWarnings("")
class Actions {

    //private final MADD Colours = new MADD();
    protected double foodP = 100.00, waterP = 100.00, energyP = 100.00, healthP = 100.00;
    private String pName;


    public static void showChoices(int movesLeft, int totalMoves) {
        out.printf("\n1 - Fight\n2 - Sleep\n3 - Drink\n4 - Eat\n5 - Condition\n6 - Quit\n%d: Moves left\n", movesLeft - totalMoves);
    }


    int eatFood() {
        healthP += (Math.random() * 8.6) + 3.1;
        energyP -= (Math.random() * 8.3) + 7.8;
        waterP += (Math.random() * 5.3) + 1;
        foodP += (Math.random() * 15.5) + 25.5;

        return statusCheck();
    }


    int drinkWater() {
        healthP += (Math.random() * 3.9) + 2.1;
        energyP -= (Math.random() * 11.27) + 5.23;
        waterP += (Math.random() * 26.757) + 14.543;
        foodP += (Math.random() * 3.7) + 0.5;
        return statusCheck();
    }


    int sleep() {


        energyP += (Math.random() * 26.4) + 15.2;
        healthP += (Math.random() * 23.21) + 8.51; // 0 -> 23.21 => at least 8.51 health points
        foodP -= (Math.random() * 21.3) + 30.5;
        waterP -= (Math.random() * 21.7) + 35.6;
        return statusCheck();
    }

    int fight() {
        ArrayList<Integer> luck = new ArrayList<>();
        for (int i = 1; i <= 100; i++) { // create 100 random numbers between 1 and 2501
            luck.add((int) (Math.random() * (2500)) + 1);
        }
        int totalNumberOfIntegers = 0;
        int sum = 0;
        for (int integer : luck) {
            sum += integer;
            totalNumberOfIntegers++;
        }
        int fin = sum / totalNumberOfIntegers;
        healthP -= (Math.random() * 99.99) + 0.01;
        energyP -= (Math.random() * 21.202) + 10.21;

        int winThreshold = 1215;
        if (fin < winThreshold) { // if sum is below threshold, player wins

            if (statusCheck() < 0) { //  check if player died before adding moves
                return -999999999;
            } else {
                int addMoves = (int) (Math.random() * 10) + 5; // at least 5 moves
                ANSI_RED.printCode();
                ANSI_HIGH_INTENSITY.printCode();
                out.println("You have gained " + addMoves + " moves for destroying your foe in battle!");
                Colours.clear();
                return (addMoves);
            }

        } else if (statusCheck() < 0) { // if dead, exit program, otherwise print funny message
            return -999999999;
        } else { //done fight loss message; put all strings into an array, split them into words, replace them in the array and randomize each array block to be printed.
            String[] loss_msg = new String[16]; //TODO: API calls to AI to generate sentences?
            String loss1 = " unfortunately you were outnumbered and barely managed to get away . ";
            loss_msg[0] = loss1;
            String loss2 = " you tried to fight a dragon barehanded and got your teeth knocked out . ";
            loss_msg[1] = loss2;
            String loss3 = " you tripped on a rock on your way to the dungeon and broke your arm . ";
            loss_msg[2] = loss3;
            String loss4 = " maybe drinking suspicious potions from a stranger wasn't the best idea . ";
            loss_msg[3] = loss4;
            String loss5 = " you feel a slight breeze brush against you, you sense your liver missing . ";
            loss_msg[4] = loss5;
            String loss6 = " dealings with a demon have their consequences . ";
            loss_msg[5] = loss6;
            String loss7 = " you jumped in a lake full of leeches . ";
            loss_msg[6] = loss7;
            String loss8 = " you suck . ";
            loss_msg[7] = loss8;
            String loss9 = " you forgot that if you go to hell , you can't come back . ";
            loss_msg[8] = loss9;
            String loss10 = " ur dead LOL get rekt ";
            loss_msg[9] = loss10;
            String loss11 = " Sucks to suck ";
            loss_msg[10] = loss11;
            String loss12 = " Dead from blunt trauma to the face ";
            loss_msg[11] = loss12;
            String loss13 = " They will surely feast on your flesh ";
            loss_msg[12] = loss13;
            String loss14 = " Try playing on Drizzle mode for an easier time ";
            loss_msg[13] = loss14;
            String loss15 = " You die in a hilarious pose ";
            loss_msg[14] = loss15;
            String loss16 = "You're hired.";
            loss_msg[15] = loss16;


            StringBuilder sb = new StringBuilder();
            for (String s : loss_msg) {

                sb.append(s.toLowerCase());
            }

            String[] funnyHaha = sb.toString().split("\\s+");

            funnyHaha = Arrays.stream(funnyHaha).distinct().toArray(String[]::new);

            Random rand = new Random();
            for (int i = 0; i < funnyHaha.length; i++) {
                int randomIndexToSwap = rand.nextInt(funnyHaha.length);
                String temp = funnyHaha[randomIndexToSwap];
                funnyHaha[randomIndexToSwap] = funnyHaha[i];
                funnyHaha[i] = temp;
            }

            ANSI_RED.printCode();
            ANSI_HIGH_INTENSITY.printCode();
            int o = (int) (Math.random() * funnyHaha.length / 5); // random sentence size, funnyHaha for some reason is tiny, exception thrown, so use its own size
            for (int i = 0; i < o; i++) { // o is the new "array length"
                funnyHaha[i] = funnyHaha[i].replaceAll("[^\\w]", ""); // remove all non-word chars aka . ! /  ...

                if (i == 0) {
                    out.print(funnyHaha[i] = funnyHaha[i].replaceFirst(funnyHaha[i].substring(0, 1), funnyHaha[i].toUpperCase().substring(0, 1))); // this looks great :)
                    // the above is so that the 1st letter is always Upper
                } else {
                    out.print((funnyHaha[i])); // or any random number ig
                }
                if (i != o - 1)
                    out.print(" ");
            } // end sentences with .
            out.print("."); // set with !, ?, !! enums with rand


            Colours.clear();
            return 0;
        }
    }

    void statusReport() { // reduce boilerplate
        ANSI_HIGH_INTENSITY.printCode();
        out.println(pName + "'s current state:");
        out.printf("%sHP: %f\n%sEP: %f\n%sWP: %f\n%sFP: %f", ANSI_RED.colourCode(), healthP, ANSI_YELLOW.colourCode(), energyP, ANSI_CYAN.colourCode(), waterP, ANSI_GREEN.colourCode(), foodP);
        Colours.clear();
        //madd.rngC(); // turn on for some random colors every time you press 5 lol
        statusCheck();

    }

    int statusCheck() {

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


    String pNameChange() {
        Scanner nameChange = new Scanner(in);
        out.print("Input your new name: ");
        return nameChange.nextLine();
    }

    void getpName(String defName) {
        pName = defName;
    }

    int endGame() { //fixed score is not registered on the file if quit, or changed names.
        Scanner check = new Scanner(in);
        out.println("You've chosen to quit the game. Are you sure? (Y/N)");
        String b = check.next();
        if (b.equalsIgnoreCase("y")) {
            out.println("Goodbye!");
            return 0;
        } else if (b.equalsIgnoreCase("n")) {
            return 1;
        } else {
            ANSI_RED.printCode();
            out.println("Please input only 1 character. (Y/N)");
            Colours.clear();
            endGame(); // recursive
        }
        return 1; // TODO:replace with boolean
    }
}
