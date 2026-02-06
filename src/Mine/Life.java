package Mine;

import Acts.*;

import java.time.*;
import java.time.temporal.ChronoUnit;

import java.util.*;

import static Acts.Actions.playerState;
import static Mine.Colours.AnsiCodes.*;
import static Mine.PossibleMoves.*;

import static java.lang.System.*;


public class Life implements UserInterface, Constants {

    static int startGame(Scanner reader, MADD madd) {
        int finalMoves = 0, movesLeft = STARTING_MOVES;
        for (int totalMoves = 1; totalMoves <= movesLeft; totalMoves++) {
                var choice = reader.nextLine().replaceAll(" ", "").toLowerCase();

                switch (PossibleMoves.checkInput(choice)) {
                    case FIGHT -> {
                        new Fight();
                        if (playerState == PlayerState.FIGHT_WIN) {
                            movesLeft += PlayerState.moveLogic();
                            playerState = PlayerState.ALIVE;
                        }
                    }
                    case SLEEP -> new Sleep();
                    case DRINK -> new Drink();
                    case EAT -> new Eat();
                    case CONDITION -> { new Status();
                        totalMoves--;
                        movesLeft--; // don't count towards the final score but still uses a move
                    }
                    case QUIT -> { new Quit(reader); // needs to be in catch block
                        totalMoves--; // dont count towards total moves
                    }
                    case null -> {
                        out.println("Choose a number between 0 and 7. You lose a move every time you mistype.");
                        totalMoves--;
                        movesLeft--; // don't count for final score.
                    }
                }
                if (playerState == PlayerState.DEAD || playerState == PlayerState.EXIT_GAME) {
                    //TODO: run the "status check" on a separate thread when main game loop is running
                    break;
                }
                UserInterface.showChoices(movesLeft, totalMoves);
                if (movesLeft - totalMoves == 5) {
                    UserInterface.lowMovesWarning(madd, movesLeft, totalMoves);
                }
                finalMoves = totalMoves; // set it after every move for quit
        }
        finalMoves++;       // count the last move before death
        return finalMoves;
    }

    static void endGame(int totalMoves) {
        if (playerState != PlayerState.EXIT_GAME) {
            ANSI_HIGH_INTENSITY.printCode();
            ANSI_RED.printCode();
            out.println("You died! Game over!");
            Colours.clear();
        }
        out.println("In total you've had " + totalMoves + " moves!");
    }

    public static void main() throws NoSuchElementException {
        Colours.clear(); // initialize enum
        MADD madd = new MADD();
        var reader = new Scanner(in);
        var player1 = Player.initPlayer(reader);
        new Actions(player1.name(), player1.foodP(), player1.waterP(), player1.energyP(), player1.healthP(), player1.currentState());
        UserInterface.showChoices(STARTING_MOVES, 0);
        var gameStartTime = Instant.now();
        endGame(startGame(reader, madd));
        var gameOverTime = Instant.now();
        out.println("You lasted: " + ANSI_HIGH_INTENSITY.colourCode() + (ChronoUnit.MINUTES.between(gameStartTime, gameOverTime)) + " minutes and " + (ChronoUnit.SECONDS.between(gameStartTime, gameOverTime)) + " seconds.");
        //HighScores highScores = new HighScores();
        Colours.clear();
        //highScores.filePrintHS(player1.name(), temp); TODO:fix the sorting
        reader.close();
        exit(0);
    }
}

