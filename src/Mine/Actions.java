package Mine;



import java.util.*;
import static Mine.Colours.AnsiCodes.*;

import static java.lang.System.*;
//@SuppressWarnings("")
class Actions{

    //private final MADD Colours = new MADD();
    public double foodP = 100.00, waterP = 100.00, energyP = 100.00, healthP = 100.00;
    private String pName;


    void showChoices(int l, int i){
        out.printf("\n1 - Fight\n2 - Sleep\n3 - Drink\n4 - Eat\n5 - Condition\n6 - Quit\n%d: Moves left\n", l - i);
    }


    public int eatFoodTEST() {
        healthP += (Math.random() * ((10.7 - 3.1) + 1)) + 3.1;
        energyP -= (Math.random() * ((15.1 - 7.8) + 1)) + 7.8;
        waterP += (Math.random() * ((5.3 - 1) + 1)) + 1;
        foodP += (Math.random() * ((40.0 - 25.5) + 1)) + 25.5;

            return statusCheck();
    }


    public int drinkWaterTEST() {
        healthP += (Math.random() * ((5 - 2.1) + 1)) + 2.1;
        energyP -= (Math.random() * ((15.5 - 5.23) + 1)) + 5.23;
        waterP += (Math.random() * ((40.3 - 14.543) + 1)) + 14.543;
        foodP += (Math.random() * ((3.2 - 0.5) + 1)) + 0.5;
        return statusCheck();
    }


    public int sleepTEST() {


        energyP += (Math.random() * ((40.6 - 15.2) + 1)) + 15.2;
        healthP += (Math.random() * ((30.72 - 8.51) + 1)) + 8.51;
        foodP -= (Math.random() * ((50.8 - 30.5) + 1)) + 30.5;
        waterP -= (Math.random() * ((56.3 - 35.6) + 1)) + 35.6;
        return statusCheck();
    }

    public int fightTEST() {
        ArrayList<Integer> luck = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            luck.add((int) (Math.random() * (2500 - 1) + 1) + 1);
        }
        int j = 0;
        int sum = 0;
        for (int i : luck) {
            sum += i;
            j++;
        }
        int fin = sum / j;
        healthP -= (Math.random() * ((100.00 - 0.01) + 1) + 0.01);
        energyP -= (Math.random() * ((30.412 - 10.21) + 1) + 10.21);
        if (fin < 1215) { //done WARNING ON FIGHT LOSS

           if (statusCheck() < 0){
               return -999999999;
           }  else {
                int addL = (int) (Math.random() * (15 - 5) + 5);
               ANSI_RED.printCode();
               ANSI_HIGH_INTENSITY.printCode();
                out.println("You have gained " + addL + " moves for destroying your foe in battle!");
               Colours.clear();
               return (addL);
            }

        } else if(statusCheck() < 0){
            return -999999999;
        }
        else { //done fight loss message; put all strings into an array, split them into words, replace them in the array and randomize each array block to be printed.
            String[] loss_msg = new String[15];
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
            loss_msg[9]= loss10;
            String loss11 = " Sucks to suck ";
            loss_msg[10] = loss11;
            String loss12= " Dead from blunt trauma to the face ";
            loss_msg[11] = loss12;
            String loss13= " They will surely feast on your flesh ";
            loss_msg[12] = loss13;
            String loss14= " Try playing on Drizzle mode for an easier time " ;
            loss_msg[13] = loss14;
            String loss15= " You die in a hilarious pose ";
            loss_msg[14] = loss15;



            StringBuilder sb = new StringBuilder();
            for (String s : loss_msg) {

                sb.append(s.toLowerCase());
            }

            String[] funnyHaha = sb.toString().split("\\s+");


            funnyHaha = Arrays.stream(funnyHaha).distinct().toArray(String[]::new);
            // for (String s : funnyHaha) { // fixed null spam error - due to too many empty blocks assigned to the initial array
            //    out.println(s);
            // }

            Random rand = new Random();
            for (int i = 0; i < funnyHaha.length; i++) {
                int randomIndexToSwap = rand.nextInt(funnyHaha.length);
                String temp = funnyHaha[randomIndexToSwap];
                funnyHaha[randomIndexToSwap] = funnyHaha[i];
                funnyHaha[i] = temp;
            }

            ANSI_RED.printCode();
            ANSI_HIGH_INTENSITY.printCode();
            int o = (int) (Math.random() * funnyHaha.length/5); // random sentence size, funnyHaha for some reason is tiny, exception thrown, so use its own size
            for(int i = 0; i < o; i++){ // o is the new "array length"
                funnyHaha[i] = funnyHaha[i].replaceAll("[^\\w]", ""); // remove all non-word chars aka . ! /  ...

                if( i == 0 ){
                    out.print(funnyHaha[i] = funnyHaha[i].replaceFirst(funnyHaha[i].substring(0,1), funnyHaha[i].toUpperCase().substring(0,1))); // this looks great :)
                    // the above is so that the 1st letter is always Upper
                } else {
                    out.print((funnyHaha[i])); // or any random number ig
                }
                if(i != o - 1 )
                    out.print(" ");
            } // end sentences with .
            out.print("."); // set with !, ?, !! enums with rand


            Colours.clear();
            return 0;
        }
    }

    void statusReportTEST() {
        ANSI_HIGH_INTENSITY.printCode();
        out.println(pName + "'s current state:");
        out.printf("%sHP: %f\n%sEP: %f\n%sWP: %f\n%sFP: %f", ANSI_RED.colourCode(), healthP, ANSI_YELLOW.colourCode(), energyP, ANSI_CYAN.colourCode(), waterP, ANSI_GREEN.colourCode(),foodP);
        Colours.clear();
        //madd.rngC(); //TODO turn on for some random colors every time you press 5 lol
        statusCheck();

    }
    private int statusCheck(){

        double meanWarning = ((foodP+waterP+energyP+healthP)/4);
        if (energyP < -10 || waterP < -10 || foodP < -10 || healthP < -10){
            ANSI_HIGH_INTENSITY.printCode();

            out.println(pName + "'s current state:");
            out.printf("%sHP: %f\n%sEP: %f\n%sWP: %f\n%sFP: %f\n%s", ANSI_RED.colourCode(), healthP, ANSI_YELLOW.colourCode(), energyP, ANSI_CYAN.colourCode(), waterP, ANSI_GREEN.colourCode(),foodP, ANSI_RESET.colourCode());
            Colours.clear();
            return -999999999;
        }else
        if(meanWarning < 50.00 && meanWarning > -10.00){
            out.printf("%s%sWatch it!%s Your state is in critical condition!\nYou will lose the game if one of your points go below -10!", ANSI_RED.colourCode(), ANSI_HIGH_INTENSITY.colourCode(), ANSI_RESET.colourCode());
            Colours.clear();

            return 0;
        }else return 21;

    }
    public String pNameChange(){
        Scanner nameChange = new Scanner(in);
        out.print("Input your new name: ");
        pName = nameChange.nextLine();
        out.println("New name registered successfully, " + pName);
        return pName;
    }

    public void getpName(String defName){
        pName = defName;
    }

    public int endGameTEST() { //fixed score is not registered on the file if quit, or changed names.

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
            endGameTEST();
        }
        return 1;
    }

   /* public void highScores(int temp){
        out.println("In total you've had " + temp + " moves!");
        HighScores highScores = new HighScores();
        Life player1 = new Life(pName);
        try {
            highScores.filePrintHS(player1, temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }*/ //TODO figure out later
}