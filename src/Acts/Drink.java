package Acts;

import Ents.Entities;

public final class Drink implements Actions{

    public void action(Entities entity){

        entity.update(
                (Math.random() * 3.7) + 0.5,
                (Math.random() * 26.757) + 14.543,
                -1 * ((Math.random() * 11.27) + 5.23),
                (Math.random() * 3.9) + 2.1
        );
        entity.deathCheck(entity);
    }
}
