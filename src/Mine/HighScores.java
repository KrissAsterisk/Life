package Mine;


import java.io.*;
import java.util.*;

import static Mine.Colours.AnsiCodes.*;
import static java.lang.System.*;


class HighScores {

    private final String name;
    private final int finalMoves;


    public HighScores(String name, int finalMoves) {
        this.name = name;
        this.finalMoves = finalMoves;
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
        ArrayList<Integer> playerScores = extractedData.stream()
                //.peek(out::println)
                .distinct()
                .mapToInt(x -> (Integer.parseInt(x.replaceAll("\\D+", ""))))
                .collect(
                        ArrayList::new,
                        ArrayList::add,
                        ArrayList::addAll
                ); // THIS is how REAL PEOPLE code


        Set<String> sorted = new LinkedHashSet<>(extractedData); // make sure names are unique - Case Sensitive on purpose!

        ArrayList<String> playerNames = sorted.stream()
                .map(x -> x.replaceAll("'s highest number of moves achieved: ", "") // replace flavour text
                        .replaceAll("\\d+", "")
                )
                .collect( // instead of using stinky array turn into a nice Collection
                        ArrayList::new,
                        ArrayList::add,
                        ArrayList::addAll
                ); // replace all digits


        for (int i = 0; i < playerNames.size(); i++) { // i have no idea why my small brain thought i needed two nested for loops...
            String name = playerNames.get(i).trim();
            int moves = playerScores.get(i);
            uniqueScores.merge(name, moves, Math::max); // Math max!! - solves my dupe + higher values issues
        }


        uniqueScores.values().parallelStream()
                .mapToInt(x -> x)
                .average() // starting Optional chain
                .ifPresent(x -> out.println("Average Moves per player: " + ANSI_HIGH_INTENSITY + ANSI_CYAN + x + ANSI_RESET)
                );
        //future proofing for when we instantiate high scores before the game is played

        out.println(ANSI_YELLOW + "Sorting..." + ANSI_RESET);

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(uniqueScores.entrySet()); // put into a list using Map.Entry

        entries.sort(Map.Entry.<String, Integer>comparingByValue().reversed()); // to reverse, you need to specify T

        out.println(entries); // big nr first

        out.println("Highest scoring legend: " + ANSI_HIGH_INTENSITY + ANSI_RED + entries.getFirst().getKey() + ANSI_RESET + "!");

        Colours.clear();

        writeHighScoresToFile(highS, entries);
    }

    private void writeHighScoresToFile(File highS, List<?> entries) {
        String getStringBack = entries.toString();
        getStringBack = getStringBack.replaceAll("=", "'s highest number of moves achieved: ").replace("[", "").replace("]", "");
        out.println(getStringBack);
        ArrayList<String> cool = new ArrayList<>(List.of(getStringBack.split(", ")));
    } // make it so it reads from github or smth to get the up-to-date values of the actual HS list

}