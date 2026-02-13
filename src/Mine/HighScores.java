package Mine;


import java.io.*;
import java.util.*;
import java.util.stream.IntStream;


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
        try {
            if (!highS.exists()) {
                if (!highS.createNewFile()) {
                    out.println("Failed to create file!");
                }
            }
            scores = new FileWriter(highS, true);
            scores.write(name + "'s highest number of moves achieved: " + finalMoves + "\n");
            scores.close();
            out.println("Wrote your high score into a local file!");

        } catch (IOException _) {
        }

        readHighScoresFromFile(highS);

    }

    private void readHighScoresFromFile(File highS) {
        Map<String, Integer> uniqueScores = new TreeMap<String, Integer>();
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
        TreeSet<String> sortedByKeyDupeData = new TreeSet<>(extractedData);
        out.println(sortedByKeyDupeData);
        Object[] sortedByKeyUniqueDataInArray = sortedByKeyDupeData.stream().distinct().toArray();
        out.println(Arrays.toString(sortedByKeyUniqueDataInArray));


         //out.println(Arrays.toString(Arrays.stream(sortedByKeyUniqueDataInArray).mapToInt(x->Integer.parseInt(x.toString().replaceAll("[^0-9] ", ""))).toArray()));
         out.println(Arrays.toString(Arrays.stream(sortedByKeyUniqueDataInArray).toArray()).replaceAll("[^0-9 ]",""));
         // split  into array
//        int i = 0;
//        for(int x : intArray){
//            out.println(i+ "\t" + x);
//            i++;
//        }


    }

    void retryGame() {
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