package Mine;

import Acts.RandomGenerator;
import Ents.Players;


import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.Scanner;

import static Mine.Colours.AnsiCodes.*;
import static java.lang.System.*;
import static Mine.GameStatus.*;


public final class Life implements UserInterface, Constants, GameStatus {


    public static void main(){

        Colours.clear(); // initialize enum
        var reader = new Scanner(in);
        Player firstPlayer = Player.initPlayer(reader);
        var player = new Players(
                firstPlayer.name(),
                firstPlayer.currentState(),
                firstPlayer.foodP(),
                firstPlayer.waterP(),
                firstPlayer.energyP(),
                firstPlayer.healthP(),
                firstPlayer.xp()
        );
        UserInterface.showChoices(STARTING_MOVES, 0);
        var gameStartTime = Instant.now();
        out.println("Current seed: " + RandomGenerator.RANDOM_SEED);
        var totalMoves = endGame(player,
                startGame(reader, player)
        ); // start the game
        var gameOverTime = Instant.now();
        out.println("You lasted: " + ANSI_HIGH_INTENSITY + (ChronoUnit.MINUTES.between(gameStartTime, gameOverTime)) + " minutes and " + (ChronoUnit.SECONDS.between(gameStartTime, gameOverTime)) + " seconds.");
        Colours.clear();
        new HighScores(player.getName(), totalMoves); //TODO:fix the sorting
        retryGame(reader);
    }
}
