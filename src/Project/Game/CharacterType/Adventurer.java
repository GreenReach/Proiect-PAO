package Project.Game.CharacterType;

import Project.Details;
import java.util.Random;

import Project.Game.Adventure;
import Project.Print;

public class Adventurer implements Details,Print,Comparable {


    private String name;
    protected String ClassName;
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

    public String getClassType(){return ClassName;}

    public String getDetails()
    {
        String details =name +", Class:" + ClassName + ", Level:" + level + ", Strength:" + strength + ", Agility:" + agility + ", Stamina:" + stamina;
        return details;
    }

    public void getTired(int x){stamina -= x;}

    /** Aventurierului ii sunt marite atributele*/
    public void LevelUp()
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

    /** Constructori
        cu atribute alese aleator */
    public Adventurer(int levelCap, int statsCap)
    {

        name = "John";
        ClassName = "Adventurer";
        level = rand.nextInt(levelCap);
        strength = rand.nextInt(statsCap);
        agility = rand.nextInt(statsCap);
        double s = level * 0.25 +10;
        stamina = (int) s;

    }

    /** cu atribute primite */
    public Adventurer(String cls,String n, int lvl, int str, int agi, int sta)
    {
        ClassName = cls;
        name = n;
        level = lvl;
        strength = str;
        agility = agi;
        stamina = sta;
    }

    /** simplu*/
    public Adventurer()
    {
       System.out.println("I think something went wrong, default constructor in Adventurer called");
    }

    /** Supraincarcarea fucntiei de comparatie */
    @Override
    public int compareTo(Object o) {
        Adventurer adv = (Adventurer)o;

        return adv.getLevel() - this.getLevel();
    }
}
