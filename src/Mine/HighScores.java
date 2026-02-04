package Mine;


import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import static Mine.Life.main;
import static java.lang.System.*;


class HighScores {

    final static String filePath = "Highscores.txt";
    final static String outputFilePath = "Sorted.txt";

    void filePrintHS(Life name, int temp) throws IOException {

        File highS = new File("Highscores.txt");
        if (!highS.exists()) {
            //noinspection ResultOfMethodCallIgnored
            highS.createNewFile();
        }
        FileWriter scores = new FileWriter(highS, true);
        scores.write( name + "'s highest number of moves achieved: " + temp + "\n");
        scores.close();

        //-----------------------------------------

        // read text file to HashMap
        Map<String, String> mapFromFile = HashMapFromTextFile();

        // iterate over HashMap entries
        for (Entry<String, String> entry :
                mapFromFile.entrySet()) {
            out.println(entry.getKey() + "'s highest number of moves achieved: "
                    + entry.getValue());
        }
        File sorted = new File(outputFilePath);
        if (!sorted.exists()) {
            //noinspection ResultOfMethodCallIgnored
            sorted.createNewFile();
        }
        // -----------------------------------------------------

        Set<Entry<String, String>> entries = mapFromFile.entrySet();
        // sort HashMap by keys first
        // all you need to do is create a TreeMap with mappings of HashMap
        // TreeMap keeps all entries in sorted order
        TreeMap<String, String> sortedMap = new TreeMap<>(mapFromFile);
        Set<Entry<String, String>> mappings = sortedMap.entrySet();

        out.println("HashMap after sorting by keys in ascending order ");
        for(Entry<String, String> mapping : mappings){
            out.println(mapping.getKey() + " ==> " + mapping.getValue());
        }


        // sort the HashMap by values
        // there is no direct way to sort HashMap by values, but you
        // can do this by writing your own comparator, which takes
        // Map.Entry object and arrange them in order increasing
        // or decreasing by values.

        Comparator<Entry<String, String>> valueComparator
                = (e1, e2) -> {
                    String v1 = e1.getValue();
                    String v2 = e2.getValue();
                    return v2.compareTo(v1);
                };

        // Sort method needs a List, so let's first convert Set to List in Java
        List<Entry<String, String>> listOfEntries
                = new ArrayList<>(entries);

        // sorting HashMap by values using comparator
        listOfEntries.sort(valueComparator);

        LinkedHashMap<String, String> sortedByValue
                = new LinkedHashMap<>(listOfEntries.size());

        // copying entries from List to Map
        for(Entry<String, String> entry : listOfEntries){
            sortedByValue.put(entry.getKey(), entry.getValue());
        }

        out.println("HashMap after sorting entries by values ");
        Set<Entry<String, String>> entrySetSortedByValue = sortedByValue.entrySet();

        for(Entry<String, String> mapping : entrySetSortedByValue){
            out.println(mapping.getKey() + "'s highest number of moves achieved: " + mapping.getValue());
        }

       //fixed sort this shit already TODO only problem is that it overwrites existing values with lower ones.

        // ------------------------------------------------------


        try (BufferedWriter bf = new BufferedWriter(new FileWriter(sorted))) {

            // iterate map entries
            for (Entry<String, String> entry : sortedByValue.entrySet()) {
                // put key and value separated
                bf.write(entry.getKey() + "'s highest number of moves achieved: " + entry.getValue() + "\n"); //TODO if the value is smaller than the already in the sorted file, then it should be ignored

                // new line
                bf.newLine();
            }

            bf.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
    //-------------------------------------------
        out.println("Wanna try again? Y / N");

    retryGame();

}



    public static Map<String, String> HashMapFromTextFile() {

        Map<String, String> map = new HashMap<>(); //TODO why turn integers into strings???????????????????
        BufferedReader br;
        try {

            // create file object
            File file = new File(filePath);

            // create BufferedReader object from the File
            br = new BufferedReader(new FileReader(file));

            String line;

            // read file line by line
            while ((line = br.readLine()) != null) {

                // split the line by :
                String[] parts = line.split("'s highest number of moves achieved: ");

                // first part is name, second is number

                    String name = parts[0].trim();
                    String number = parts[1].trim();
                    //String nextNumber = parts[j+=3].trim();

                    // put name, number in HashMap if they are
                    // not empty
                    if (!name.isEmpty() && !number.isEmpty()) {
                        map.put(name, number);

                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }


    void retryGame() throws IOException {
        //MADD Colours = new MADD();

        Scanner checker = new Scanner(in);
        String ch = checker.next();
        if(ch.equalsIgnoreCase("y")){
            main(null);
        }
        else if(ch.equalsIgnoreCase("n")) {
            out.println("See ya!");
            exit(0);
        } else {
            Colours.AnsiCodes.ANSI_RED.printCode();
            out.println("Please type in only 1 character: Y or N");
            Colours.clear();
            retryGame();
        }
    }
}