package Mine;

import entity.types.Players.PlayerTemplate;
import entity.types.Players.Players;
import Shareables.Colours;
import Shareables.RandomGenerator;
import HandlingScores.HighScores;



import java.time.Instant;
import java.time.temporal.ChronoUnit;



import java.util.Locale;
import java.util.Scanner;



import static Shareables.Colours.AnsiCodes.*;
import static java.lang.System.*;


public final class Life implements UserInterface, Mine.Constants {

    //TODO LIST:
    //Refactor Status first
    //Replace getClass() branching with presenter/polymorphism. This is localized and improves design immediately.
    //Split interfaces (Entities → smaller ones)
    //Update method signatures in actions to accept what they actually need (e.g., Combatant).
    //Remove side effects from constructors
    //Make actions explicit execute methods.
    //Add GameContext & GameIO
    //Gradually thread it through, starting at entry points.
    //Fix enemy identity & randomness
    //Add type field and make stat generation explicit.

    public static void main() {
        Colours.clear(); // initialize enum
        out.print(ANSI_CLEAR);
        out.flush();
        System.out.println(Locale.getDefault());
        var reader = new Scanner(in);
        var player = new Players(PlayerTemplate.initPlayer(reader));
        out.println("Every execute costs a move. Use them wisely!");
        UserInterface.showChoices(STARTING_MOVES, 0);
        var gameStartTime = Instant.now();
        out.println("Current seed: " + RandomGenerator.RANDOM_SEED);
        var totalMoves = GameStatus.endGame(player,
                GameStatus.startGame(reader, player)
        ); // start the game
        var gameOverTime = Instant.now();
        out.println("You lasted: " + ANSI_HIGH_INTENSITY + (ChronoUnit.MINUTES.between(gameStartTime, gameOverTime)) + " minutes and " + (ChronoUnit.SECONDS.between(gameStartTime, gameOverTime)) + " seconds.");
        Colours.clear();
        new HighScores(player.getName(), totalMoves);
        GameStatus.retryGame(reader);

    }
}
