package Acts.PlayerActions;

import Acts.Actions;
import Acts.GeneralActions.Attack;
import Acts.Consumption.EnergyUsage;
import Acts.FightLossMessages;
import entity.types.Enemies.Enemies;
import Shareables.Colours;
import entity.types.Players.Players;


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static Acts.Constants.*;
import static Acts.PlayerActions.ExploreOptions.Battle.battle;
import static Acts.PlayerActions.ExploreOptions.Gauntlet.event;
import static Shareables.EntityState.*;
import static Shareables.RandomGenerator.*;
import static Shareables.Colours.AnsiCodes.*;
import static Acts.PossibleFightMoves.checkInput;
import static Shareables.NormalizeStrings.normalize;
import static entity.types.Enemies.EnemyTypes.getEnemyRarity;
import static entity.types.Enemies.EnemyTypes.randomizeEncounter;
import static java.lang.System.out;

public final class Explore implements Actions {
    public void explore(Players player, Scanner reader) {
//        if(new Enemies((EnemyTypes.randomized(new Random().nextInt(0, EnemyTypes.values().length)))).getName().equals("Goblin")){ // Jesus Christ
//            new Goblin(); // this belongs in a museum
//        }
        out.println(ANSI_BLACK_BACKGROUND + "You venture deeper into the unknown..." + ANSI_RESET);
        player.setState(RESET); // just in case
        if (getXinY(1, 50)) {
            event(player);
        } else {
            battle(player, reader, new Explore());
        }
    }

    //prints a randomly generated sentence
    public void execute() {

        String[] funnyHaha = FightLossMessages.getStrings();

        for (int i = 0; i < funnyHaha.length; i++) {
            int randomIndexToSwap = rand.nextInt(0, funnyHaha.length); // bound is exclusive
            String temp = funnyHaha[randomIndexToSwap];
            funnyHaha[randomIndexToSwap] = funnyHaha[i];
            funnyHaha[i] = temp;
        }

        ANSI_RED.printCode();
        ANSI_HIGH_INTENSITY.printCode();

        int newArrayLength = (new Random().nextInt(1, (randomize(2, funnyHaha.length / 5)) + 1)); // random sentence size, funnyHaha for some reason is tiny, exception thrown, so use its own size
        for (int i = 0; i < newArrayLength; i++) {
            funnyHaha[i] = funnyHaha[i].trim().replaceAll("[^\\w]", "").replaceAll(" ", ""); // remove all non-word chars aka . ! /  ...
            // had to replace all spaces with blanks since we're adding them ourselves
            if (i == 0) {
                if (!funnyHaha[i].isEmpty())
                    out.print(funnyHaha[i] = funnyHaha[i].replaceFirst(
                            funnyHaha[i].substring(0, 1),
                            funnyHaha[i].toUpperCase().substring(0, 1))); // this looks great :)
                // the above is so that the 1st letter is always Upper
                // also checks that it isnt empty
            } else {
                out.print((funnyHaha[i]));
            }
            if (i != newArrayLength - 1) // put a space between every word except the last
                out.print(" ");
        } // end sentences with .
        out.print(".\n"); // set with !, ?, !! enums with rand
        Colours.clear();
    }
}