package Acts.Consumption;

import entity.types.Entities;

import static Acts.Constants.COST;

public class EnergyUsage {

    public Entities useEnergy(Entities entity) {
        entity.updateEnergy(-COST);
        return entity;
    }
}
