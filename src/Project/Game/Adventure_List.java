package Project.Game;

import java.util.Scanner;
import Project.Print;

public class Adventure_List implements Print {

    private Adventure adventures[];
    private int n;

    public Adventure[] getAdventures() {
        return adventures;
    }

    public int getN() {
        return n;
    }

    public int ChooseAdventure() {
        Scanner scan = new Scanner(System.in);
        int option;
        String choice = "n";

        System.out.println("We have some adventures ready to go, just choose one from the list but be careful, \nonce you choose one the rest will just go away, after all\n there are other adventures in the world ready to go and play and we can't just hoard all these great adventures for us ");

        do{
            for (int i = 0; i < n; i++)
                System.out.println(i + 1 + ". " + adventures[i].getDetails());

        System.out.print("Your choice: ");
        option = scan.nextInt();
        option--;
        while (option < 0 || option >= n) {
            System.out.print("Invalid choice, please choose again: ");
            option = scan.nextInt();
            option--;
        }

        NewScreen();

        System.out.println("You have chosen: \n" + adventures[option].getDetails() + "\n Confirm? [y/n] ");
        choice = scan.next();

        while( choice.length() != 1 || ( choice.charAt(0) != 'y' && choice.charAt(0) != 'n')) {
            System.out.print("Invalid choice, please choose again: ");
            choice = scan.nextLine();
        }

        }while(choice.charAt(0) != 'y');

        return option;
    }

    Adventure_List(int nr)
    {
        n = nr;
        adventures = new Adventure[n];

        for(int i = 0; i < n; i++)
            adventures[i] = new Adventure(1,2,2,1,100,300);
    }

}
