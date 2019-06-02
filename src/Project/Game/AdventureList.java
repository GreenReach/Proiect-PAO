package Project.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import Project.Game.CharacterType.Adventurer;
import Project.Print;

import javax.swing.*;

public class AdventureList implements Print {

    private Adventure adventures[];
    private int n;

    public Adventure[] getAdventures() {
        return adventures;
    }

    public int getN() {
        return n;
    }

    /** Din lista de aventuri creata jucatorul alege una in consola*/
    public int ChooseAdventure()
    {
        Scanner scan = new Scanner(System.in);
        int option;
        String choice = "n";

        System.out.println("We have some adventures ready to go, just choose one from the list but be careful,once you choose one the rest will just go away");

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

    /** Din lista de aventuri creata jucatorul alege una in UI*/
    public void ChooseAdventureUI(JFrame frame, Adventurer adv, Guild guild)
    {
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();

        JLabel text = new JLabel("<html><body> Choose an adventure</body></html>");
        text.setBounds(100,100,450,40);
        frame.add(text);

        int x = 100;
        int y = 150;
        for(int i=0; i<adventures.length;i++)
        {
            int nr = i;
            JButton b = new JButton(adventures[i].getDetails());
            b.setBounds(x,y,450,40);
            y += 50;
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int money = adventures[nr].GoOnAdventureUI(adv);
                    adventures[nr].start();
                    guild.addMoney(money);
                    frame.setVisible(false);
                    frame.dispose();
                }
            });

            frame.add(b);
        }



    }

    /** Constructor ce primeste numarul de aventuri ce trebuie create*/
    public AdventureList(int nr)
    {
        n = nr;
        adventures = new Adventure[n];

        for(int i = 0; i < n; i++)
            adventures[i] = new Adventure(1,2,2,1,100,300);
    }



}
