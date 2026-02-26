package entity.types;

import entity.types.Enemies.Enemies;
import Shareables.EntityState;
import entity.types.Players.Players;

public sealed interface Entities permits Players, Enemies, HasLevels,  Survivor {
    String getName();

    double energy();
    double health();
    double damage();
    EntityState state();
    EntityState deathCheck();


    void updateHealth(double healthP);
    void updateEnergy(double energyP);
    void setState(EntityState state);


}
