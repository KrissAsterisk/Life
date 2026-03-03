package Acts.PlayerActions.ExploreOptions;

import entity.types.Players.Players;

import java.util.ArrayList;

import static Acts.Constants.*;
import static Acts.Constants.MIN_XP_GAIN;
import static Shareables.EntityState.DEAD;
import static Shareables.EntityState.GAUNTLET_END;
import static Shareables.RandomGenerator.rand;
import static Shareables.RandomGenerator.randomize;
import static java.lang.System.out;

public final class Gauntlet {
    private Gauntlet(){}
    public static void event(Players player) {
        out.println("The floor beneath you crumbles and you find yourself in a gauntlet!");
        var fightNumber = 1;
        ArrayList<Float> xpEarned = new ArrayList<>();
        while (player.state() != DEAD && player.state() != GAUNTLET_END) {
            ArrayList<Integer> luck = new ArrayList<>();
            for (int i = 1; i <= 100; i++) { // create 100 random numbers between 1 and 2501
                luck.add((int) (randomize(2500.0, 1.0)));
            }
            int sum = luck.stream().mapToInt(x -> x).sum();
            player.updateHealth(-GAUNTLET_HEALTH_CONSUMPTION);
            player.updateEnergy(-GAUNTLET_ENERGY_CONSUMPTION);
            if (player.deathCheck() != DEAD) {
                if ((sum / luck.size()) < GAUNTLET_WIN_THRESHOLD) { // if sum is below threshold, player wins
                    out.println("You've defeated enemy number " + fightNumber);
                    xpEarned.add(player.updateXP((float) randomize(rand.nextFloat(10), MIN_XP_GAIN)));
                } else {
                    player.setState(GAUNTLET_END);
                    out.println("You've lost fight number " + fightNumber + ", better retreat before you get overrun...");
                }
                fightNumber++;
            }
        }
        var xpSum = xpEarned.stream().mapToDouble(x -> x).sum();
        if (player.state() != DEAD && xpSum != 0) out.println("In total you've gained: " + xpSum + " XP!");
    }
}
