package org.example;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import java.io.IOException;


public class Machine {
    private ArrayList<ArrayList<String>> selection;
    private Stats stats;

    public Machine() {
        this.roll();

    }

    public Stats getStats()
    {
        return this.stats;
    }

    public void setStats(Stats stats)
    {
        this.stats = stats;
    }

    public void roll()
    {
        //TEST
        //this.selection = new ArrayList<ArrayList<String>>() {{
        //    add(new ArrayList<String>() {{ add("BAR"); add("P"); add("C"); }});
        //    add(new ArrayList<String>() {{ add("BAR"); add("P"); add("C"); }});
        //    add(new ArrayList<String>() {{ add("BAR"); add("P"); add("C"); }});
        //}};



        this.selection = ColumnsHandler.getMatriceCasino("./src/main/resources/data.json");
    }

    public void print() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < this.selection.size(); i++) {
            for (ArrayList<String> ligne : this.selection) {

                StringBuilder element = new StringBuilder(ligne.get(i));
                if(element.toString().matches("^([C,T,R,P]|1)")) { //converti la valeur C en (C)

                    element.insert(0,"(").insert(element.length(),")");

                } else if (element.toString().matches("7")) { //converti la valeur 7 en *7*

                    element.insert(0,"*").insert(element.length(),"*");
                }

                stringBuilder.append(element.toString()).append(" | ");
            }

            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
            System.out.println(stringBuilder.toString());
            stringBuilder = new StringBuilder("");
        }


    }

    public Integer checkMiddleLine()
    {

        Integer win = 0;

        String elt1 = this.selection.get(0).get(1);
        String elt2 = this.selection.get(1).get(1);
        String elt3 = this.selection.get(2).get(1);

        // ligne du milieu =
        if(elt1.matches(elt2) && elt1.matches(elt3) && elt2.matches(elt2)) win += getGain(elt1);

        return win;

    }

    public Integer checkAllLines()
    {
        Integer winCount = 0;

        for (int i = 0; i < 3; i++) {
            String elt1 = this.selection.get(0).get(i);
            String elt2 = this.selection.get(1).get(i);
            String elt3 = this.selection.get(2).get(i);

            //check element de la ligne
            if (elt1.matches(elt2) && elt1.matches(elt3) && elt2.matches(elt2)) winCount += getGain(elt1) ;
        }

        return winCount;
    }
    public Integer checkLinesAndDiags()
    {
        Integer winCount = 0;
        winCount += this.checkAllLines();

        //check en diagonal de gauche a droite
        String elt1 = this.selection.get(0).get(0);
        String elt2 = this.selection.get(1).get(1);
        String elt3 = this.selection.get(2).get(2);
        if (elt1.matches(elt2) && elt1.matches(elt3) && elt2.matches(elt2)) winCount += getGain(elt1) ;

        //check en diagonal de droite a gauche
        elt1 = this.selection.get(0).get(2);
        elt2 = this.selection.get(1).get(1);
        elt3 = this.selection.get(2).get(0);
        if (elt1.matches(elt2) && elt1.matches(elt3) && elt2.matches(elt2)) winCount += getGain(elt1) ;

        return winCount;
    }


    public String ask(String textToAsk) {
        Scanner scan = new Scanner(System.in);
        System.out.print(textToAsk); //Question

        return scan.nextLine();
    }

    public Integer getGain(String case_)
    {
        if(Objects.equals(case_, "7")) return 300;
        if(Objects.equals(case_, "BAR")) return 100;
        if(Objects.equals(case_, "R")) return 15;
        if(Objects.equals(case_, "P")) return 15;
        if(Objects.equals(case_, "T")) return 15;
        if(Objects.equals(case_, "C")) return 8;

        return 0;
    }


    public void saveStats(String statsJsonPath) {
        try (FileWriter writer = new FileWriter(statsJsonPath)) {

            Gson gson = new Gson();
            String json = gson.toJson(this.stats);

            writer.write(json);
        } catch (IOException e) {
            System.out.println(">>> Error: File Stats NotFound");
        }
    }
    public void loadStats(String statsJsonPath) {
        try (FileReader reader = new FileReader(statsJsonPath)) {
            // Parse JSON file using Gson
            Gson gson = new Gson();
            Stats stats = gson.fromJson(reader, Stats.class);
            this.stats = stats;

        } catch (IOException e) {
            System.out.println(">>> Error: File Stats NotFound");
            this.stats = null;

        }
    }
}

