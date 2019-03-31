package Project.Game;

import Project.Details;
import java.util.Random;
import Project.Print;

public class Adventure implements Details,Print {
    private String name;
    private int difficulty;
    private int strReq;
    private int agiReq;
    private int staminaCost;
    private int moneyLower,moneyUpper,money;
    private Random rand = new Random();



    public String getDetails()
    {
        String details = name + ", Difficulty:" + difficulty + ", Recommended Strength:" +strReq + ", Recommended Agility:" + agiReq + ", Stamina Cost:" + staminaCost + ", Reward:" + moneyLower + "-"+ moneyUpper;
        return details;

    }

    public void GoOnAdventure( Adventurer john)
    {
        NewScreen();
        System.out.println("WE'RE GOING ON AN ADVENTURE " + john.getDetails());
    }

    Adventure(int diff, int strength, int agility,int stamina, int lower,int upper)
    {
        name = "Adventure name";
        difficulty = diff;
        strReq = strength;
        agiReq = agility;
        staminaCost = stamina;
        moneyLower = lower;
        moneyUpper = upper;
        money = rand.nextInt(upper - lower) + lower;
    }

}
