package Acts;

public enum PossibleFightMoves {
    ATTACK(1, "attack"),
    DODGE(2, "dodge"),
    FLEE(3, "flee");

    private final int intValue;
    private final String stringValue;

    PossibleFightMoves(int intValue, String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    public static PossibleFightMoves checkInput(String input) {

        if (input.matches("\\d+")) {
            int n = Integer.parseInt(input);
            for (PossibleFightMoves moves : values()) {
                if (moves.intValue == n) return moves;
            }
            return null;
        }

        for (PossibleFightMoves moves : values()) {
            if (moves.stringValue.equalsIgnoreCase(input)) {
                return moves;
            }
        }

        return null;
    }


}
