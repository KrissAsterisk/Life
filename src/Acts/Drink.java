package Acts;

import java.util.Scanner;

public final class Drink extends Actions {

    public int act(Scanner reader){
        healthP += (Math.random() * 3.9) + 2.1;
        energyP -= (Math.random() * 11.27) + 5.23;
        waterP += (Math.random() * 26.757) + 14.543;
        foodP += (Math.random() * 3.7) + 0.5;

        return super.act(reader);
    }
}
