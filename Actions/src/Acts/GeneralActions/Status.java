package Acts.GeneralActions;

import entity.types.Entities;
import entity.types.Enemies.Enemies;

import Shareables.Colours;
import entity.types.Players.Players;


import static Acts.Constants.*;
import static Shareables.Colours.AnsiCodes.*;
import static java.lang.System.out;

public final class Status {

    private final Entities entity;

    public Status(Entities entities) {
       this.entity = entities;

    }

    public void execute() {
        ANSI_HIGH_INTENSITY.printCode();
        out.println(entity.getName() + "'s current state:");
        if(entity.getClass() == Enemies.class){
            printEnemyStatus.accept((Enemies) entity);
        }
        if(entity.getClass() == Players.class){
            printPlayerStatus.accept((Players) entity);
        }
        Colours.clear();
    }

}
