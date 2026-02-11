package Mine;

import Ents.Players;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.Scanner;

import static Mine.Colours.AnsiCodes.*;
import static Ents.EntityState.*;
import static java.lang.System.*;


public final class Life implements UserInterface, Constants, GameStatus {
    public static void main() {
        Colours.clear(); // initialize enum
        var reader = new Scanner(in);
        var player = new Players(Player.initPlayer(reader).name(), ALIVE, DEFAULT_FOOD_POINTS, DEFAULT_WATER_POINTS, DEFAULT_ENERGY_POINTS, DEFAULT_HEALTH_POINTS);
        UserInterface.showChoices(STARTING_MOVES, 0);
        var gameStartTime = Instant.now();
        GameStatus.endGame(player, GameStatus.startGame(reader, player)); // start the game
        var gameOverTime = Instant.now();
        out.println("You lasted: " + ANSI_HIGH_INTENSITY.colourCode() + (ChronoUnit.MINUTES.between(gameStartTime, gameOverTime)) + " minutes and " + (ChronoUnit.SECONDS.between(gameStartTime, gameOverTime)) + " seconds.");
        //HighScores highScores = new HighScores();
        Colours.clear();
        //highScores.filePrintHS(player.getName(), temp); TODO:fix the sorting
        reader.close();
        exit(0);
    }
}

