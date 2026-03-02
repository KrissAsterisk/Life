package Mine;

import Acts.PlayerActions.*;
import Shareables.Colours;
import entity.types.Players.Players;

import java.util.Scanner;

import static Mine.Constants.STARTING_MOVES;
import static Shareables.Colours.AnsiCodes.ANSI_HIGH_INTENSITY;
import static Shareables.Colours.AnsiCodes.ANSI_RED;
import static Shareables.EntityState.*;
import static Shareables.NormalizeStrings.normalize;
import static java.lang.System.out;

/**
 * The GameEngine class is responsible for managing the main game loop and handling player actions,
 * their consequences, and overall game flow.
 * <p>
 * This class provides methods to start and stop the game. It interacts with the player entity and other
 * components such as battle management, UI, and various game-specific actions. The game loop processes
 * player inputs, handles the possible actions based on their choice, checks player states, and updates
 * player progress accordingly.
 */
public final class GameEngine {
    private GameEngine(){}
    public static int start(Scanner reader, Players player) {
        var sleep = new Sleep(player);  // id rather have these methods defined in survivor, so i can call player.eat(); instead of eat player
        var drink = new Drink(player);
        var eat = new Eat(player);
        var quit = new Quit();
        int finalMoves = 0, movesLeft = STARTING_MOVES;
        for (int totalMoves = 1; totalMoves <= movesLeft; totalMoves++) {
            player.levelUpCheck(player);
            var choice = normalize(reader);
            if (movesLeft - totalMoves == 5) {
                UserInterface.lowMovesWarning();
            }
            switch (PossibleMoves.checkInput(choice)) {
                case FIGHT ->
                        movesLeft += BattleManager.startFight(player, reader);

                case SLEEP -> sleep.execute();
                case DRINK -> drink.execute();
                case EAT -> eat.execute();
                case CONDITION -> {
                    player.printStatus();
                    totalMoves--;
                    movesLeft--;
                }
                case QUIT -> {
                    quit.execute(player, reader);
                    totalMoves--;
                }
                case null -> {
                    out.println("Choose a number between 0 and 7. You lose a move every time you mistype.");
                    totalMoves--;
                    movesLeft--;
                }
            }
            if (player.state() == DEAD || player.state() == EXIT_GAME) {
                break;
            }
            if (movesLeft - totalMoves != 0)
                UserInterface.showChoices(movesLeft, totalMoves);
            finalMoves = totalMoves;
        }
        finalMoves++;
        return finalMoves;
    }

    public static int stop(Players player, int totalMoves) {
        if (player.state() != ALIVE && player.state() != RESET && player.state() != COWARD && player.state() != EXIT_GAME) {
            ANSI_HIGH_INTENSITY.printCode();
            ANSI_RED.printCode();
            out.println("You died! Game over!");
            Colours.clear();
        }
        out.println("In total you've had " + totalMoves + " moves!");
        return totalMoves;
    }
}
