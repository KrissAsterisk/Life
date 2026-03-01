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


import static Mine.Constants.STARTING_MOVES;
import static Shareables.Colours.AnsiCodes.*;
import static java.lang.System.*;


public final class Life {
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
        var totalMoves = GameEngine.stop(player,
                GameEngine.start(reader, player)
        ); // start the game
        var gameOverTime = Instant.now();
        out.println("You lasted: " + ANSI_HIGH_INTENSITY + (ChronoUnit.MINUTES.between(gameStartTime, gameOverTime)) + " minutes and " + (ChronoUnit.SECONDS.between(gameStartTime, gameOverTime)) + " seconds.");
        Colours.clear();
        new HighScores(player.getName(), totalMoves);
        GameSession.retryGame(reader);

    }
}
