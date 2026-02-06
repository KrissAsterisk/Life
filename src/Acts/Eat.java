package Acts;

public final class Eat implements Actions {


    public void action(Entities entity){

        entity.update(
                (Math.random() * 15.5) + 25.5,
                (Math.random() * 5.3) + 1,
                (-1 * ((Math.random() * 8.3) + 7.8)),
                (Math.random() * 8.6) + 3.1
        );
        Actions.deathCheck(entity);
    }
}
