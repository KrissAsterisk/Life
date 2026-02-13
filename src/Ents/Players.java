package Ents;


import Acts.Actions;
import Acts.Status;
import Mine.Colours;

import static Acts.RandomGenerator.randomize;
import static Mine.Colours.AnsiCodes.*;
import static java.lang.System.out;

public final class Players implements Entities {

    private double foodP, waterP, energyP, healthP;
    private EntityState entityState;
    private final String pName;
    private final double damage; //

    public Players(String pName, EntityState entityState, double foodP, double waterP, double energyP, double healthP) {
        this.entityState = entityState;
        this.pName = pName;
        this.damage  = randomize (10.0, 10.0); // TODO: add lvl up system
        this.foodP = healthP;
        this.waterP = energyP;
        this.energyP = waterP;
        this.healthP = foodP;
    }

    public void update(double foodP, double waterP, double energyP, double healthP) {
        this.foodP += foodP;
        this.waterP += waterP;
        this.energyP += energyP;
        this.healthP += healthP;
    }

    public void updateHealth(double healthP) {
        this.healthP += healthP;
    }

    public void updateEnergy(double energyP) {
        this.energyP += energyP;
    }

    public EntityState deathCheck(Entities entity) {
        double meanWarning = ((this.food() + this.water() + entity.energy() + entity.health()) / 4);
        if (entity.food() < -10 || entity.water() < -10 || entity.energy() < -10 || entity.health() < -10) {
            new Status(entity); // now call the status action that handles its own logic
            Colours.clear();
            entity.setState(EntityState.DEAD);
        } else if (meanWarning < 50.00 && meanWarning > -10.00) { // intentionally useless right now! // TODO: put all data in a Tree and pick the lowest one to show the player which is the lowest
            out.printf("%n%s%sWatch it!%s Your state is in critical condition!\nYou will lose the game if one of your points go below -10!%n",
                    ANSI_RED.colourCode(), ANSI_HIGH_INTENSITY.colourCode(), ANSI_RESET.colourCode());
            Colours.clear();

            entity.setState(EntityState.ALIVE); // redundant but its ok
        }
        return entity.state();
    }

    public void setState(EntityState state) {
        this.entityState = state;
    }

    public double damage() {
        return damage;
    }

    public String getName() {
        return pName;
    }

    public EntityState state() {
        return entityState;
    }

    public double food() {
        return foodP;
    }

    public double water() {
        return waterP;
    }

    public double energy() {
        return energyP;
    }

    public double health() {
        return healthP;
    }

}
