package Project.Game.Rooms;

import Project.Game.Adventure;
import Project.Game.CharacterType.Adventurer;
import Project.Game.CharacterType.Archer;
import Project.Game.CharacterType.Warrior;
import Project.Game.Guild;
import com.sun.security.auth.module.JndiLoginModule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Scanner;

public class AdventurerFinder extends Room {

    private Random rand = new Random();
    private int price = 100;
    private Adventurer chosen;

   public AdventurerFinder(){name = "Adventurer Finder";}

   public Adventurer getChosen(){return chosen;}

   public void setChosen(Adventurer adv){chosen = adv;}

   public int getPrice(){return price;}

    /** Fucntia enter in consola */
   public Adventurer enter(int money )
   {
      return SearchAdventurer(money);
   }

    /** Se verifica daca jucatorul poate si este dispus sa plateasca pentru un aventurier si alegerea aventurierului in consola*/
    public Adventurer SearchAdventurer(int money)
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("The search for a new adventurer will cost " + price + " gold. Spend " + price + " gold ? [y/n]");
        String choice = scan.nextLine();
        while( choice.length() != 1 || ( choice.charAt(0) != 'y' && choice.charAt(0) != 'n')) {
            System.out.print("Invalid option, please enter again:");
            choice = scan.nextLine();
        }

        if(choice == "n")
            return null;

        if( money < 100) {
            System.out.println("You don't have enough money for this");
            return null;
        }
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

        return list[option];


    }

   /** Functia enter in UI*/
   public void enterUI(int money, Guild guild )
    {
         SearchAdventurerUI(money,guild);
    }

    /** Mai multi aventurieri sunt generati si jucatorul alege unul pentru a fi introdus in ghilda in UI*/
    public void ChooseAdventurerUI(JFrame frame, Guild guild)
    {

       frame.getContentPane().removeAll();
       frame.revalidate();
       frame.repaint();


       int x = 100;
       int y = 100;
        int n = rand.nextInt( 5);
        if(n < 2)
            n = 2;

        Adventurer list[] = new Adventurer[n];

        System.out.println("The following " + n + " adventurers have been found");
        for(int i = 0; i < n; i++)
        {
            int nr = i;
            int adventurerClass = rand.nextInt(3);

            if(adventurerClass == 0)
                list[i] = new Adventurer(5,5);

            if(adventurerClass == 1)
                list[i] = new Archer(5,5);

            if(adventurerClass == 2)
                list[i] = new Warrior(5,5);

            JButton b = new JButton(list[i].getDetails());
            b.setBounds(x,y,400,40);
            y += 50;
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    guild.addAdventurer(list[nr]);
                    frame.setVisible(false);
                    frame.dispose();
                }
            });

            frame.add(b);
        }

    }

    /** Se verifica daca jucatorul poate si este dispus sa plateasca pentru un aventurier in UI*/
   public void SearchAdventurerUI(int money, Guild guild)
   {
       Adventurer adven;
       if(money < price)
       {
           JOptionPane.showMessageDialog(null,"You need at least + " + price + " gold to get a new adventurer.\n" +
                   "You currently need " + (price-money) + "more gold.");
           adven = null;
       }
       else
       {
           JFrame frame = new JFrame();

           frame.setSize(700,500);

           JLabel text = new JLabel("The search for a new adventurer will cost " + price + " gold.\n Spend " + price + " gold ?");
           text.setBounds(100,100,500,80);

           JButton yes = new JButton("YES");
           yes.setBounds(150,150,60,60);
           yes.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   ChooseAdventurerUI(frame,guild);
               }
           });
           JButton no = new JButton("NO");
           no.setBounds(230,150,60,60);
           no.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   frame.setVisible(false);
                   frame.dispose();
               }
           });

           frame.add(text);
           frame.add(yes);
           frame.add(no);

           frame.setLayout(null);
           frame.setVisible(true);

       }

   }



}
