package Mine;

public enum PossibleMoves {
    FIGHT(1, "fight"),
    SLEEP(2, "sleep"),
    DRINK (3, "drink"),
    EAT (4, "eat"),
    CONDITION (5, "condition"),
    QUIT (6, "quit");

    private final int intValue;
    private final String stringValue;

    PossibleMoves(int intValue, String stringValue){
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    public static PossibleMoves checkInput(String input){

        if(input.matches("\\d+")){
            int n = Integer.parseInt(input);
            for(PossibleMoves moves : values()){
                if(moves.intValue == n) return moves;
            }
            return null;
        }

        for(PossibleMoves moves: values()){
            if(moves.stringValue.equalsIgnoreCase(input)){
                return moves;
            }
        }

        return null;
    }


}
