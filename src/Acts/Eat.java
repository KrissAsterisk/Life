package Acts;

import Ents.Entities;

import static Acts.RandomGenerator.randomize;

public final class Eat implements Actions {


    public Eat(Entities entities) {
        action(entities);
    }

    public void action(Entities entity){

        entity.update(
                1 * (randomize(15.5, 25.5)),
                1 *(randomize(5.3, 1)),
                -1 * (randomize(8.3, 7.8)),
                1 * (randomize(8.6, 3.1))
        );
        entity.deathCheck(entity);
    }
}
