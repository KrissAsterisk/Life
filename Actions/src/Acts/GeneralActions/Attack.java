package Acts.GeneralActions;

import Shareables.EntityState;
import entity.types.Entities;

import static java.lang.System.out;

public class Attack {

    public Entities execute(Entities attacker, Entities defender) {
        out.println(attacker.getName() + " attacks for " + attacker.damage() + " damage!");
        defender.updateHealth(-attacker.damage());
        return defender;
    }
    public Entities execute(Entities attacker, double VULNERABILITY_DEBUFF, Entities defender){
        out.println("The "+ attacker.getName() +" strikes a CRITICAL HIT for " + attacker.damage() * VULNERABILITY_DEBUFF + " damage!");
        defender.updateHealth(-(attacker.damage() * VULNERABILITY_DEBUFF));
        defender.setState(EntityState.RESET); // reset defender's state after crit
        return defender;
    }
}