package entity.types.Enemies;

import Shareables.Colours;
import Shareables.EntityState;
import entity.types.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static Shareables.EntityState.DEAD;
import static Shareables.RandomGenerator.rand;
import static Shareables.RandomGenerator.randomize;
import static entity.types.Constants.*;
import static java.lang.System.out;

public final class Enemies implements Entities, Serializable {

    private transient EntityState entityState;
    private transient double healthP;
    private transient double energyP;

    private final transient double damage;
    private final transient String name;

    private transient final Consumer<Enemies> printEnemyStatus = entity -> printHealth.andThen(printEnergy).accept(entity);
//    public Enemies(String name, EntityState state, double healthP, double energyP, double damage){
//        this.name = name;
//        this.entityState = state;
//        this.healthP = healthP;
//        this.energyP = energyP;
//        this.damage = damage;
//    }

    public void printEnemyName() {
        out.println("A new " + getName() + " appears!");
    }

    public Enemies(EnemyTypes type) {
        this.name = type.namedEnemy;
        this.healthP = type.maxHealth;
        this.energyP = type.maxEnergy;
        this.damage = randomize(rand.nextDouble(10), type.minDamage); // randomizes enemy damage when ran & if mathrandom rolls 0, its minDamage will be used; this is kinda bad since well never really be able to know its data at runtime
        this.entityState = EntityState.ALIVE;
    }

    public boolean canAct() { // idea is so that the more energy an enemy has, the more likely they are to move
        return ((!(state() == DEAD)) && Math.random() < (energy() / (energy() + MIN_ENERGY_COST))) | energy() >= IGNORE_ENERGY_RESTRICTIONS;
    }

    public double damage() {
        return damage;
    }

    public ArrayList<Object> getAll(){
        return new ArrayList<>(List.of(name, entityState, healthP, energyP, damage));
    }

    public String getName() {
        return name;
    }

    public double energy() {
        return energyP;
    }

    public double health() {
        return healthP;
    }

    public EntityState state() {
        return entityState;
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

    public EntityState deathCheck() {
        if (this.energyP < -1 || this.healthP <= 0) {
            this.entityState = EntityState.DEAD;
        } else {
            this.entityState = EntityState.ALIVE; // redundant but its ok
        }
        return this.entityState;
    }

    public void printStatus(){
        printEnemyStatus.accept(this);
        Colours.clear();
    }
}
