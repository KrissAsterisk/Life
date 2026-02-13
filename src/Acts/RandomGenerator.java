package Acts;

import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;

public interface RandomGenerator {
    static final long DEBUG_SEED = 69420L;
    public static final Random rand = new Random(DEBUG_SEED);

    Supplier<Double> roll01Double = Math::random;


    /**
     * Generates a random integer between the specified origin (inclusive) and bound (inclusive),
     * then checks if the generated value equals 1.
     *
     * @param origin the inclusive lower bound of the random number range.
     * @param bound the inclusive upper bound of the random number range.
     * @return {@code true} if the randomly generated value equals 1, otherwise {@code false}.
     */
    public static boolean getXinY(int origin, int bound){
        bound+=1; // include the bound
        BiFunction<Integer, Integer, Boolean> roll = (x,y) -> rand.nextInt(x, y) == 1;
        return roll.apply(origin, bound);
    }


    /**
     * Rand nextInt
     * @param origin
     * @param bound
     * @return 1 integer between the origin and bound
     */
    public static int randomize(int origin, int bound){
        bound+=1;
        BiFunction<Integer,Integer,Integer> roll = rand::nextInt;
        return roll.apply(origin, bound);
    }


    /**
     * Generates a randomized double value by scaling a random number in the range [0, 1)
     * with the specified value to be randomized and adding a minimum desired result.
     *
     * @param valueToBeRandomized the range within which the random value will be scaled.
     * @param minimumDesiredResult the minimum value added to the randomized result to
     *                             ensure the output meets or exceeds this value.
     * @return a double value that is the result of the randomization process within
     *         the calculated range.
     */
    public static double randomize(double valueToBeRandomized, double minimumDesiredResult ){
        ToDoubleBiFunction<Double, Double> getRandDouble = (x, y) -> ((roll01Double.get() * x) + y);
        return getRandDouble.applyAsDouble(valueToBeRandomized, minimumDesiredResult);
    }

}
