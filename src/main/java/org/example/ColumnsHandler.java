package org.example;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Random;

public class ColumnsHandler {

    static public ArrayList<ArrayList<String>> getMatriceCasino(String dataPathFile) {

        try (Reader reader = new FileReader(dataPathFile)) {
            Gson gson = new Gson();
            Random rand = new Random();

            ArrayList<ArrayList<String>> data = gson.fromJson(reader, ArrayList.class);
            ArrayList<ArrayList<String>> matriceCasion = new ArrayList<ArrayList<String>>(3);

            for (int i = 0; i < data.size(); i++) { //pour toute les colonnes

                ArrayList<String> colonne = new ArrayList<>();

                //faire la selection d'une colonne
                int indexRandom =  rand.nextInt(data.get(i).size());

                for(int j = 0; j < 3;j++) {
                    if( indexRandom >= data.get(i).size()-1 ) {
                        indexRandom = 0;
                    }

                    colonne.add( data.get(i).get(indexRandom) );
                    indexRandom++;
                }
                //ajouter la colonne a la matrice
                matriceCasion.add(colonne);
            }

            return matriceCasion;


        } catch (IOException e) {
            System.err.println(">>> Error: ColumnsHandler Not Found");
            throw new RuntimeException(e);
        }

    }
}
