package Acts;

public final class Sleep implements Actions {


    public void action(Entities entity){
        entity.update(
                -1 * ((Math.random() * 21.3) + 30.5),
                -1 * ((Math.random() * 21.7) + 35.6),
                (Math.random() * 26.4) + 15.2,
                (Math.random() * 23.21) + 8.51
        );
        Actions.deathCheck(entity);
    }
}
