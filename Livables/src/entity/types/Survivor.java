package entity.types;

import entity.types.Players.Players;

public sealed interface Survivor extends Entities permits Players {


    double food();
    double water();
    void update(double foodP, double waterP, double energyP, double healthP);

}
