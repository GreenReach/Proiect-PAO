package Project.Game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import Project.Print;

public class Guild implements Print{

    protected String GuildName;
    protected String Username;
    protected int money;
    private ArrayList<Adventurer> Adventurers= new ArrayList<>();
    private Random rand = new Random();

    public String getDetails()
    {
        String details = "Owners name: " + Username + " Guilds Name: " + GuildName + " Current wealth: " + money + "\n" + "You have " + Adventurers.size() + " adventurers in your guild.";
        return details;
    }

    public void DisplayGuildMenu()
    {
        System.out.println( GuildName.toUpperCase() +"       MONEY:"+money);
        System.out.println("1. Send adventurers on a journey");
        System.out.println("2. Guild details ");
        System.out.println("3. Show all adventurers");
        System.out.println("4. Search for an adventurer");
        System.out.println("0. Leave the Guild");
    }

    public void SearchAdventurer()
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("The search for a new adventurer will cost 100 gold. Spend 100 gold ? [y/n]");
        String choice = scan.nextLine();
        while( choice.length() != 1 || ( choice.charAt(0) != 'y' && choice.charAt(0) != 'n')) {
            System.out.print("Invalid option, please enter again:");
            choice = scan.nextLine();
        }

        if(choice == "n")
            return ;

        int n = rand.nextInt( 5);
        if(n < 2)
            n = 2;

        Adventurer list[] = new Adventurer[n];

        System.out.println("The following " + n + " adventurers have been found");
        for(int i = 0; i < n; i++)
        {
            int adventurerClass = rand.nextInt(3);

            if(adventurerClass == 0)
            list[i] = new Adventurer(5,5);

            if(adventurerClass == 1)
                list[i] = new Archer(5,5);

            if(adventurerClass == 2)
                list[i] = new Warrior(5,5);

            System.out.println(i+1 + ". " +list[i].getDetails());
        }
        System.out.println("Who do you wish to add to your guild ?");
        int option = scan.nextInt();
        option--;
        while(option < 0 || option >= n)
        {
            System.out.print("Invallid choice, choose again:");
            option = scan.nextInt();
            option--;
        }

        Adventurers.add(list[option]);

    }

    public void ListAdventurers()
    {
        if(Adventurers.size()==0)
        {
            System.out.println("There aren't any adventurers in the guild, you can search for adventurers in the guild menu.");
            return;
        }

        for(int i = 0; i<Adventurers.size(); i++)
            System.out.println(i+1 + ". " + Adventurers.get(i).getDetails());


    }

    public int ChooseAdventurer()
    {
        int option;
        String choice;
        Scanner scan = new Scanner(System.in);

        do {
            NewScreen();

            System.out.println("Choose what adventurer you wish to send on this journey:");
            ListAdventurers();
            System.out.print("Your choice: ");
            option = scan.nextInt();
            option--;

            while (option < 0 || option >= Adventurers.size()) {
                System.out.print("Invalid choice, please choose again: ");
                option = scan.nextInt();
                option--;
            }

            NewScreen();

            System.out.println("You have chosen: \n" + Adventurers.get(option).getDetails() + "\n Confirm? [y/n] ");
            choice = scan.next();

            while (choice.length() != 1 || (choice.charAt(0) != 'y' && choice.charAt(0) != 'n')) {
                System.out.print("Invalid choice1, please choose again: ");
                choice = scan.nextLine();
            }
        }while(choice.charAt(0) != 'y');

       return option;
    }

    public void menu()
    {
        int option;
        Scanner scan = new Scanner(System.in);

        do{
            NewScreen();
            DisplayGuildMenu();

            System.out.print("Your option: ");
            option = scan.nextInt();
            while( option != 1 && option !=2 && option != 3 && option !=4 && option !=0)
            {
                System.out.print("Invalid choice, choose again: ");
                option = scan.nextInt();
            }

            switch (option){
                case 1:
                    NewScreen();
                    Adventure_List adventure_list = new Adventure_List( rand.nextInt(4)+1);
                    int adventureIndex = adventure_list.ChooseAdventure();
                    int adventurerIndex = ChooseAdventurer();
                    Adventure[] list=adventure_list.getAdventures();
                    list[adventureIndex].GoOnAdventure(Adventurers.get(adventurerIndex));
                    break;

                case 2:
                    NewScreen();
                    System.out.println(getDetails());
                    break;
                case 3:
                    NewScreen();
                    ListAdventurers();
                    break;
                case 4:
                    NewScreen();
                    SearchAdventurer();
                    break;

            }


        }while(option != 0);
    }
}
