package Project.Game.CharacterType;

public class Archer extends Adventurer {

    /** Aventurierului ii sunt marite atributele*/
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

    /** Constructori*/

    /** cu atribute alese aleator */
   public  Archer( int levelCap, int statsCap)
    {
        super(levelCap,statsCap);
        ClassName = "Archer";
    }

    /** cu atribute primite */
    public Archer(String cls,String n, int lvl, int str, int agi, int sta)
    {
        super(cls,n,lvl,str,agi,sta);
        ClassName = "Archer";
    }
}
