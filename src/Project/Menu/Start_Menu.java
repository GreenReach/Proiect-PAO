package Project.Menu;

import java.util.Scanner;
import java.util.ArrayList;
public class Start_Menu {

    private static ArrayList<User> UserList = new ArrayList<>();

    public static void NewScreen()
    {
        System.out.println();
        System.out.println("/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)");
        System.out.println("(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/");
        System.out.println();
    }

    public static void DisplayMenu()
    {
        System.out.println("     MAIN MENU");
        System.out.println();

        System.out.println("1. New Game");
        System.out.println("2. Save Game");
        System.out.println("0. EXIT");
    }

    private static int ChooseUser()
    {
        int i,option;
        Scanner scan = new Scanner(System.in);
        if(UserList.size()==0)
        {
            System.out.println("No users created");
            return 0;
        }

        System.out.println("Choose the profile you wish to load");
        for(i = 0; i < UserList.size(); i++)
            System.out.println(i+1 + ") " + UserList.get(i).getUsername());
        System.out.println("0) Return to main menu");

        System.out.print("Your selection:");
        option = scan.nextInt();

        return option;

    }
    public static void main(String[] args) {

       int option;
       Scanner scan = new Scanner(System.in);

       do {
           NewScreen();
           DisplayMenu();

           System.out.print("Your choice: ");
           option = scan.nextInt();
           while( option != 1 && option !=2 && option != 0)
           {
               System.out.print("Invalid choice, choose again: ");
               option = scan.nextInt();
           }

           switch(option) {
               case 1:
                   User user = new User();
                   UserList.add(user);
                   UserList.get(UserList.size()-1).menu();
                   break;

               case 2:
                   int UserIndex = ChooseUser();
                   if(UserIndex == 0)
                       break;

                   UserIndex--;
                   UserList.get(UserIndex).menu();
                   break;

           }
       }while(option !=0);
       }
    }

