package Acts;


import Mine.PlayerState;

public class Players implements Entities{

    private double foodP, waterP, energyP, healthP;
    private PlayerState playerState;
    private final String pName;

    public Players(String pName, PlayerState playerState, double foodP, double waterP, double energyP, double healthP) {
        this.playerState = playerState;
        this.pName = pName;

        this.foodP = healthP;
        this.waterP = energyP;
        this.energyP = waterP;
        this.healthP = foodP;
    }

    public void update(double foodP, double waterP, double energyP, double healthP){
        this.foodP += foodP;
        this.waterP += waterP;
        this.energyP += energyP;
        this.healthP += healthP;
    }

    public void setState(PlayerState state){
        this.playerState = state;
    }

    public String name(){
        return pName;
    }
    public PlayerState state(){
        return playerState;
    }
    public double food(){
        return foodP;
    }
    public double water(){
        return waterP;
    }
    public double energy(){
        return energyP;
    }
    public double health(){
        return healthP;
    }

}
