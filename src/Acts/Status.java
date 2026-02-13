package Acts;

import Ents.Enemies;
import Ents.Entities;
import Ents.Players;
import Mine.Colours;


import java.util.List;
import java.util.function.Consumer;

import static Mine.Colours.AnsiCodes.*;
import static Mine.Constants.*;
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
