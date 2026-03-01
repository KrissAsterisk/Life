package Acts;

import static Shareables.RandomGenerator.rand;
import static Shareables.RandomGenerator.randomize;

public final class Constants {
    Constants(){}
    public final static double MIN_XP_GAIN = 10;
    public final static double MIN_ENERGY_COST = 10;
    public final static double VULNERABILITY_DEBUFF = 2.25;
    public final static double IGNORE_ENERGY_RESTRICTIONS = 200.0;
    public static final double cost = (randomize(rand.nextDouble(5.2364), MIN_ENERGY_COST));
}
