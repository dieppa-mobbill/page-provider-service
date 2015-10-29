package com.pageprovider.util;

import java.util.UUID;

/**
 * Created by dieppa-mint on 31/05/15.
 */
public class StringUtil {


    public static String getString(String[] words){
        StringBuilder queryString = new StringBuilder("");

        if(words !=null && words.length>0){
            boolean started = false;
            for(String word:words){
                if(started){
                    queryString.append(" ");
                }
                queryString.append(word);
                started = true;
            }
        }
        return queryString.toString();
    }


    //let's keep simple for now
    public static String generateAccessToken(){

        return UUID.randomUUID().toString();
    }




    public static boolean nullEmpty(String string){
        return string ==null || string.length() <=0;
    }
}
