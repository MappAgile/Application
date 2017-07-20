package com.food_picker.core;

import java.net.*;

import com.food_picker.conn.NutritionixConnection;


public class Runner{

    private final static int CONN_TIMEOUT           = 5000;
    private final static int READ_TIMEOUT           = 5000;
    private final static String NUTRITIONIX_ID      = "a7760d66";
    private final static String NUTRITIONIX_KEY     = "f4b8bd4871a787ae3a59c5303aa3faa1";
    private final static String NUTRITIONIX_URL     = "https://api.nutritionix.com/v1_1/search";
 
    
    public static void main( String[] args ) throws Exception{
    
        NutritionixConnection api   = new NutritionixConnection( NUTRITIONIX_ID, NUTRITIONIX_KEY, NUTRITIONIX_URL );
        HttpURLConnection connection= api.connect( CONN_TIMEOUT, READ_TIMEOUT );
        
        String query                = "kashi";
        String[] fields             = {"item_name", "brand_name", "nf_calories", "nf_serving_size_qty", "nf_serving_size_unit" };
        String queryString          = api.createQuery( query, fields );
        
        String jsonResponse         = api.executeQuery( connection, queryString );
        System.out.println( "Raw Response:\n" + jsonResponse );
               
        /*
        List<String> results        = api.parseResponse( jsonResponse );
        System.out.println( "Parsed Response:\n" );
        for( String result : results ){
            System.out.println( result );
        }
        */
        
    }
    
}
