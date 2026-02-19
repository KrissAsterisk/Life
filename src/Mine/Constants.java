package Mine;

import Ents.Entities;

import java.util.function.Consumer;

import static Mine.Colours.AnsiCodes.*;
import static Mine.Colours.AnsiCodes.ANSI_GREEN;
import static java.lang.System.out;

public interface Constants {
    public int STARTING_MOVES = 50;
    final double VULNERABILITY_DEBUFF = 2.25;
    final static String highScoresFilePath = "Highscores.txt";
    public static final Consumer<Entities> printHealth = entity -> out.printf("%sHP: %f%n", ANSI_RED, entity.health());
    public static final Consumer<Entities> printEnergy = entity -> out.printf("%sEP: %f%n", ANSI_YELLOW, entity.energy());
    public static final Consumer<Entities> printWater = entity -> out.printf("%sWP: %f%n", ANSI_CYAN, entity.water());
    public static final Consumer<Entities> printFood = entity -> out.printf("%sFP: %f%n", ANSI_GREEN, entity.food());
    Consumer<Entities> printPlayerStatus = entity -> printHealth.andThen(printEnergy).andThen(printWater).andThen(printFood).accept(entity);
    Consumer<Entities> printEnemyStatus = entity -> printHealth.andThen(printEnergy).accept(entity);
}
