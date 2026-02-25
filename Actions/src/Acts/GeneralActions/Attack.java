package Acts.GeneralActions;

import Acts.Consumption.EnergyUsage;
import Shareables.EntityState;
import entity.types.Entities;

import static java.lang.System.out;

public class Attack extends EnergyUsage {

    public Attack(Entities entity){
        super(entity);
    }

    public Attack(){
    }

    public EntityState action(Entities attacker, Entities defender) {
        out.println(attacker.getName() + " attacks for " + attacker.damage() + " damage!");
        defender.updateHealth(-attacker.damage());
        return defender.deathCheck();
    }
    public EntityState action(Entities attacker, double VULNERABILITY_DEBUFF, Entities defender){
        out.println("The "+ attacker.getName() +" strikes a CRITICAL HIT for " + attacker.damage() * VULNERABILITY_DEBUFF + " damage!");
        defender.updateHealth(-(attacker.damage() * VULNERABILITY_DEBUFF));
        return defender.deathCheck();
    }
}