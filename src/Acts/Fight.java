package Acts;

import Ents.*;
import Mine.Colours;

import java.util.*;

import static Ents.EntityState.*;
import static Mine.Colours.AnsiCodes.*;
import static Acts.PossibleFightMoves.checkInput;
import static java.lang.System.out;

public final class Fight implements Actions, FightLossMessages {
    private static final int ENERGY_COST = 10;


    public void attack(Entities player, Entities enemy, Scanner reader, Actions status) {
//        if(new Enemies((EnemyTypes.randomized(new Random().nextInt(0, EnemyTypes.values().length)))).getName().equals("Goblin")){ // Jesus Christ
//            new Goblin(); // this belongs in a museum
//        }
        player.setState(RESET); // reset vulnerable
        final double VULNERABILITY_DEBUFF = 2.25;
        LOOP:
        do {
            status.action(enemy);
            out.println("What is your next move?");
            out.println("1 - Attack\t2 - Dodge\t3 - Flee");
            switch (checkInput(reader.nextLine().toLowerCase().trim())) {
                case ATTACK -> { // TODO use constants for some of these
                    player.updateEnergy(-15);
                    if (player.deathCheck(player) == DEAD) break LOOP; // break instantly
                    enemy.updateHealth(-player.damage());
                    if (enemy.deathCheck(enemy) == DEAD) break LOOP;
                    status.action(enemy);
                }
                case DODGE -> {
                    if (new Random().nextInt(1, 11) == 1) { //TODO: combine with level up
                        out.println("You gracefully dodge and the " + enemy.getName() + " hits itself!");
                        enemy.updateHealth(-enemy.damage());
                    } else {
                        out.println("Uh Oh....");
                        player.setState(VULNERABLE);
                    }
                    if (enemy.deathCheck(enemy) == DEAD) break LOOP;
                }
                case FLEE -> {
                    //need more
                    enemy.setState(DEAD);
                    player.setState(COWARD);
                    action(player); // TODO rename this later
                    break LOOP;
                }
                case null, default -> {
                    System.out.println("You trip on a rock!");
                    player.updateHealth(-5);
                }
            }
            if (!(enemy.state() == DEAD) && new Random().nextDouble(1, enemy.energy() + 1) / enemy.energy() > 0.5) { // TODO idea is so that the more energy an enemy has, the more likely they are to move
                out.println("The " + enemy.getName() + " attacks!\n");
                enemy.updateEnergy(-ENERGY_COST);
                if (!(new Random().nextInt(1, 21) == 1)) { // 1 in 21 to dodge
                    if(player.state() == VULNERABLE){
                        player.updateHealth(-(enemy.damage()*VULNERABILITY_DEBUFF));// punishment for missing dodge
                        if(player.deathCheck(player) == DEAD){ break LOOP;}
                    }
                    else player.updateHealth(-enemy.damage()); // TODO: maybe put death check in update methods? passing reference now?

                    action(player); // print funny loss msg
                } else out.println("You managed to dodge in the nick of time!");
                player.deathCheck(player);
                enemy.deathCheck(player);
            }
        } while (player.state() != DEAD & enemy.state() != DEAD);

        if (player.state() != DEAD & player.state() != COWARD) player.setState(FIGHT_WIN);
    }

    //prints a random generate sentence
    public void action(Entities entity) {

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

        int newArrayLength = (new Random().nextInt(1, funnyHaha.length / 5 + 1)); // random sentence size, funnyHaha for some reason is tiny, exception thrown, so use its own size
        for (int i = 0; i < newArrayLength; i++) {
            funnyHaha[i] = funnyHaha[i].trim().replaceAll("[^\\w]", "").replaceAll(" ", ""); // remove all non-word chars aka . ! /  ...
            // had to replace all spaces with blanks since we're adding them ourselves
            if (i == 0) {
                if (!funnyHaha[i].isEmpty())
                    out.print(funnyHaha[i] = funnyHaha[i].replaceFirst(funnyHaha[i].substring(0, 1), funnyHaha[i].toUpperCase().substring(0, 1))); // this looks great :)
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