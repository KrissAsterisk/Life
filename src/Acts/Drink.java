package Acts;

import Ents.Entities;

import static Acts.RandomGenerator.randomize;

public final class Drink implements Actions{

    public Drink(Entities entities) {
        action(entities);
    }

    public void action(Entities entity){

        entity.update(
                1 * (randomize(3.7,0.5)),
                1 * (randomize(26.757, 14.543)),
                -1 * (randomize(11.27, 5.23)),
                1 * (randomize(3.9, 2.1))
        );
        entity.deathCheck(entity);
    }
}
