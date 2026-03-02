package Acts.PlayerActions;

import Acts.Actions;
import Acts.GeneralActions.Attack;
import Acts.Consumption.EnergyUsage;
import Acts.FightLossMessages;
import entity.types.Enemies.Enemies;
import Shareables.Colours;
import entity.types.Players.Players;


import java.util.Random;
import java.util.Scanner;

import static Acts.Constants.*;
import static Shareables.EntityState.*;
import static Shareables.RandomGenerator.*;
import static Shareables.Colours.AnsiCodes.*;
import static Acts.PossibleFightMoves.checkInput;
import static Shareables.NormalizeStrings.normalize;
import static java.lang.System.out;

public final class Fight implements Actions {
    public void attack(Players player, Enemies enemy, Scanner reader) {
//        if(new Enemies((EnemyTypes.randomized(new Random().nextInt(0, EnemyTypes.values().length)))).getName().equals("Goblin")){ // Jesus Christ
//            new Goblin(); // this belongs in a museum
//        }
        var initiateBattle = new Attack();
        var playerEnergy = new EnergyUsage().useEnergy(player);
        var enemyEnergy = new EnergyUsage().useEnergy(enemy);
        player.setState(RESET);// reset vulnerable

        LOOP:
        do {
            enemy.printStatus();
            out.println("What is your next move?");
            out.println("1 - Attack\t2 - Dodge\t3 - Flee");
            switch (checkInput(normalize(reader))) {
                case ATTACK -> {
                    if(playerEnergy.deathCheck() == DEAD) break LOOP; // do energy check before attack
                    if (!getXinY(1, 50)) { // if 1 in 50, enemy dodges - higher rarity = more dodge chance TBD
                        if (initiateBattle.execute(player, enemy).deathCheck() == DEAD) break LOOP; // if enemy dies, get out
                    } else out.println("You... missed.");
                }
                case DODGE -> {
                    if ((getXinY(player.level(), 10))) { // the higher the players level the higher the chance to dodge
                        out.println("You gracefully dodge and the " + enemy.getName() + " hits itself!");
                        player.setState(DODGING);
                        if (initiateBattle.execute(enemy, enemy).deathCheck() == DEAD) break LOOP; // doesnt use energy
                    } else {
                        out.println("Uh Oh....");
                        player.setState(VULNERABLE);
                    }
                }
                case FLEE -> { // TODO: make this cooler?
                    enemy.setState(DEAD);
                    player.setState(COWARD);
                    out.println(ANSI_RED + "You coward.");
                    Colours.clear();
                    break LOOP;
                }
                case null, default -> {
                    System.out.println("You trip on a rock!");
                    player.updateHealth(-5);
                    player.deathCheck();
                }
            }
            if (!(player.state() == DODGING) && enemy.canAct()) {
                if (!(getXinY(player.level(), 20))) { // 1 in 20 to dodge for player
                    if (player.state() == VULNERABLE) {
                        if (initiateBattle.execute(enemy, VULNERABILITY_DEBUFF, player).deathCheck() == DEAD) break LOOP; // attack then check for death
                    } else {
                        initiateBattle.execute(enemy, player);
                    }
                    execute(); // print funny loss msg (i know this is awful)
                } else out.println("You managed to dodge in the nick of time!");
                enemyEnergy.deathCheck();
            }

        } while (player.state() != DEAD & enemy.state() != DEAD);

        if (player.state() != DEAD & player.state() != COWARD) { // win the fight if still alive
            player.setState(FIGHT_WIN);
            out.printf("You've gained "+ player.updateXP((float) randomize(rand.nextFloat(10), MIN_XP_GAIN)) +" XP!\n");
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


//        ArrayList<Integer> luck = new ArrayList<>(); // TODO turn this into pot of greed rare event
//        for (int i = 1; i <= 100; i++) { // create 100 random numbers between 1 and 2501
//            luck.add((int) (Math.random() * (2500)) + 1);
//        }
//        int sum = 0, winThreshold = 1215;
//        for (int numbers : luck) {
//            sum += numbers;
//        }
//        entity.update(
//              0,
//                0,
//               -1 * ((Math.random() * 21.202) + 10.210),
//               -1 * ((Math.random() * 99.99) + 0.01)
//       );
//       entity.deathCheck(entity); // check if dead
//
//        if (entity.state() == EntityState.ALIVE) {
//            if ((sum / luck.size()) < winThreshold) { // if sum is below threshold, player wins
//                entity.setState(EntityState.FIGHT_WIN);
//            } else {// put all strings into an array, split them into words, replace them in the array and randomize each array block to be printed.