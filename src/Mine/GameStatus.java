package Mine;

import Acts.*;

import Acts.GeneralActions.Status;
import Acts.PlayerActions.*;

import entity.types.Players.Players;
import Shareables.*;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static Shareables.Colours.AnsiCodes.*;
import static Mine.Constants.*;
import static Shareables.NormalizeStrings.normalize;
import static Shareables.EntityState.*;
import static java.lang.System.*;

interface GameStatus extends GameplayLogic { // this interface is WAY too important - needs to be split up


    static int startGame(Scanner reader, Players player) {
        Quit quit = new Quit();
        Fight fight = new Fight();
        int finalMoves = 0, movesLeft = STARTING_MOVES;
        for (int totalMoves = 1; totalMoves <= movesLeft; totalMoves++) {
            player.levelUpCheck(player);
            var choice = normalize(reader);
            if (movesLeft - totalMoves == 5) {
                UserInterface.lowMovesWarning(movesLeft, totalMoves);
            }
            switch (PossibleMoves.checkInput(choice)) {
                case FIGHT -> // dont get a move for retreating
                        movesLeft += GameplayLogic.fightLogic(fight, player, reader);// the lambdas don't really do anything right now; I have no idea how to actually make them useful YET

                case SLEEP -> new Sleep(player).execute();
                case DRINK -> new Drink(player).execute();
                case EAT -> new Eat(player).execute();
                case CONDITION -> {
                    new Status(player).execute();
                    totalMoves--;
                    movesLeft--; // don't count towards the final score but still uses a move
                }
                case QUIT -> {
                    quit.execute(player, reader);
                    totalMoves--; // dont count towards total moves
                }
                case null -> {
                    out.println("Choose a number between 0 and 7. You lose a move every time you mistype.");
                    totalMoves--;
                    movesLeft--; // don't count for the final score.
                }
            }
            if (player.state() == DEAD || player.state() == EXIT_GAME) {
                //TODO: run the "status check" on a separate thread when main game loop is running
                break;
            }
            if (movesLeft - totalMoves != 0)
                UserInterface.showChoices(movesLeft, totalMoves); // show choices after every move except the last one
            finalMoves = totalMoves; // set it after every move for the quit
        }
        finalMoves++;       // count the last move before death
        return finalMoves;
    }

    static int endGame(Players player, int totalMoves) {
        if (player.state() != ALIVE & player.state() != EXIT_GAME & player.state() != RESET & player.state() != COWARD) { //mb make it so that if reset player = alive?
            ANSI_HIGH_INTENSITY.printCode();
            ANSI_RED.printCode();
            out.println("You died! Game over!");
            Colours.clear();
        }
        out.println("In total you've had " + totalMoves + " moves!");
        return totalMoves;
    }

    static void retryGame(Scanner reader) { // move this somewhere else
        out.println("Would you like to try again?");
        String ch = NormalizeStrings.normalize(reader);
        if (ch.contains("y")) {
            try {
                restartApplication();
            } catch (IOException e) {
                out.println("Runtime was not found, you'll have to reboot the game manually!");
                System.exit(0);
            }
        } else if (ch.contains("n")) {
            out.println("See ya!");
            reader.close();
            exit(0);
        } else {
            ANSI_RED.printCode();
            out.println("Please type in only Yes or No");
            Colours.clear();
            retryGame(reader);
        }
    }

    /**
     * Courtesy of Leo Lewis <a href="https://lewisleo.blogspot.com/2012/08/programmatically-restart-java.html">How to programmatically restart a Java application</a>
     */
    private static void restartApplication() throws IOException {
        String javaExe = System.getProperty("java.home") + "\\bin\\java.exe";

        List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        List<String> childArgs = new java.util.ArrayList<>();

        for (String arg : vmArguments) {
            if (!arg.contains("-agentlib")) {
                childArgs.add(arg);
            }
        }

        String[] mainCommand = System.getProperty(SUN_JAVA_COMMAND).split(" ");
        if (!mainCommand[0].endsWith(".jar")) {
            out.println(ANSI_RED + "ERROR: "+ ANSI_RESET + "Please specify the main jar file. You're probably running this inside the IDE.\nTry again by running the jar.");
            System.exit(0);
        }

        childArgs.add("-jar");
        childArgs.add(new File(mainCommand[0]).getPath());

        childArgs.addAll(Arrays.asList(mainCommand).subList(1, mainCommand.length));

        out.println("Restarting with: [" + javaExe + ", " + String.join(" ", childArgs) + "]");

        // On Windows, open a NEW console window so PowerShell doesn't steal stdin.
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            List<String> cmd = new ArrayList<>();
            cmd.add("cmd.exe");
            cmd.add("/c");
            cmd.add("start");
            cmd.add("\"\""); // empty window title (IMPORTANT), otherwise its treated as an executable
            cmd.add(javaExe);
            cmd.addAll(childArgs);
            new ProcessBuilder(cmd).start();
        } else {
            List<String> cmd = new ArrayList<>();
            cmd.add(javaExe);
            cmd.addAll(childArgs);
            new ProcessBuilder(cmd).inheritIO().start();
        }

        System.exit(0);
    }
}
