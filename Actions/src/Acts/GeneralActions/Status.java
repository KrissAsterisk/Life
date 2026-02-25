package Acts.GeneralActions;

import entity.types.Entities;
import entity.types.Players.Players;
import entity.types.Enemies.Enemies;

import Shareables.Colours;


import static Acts.Constants.*;
import static Shareables.Colours.AnsiCodes.*;
import static java.lang.System.out;

public final class Status {

    public Status(Entities entities) {
        action(entities);

    }

    public void action(Entities entity) {
        ANSI_HIGH_INTENSITY.printCode();
        out.println(entity.getName() + "'s current state:");
        if(entity.getClass() == Enemies.class){
            printEnemyStatus.accept(entity);
        }
        if(entity.getClass() == Players.class){
            printPlayerStatus.accept(entity);
        }
        Colours.clear();
    }

}
