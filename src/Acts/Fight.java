package Acts;

import Mine.Colours;
import Mine.PlayerState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static Mine.Colours.AnsiCodes.ANSI_HIGH_INTENSITY;
import static Mine.Colours.AnsiCodes.ANSI_RED;
import static java.lang.System.out;

public final class Fight implements Actions, FightLossMessages{


    public void action(Entities entity){
        ArrayList<Integer> luck = new ArrayList<>();
        for (int i = 1; i <= 100; i++) { // create 100 random numbers between 1 and 2501
            luck.add((int) (Math.random() * (2500)) + 1);
        }
        int sum = 0, winThreshold = 1215;
        for (int numbers : luck) {
            sum += numbers;
        }
        entity.update(
                0,
                0,
                -1 * ((Math.random() * 21.202) + 10.210),
                -1 * ((Math.random() * 99.99) + 0.01)
        );
        Actions.deathCheck(entity); // check if dead

        if(entity.state() == PlayerState.ALIVE){
            if ((sum / luck.size()) < winThreshold) { // if sum is below threshold, player wins
                entity.setState(PlayerState.FIGHT_WIN);
            } else {// put all strings into an array, split them into words, replace them in the array and randomize each array block to be printed.
                String[] funnyHaha = FightLossMessages.getStrings();
                funnyHaha = Arrays.stream(funnyHaha).distinct().toArray(String[]::new); // this also removes duplicates but its a redundancy now

                Random rand = new Random();
                for (int i = 0; i < funnyHaha.length; i++) {
                    int randomIndexToSwap = rand.nextInt(0, funnyHaha.length); // bound is exclusive
                    String temp = funnyHaha[randomIndexToSwap];
                    funnyHaha[randomIndexToSwap] = funnyHaha[i];
                    funnyHaha[i] = temp;
                }

                ANSI_RED.printCode();
                ANSI_HIGH_INTENSITY.printCode();
                int newArrayLength = (int) (Math.random() * funnyHaha.length / 5); // random sentence size, funnyHaha for some reason is tiny, exception thrown, so use its own size
                for (int i = 0; i < newArrayLength; i++) {
                    funnyHaha[i] = funnyHaha[i].trim().replaceAll("[^\\w]", "").replaceAll(" ", ""); // remove all non-word chars aka . ! /  ...
                    // had to replace all spaces with blanks since we're adding them ourselves
                    if (i == 0) {
                        if(!funnyHaha[i].isEmpty()) out.print(funnyHaha[i] = funnyHaha[i].replaceFirst(funnyHaha[i].substring(0, 1), funnyHaha[i].toUpperCase().substring(0, 1))); // this looks great :)
                        // the above is so that the 1st letter is always Upper
                        // also checks that it isnt empty
                    } else {
                        out.print((funnyHaha[i]));
                    }
                    if (i != newArrayLength - 1) // put a space between every word except the last
                        out.print(" ");
                } // end sentences with .
                out.print("."); // set with !, ?, !! enums with rand


                Colours.clear();
            }
        }
    }
}
