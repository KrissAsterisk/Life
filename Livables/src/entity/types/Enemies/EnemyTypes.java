package entity.types.Enemies;


import entity.types.CheckTrait;
import entity.types.GetNumber;

import java.util.*;

import static Shareables.RandomGenerator.randomize;

/**
 * Holds all possible types of enemies and their specific parameters
 */
public enum EnemyTypes {
    GOBLIN("Goblin", 0, 150, 50, 15, true, true, false),
    DWARF("Dwarf", 1, 50, 100, 25, false, true, false),
    MONGREL("Mongrel", 2, 100, 1, 100, false, false, false),
    ASSASSIN("Assassin", 3, 25, 200, 30, false, true, false),
    NEANDERTHAL("Neanderthal", 4, 200, 100, 25, false, true, false),
    WEREWOLF("Werewolf", 5, 250, 300, 10, false, false, true),
    ROBOT("Robot", 6, 400, 30, 5, false, true, true),
    FLESHWALL("Flesh Wall", 7, 20, 0, 0, false, false, true);

    public final String namedEnemy;
    private final int position;
    public final double maxHealth, maxEnergy;
    public final double minDamage;
    public final boolean isDefault, isCommon, isRare;

    EnemyTypes(String name, int position, double maxHealth, double maxEnergy, double minDamage, boolean isDefault, boolean isCommon, boolean isRare) {
        this.namedEnemy = name;
        this.position = position;
        this.maxHealth = maxHealth;
        this.maxEnergy = maxEnergy;
        this.minDamage = minDamage;
        this.isDefault = isDefault;
        this.isCommon = isCommon;
        this.isRare = isRare;
    }

    /**
     * Checks every value in the EnemyTypes enum for a desired match
     *
     * @param checker Functional Interface reference for lambda use!
     * @param enemy   The enemy object created whenever the player decides to fight
     * @return True if the new enemy generated has the boolean passed by lambda. Returns false otherwise.
     */
    public static boolean getEnemyRarity(CheckTrait checker, Enemies enemy) {
        for (var enemyType : EnemyTypes.values()) { // for every type in the enum
            if (checker.checkTrait(enemyType)) { // check for the desired boolean - declared in the interface
                if (enemyType.namedEnemy.equals(enemy.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retrieves a list of EnemyTypes that match a specific trait defined by the provided CheckTrait instance.
     *
     * @param checker A functional interface that defines the trait-checking logic for each EnemyTypes enumeration.
     *                The implementation of this parameter determines whether an EnemyType is included in the result.
     * @return An ArrayList containing all EnemyTypes that satisfy the condition evaluated by the CheckTrait implementation.
     */
    public static ArrayList<EnemyTypes> getEnemyRarity(CheckTrait checker) {
        var soughtTypes = new ArrayList<EnemyTypes>();
        for (var enemy : EnemyTypes.values()) {
            if (checker.checkTrait(enemy)) {
                soughtTypes.add(enemy);
            }
        }
        return soughtTypes;
    }

    /**
     * Retrieves the EnemyTypes enumeration corresponding to a given enemy object,
     * based on matching name and associated numerical data.
     *
     * @param getNumber A functional interface reference that provides numbers
     *                  associated with each EnemyTypes. Used for comparison logic.
     * @param enemy     The enemy object whose corresponding EnemyTypes needs to be identified.
     * @return The EnemyTypes that matches the provided enemy object based on name
     * and numerical data. Returns null if no match is found.
     */
    public static Optional<Map<EnemyTypes, Number>> getNumericEnemyData(GetNumber getNumber, Enemies enemy) {
        var allEnemyData = enemy.getAll(); // get all Object[] values from the enemy, health, name, etc...

        var matchingList = Arrays.stream(EnemyTypes.values()) // this checks if the name from enemy exists within EnemyTypes enum
                .filter(enemyTypes -> allEnemyData.stream()
                        .anyMatch(x -> x.toString().contains(enemyTypes.namedEnemy))) // any enemy whose name is the same any in the enum
                .collect(
                        ArrayList::new,
                        List::add,
                        List::addAll);

        var anyEnemyTypeNumber = getNumber.getNumbers((EnemyTypes) matchingList.getFirst());

        if (allEnemyData.contains(anyEnemyTypeNumber)) {// if the number specified, like maxHealth, exists within this enemy, return the enemy.
            return Optional.of(Map.of((EnemyTypes) matchingList.getFirst(), anyEnemyTypeNumber));
        }

        return Optional.empty();
    }

    //      id say the commented code is better;
//        for(var enemyType: EnemyTypes.values()){
//            var anyEnemyTypeNumber = getNumber.getNumbers(enemyType);// looks for Number[]; gets all possible numbers available in enemyType
//            if(allEnemyData.contains((anyEnemyTypeNumber.toString()))) { // if the created enemy in main contains the number name declared by our lambda
//                if(enemyName.equals(enemyType.namedEnemy)) return enemyType; // if the name of the match, corresponds to that of the current enemy object return its type.
//            }
//        }

    public Object[] getData() { //TODO: remake this
        return new Object[]{namedEnemy, maxHealth, maxEnergy, minDamage};
    }

    public static EnemyTypes randomizeEncounter() throws RuntimeException {
        var randomVariable = randomize(0, EnemyTypes.values().length); // in this specific scenario, the inclusive triggers an IndexOutOfBoundsException! which is great - makes the default enemy more common (which is what I want)
        for (var enemy : EnemyTypes.values()) {
            if (randomVariable == enemy.position) {
                return enemy;
            }
        }
        throw new RuntimeException();
    }
}

