package entity.types;

import entity.types.Enemies.EnemyTypes;

@FunctionalInterface
public interface GetNumber {
     Number getNumbers(EnemyTypes type);
}
