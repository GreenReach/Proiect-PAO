package Project.Game;

import Project.Details;
import java.util.Random;
import Project.Print;

public class Adventurer implements Details,Print {

    private String name;
    protected String Class;
    protected int level;
    protected int strength;
    protected int agility;
    protected int stamina;
    protected Random rand = new Random();

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getStrength() {
        return strength;
    }

    public int getAgility() {
        return agility;
    }

    public int getStamina() {
        return stamina;
    }

    public String getDetails()
    {
        String details =name +", Class:" + Class + ", Level:" + level + ", Strength:" + strength + ", Agility:" + agility + ", Stamina:" + stamina;
        return details;
    }

    void LevelUp()
    {
        level++;

        boolean choice= rand.nextBoolean();
        if(choice)
            strength++;
        else
            agility++;

        double s = level * 0.25 +10;
        stamina = (int) s;
    }

    public Adventurer(int levelCap, int statsCap)
    {

        name = "John";
        Class = "Adventurer";
        level = rand.nextInt(levelCap);
        strength = rand.nextInt(statsCap);
        agility = rand.nextInt(statsCap);
        double s = level * 0.25 +10;
        stamina = (int) s;

    }

    public Adventurer()
    {
       System.out.println("I think something went wrong, default constructor in Adventurer called");
    }
}
