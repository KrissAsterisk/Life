package Acts.Consumption;

import Acts.GeneralActions.Attack;
import entity.types.Entities;

import static Acts.Constants.cost;

public class EnergyUsage {



    public Entities useEnergy(Entities entity) {
        entity.updateEnergy(-cost);
        return entity;
    }
}
