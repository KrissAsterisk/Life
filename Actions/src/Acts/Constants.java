package Acts;

import entity.types.Entities;

import java.util.function.Consumer;

import static Shareables.Colours.AnsiCodes.*;
import static Shareables.Colours.AnsiCodes.ANSI_GREEN;
import static java.lang.System.out;

public interface Constants {
    double MIN_XP_GAIN = 10;
    double MIN_ENERGY_COST = 10;
    double VULNERABILITY_DEBUFF = 2.25;
    double IGNORE_ENERGY_RESTRICTIONS = 200.0;
    Consumer<Entities> printHealth = entity -> out.printf("%sHP: %f%n", ANSI_RED, entity.health());
    Consumer<Entities> printEnergy = entity -> out.printf("%sEP: %f%n", ANSI_YELLOW, entity.energy());
    Consumer<Entities> printWater = entity -> out.printf("%sWP: %f%n", ANSI_CYAN, entity.water());
    Consumer<Entities> printFood = entity -> out.printf("%sFP: %f%n", ANSI_GREEN, entity.food());
    Consumer<Entities> printPlayerStatus = entity -> printHealth.andThen(printEnergy).andThen(printWater).andThen(printFood).accept(entity);
    Consumer<Entities> printEnemyStatus = entity -> printHealth.andThen(printEnergy).accept(entity);


}
