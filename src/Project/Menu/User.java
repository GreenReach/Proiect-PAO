package Project.Menu;
import Project.Game.*;
import java.util.Date;
import java.util.Scanner;
import Project.Print;

public class User extends Guild implements Print {

   // private String Username;
    //private String GuildName;
    private Date DateOfCreation;

    public void setUsername(String username) {
        Username = username;
    }
    public void setGuildName(String guildName) {
        GuildName = guildName;
    }
    public void setDateOfCreation(Date dateOfCreation) {
        DateOfCreation = dateOfCreation;
    }
    public String getUsername() {
        return Username;
    }
    public String getGuildName() {
        return GuildName;
    }
    public Date getDateOfCreation() {
        return DateOfCreation;
    }

    private void Greetings()
    {
        System.out.println();
        System.out.println();
        System.out.println("WELCOME " + Username.toUpperCase() + " TO YOUR NEW ADVENTURERS GUILD SO WISELY NAMED THE " + GuildName.toUpperCase());
        System.out.println();
        System.out.println("This is your player menu, from here you have a variety of choices. So, what shall it be ?");
        System.out.println();
    }

    public void DisplayMenu()
    {
        System.out.println();
        System.out.println("     PLAYER MENU");
        System.out.println();

        System.out.println("1. Start/Continue your adventure");
        System.out.println("2. See profile information");
        System.out.println("3. Change name");
        System.out.println("4. Change the Guilds name ");
        System.out.println("0. Return to Main Menu ");
    }

    private void SeeProfile()
    {
        System.out.println();
        System.out.println("Date of creation: " + DateOfCreation.toString());
        System.out.println("Name: " + Username);
        System.out.println("Guilds name: " + GuildName);
        System.out.println();
    }

    private void ChangeUsername()
    {
        String newUser;
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter your new name: ");
        newUser = scan.nextLine();
        setUsername(newUser);
    }

    private void ChangeGuildName()
    {
        String newGuildName;
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter your new Guilds name : ");
        newGuildName = scan.nextLine();
        setGuildName(newGuildName);
    }


    public void menu() {

        int option;
        Scanner scan = new Scanner(System.in);

        NewScreen();
        Greetings();

        do{
            NewScreen();
            DisplayMenu();


            System.out.print("Choose your option: ");
            option = scan.nextInt();
            while( option != 1 && option !=2 && option != 3 && option != 4 && option != 0 )
            {
                System.out.print("Invalid choice, choose again: ");
                option = scan.nextInt();
            }

            switch(option){
                case 1:
                    super.menu();
                    break;
                case 2:
                    NewScreen();
                    SeeProfile();
                    break;
                case 3:
                    System.out.println();
                    ChangeUsername();
                    break;
                case 4:
                    System.out.println();
                    ChangeGuildName();
                    break;

            }

        }while(option != 0);

    }

    User()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter your name: ");
        Username = in.nextLine();
        System.out.print("Please enter your Project.Game.Guild's name: ");
        GuildName = in.nextLine();
        money = 100;
        DateOfCreation = new Date();

    }


}
