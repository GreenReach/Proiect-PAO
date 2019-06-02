package Project;

public interface Print{

    default void ClearScreen()
    {
        System.out.println();
        System.out.println();
        System.out.println();
    }

    default void NewScreen()
    {
        System.out.println();
        System.out.println("/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)");
        System.out.println("(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/");
        System.out.println();
    }
}
