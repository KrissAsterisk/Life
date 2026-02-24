package entity.types;

import entity.types.Enemies.EnemyTypes;

@FunctionalInterface
public interface CheckTrait {
    boolean checkTrait(EnemyTypes type);
}
