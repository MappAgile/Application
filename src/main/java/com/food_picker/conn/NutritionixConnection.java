package com.food_picker.conn;

import java.io.*;
import java.net.*;
import java.util.*;

import com.eclipsesource.json.*;
import com.food_picker.util.*;


public final class NutritionixConnection implements Connection{

    private final String appId;
    private final String appKey;
    private final String urlString;
       
    private final static String REQUEST_METHOD      = "POST";
    private final static String CONTENT_TYPE_KEY    = "Content-Type";
    private final static String CONTENT_TYPE_VALUE  = "application/json";

        
    public NutritionixConnection( String appId, String appKey, String urlString ){
        this.appId      = appId;
        this.appKey     = appKey;
        this.urlString  = urlString;        
    }
    
    
    public final ConnectionType getType( ){
        return ConnectionType.NUTRITIONIX;
    }
    
    
    @Override
    public final HttpURLConnection connect( int connTimeout, int readTimeout ) throws Exception{
        URL url                 = new URL( urlString );
        HttpURLConnection conn  = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(connTimeout);
        conn.setReadTimeout(readTimeout);
        conn.setRequestMethod( REQUEST_METHOD );
        conn.setDoOutput(true);
        conn.setRequestProperty(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
     
        System.out.println( "Successfully connected to url:: " + urlString );
        
        return conn;
    }
    
    
    public final String executeQuery( HttpURLConnection conn, String queryString ) throws Exception{

        OutputStream outWriter      = null;
        BufferedReader inReader     = null;
        StringBuilder builder       = new StringBuilder( );
        
        try{
        
            System.out.println( "Executing query: " + queryString );
            
            outWriter   = conn.getOutputStream();  
            outWriter.write( queryString.getBytes( ) );                
            outWriter.flush();
            outWriter.close();

            InputStream instream    = conn.getInputStream();
            inReader                = new BufferedReader(new InputStreamReader(instream) );
        
            String line             = null;
            while( (line = inReader.readLine() ) != null ){
                builder.append( line );
            }
            
        }catch( Exception e ){
            System.err.println( "FAILED to execute query: " + queryString );
            e.printStackTrace( );
        
        }finally{
            conn.disconnect();
            if( outWriter != null ) outWriter.close( );
            if( inReader != null ) inReader.close( );
        }
        
        return builder.toString( );

    }
    
    
    
    public final String createQuery( String query, String[] fields ){
        
        JsonObject jsonQuery    = new JsonObject( );
        jsonQuery.add( "appId",    appId );
        jsonQuery.add( "appKey",   appKey );
        jsonQuery.add( "query",    query );
        jsonQuery.add( "fields",   Utility.create(fields) );
        
        String queryString      = jsonQuery.toString( );
        
        return queryString;
        
    }
    
    
    public final List<String> parseResponse( String jsonResponse ){
        
        JsonObject jsonObject   = JsonObject.readFrom( jsonResponse );
        JsonArray hitsArray     = jsonObject.get( "hits" ).asArray( );
        List<String> list       = new ArrayList<String>();
        
        for( JsonValue value : hitsArray ){
            list.add(  value.asString( ) );
        }

        return list;
        
    }
    
    
}
