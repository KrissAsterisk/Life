package entity.types.Players;

import Shareables.Colours;
import Shareables.EntityState;
import entity.types.Entities;
import entity.types.HasLevels;
import entity.types.Survivor;


import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;

import static Shareables.RandomGenerator.rand;
import static entity.types.Constants.*;
import static Shareables.RandomGenerator.randomize;
import static Shareables.Colours.AnsiCodes.*;
import static java.lang.System.out;


public final class Players implements Entities, Survivor, HasLevels {

    private double foodP, waterP, energyP, healthP;
    private EntityState entityState;
    private final String pName;
    private double damage; // uses xp to increase maxDmg
    private float xp;
    private byte level;

    Consumer<Players> printPlayerStatus = entity -> printLevel.andThen(printHealth).andThen(printEnergy).andThen(printWater).andThen(printFood).accept(entity);


    public Players(PlayerTemplate playerTemplate) {
        this.entityState = playerTemplate.currentState();
        this.pName = playerTemplate.name();
        this.damage = playerTemplate.damage();
        this.foodP = playerTemplate.foodP();
        this.waterP = playerTemplate.waterP();
        this.energyP = playerTemplate.energyP();
        this.healthP = playerTemplate.healthP();
        this.xp = playerTemplate.xp();
        this.level = playerTemplate.level();
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

    public double updateDamage(double damage) {
        this.damage += damage;
        return damage;
    }

    public float updateXP(float xp) {
        this.xp += xp;
        return xp;
    }

    public EntityState deathCheck() {
        double meanWarning = ((food() + water() + energy() + health()) / 4); // purposefully bad
        if (deathThreshold(food(), water(), energy(), health())) {
            var smallestValue = DoubleStream.of(food(), water(), energy(), health()).min().getAsDouble(); // TODO: add messages pertinent to the cause of death, like health -> heart stopped beating
            if (health() == smallestValue) {
                out.println("Cause of death: " + ANSI_RED + "Health(" + ANSI_HIGH_INTENSITY + smallestValue + ANSI_RESET + ")");
            }
            if (energy() == smallestValue) {
                out.println("Cause of death: " + ANSI_YELLOW + "Energy(" + ANSI_HIGH_INTENSITY + smallestValue + ANSI_RESET + ")");
            }
            if (water() == smallestValue) {
                out.println("Cause of death: " + ANSI_CYAN + "Water(" + ANSI_HIGH_INTENSITY + smallestValue + ANSI_RESET + ")");
            }
            if (food() == smallestValue) {
                out.println("Cause of death: " + ANSI_GREEN + "Food(" + ANSI_HIGH_INTENSITY + smallestValue + ANSI_RESET + ")");
            }
            Colours.clear();
            setState(EntityState.DEAD);
        } else if (meanWarning < 50.00 && meanWarning > -10.00) { // intentionally useless right now! //
            out.printf("%n%s%sWatch it!%s Your state is in critical condition!\nYou will lose the game if one of your points go below -10!%n",
                    ANSI_RED, ANSI_HIGH_INTENSITY, ANSI_RESET);
            Colours.clear();
        }
        return state();
    }


    /**
     * Checks if the stream of doubles contains any that are below PLAYER_DEATH_THRESHOLD (-10)
     */
    public boolean deathThreshold(double... v) { // im proud of this one
        return DoubleStream.of(v).anyMatch(x -> x < PLAYER_DEATH_THRESHOLD);
    }

    public void levelUpCheck(Players player) {
        if (player.xp() >= LEVEL_UP_THRESHOLD) {
            player.updateXP(-LEVEL_UP_THRESHOLD);
            var dmgGained = player.updateDamage(randomize(rand.nextDouble(1), MAX_DAMAGE_GAIN));// at most 10 extra damage
            level++;
            out.println("You've just leveled up and gained " + ANSI_RED + dmgGained + ANSI_RESET + " extra damage!");
            out.println("You're more confident you can dodge due to your " + ANSI_PURPLE + "experience" + ANSI_RESET + "!");
            Colours.clear();
        }
    }

    public void setState(EntityState state) {
        this.entityState = state;
    }

    public double damage() {
        return damage;
    }

    public byte level() {
        return level;
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

    public void printStatus(){
        printPlayerStatus.accept(this);
        Colours.clear();
    }

}
