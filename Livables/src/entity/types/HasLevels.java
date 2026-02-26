package entity.types;

import entity.types.Players.Players;

public sealed interface HasLevels extends Entities permits Players {

    byte level();
    float updateXP(float xp);
}
