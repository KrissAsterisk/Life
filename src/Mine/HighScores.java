package Mine;


import javax.print.DocFlavor;
import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.Map.Entry;


import static Mine.Colours.AnsiCodes.*;
import static java.lang.System.*;


class HighScores {

    private final String name;
    private final int finalMoves;
    private final Scanner reader;


    public HighScores(String name, int finalMoves, Scanner reader) {
        this.name = name;
        this.finalMoves = finalMoves;
        this.reader = reader;
        writeHighScoreToFile();
    }

    private void writeHighScoreToFile() {
        File highS;
        FileWriter scores;
        highS = new File(Constants.highScoresFilePath);
        highS.setWritable(true);

        try {
            if (!highS.exists()) {
                if (!highS.createNewFile()) {
                    out.println("Failed to create file!");
                }
            }
            ;
            scores = new FileWriter(highS, true);
            scores.write(name + "'s highest number of moves achieved: " + finalMoves + "\n");
            scores.close();
            out.println("Wrote your high score into a local file!");

        } catch (IOException _) {
        }

        readHighScoresFromFile(highS);

    }


    private void readHighScoresFromFile(File highS) {
        Map<String, Integer> uniqueScores = new LinkedHashMap<>();
        FileReader fileReader = null;
        List<String> extractedData = new ArrayList<>();
        try {

            fileReader = new FileReader(highS);
            try {
                extractedData = fileReader.readAllLines();
            } catch (IOException _) {
            }
        } catch (FileNotFoundException e) {

            out.println("File not found!");
        }
        out.println(extractedData);
        int[] playerScores = extractedData.stream().distinct().mapToInt(x -> (Integer.parseInt(x.replaceAll("\\D+", "")))).toArray(); // this is how real men code
        out.println(Arrays.toString(playerScores));


        Set<String> sorted = new LinkedHashSet<>(extractedData); // make sure names are unique - Case Sensitive on purpose!

        Object[] playerNames = sorted.stream().map(x -> x.replaceAll("'s highest number of moves achieved: ", "") // replace flavour text
                .replaceAll("\\d+", "")).toArray(); // replace all digits
        out.println(Arrays.toString(playerNames));

        // STEP 1: get name and score. set it.
        // read from file again
        // get new name and score and if the name is the same, check the score, replace only if its bigger than the one already set
        // keep going until new name
        // then if new name, repeat step 1


        // NOTE:
        // All numbers - order is crucial
        // every number taken out of the list
        // is directly related to the name its attached to
        // e.g., 1st number = 1st name

        // playerNames[0] & playerScores[0] is the correct players score
        // need to iterate through whole names array to find the player Dupes and get their bigges move, then add it to the map

        List<String> listName = new ArrayList<>();
        for (Object name : playerNames) {
            listName.add(name.toString());
            uniqueScores.put(name.toString(), null);
        }
        List<Integer> listNum = new ArrayList<>();
        for (int score : playerScores) {
            listNum.add(score);
        }

        StringBuilder test = new StringBuilder();
        for(int i = 0; i < playerNames.length; i++){

            test.append(listName.get(i)).append(" ").append(listNum.get(i)).append("-");
        }

        String[] clipped = test.toString().split("-"); // now we have array with String + Nr together Ast 16
        // we can do smth with this mb god save me
        out.println(clipped[0]);

        //out.println(uniqueScores);
    }

    private void writeHighScoresToFile(File highS) {

    } // make it so it reads from github or smth to get the up-to-date values of the actual HS list

    void retryGame() { // move this somewhere else
        out.println("Would you like to try again?");
        String ch = NormalizeStrings.normalize(reader);
        if (ch.contains("y")) {
            Life.main();
        } else if (ch.contains("n")) {
            out.println("See ya!");
            reader.close();
            exit(0);
        } else {
            ANSI_RED.printCode();
            out.println("Please type in only Yes or No");
            Colours.clear();
            retryGame();
        }
    }
}