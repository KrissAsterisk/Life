package Mine;

import entity.types.Players.PlayerTemplate;
import entity.types.Players.Players;
import Shareables.Colours;
import Shareables.RandomGenerator;


import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


import java.util.Locale;
import java.util.Scanner;


import static Shareables.Colours.AnsiCodes.*;
import static java.lang.System.*;


public final class Life implements UserInterface, Mine.Constants{
    public static void main() throws IOException {


        Locale locale = Locale.getDefault();
        System.out.println(locale);
        Colours.clear(); // initialize enum
        var reader = new Scanner(in);
        PlayerTemplate firstPlayer = PlayerTemplate.initPlayer(reader);
        var player = new Players(
                firstPlayer.name(),
                firstPlayer.currentState(),
                firstPlayer.foodP(),
                firstPlayer.waterP(),
                firstPlayer.energyP(),
                firstPlayer.healthP(),
                firstPlayer.xp()
        );
        out.println("Every action costs a move. Use them wisely!");
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
