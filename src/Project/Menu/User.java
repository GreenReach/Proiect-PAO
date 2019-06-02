package Project.Menu;
import Project.Game.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Scanner;
import Project.Print;
import Project.Services.Setup;

import javax.swing.*;

public class User extends Guild implements Print {

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

    /** Mesaj introductiv afisat in consola*/
    private void Greetings()
    {
        System.out.println();
        System.out.println();
        System.out.println("WELCOME " + Username.toUpperCase() + " TO YOUR NEW ADVENTURERS GUILD SO WISELY NAMED THE " + GuildName.toUpperCase());
        System.out.println();
        System.out.println("This is your player menu, from here you have a variety of choices. So, what shall it be ?");
        System.out.println();
    }

    /** Afisarea meniului de User in consola*/
    public void DisplayMenu()
    {
        System.out.println();
        System.out.println("     PLAYER MENU");
        System.out.println();

        System.out.println("1. Start/Continue your adventure");
        System.out.println("2. See profile information");
        System.out.println("3. Change the Guilds name ");
        System.out.println("4. Save Game ");
        System.out.println("0. Return to Main Menu ");
    }

    /** Returneaza datele profilului*/
    private void SeeProfile()
    {
        System.out.println();
        System.out.println("Date of creation: " + DateOfCreation.toString());
        System.out.println("Name: " + Username);
        System.out.println("Guilds name: " + GuildName);
        System.out.println();

    }

    /** Schimbarea numelui User-ului*/
    private void ChangeUsername()
    {
        String newUser;
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter your new name: ");
        newUser = scan.nextLine();
        setUsername(newUser);
    }

    /** Schimbarea numelui ghildei*/
    private void ChangeGuildName()
    {
        String newGuildName;
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter your new Guilds name : ");
        newGuildName = scan.nextLine();
        setGuildName(newGuildName);
    }

    /** Salvarea jocului in baza de date*/
    private void saveGame()
    {
        Setup.SaveDB(this);
    }

    public User getUser(){return this;}

    /** Meniul User-ului din UI*/
    public void menuUI(JFrame frame)
    {
        System.out.println("SUNT IN MENIU");
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();

        /** Buton ce duce User-ul in meniul ghildei*/
        JButton startContinue = new JButton("Start/Continue your adventure");
        startContinue.setBounds(100,100,250,40);
        startContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guildMenuUI(frame,getUser());
            }
        });
        frame.add(startContinue);

        /** Buton ce afiseaza informatiile utilizatorului*/
        JButton seeInfo = new JButton("See profile information");
        seeInfo.setBounds(100,150,250,40);
        seeInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = "Name: " + Username + "\n" +"Guilds name: " + GuildName;
                JOptionPane.showMessageDialog(frame,s,"Profile Information",JOptionPane.PLAIN_MESSAGE);
            }
        });
        frame.add(seeInfo);

        /** Buton ce schimba numele ghildei*/
        JButton changeGuildName = new JButton("Change the Guilds name");
        changeGuildName.setBounds(100,200,250,40);
        changeGuildName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame();
                frame.setSize(200,200);

                JTextArea guildText = new JTextArea("Guild's Name...");

                guildText.setBounds(100,100,150,20);

                JButton submit = new JButton("Submit");
                submit.setBounds(125,150,100,40);

                frame.add(guildText);

                frame.add(submit);

                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        GuildName = guildText.getText();
                        System.out.println("Numele a fost schimbat in " + guildText.getText());
                        frame.setVisible(false);
                        frame.dispose();
                    }
                });

                frame.setLayout(null);
                frame.setVisible(true);

            }
        });
        frame.add(changeGuildName);

        /** Buton prin care se executa salvarea jocului*/
        JButton saveGame = new JButton("Save Game");
        saveGame.setBounds(100,250,250,40);
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                saveGame();
            }
        });
        frame.add(saveGame);

        frame.setVisible(true);
    }

    /** Meniul User-ului din consola*/
    public void menu(JFrame frame)
    {

        menuUI(frame);
        int option;
        Scanner scan = new Scanner(System.in);

        NewScreen();
        Greetings();

        do{
            NewScreen();
            DisplayMenu();


            System.out.print("Choose your option: ");
            option = scan.nextInt();
            while( option < 0 || option > 5 )
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
                    ChangeGuildName();
                    break;
                case 4:
                    Setup.SaveDB(this);
                    break;


            }

        }while(option != 0);

    }

    /** Constructori */

    /** din UI*/
    User(int x)
    {
        super(1);
        JFrame frame = new JFrame();
        frame.setSize(200,200);

        JTextArea userText = new JTextArea("Username...");
        JTextArea guildText = new JTextArea("Guild's Name...");

        userText.setBounds(100,100,150,20);
        guildText.setBounds(100,150,150,20);

        JButton submit = new JButton("Submit");
        submit.setBounds(125,200,100,40);

        frame.add(userText);
        frame.add(guildText);

        frame.add(submit);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Username = userText.getText();
                GuildName = guildText.getText();
                money = 0;
                frame.setVisible(false);
                frame.dispose();

            }
        });

        frame.setLayout(null);
        frame.setVisible(true);
    }

    /** din consola*/
    User()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter your name: ");
        Username = in.nextLine();
        System.out.print("Please enter your guilds name: ");
        GuildName = in.nextLine();
        money = 100;
        DateOfCreation = new Date();

    }

    /** cu parametrii primiti*/
   public User(String u, String g,int m)
    {
        super(1);
        Username = u;
        GuildName = g;
         money = m;

    }


}
