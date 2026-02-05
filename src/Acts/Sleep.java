package Acts;

import java.util.Scanner;

public final class Sleep extends Actions {

    public int act(Scanner reader){

        energyP += (Math.random() * 26.4) + 15.2;
        healthP += (Math.random() * 23.21) + 8.51; // 0 -> 23.21 => at least 8.51 health points
        foodP -= (Math.random() * 21.3) + 30.5;
        waterP -= (Math.random() * 21.7) + 35.6;

        return super.act(reader);
    }
}
