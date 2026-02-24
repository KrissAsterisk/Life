package Acts;

import java.util.*;

public interface FightLossMessages {

    static String[] getStrings() {
        ArrayList<String> loss = new ArrayList<>(); //TODO API calls to ai for sentence generation or YAML file that stores all possible values
        loss.add(" unfortunately you were outnumbered and barely managed to get away . ");
        loss.add(" you tried to fight a dragon barehanded and got your teeth knocked out . ");
        loss.add(" you tripped on a rock on your way to the dungeon and broke your arm . ");
        loss.add(" you were hit by a car and lost your leg . ");
        loss.add(" maybe drinking suspicious potions from a stranger wasn't the best idea . ");
        loss.add(" you feel a slight breeze brush against you, you sense your liver missing . ");
        loss.add(" dealings with a demon have their consequences . ");
        loss.add(" you jumped in a lake full of leeches . ");
        loss.add(" you suck . ");
        loss.add(" you forgot that if you go to hell , you can't come back . ");
        loss.add(" ur dead LOL get rekt ");
        loss.add(" Sucks to suck ");
        loss.add(" Dead from blunt trauma to the face ");
        loss.add(" They will surely feast on your flesh ");
        loss.add(" Try playing on Drizzle mode for an easier time ");
        loss.add(" You die in a hilarious pose ");
        loss.add(" You're fired . ");
        loss.add(" A light flashes before your eyes ! Oh wait it's just your google glasses ... ");
        loss.add(" You trip on a VERY large rock . ");


        Set<String> words = new LinkedHashSet<>(); // unique words only

        for (String word : loss.toString().toLowerCase().trim().split("\\s+")) { // avoid dupes
            if (!word.isBlank()) words.add(word); // make sure were only adding words
        }

        return words.toArray(String[]::new);
    }
}

