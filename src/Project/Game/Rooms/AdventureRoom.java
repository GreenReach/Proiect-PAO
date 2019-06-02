package Project.Game.Rooms;

import Project.Game.Adventure;
import Project.Game.AdventureList;
import Project.Game.CharacterType.Adventurer;
import Project.Game.Guild;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static Project.Menu.StartMenu.NewScreen;

public class AdventureRoom extends Room{

    public AdventureRoom(){ name = "Adventure Room";}

    /** Functie ce se ocupa cu alegerea unei aventuri si a unui aventurier ce va incerca sa o termine in consola*/
    public int enter( ArrayList<Adventurer> Adventurers)
    {
        Random rand = new Random();

        /** Creearea aventurilor ce vor putea fi alese */
        AdventureList adventure_list = new AdventureList( rand.nextInt(4)+1);
        /** Alegerea aventurii si a aventurierului*/
        int adventureIndex = adventure_list.ChooseAdventure();
        int adventurerIndex = ChooseAdventurer(Adventurers);
        Adventure[] list=adventure_list.getAdventures();

        /** Rezultatul aventurii */
        int money = list[adventureIndex].GoOnAdventure(Adventurers.get(adventurerIndex));
        return money;
    }

    /** Afiseaza toti aventurierii si unul este ales pentru a merge in aventura in consola*/
    public void ListAdventurers( ArrayList<Adventurer> Adventurers)
    {
        if(Adventurers.size()==0)
        {
            System.out.println("There aren't any adventurers in the guild, you can search for adventurers in the guild menu.");
            return;
        }

        for(int i = 0; i<Adventurers.size(); i++)
            System.out.println(i+1 + ". " + Adventurers.get(i).getDetails());


    }

    /** Afisarea tuturor aventurilor si una este aleasa pentru a fi completata in consola*/
    public int ChooseAdventurer(ArrayList<Adventurer> Adventurers)
    {
        int option;
        String choice;
        Scanner scan = new Scanner(System.in);

        do {
            NewScreen();

            System.out.println("Choose what adventurer you wish to send on this journey:");
            ListAdventurers( Adventurers);
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
                System.out.print("Invalid choice, please choose again: ");
                choice = scan.nextLine();
            }
        }while(choice.charAt(0) != 'y');

        return option;
    }

    /** Functie ce se ocupa cu alegerea unei aventuri si a unui aventurier ce va incerca sa o termine in UI*/
    public void enterUI(ArrayList<Adventurer> Adventurers, Guild guild)
    {
        ChooseAdventurerUI(Adventurers,guild);
    }

    /** Alegerea aventurierului si a aventurii in UI*/
    public void ChooseAdventurerUI(ArrayList<Adventurer> Adventurers, Guild guild)
    {
        JFrame frame = new JFrame();
        frame.setSize(700,500);

        JLabel text = new JLabel("<html><body> Choose an adventurer to go on an adventure</body></html>");
        text.setBounds(100,100,450,40);
        frame.add(text);

        int x = 100;
        int y = 150;
        /** Afisarea aventurierilro pentru alegerea unuia*/
        for(int i =0; i<Adventurers.size(); i++)
        {
            int nr = i;
            JButton b = new JButton(Adventurers.get(i).getDetails());
            b.setBounds(x,y,450,40);
            y+=50;
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Random rand = new Random();
                    AdventureList list = new AdventureList(rand.nextInt(5)+1);
                    list.ChooseAdventureUI(frame,Adventurers.get(nr),guild);

                }
            });

            frame.add(b);

        }

        frame.setLayout(null);
        frame.setVisible(true);
    }
}
