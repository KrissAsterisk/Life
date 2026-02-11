package Mine;

public interface NormalizeStrings {

    public static String normalizeString(String input){
        return input.trim().toLowerCase().replaceAll(" ", "");
    } //TODO maybe override toString()?
}
