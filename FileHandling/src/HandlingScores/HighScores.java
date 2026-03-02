package HandlingScores;

import Shareables.Colours.AnsiCodes;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static HandlingScores.Constants.*;
import static java.lang.System.*;
import static Shareables.Colours.clear;


public class HighScores {

    private final String name;
    private final int finalMoves;
    private final File highScoresFile = new File(highScoresFilePath);
    private final File serializedObjFile = new File(serializedObjectsPath);
    private final Path scoresDirPath  = Path.of(Constants.scoresDirPath);

    public HighScores(String name, int finalMoves) {
        this.name = name;
        this.finalMoves = finalMoves;
    }

    public void writeHighScoreToFile() {
        FileWriter scores;
        assert highScoresFile.setWritable(true);

        try{
            Files.createDirectory(scoresDirPath);
        } catch (IOException e){
            if(!scoresDirPath.toFile().exists()) out.println("ERROR: Could not create directory for Highscores!");
        }

        try {
            if (!highScoresFile.exists()) {
                if (!highScoresFile.createNewFile()) {
                    out.println("Failed to create file!");
                }
            }

            scores = new FileWriter(highScoresFile, true);
            scores.write(name + "'s highest number of moves achieved: " + finalMoves + "\n");
            scores.close();
            out.println("Wrote your high score into a local file!");

        } catch (IOException unnamedVar) {
            out.println("Something went wrong during file writing.");
        }

        readHighScoresFromFile(highScoresFile);

    }


    private void readHighScoresFromFile(File highS) {
        Map<String, Integer> uniqueScores = new LinkedHashMap<>();
        List<String> extractedData = new ArrayList<>();
        try (var fileReader = new FileReader(highS)) {
            extractedData = fileReader.readAllLines();
        } catch (IOException e) {

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
                );


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
                .ifPresent(x -> out.println("Average Moves per player: " + AnsiCodes.ANSI_HIGH_INTENSITY + AnsiCodes.ANSI_CYAN + x + AnsiCodes.ANSI_RESET)
                );
        //future proofing for when we instantiate high scores before the game is played

        out.println(AnsiCodes.ANSI_YELLOW + "Sorting..." + AnsiCodes.ANSI_RESET);

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(uniqueScores.entrySet()); // put into a list using Map.Entry

        entries.sort(Map.Entry.<String, Integer>comparingByValue().reversed()); // to reverse, you need to specify T

        out.println(entries); // big nr first

        out.println("Highest scoring legend: " + AnsiCodes.ANSI_HIGH_INTENSITY + AnsiCodes.ANSI_RED + entries.getFirst().getKey() + AnsiCodes.ANSI_RESET + "!");

        clear();

        writeHighScoresToFile(highS, entries);
    }

    private void writeHighScoresToFile(File highS, List<?> entries) {
        String getStringBack = entries.toString();
        getStringBack = getStringBack.replaceAll("=", "'s highest number of moves achieved: ").replace("[", "").replace("]", "");
        ArrayList<String> cool = new ArrayList<>(List.of(getStringBack.split(", ")));
        try (var fileWriter = new FileWriter(sortedHighScoresPath)) {
            cool.forEach(x -> {
                try {
                    fileWriter.write(x + "\n");
                } catch (IOException e) {
                    e.getCause();
                    throw new RuntimeException(e);
                }
            });
            out.println("Wrote sorted scores to file!");
        } catch (IOException e) {
            e.getCause();
            throw new RuntimeException(e);
        } finally {
            assert highS.setReadOnly(); // this is really risky and also ignored so its ok
        }
    } // make it so it reads from github or smth to get the up-to-date values of the actual HS list


    public void saveObject(Object... v) throws IOException { // this is not how you serialize im just testing for now
        try(var out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(serializedObjFile)))){
            for(var obj : v) out.writeObject(obj);
        }
    }

    public List<Object> readObject() throws IOException, ClassNotFoundException {
        List<Object> test = new ArrayList<>();
        try(var in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(serializedObjFile)))){
            while(true){
                var object = in.readObject();
                test.add(object);
            }
        } catch(EOFException e){
            // end of file
        }
        return test;
    }
}
