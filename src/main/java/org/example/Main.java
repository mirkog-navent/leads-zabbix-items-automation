package org.example;

import javax.json.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws IOException {
        // ALGORITMO TRIGGER => (ayer-hoy) * 100 / ayer < 40
        // comparar 1 semana si sube o baja 40%
        generateJson();
        prettyJson();
    }

    public static void generateJson() throws FileNotFoundException {
        JsonArrayBuilder builder = Json.createArrayBuilder();

        ArrayList<String> countrys = new ArrayList<>();
        Collections.addAll(countrys,"ZPAR","IWBR","UBPE","DVPE","24MX");
        ArrayList<String> contactTypes = new ArrayList<>();
        Collections.addAll(contactTypes,"1","6","10","others");

        ArrayList<String> userTypes = new ArrayList<>();
        Collections.addAll(userTypes,"anonymus","fullUser","leadGenerator","publisherUser");

        ArrayList<String> sourceTypes = new ArrayList<>();
        Collections.addAll(sourceTypes,"portalDesktop","portalMobile","others");

        ArrayList<String> operationTypes = new ArrayList<>();
        Collections.addAll(operationTypes,"alquiler","venta");

        ArrayList<String> tipoAvisos = new ArrayList<>();
        Collections.addAll(tipoAvisos,"clasificado","emprendimiento");

        for (String country : countrys) {
            for (String contactType : contactTypes) {
                for (String userType : userTypes) {
                    for (String sourceType : sourceTypes) {
                        for (String operationType : operationTypes) {
                            for (String tipoAviso : tipoAvisos) {
                                builder.add(Json.createObjectBuilder().add("{#COUNTRY}", country).add("{#CONTACT}",contactType).add("{#CONTACT}",contactType).add("{#USER}",userType).add("{#SOURCE}",sourceType).add("{#OPERATION}",operationType).add("{#AVISO}",tipoAviso));
                            }
                        }
                    }
                }
            }
        }
    /*    for (String country : countrys) {
            for (String contactType : contactTypes) {
                builder.add(Json.createObjectBuilder().add("{#COUNTRY}", country).add("{#CONTACT}",contactType));
            }
        }*/
        JsonArray arr = builder.build();
        JsonWriter writer = Json.createWriter(new FileOutputStream("sampleData.json"));
        writer.writeArray(arr);
        writer.close();
    }

    public static void prettyJson() throws IOException {
        char actual;
        char prev = ' ';
        FileReader fileReader = new FileReader("/home/mgabriel/IdeaProjects/itemsParaAlarmas/sampleData.json");
        FileWriter fw = new FileWriter ("/home/mgabriel/IdeaProjects/itemsParaAlarmas/prettyData.json");
        BufferedWriter bw = new BufferedWriter (fw);

        int caracterLeido = fileReader.read();
        while(caracterLeido!= -1) {
            char caracter = (char) caracterLeido;
            actual = caracter;

            caracterLeido = fileReader.read();
            if(caracter == '['){
                bw.write(caracter+"\n\t");
                bw.flush();
            }else if(prev == '}' && actual == ','){
                bw.write(caracter+"\n\t");
                bw.flush();
            }else if(actual == '}' && caracterLeido == ']'){
                bw.write(caracter+"\n");
                bw.flush();
            }else {
                bw.write(caracter);
                bw.flush();
            }
            prev = actual;
        }
        bw.close();
    }
}