package Project;

public interface Print {

    default void clear()
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
