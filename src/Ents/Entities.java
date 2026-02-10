package Ents;

public sealed interface Entities permits Enemies, Players {
    String getName();

    double food(); // TODO: not all entities need to have food & water parms
    double water();
    double energy();
    double health();
    double damage();

    EntityState state();
    abstract EntityState deathCheck(Entities entity);

    void update(double foodP, double waterP, double energyP, double healthP);
    void updateHealth(double healthP);
    void updateEnergy(double energyP);
    void setState(EntityState state);

}
