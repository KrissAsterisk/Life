package Mine;

import Shareables.Colours;
import Shareables.NormalizeStrings;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static Shareables.Colours.AnsiCodes.*;
import static Mine.Constants.*;
import static java.lang.System.out;

/**
 * Represents a game session where users can choose to retry or exit the game.
 * Provides functionality to handle user input for retry prompts and restarting the application.
 */
public class GameSession {

    public static void retryGame(Scanner reader) {
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
            System.exit(0);
        } else {
            ANSI_RED.printCode();
            out.println("Please type in only Yes or No");
            Colours.clear();
            retryGame(reader);
        }
    }

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
            out.println(ANSI_RED + "ERROR: " + ANSI_RESET + "Please specify the main jar file. You're probably running this inside the IDE.\nTry again by running the jar.");
            System.exit(0);
        }

        childArgs.add("-jar");
        childArgs.add(new File(mainCommand[0]).getPath());

        childArgs.addAll(Arrays.asList(mainCommand).subList(1, mainCommand.length));

        out.println("Restarting with: [" + javaExe + ", " + String.join(" ", childArgs) + "]");

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            List<String> cmd = new ArrayList<>();
            cmd.add("cmd.exe");
            cmd.add("/c");
            cmd.add("start");
            cmd.add("\"\"");
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
