package Acts.PlayerActions;

import Acts.Actions;
import entity.types.Players.Players;

import static Shareables.RandomGenerator.rand;
import static Shareables.RandomGenerator.randomize;

public final class Drink implements Actions {

    private final Players player;

    public Drink(Players player) {
        this.player = player;
    }

    public void execute(){

        player.update(
                1 * (randomize(rand.nextDouble(3.7),0.5)),
                1 * (randomize(rand.nextDouble(26.757), 14.543)),
                -1 * (randomize(rand.nextDouble(7.27), 5.23)),
                1 * (randomize(rand.nextDouble(3.9), 2.1))
        );
        player.deathCheck();
    }
}
