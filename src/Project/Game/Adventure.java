package Project.Game;

import Project.Details;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Project.Game.CharacterType.Adventurer;
import Project.Print;

import javax.swing.*;

public class Adventure extends Thread implements Details,Print {
    private String name;
    private int difficulty;
    private int strReq;
    private int agiReq;
    private int staminaCost;
    private int moneyLower, moneyUpper, money;
    private Random rand = new Random();


    public String getDetails() {
        String details = name + ", Difficulty:" + difficulty + ", Recommended Strength:" + strReq + ", Recommended Agility:" + agiReq + ", Stamina Cost:" + staminaCost + ", Reward:" + moneyLower + "-" + moneyUpper;
        return details;

    }

    /** Imolementez fucntia run*/
    public void run()
    {
        WriteToAudit();
    }

    /** Verific atributele aventurierului si vad daca aventura se incheie cu succes sau nu in consola */
    public int GoOnAdventure(Adventurer john) {
        NewScreen();
        /** In functie de atributele aventurierului aventura poate sa aiba sau nu succes*/
        if (john.getStrength() < strReq || john.getAgility() < agiReq || john.getStamina() < 1)
            System.out.println("Your adventurer didn't succseed");
        else {
            System.out.println("WE'RE GOING ON AN ADVENTURE, " + john.getDetails());
            System.out.println("Your adventurer just leveled up!");
            john.LevelUp();
            john.getTired(staminaCost);
        }
       // WriteToAudit();

        return money;
    }

    /** Verific atributele aventurierului si vad daca aventura se incheie cu succes sau nu in UI */
    public int GoOnAdventureUI(Adventurer john) {

        if (john.getStrength() < strReq || john.getAgility() < agiReq || john.getStamina() < 1)
            JOptionPane.showMessageDialog(null,"Your adventurer didn't succseed");
        else {
           JOptionPane.showMessageDialog (null,"WE'RE GOING ON AN ADVENTURE, " + john.getDetails()+"\nYour adventurer just leveled up!");
            john.LevelUp();
        }
       // WriteToAudit();

        return money;
    }

    /** Scriu in fisierul Audit*/
    public void WriteToAudit() {

        try {
            FileWriter file = new FileWriter("audit.csv", true);
            file.append(name + " , " + new Timestamp(System.currentTimeMillis()).toString() + " , " + Thread.currentThread().getName() + "\n");
            file.flush();
            file.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /** Constructor*/
    Adventure(int diff, int strength, int agility, int stamina, int lower, int upper) {
        name = "Adventure name";
        difficulty = diff;
        strReq = strength;
        agiReq = agility;
        staminaCost = stamina;
        moneyLower = lower;
        moneyUpper = upper;
        money = rand.nextInt(upper - lower) + lower;


    }

    public void LoadDriver() {


        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
