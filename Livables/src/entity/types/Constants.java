package entity.types;

import entity.types.Players.Players;

import java.util.function.Consumer;

import static Shareables.Colours.AnsiCodes.*;
import static java.lang.System.out;

public final class Constants { // namespace of constants
    Constants(){}

    public final static int LEVEL_UP_THRESHOLD = 100;
    public final static int MAX_DAMAGE_GAIN = 10;
    public final static int PLAYER_DEATH_THRESHOLD = -10;
    public final static double MIN_ENERGY_COST = 10;
    public final static double IGNORE_ENERGY_RESTRICTIONS = 200.0;

    public final static Consumer<Entities> printHealth = entity -> out.printf("%sHP: %f%n", ANSI_RED, entity.health());
    public final static Consumer<Entities> printEnergy = entity -> out.printf("%sEP: %f%n", ANSI_YELLOW, entity.energy());
    public final static Consumer<Players> printWater = entity -> out.printf("%sWP: %f%n", ANSI_CYAN, entity.water());
    public final static Consumer<Players> printFood = entity -> out.printf("%sFP: %f%n", ANSI_GREEN, entity.food());
    public final static Consumer<Players> printLevel = entity -> out.printf("%sLevel: %d, XP: %f%n", ANSI_PURPLE, entity.level(), entity.xp());

}
