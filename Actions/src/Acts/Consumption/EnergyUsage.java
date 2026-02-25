package Acts.Consumption;

import Shareables.EntityState;
import entity.types.Entities;

import static Acts.Constants.MIN_ENERGY_COST;
import static Shareables.RandomGenerator.rand;
import static Shareables.RandomGenerator.randomize;

public class EnergyUsage {
    @SuppressWarnings("all")
    private Entities entity; // NOT FINAL!!

    public EnergyUsage(Entities entity) {
        this.entity = entity;
        entity.updateEnergy(-(randomize(rand.nextDouble(5.2364), MIN_ENERGY_COST)));
    }

    public EnergyUsage(){}

    public EntityState useEnergy(){
        return entity.deathCheck();
    }
}
