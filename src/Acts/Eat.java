package Acts;

import java.util.Scanner;

public final class Eat extends Actions {

    public Eat(){
        healthP += (Math.random() * 8.6) + 3.1;
        energyP -= (Math.random() * 8.3) + 7.8;
        waterP += (Math.random() * 5.3) + 1;
        foodP += (Math.random() * 15.5) + 25.5;
        super();
    }
}
