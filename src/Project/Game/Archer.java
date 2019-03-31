package Project.Game;

public class Archer extends Adventurer {


    public void LevelUp()
    {
        level++;

        int choice= rand.nextInt(10);
        if(choice > 8)
            strength++;
        else
            agility++;

        double s = level * 0.25 +10;
        stamina = (int) s;
    }


   public  Archer( int levelCap, int statsCap)
    {
        super(levelCap,statsCap);
        Class = "Archer";
    }
}
