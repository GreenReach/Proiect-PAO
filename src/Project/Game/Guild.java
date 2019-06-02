package Project.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.concurrent.TimeUnit;

import Project.Game.CharacterType.Adventurer;
import Project.Game.CharacterType.Archer;
import Project.Game.CharacterType.Warrior;
import Project.Game.Rooms.AdventureRoom;
import Project.Game.Rooms.AdventurerFinder;
import Project.Game.Rooms.Room;
import Project.Menu.User;
import Project.Print;

import javax.management.PersistentMBean;
import javax.swing.*;


public class Guild implements Print{

    protected String GuildName;
    protected String Username;
    protected int money;
    private ArrayList<Adventurer> Adventurers= new ArrayList<>();
    private Vector<Room> Rooms = new Vector<>();
    private Random rand = new Random();

    /** Returneaza detaliile ghildei*/
    public String getDetails()
    {
        String details = "Owners name: " + Username + " Guilds Name: " + GuildName + " Current wealth: " + money + "\n" + "You have " + Adventurers.size() + " adventurers in your guild.";
        return details;
    }

    public int getMoney() {return money;}

    public void addMoney(int n){money += n;}

    public ArrayList<Adventurer> getAdventurers(){return  Adventurers;}

    public void addAdventurer( Adventurer adv){ Adventurers.add(adv);}

    /** Afiseaza meniul in consola*/
    public void DisplayGuildMenu()
    {
        System.out.println( GuildName.toUpperCase() + "       MONEY:" + money);
        System.out.println("1. Go to a room in the guild");
        System.out.println("2. Guild details ");
        System.out.println("3. Show all adventurers");
        System.out.println("0. Leave the Guild");
    }

    /** Afiseaza aventurierii in consola*/
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

    /** Apeleaza functia "enter" a unei camere in functie de tipul ei*/
    public void EnterRoom()
    {
        Scanner scan = new Scanner(System.in);
        int option;
                for(int i=0;i<Rooms.size();i++)
                    System.out.println(i+1 + ". " + Rooms.get(i).getName());
        do{
            System.out.println("Choose the room you wish to go to: ");
            option = scan.nextInt();

        }while(option < 0 || option>Rooms.size());

        if(option == 0) return;

        Room room = Rooms.get(option-1);
        if( room instanceof AdventurerFinder)
            {
                Adventurer adv = ((AdventurerFinder) room).enter(money);
                if(adv != null)
                {
                    Adventurers.add(adv);
                    money -= ((AdventurerFinder) room).getPrice();
                }

            }
        else if( room instanceof AdventureRoom)
        {
            int winnings;
            winnings = ((AdventureRoom) room).enter(Adventurers);
            money += winnings;
        }



    }

    /** Apeleaza in funtia "enter" a unei camere de tipul AdventureFinder (pentru UI)*/
    public void gotoAdventurerFinder(Room room)
    {
        ((AdventurerFinder)room).setChosen(null);
        ((AdventurerFinder)room).enterUI(money,this);
        money -= ((AdventurerFinder)room).getPrice();
        System.out.println(money);
    }

    /** Apeleaza in funtia "enter" a unei camere de tipul AdventureRoom (pentru UI)*/
    public void gotoAdventureRoom(Room room)
    {
         ((AdventureRoom)room).enterUI(Adventurers,this);
    }

    /** Initializeaza meniul ghildei in UI*/
    public void guildMenuUI(JFrame frame, User user)
    {
        System.out.println("Meniul ghildei");
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();

        JLabel text = new JLabel("<html><base><p> GUILD'S NAME : " + GuildName + " <br>MONEY : "+ money + "</p></base></html>");
        text.setBounds(100,50,350,40);
        frame.add(text);

        /** Buton ce afiseaza meniul de camere */
        JButton goToRoom = new JButton("Go to a room in the guild");
        goToRoom.setBounds(100,100,250,40);
        goToRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.getContentPane().removeAll();
                frame.revalidate();
                frame.repaint();
                Vector<JButton> rooms = new Vector<>();
                int x = 100;
                int y = 100;

                /** Afisarea camerelor din ghilda drept butoane, jucatorul va intra in camera butonului apasat*/
                for(int i = 0; i < Rooms.size(); i++)
                {
                    int nr = i;
                    JButton b = new JButton();
                    b.setText(Rooms.get(i).getName());
                    b.setBounds(x,y,250,40);
                    b.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println(nr);
                            if( Rooms.get(nr) instanceof AdventurerFinder)
                                gotoAdventurerFinder(Rooms.get(nr));

                            if( Rooms.get(nr) instanceof AdventureRoom)
                            {
                                if(Adventurers.size()==0)
                                    JOptionPane.showMessageDialog(frame,"You can't go on a adventure, you don't have any adventurers");
                                else
                                    gotoAdventureRoom(Rooms.get(nr));
                            }

                            guildMenuUI(frame,user);
                        }
                    });
                    rooms.add(b);
                    frame.add(b);
                    y+=50;
                }

            }
        });
        frame.add(goToRoom);

        /** Buton ce afiseaza detaliile ghildei*/
        JButton guildDetailsUI = new JButton("Guild details");
        guildDetailsUI.setBounds(100,150,250,40);
        guildDetailsUI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = "Owners name: " + Username + "\n Guilds Name: " + GuildName +
                             "\n Current wealth: " + money + "\n" + "You have " + Adventurers.size() + " adventurers in your guild.";

                JOptionPane.showMessageDialog(frame, s, "Guild Details", JOptionPane.PLAIN_MESSAGE);
            }
        });
        frame.add(guildDetailsUI);

        /** Buton ce afiseaza toti aventurierii*/
        JButton showAdventurers = new JButton("Show all adventurers");
        showAdventurers.setBounds(100,200,250,40);
        showAdventurers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = "";
                if(Adventurers.size()==0)
                {
                    s= "There aren't any adventurers in the guild, you can search for adventurers in the guild menu.";
                }
                else
                    for(int i = 0; i<Adventurers.size(); i++)
                       s +=(i+1) + ". " + Adventurers.get(i).getDetails()+ "\n";

                JOptionPane.showMessageDialog(frame, s, "Adventurers", JOptionPane.PLAIN_MESSAGE);
            }
        });
        frame.add(showAdventurers);

        /** Intoarcere la meniul User-ului*/
        JButton leaveGuild = new JButton("Leave the Guild");
        leaveGuild.setBounds(100,250,250,40);
        leaveGuild.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            user.menuUI(frame);
            }
        });
        frame.add(leaveGuild);
    }

    /** Se ocupa cu alegerea unei optiuni din consola*/
    public void menu()
    {
        int option;
        Scanner scan = new Scanner(System.in);

        do{
            // COLECTIE SORTAT
            Collections.sort(Adventurers);
            NewScreen();
            DisplayGuildMenu();

            System.out.print("Your option: ");
            option = scan.nextInt();
            while( option < 0 || option > 3)
            {
                System.out.print("Invalid choice, choose again: ");
                option = scan.nextInt();
            }

            switch (option){
                case 1:
                   EnterRoom();
                    break;

                case 2:
                    NewScreen();
                    System.out.println(getDetails());
                    break;
                case 3:
                    NewScreen();
                    ListAdventurers();
                    break;
            }


        }while(option != 0);
    }

    /** Constructori*/
    public Guild()
    {

        Rooms.add(new AdventurerFinder());
        Rooms.add(new AdventureRoom());

        Adventurer adv;
        int adventurerClass = rand.nextInt(3);
        adv = new Adventurer(10,10);

        if(adventurerClass == 0)
            adv = new Adventurer(10,10);

        if(adventurerClass == 1)
            adv = new Archer(10,10);

        if(adventurerClass == 2)
            adv = new Warrior(10,10);

        Adventurers.add(adv);
    }

    public Guild(int x)
    {
        Rooms.add(new AdventurerFinder());
        Rooms.add(new AdventureRoom());

    }
}
