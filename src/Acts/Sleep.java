package Acts;

import Ents.Entities;

import static Acts.RandomGenerator.randomize;

public final class Sleep implements Actions {


    public Sleep(Entities entity) {
        action(entity);
    }

    public void action(Entities entity){
        entity.update(
                -1 * (randomize(21.3, 30.5)),
                -1 * (randomize(21.7, 35.6)),
                1 * (randomize(26.4, 15.2)),
                1 * (randomize(23.21, 8.51))
        );
        entity.deathCheck(entity);
    }
}
