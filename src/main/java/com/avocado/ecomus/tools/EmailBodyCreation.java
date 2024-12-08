package com.avocado.ecomus.tools;

public class EmailBodyCreation {

    public static String create(String... data){
        StringBuilder res = new StringBuilder();
        for (String d : data){
            res.append(d).append(";");
        }
        return res.substring(0, res.length()-1);
    }
}
