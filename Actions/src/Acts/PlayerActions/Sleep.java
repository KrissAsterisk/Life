package Acts.PlayerActions;

import Acts.Actions;
import entity.types.Entities;

import static Shareables.RandomGenerator.rand;
import static Shareables.RandomGenerator.randomize;

public final class Sleep implements Actions {


    public Sleep(Entities entity) {
        action(entity);
    }

    public void action(Entities entity){
        entity.update(
                -1 * (randomize(rand.nextDouble(21.3), 30.5)),
                -1 * (randomize(rand.nextDouble(21.7), 35.6)),
                1 * (randomize(rand.nextDouble(26.4), 15.2)),
                1 * (randomize(rand.nextDouble(23.21), 8.51))
        );
        entity.deathCheck();
    }
}
