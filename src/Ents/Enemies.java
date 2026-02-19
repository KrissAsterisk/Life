package Ents;

import Mine.Colours;

import java.util.ArrayList;
import java.util.Arrays;

import static Acts.RandomGenerator.randomize;
import static Mine.Colours.AnsiCodes.*;
import static java.lang.System.out;

public final class Enemies implements Entities {

    private EntityState entityState;
    private double healthP;
    private double energyP;

    private double damage;
    private String name;

//    public Enemies(String name, EntityState state, double healthP, double energyP, double damage){
//        this.name = name;
//        this.entityState = state;
//        this.healthP = healthP;
//        this.energyP = energyP;
//        this.damage = damage;
//    }

    public Enemies(String name) {
        out.println("A new " + name + " appears!");
    }

    public Enemies(EnemyTypes type) {
        Object[] tempArray = type.getData();
        this.name = tempArray[0].toString();
        this.healthP = Double.parseDouble(tempArray[1].toString());
        this.energyP = Double.parseDouble(tempArray[2].toString());
        this.damage = randomize(Double.parseDouble(tempArray[3].toString()), Double.parseDouble(tempArray[3].toString())); // randomizes enemy damage when ran & if mathrandom rolls 0, its minDamage will be used; this is kinda bad since well never really be able to know its data at runtime
        this.entityState = EntityState.ALIVE;
        this(tempArray[0].toString());
    }

    public double damage() {
        return damage;
    }

    public Object[] getAll(){
        return new Object[]{name, entityState, healthP, energyP, damage};
    }

    public String getName() {
        return name;
    }

    public double energy() {
        return energyP;
    }

    public double food() {
        return 0;
    }

    public double water() {
        return 0;
    }

    public double health() {
        return healthP;
    }

    public EntityState state() {
        return entityState;
    }

    public void update(double foodP, double waterP, double energyP, double healthP) {
        this.healthP += healthP;
        this.energyP += energyP;

    }
    public void updateHealth(double healthP) {
        this.healthP += healthP;

    }
    public void updateEnergy(double energyP) {
        this.energyP += energyP;
    }

    public void setState(EntityState state) {
        this.entityState = state;
    }

    public void updateXP(float xp) {}

    public EntityState deathCheck(Entities entity) {
        if (entity.energy() < -10 || entity.health() <= 0) {
            entity.setState(EntityState.DEAD);
        } else {
            entity.setState(EntityState.ALIVE); // redundant but its ok
        }
        return entity.state();
    }
}
