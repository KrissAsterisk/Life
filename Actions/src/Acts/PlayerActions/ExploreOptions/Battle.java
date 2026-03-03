package Acts.PlayerActions.ExploreOptions;

import Acts.Actions;
import Acts.Consumption.EnergyUsage;
import Acts.GeneralActions.Attack;
import Shareables.Colours;
import entity.types.Enemies.Enemies;
import entity.types.Players.Players;

import java.util.Scanner;

import static Acts.Constants.*;
import static Acts.PossibleFightMoves.checkInput;
import static Shareables.Colours.AnsiCodes.ANSI_RED;
import static Shareables.Colours.AnsiCodes.ANSI_RESET;
import static Shareables.EntityState.*;
import static Shareables.EntityState.FIGHT_WIN;
import static Shareables.NormalizeStrings.normalize;
import static Shareables.RandomGenerator.*;
import static entity.types.Enemies.EnemyTypes.getEnemyRarity;
import static entity.types.Enemies.EnemyTypes.randomizeEncounter;
import static java.lang.System.out;

public final class Battle {
    private Battle(){}
    public static void battle(Players player, Scanner reader, Actions act) {
        var initiateBattle = new Attack();
        var playerEnergy = new EnergyUsage();
        var enemyEnergy = new EnergyUsage();
        Enemies enemy;
        try {
            enemy = new Enemies(randomizeEncounter());
        } catch (RuntimeException e) {
            out.println(ANSI_RED + "Failed generating enemy, using default." + ANSI_RESET);
            enemy = new Enemies(getEnemyRarity(enemyTypes -> enemyTypes.isDefault).getFirst());
        }
        debug(enemy);
        enemy.printEnemyName();
        LOOP:
        do {
            enemy.printStatus();
            out.println("What is your next move?");
            out.println("1 - Attack\t2 - Dodge\t3 - Flee");
            switch (checkInput(normalize(reader))) {
                case ATTACK -> {
                    if (playerEnergy.useEnergy(player).deathCheck() == DEAD)
                        break LOOP; // do energy check before explore
                    if (!getXinY(1, 50)) { // if 1 in 50, enemy dodges - TODO: higher rarity = more dodge chance
                        if (initiateBattle.execute(player, enemy).deathCheck() == DEAD)
                            break LOOP; // if enemy dies, get out
                    } else out.println("You... missed.");
                }
                case DODGE -> {
                    if ((getXinY(player.level(), 10))) { // the higher the players level the higher the chance to dodge
                        out.println("You gracefully dodge and the " + enemy.getName() + " hits itself!");
                        player.setState(DODGING);
                        if (initiateBattle.execute(enemy, enemy).deathCheck() == DEAD)
                            break LOOP; // doesnt use energy
                    } else {
                        out.println("Uh Oh....");
                        player.setState(VULNERABLE);
                    }
                }
                case FLEE -> { // TODO: make this cooler?
                    enemy.setState(DEAD);
                    player.setState(COWARD);
                    out.println(ANSI_RED + "You coward.");
                    Colours.clear();
                    break LOOP;
                }
                case null, default -> {
                    System.out.println("You trip on a rock!");
                    player.updateHealth(-5);
                    player.deathCheck();
                }
            }
            if (player.state() != DODGING && enemy.canAct()) {
                if (!(getXinY(player.level(), 20))) { // 1 in 20 to dodge for player
                    if (player.state() == VULNERABLE) {
                        if (initiateBattle.execute(enemy, VULNERABILITY_DEBUFF, player).deathCheck() == DEAD)
                            break LOOP; // explore then check for death
                    } else {
                        initiateBattle.execute(enemy, player);
                    }
                    act.execute();
                } else out.println("You managed to dodge in the nick of time!");
                enemyEnergy.useEnergy(enemy).deathCheck();
            }

        } while (player.state() != DEAD & enemy.state() != DEAD);

        if (player.state() != DEAD && player.state() != COWARD) { // win the fight if still alive
            player.setState(FIGHT_WIN);
            out.println("You've defeated the " + enemy.getName() + "!\nYou live for now...");
            out.printf("You've gained " + player.updateXP((float) randomize(rand.nextFloat(10), MIN_XP_GAIN)) + " XP!\n");
        }
    }
}
