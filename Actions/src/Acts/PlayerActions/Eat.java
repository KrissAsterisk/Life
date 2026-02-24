package Acts.PlayerActions;

import Acts.Actions;
import entity.types.Entities;

import static Shareables.RandomGenerator.rand;
import static Shareables.RandomGenerator.randomize;

public final class Eat implements Actions {

    public Eat(Entities entities) {
        action(entities);
    }

    public void action(Entities entity){

        entity.update(
                1 * (randomize(rand.nextDouble(15.5), 25.5)),
                1 *(randomize(rand.nextDouble(5.3), 1)),
                -1 * (randomize(rand.nextDouble(8.3), 7.8)),
                1 * (randomize(rand.nextDouble(8.6), 3.1))
        );
        entity.deathCheck();
    }
}
