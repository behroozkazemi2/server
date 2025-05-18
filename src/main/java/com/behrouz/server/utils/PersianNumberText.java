package com.behrouz.server.utils;

import java.util.HashMap;

/**
 * Created by Hapi KZM ( Old Server, No Changes )
 * Package com.behrouz.server.util
 * Project ximaserver
 * 22 July 2018 10:17
 **/

public class PersianNumberText {
    public final static HashMap<Character , String> alphabet = new HashMap(  ){{
        put( '0' , "۰") ;
        put( '1' , "۱") ;
        put( '2' , "۲") ;
        put( '3' , "۳") ;
        put( '4' , "۴") ;
        put( '5' , "۵") ;
        put( '6' , "۶") ;
        put( '7' , "۷") ;
        put( '8' , "۸") ;
        put( '9' , "۹");
    }};




    public static String toPersianNumber(String msg){

        StringBuilder builder = new StringBuilder(  );

        for(char c : msg.toCharArray()){
            if(alphabet.containsKey( c )){
                builder.append( alphabet.get( c ) );
            }else{
                builder.append( c );
            }
        }

        return builder.toString();

    }

    public static String toRtl(String msg){
        String ans = new String( "" );

        for(char c : msg.toCharArray()){
            ans = new String( c + ans );
        }

        return ans;
    }


    public static String reversDate(String date){

        String []seg = date.split( "/" );

        return String.format( "%s/%s/%s" ,
                seg[2],
                seg[1],
                seg[0]
        );
    }
}
