package Project.Services;

import Project.Game.Adventure;
import Project.Game.CharacterType.Adventurer;
import Project.Game.CharacterType.Archer;
import Project.Game.CharacterType.Warrior;
import Project.Menu.User;

import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Setup extends Thread{

    private static String filename = "loadfile.csv";

    /** Incarcarea unui utilizator prin fisier CSV*/
    private static User AddUserCSV(String name)
    {

        /** Se parcurge fisierul CSV al utilizatorului si se incarca datele sale*/
        String line = null;
        try {
            Scanner scan = new Scanner((new File(name)));
            line = scan.nextLine();

            /** Se creaza obiectul de tip User cu datele citite*/
            String[] data = line.split(",");
            User user = new User(data[0], data[1], Integer.parseInt(data[2]));

            /** Se citesc si memoreaza aventurierii utilizatorului*/
            Adventurer adv;
            while (scan.hasNextLine()) {
                data = scan.nextLine().split(",");

                if (data[0].equals("Adventurer"))
                    adv = new Adventurer(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]));
                else if (data[0].equals("Warrior"))
                    adv = new Warrior(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]));
                else
                    adv = new Archer(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]));

                user.addAdventurer(adv);
            }

            return user;


        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }


        return null;

    }

    /** Incarcarea jocului prin fisier CSV*/
    public static void LoadCSV(ArrayList<User> UserList)
    {
        /** Se citesc din fisierul filename numele tuturor utilizatorilor*/
        String s = null;
        try {
            Scanner scan = new Scanner(new File(filename));
            s = scan.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        /** Se creeaza numele fisierului in care au fost salvate datele utilizatorului*/
        String[] names = s.split(",");
        for (int i = 0; i < names.length; i++)
            names[i] = names[i].concat(".csv").toLowerCase();

        /** Se incarca fiecare utilizator*/
        for (int i = 0; i < names.length; i++)
            UserList.add(AddUserCSV(names[i]));

    }

    /** Salvarea jocului prin fisier CSV*/
    public static void SaveCSV(User user)
    {
        /** Se salveaza datele jucatorului*/
        String data = user.getUsername() + "," + user.getGuildName() + "," + user.getMoney() + "\n";

        ArrayList<Adventurer> list = user.getAdventurers();

        /** Se sa;veaza datele aventurierilor sai*/
        for (int i = 0; i < list.size(); i++) {
            data += list.get(i).getClassType() + "," + list.get(i).getName() + "," +
                    list.get(i).getLevel() + "," + list.get(i).getStrength() + "," +
                    list.get(i).getAgility() + "," + list.get(i).getStamina() + "\n";
        }

        try {
            /** Scriere in fisierul utilizatorului*/
            FileWriter writer = new FileWriter(user.getUsername().toLowerCase() + ".csv");
            writer.append(data);
            writer.flush();
            writer.close();

            /** Verific daca este un utilizator nou*/
            Scanner scan = new Scanner(new File(filename));
            String[] names = scan.nextLine().split(",");
            boolean ok = true;
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(user.getUsername()) == true)
                    ok = false;
            }

            /** Daca este un utilizator nou adaug numele sau in fisierul de Useri*/
            if (ok) {
                writer = new FileWriter(filename, true);
                writer.append("," + user.getUsername());
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    /** Incarcarea unui utilizator prin Baza de Date*/
    private static User AddUserDB(String username, String guildName, int money, int id)
    {
        Connection conn = null;
        User user = new User(username,guildName,money);

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:10000/userdb","root","1234");

            Statement stmt = null;
            stmt = conn.createStatement();


            /** Selectez toti aventurierii utilizatorului si ii incarc in memorie*/
            ResultSet rs = stmt.executeQuery("select * from `adventurer` where user_id =" + id );
            while(rs.next())
                {
                   Adventurer adv;

                   if(rs.getString("adventurer_type")=="Adventurer")
                      adv = new Adventurer(rs.getString("adventurer_type"),rs.getString("adventurer_name"),rs.getInt("adventurer_level"),
                              rs.getInt("adventurer_strength"),rs.getInt("adventurer_agility"),rs.getInt("adventurer_stamina"));
                      else if(rs.getString("adventurer_type")=="Archer")
                          adv = new Archer(rs.getString("adventurer_type"),rs.getString("adventurer_name"),rs.getInt("adventurer_level"),
                                  rs.getInt("adventurer_strength"),rs.getInt("adventurer_agility"),rs.getInt("adventurer_stamina"));
                      else
                          adv = new Warrior(rs.getString("adventurer_type"),rs.getString("adventurer_name"),rs.getInt("adventurer_level"),
                                  rs.getInt("adventurer_strength"),rs.getInt("adventurer_agility"),rs.getInt("adventurer_stamina"));

                      user.addAdventurer(adv);

                }



        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return user;
    }

    /** Incarcarea jocului din Baza de Date*/
    public static void LoadDB(ArrayList<User> UserList)
    {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            // handle the error
        }


        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:10000/userdb","root","1234");

            Statement stmt = null;

            /** Selectez fiecare utilizator*/
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from `user` ");
            while (rs.next())
            {
                /** Incarc in memorie utilizatorul*/
                UserList.add(AddUserDB(rs.getString("user_name"),rs.getString("user_guild"),rs.getInt("user_money"),rs.getInt("user_id")));
            }


        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

    }

    /** Salvarea jocului prin Baza de Date*/
    public static void SaveDB( User user)
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:10000/userdb","root","1234");

            Statement stmt = null;

            /** Verific daca utilizatorul exista*/
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from `user` where user_name = \""+user.getUsername() + "\" ");

            int id;
            if(rs.next())
                id = rs.getInt("user_id");
            else
            {
                /** Daca utilizatorul nu exista il adaug*/
                stmt.executeUpdate("insert into `user`(user_name,user_money,user_guild) " +
                                       "values (\"" + user.getUsername() + "\", " + user.getMoney() + " , \"" + user.getGuildName() + "\" )" );
                rs = stmt.executeQuery("select * from user where user_name = \"" + user.getUsername() + "\" ");
                rs.next();
                id = rs.getInt("user_id");
            }

            /** Sterg aventurierii utilizatorului din Baza de Date pentru a ii adauga pe toti in starea lor actuala*/
            stmt.executeUpdate("delete from adventurer where user_id = " + id);
            ArrayList<Adventurer> adventurers = user.getAdventurers();

            for(int i = 0; i < adventurers.size(); i++)
            {
                String querry = "insert into adventurer(adventurer_name, adventurer_type, adventurer_level, " +
                        "adventurer_strenght, adventurer_agility, adventurer_stamina, user_id) " +
                        "values( \"" + adventurers.get(i).getName() + "\" , " +
                        " \"" + adventurers.get(i).getClassType() + "\" , " +
                        adventurers.get(i).getLevel() +  " , " + adventurers.get(i).getStrength() +
                        " , " + adventurers.get(i).getAgility() + " , " + adventurers.get(i).getStamina() +
                        " , " + id;
                System.out.println(querry);
                stmt.executeUpdate("insert into adventurer(adventurer_name, adventurer_type, adventurer_level, " +
                                       "adventurer_strength, adventurer_agility, adventurer_stamina, user_id) " +
                                       "values( \"" + adventurers.get(i).getName() + "\" , " +
                                       " \"" + adventurers.get(i).getClassType() + "\" , " +
                                       adventurers.get(i).getLevel() +  " , " + adventurers.get(i).getStrength() +
                                       " , " + adventurers.get(i).getAgility() + " , " + adventurers.get(i).getStamina() +
                                       " , " + id + ")");
            }



        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}





