package Acts;

import Mine.PlayerState;

public interface Entities {
    String name();

    double food();
    double water();
    double energy();
    double health();

    PlayerState state();

    void update(double foodP, double waterP, double energyP, double healthP);
    void setState(PlayerState state);

}
