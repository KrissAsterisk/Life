package entity.types.Players;

import entity.types.Entities;
import Shareables.Colours;
import Shareables.EntityState;


import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static Shareables.RandomGenerator.rand;
import static entity.types.Constants.*;
import static Shareables.RandomGenerator.randomize;
import static Shareables.Colours.AnsiCodes.*;
import static java.lang.System.out;
import static java.util.Collections.min;

public final class Players implements Entities {

    private double foodP, waterP, energyP, healthP;
    private EntityState entityState;
    private final String pName;
    private double damage; // uses xp to increase maxDmg
    private float xp;

    public Players(PlayerTemplate playerTemplate) {
        this.entityState = playerTemplate.currentState();
        this.pName = playerTemplate.name();
        this.damage = playerTemplate.damage();
        this.foodP = playerTemplate.foodP();
        this.waterP = playerTemplate.waterP();
        this.energyP = playerTemplate.energyP();
        this.healthP = playerTemplate.healthP();
        this.xp = playerTemplate.xp();
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

    public void updateDamage(double damage) {
        this.damage += damage;
    }

    public void updateXP(float xp) {
        this.xp += xp;
    }

    public EntityState deathCheck() {
        double meanWarning = ((food() + water() + energy() + health()) / 4); // purposefully bad
        if (deathThreshold(food(), water(), energy(), health())) { // would be nice if it told the player why they died - more data to add do their gravestones!
            var smallestValue = DoubleStream.of(food(), water(), energy(), health()).min().getAsDouble(); // TODO: add messages pertinent to the cause of death, like health -> heart stopped beating
            if (health() == smallestValue) {out.println("Cause of death: " + ANSI_RED + "Health(" + ANSI_HIGH_INTENSITY + smallestValue + ANSI_RESET + ")");}
            if (energy() == smallestValue) {out.println("Cause of death: " + ANSI_YELLOW + "Energy(" + ANSI_HIGH_INTENSITY + smallestValue + ANSI_RESET + ")");}
            if (water() == smallestValue) {out.println("Cause of death: " + ANSI_CYAN + "Water(" + ANSI_HIGH_INTENSITY + smallestValue + ANSI_RESET + ")");}
            if (food() == smallestValue) {out.println("Cause of death: " + ANSI_GREEN + "Food(" + ANSI_HIGH_INTENSITY + smallestValue + ANSI_RESET + ")");}
            Colours.clear();
            setState(EntityState.DEAD);
        } else if (meanWarning < 50.00 && meanWarning > -10.00) { // intentionally useless right now! // TODO: put all data in a Tree and pick the lowest one to show the player which is the lowest
            out.printf("%n%s%sWatch it!%s Your state is in critical condition!\nYou will lose the game if one of your points go below -10!%n",
                    ANSI_RED, ANSI_HIGH_INTENSITY, ANSI_RESET);
            Colours.clear();

            setState(EntityState.ALIVE); // redundant but its ok
        }
        return state();
    }


    /**
     * Checks if the stream of doubles contains any that are below PLAYER_DEATH_THRESHOLD (-10)
     */
    public boolean deathThreshold(double ... v){ // im proud of this one
        var doubleStream = DoubleStream.of(v);
        var temp = doubleStream.filter(x -> x < PLAYER_DEATH_THRESHOLD)
                .collect( // can prolly replace this with something more readable but its ok for now
                        ArrayList::new,
                        ArrayList::add,
                        ArrayList::addAll
                );
        return !temp.isEmpty();
    }

    public void levelUpCheck(Players player) {
        if (player.xp() >= LEVEL_UP_THRESHOLD) {
            player.updateXP(-LEVEL_UP_THRESHOLD);
            player.updateDamage(randomize(rand.nextDouble(1), MAX_DAMAGE_GAIN)); // at most 5 extra damage

        }
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

    public float xp() {
        return xp;
    }

}
