package Project.Menu;

import Project.Services.Setup;
import com.mysql.cj.protocol.a.NativeConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Vector;

import static java.lang.System.exit;

public class StartMenu{

    private static ArrayList<User> UserList = new ArrayList<>();

    /** Afisez linii de tranzitie in consola */
    public static void NewScreen()
    {
        System.out.println();
        System.out.println("/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)/)");
        System.out.println("(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/(/");
        System.out.println();
    }


    /** Afisez meniul in consola */
    public static void DisplayMenu()
    {
        System.out.println("     MAIN MENU");
        System.out.println();

        System.out.println("1. New Game");
        System.out.println("2. Load Game");
        System.out.println("0. EXIT");
    }

    /** Afisez meniul in User Interface */
    private static void ChooseOptionUI()
    {
        int option;
        JFrame f = new JFrame();
        f.setSize(500,500);

        //Butonul "New Game" ce creeaza un nou User, il introduce in lista de Useri si acceseaza meniul ghildei
        JButton b1 = new JButton("New Game");
        b1.setBounds(100,100,100,40);
        f.add(b1);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("NEW GAME");

                User user = new User(1);
                UserList.add(user);
            }
        });

        //Butonul "Load Game" ce afiseaza lista de useri
        JButton b2 = new JButton("Load Game");
        b2.setBounds(100,150,100,40);
        f.add(b2);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Load Game");

                JFrame frame = new JFrame();
                frame.setSize(500,500);

                JPanel panel = new JPanel();
                frame.add(BorderLayout.CENTER,panel);

                JScrollPane scroll = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

                panel.setLayout( new GridLayout(40,0));


                Vector<JButton> userButtons = new Vector<>();

                // Afisez un buton pentru fiecare User, cand un buton este apasat se incarca meniul ghildei cu datele
                // User-ului ales
                for (int i = 0; i < UserList.size(); i++) {

                    userButtons.add(new JButton(UserList.get(i).getUsername()));
                    int opt = i;
                    userButtons.get(i).addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                UserList.get(opt).menuUI(f);
                                frame.dispose();
                        }
                    });

                    panel.add(userButtons.get(i));
                    panel.add(new JLabel("<html><br/></html>", SwingConstants.CENTER));
                }

                frame.add(BorderLayout.CENTER,scroll);

                frame.setVisible(true);

            }
        });


        // Butonul "Quit" ce inchide aplicatia
        JButton b3 = new JButton("Quit");
        b3.setBounds(100,200,100,40);
        f.add(b3);
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Quit");
                exit(0);
            }
        });


        f.setLayout(null);
        f.setVisible(true);
    }

    /** Alegerea User-ului din consola*/
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
            System.out.println(i + 1 + ") " + UserList.get(i).getUsername());
        System.out.println("0) Return to main menu");

        System.out.print("Your selection:");
        option = scan.nextInt();

        return option;

    }

    /** Functia main ce initializeaza meniul principal al UI-ului si de unde se pot alege optiuni din consola*/
    public static void main(String[] args)
    {

       // Setup.LoadCSV(UserList);

        /** Incarc datele din Baza de Date*/
        Setup.LoadDB(UserList);

        /** Afisez meniul principal */
        ChooseOptionUI();

        /** Alegere optiune din consola */
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
                   int ok;
                   User user;
                   do {
                       ok = 1;
                       user = new User();
                       for(int i = 0; i < UserList.size(); i++)
                           if (user.getUsername().equals(UserList.get(i).getUsername()))
                           {
                               System.out.println("Username already taken, please use another");
                               ok = 0;
                           }
                   }while(ok == 0);

                   UserList.add(user);
                   UserList.get(UserList.size()-1).menu();
                   break;

               case 2:
                   int userIndex = ChooseUser();
                   if(userIndex == 0)
                       break;

                   userIndex--;
                   UserList.get(userIndex).menu();
                   break;

           }
       }while(option !=0);
       }

    }

