package Acts.PlayerActions;

import Acts.Actions;
import entity.types.Entities;

import static Shareables.RandomGenerator.rand;
import static Shareables.RandomGenerator.randomize;

public final class Drink implements Actions {

    public Drink(Entities entities) {
        action(entities);
    }

    public void action(Entities entity){

        entity.update(
                1 * (randomize(rand.nextDouble(3.7),0.5)),
                1 * (randomize(rand.nextDouble(26.757), 14.543)),
                -1 * (randomize(rand.nextDouble(7.27), 5.23)),
                1 * (randomize(rand.nextDouble(3.9), 2.1))
        );
        entity.deathCheck();
    }
}
