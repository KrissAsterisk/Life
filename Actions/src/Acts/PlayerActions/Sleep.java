package Acts.PlayerActions;

import Acts.Actions;
import entity.types.Players.Players;

import static Shareables.RandomGenerator.rand;
import static Shareables.RandomGenerator.randomize;

public final class Sleep implements Actions {

    private final Players entity;

    public Sleep(Players entity) {
       this.entity = entity;
    }

    public void execute(){
        entity.update(
                -1 * (randomize(rand.nextDouble(21.3), 30.5)),
                -1 * (randomize(rand.nextDouble(21.7), 35.6)),
                1 * (randomize(rand.nextDouble(26.4), 15.2)),
                1 * (randomize(rand.nextDouble(23.21), 8.51))
        );
        entity.deathCheck();
    }
}
