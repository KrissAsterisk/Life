package Acts;

import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.ToDoubleBiFunction;

public interface RandomGenerator {
    Random rand = new Random(123);


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
        ToDoubleBiFunction<Double, Double> getRandDouble = (x, y) -> ((Math.random() * x) + y);
        return getRandDouble.applyAsDouble(valueToBeRandomized, minimumDesiredResult);
    }

}
