package com.food_picker.zomato;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;

import com.eclipsesource.json.*;
import com.eclipsesource.json.JsonObject.*;
import com.food_picker.conn.*;


public final class ZomatoConnection implements Connection{

    private final static String AUTH_KEY             = "3241c6291c4d13afd30dd5fea3681fd9";
    private final static String BASIC_URL_PREFIX    = "https://developers.zomato.com/api/v2.1/search?entity_type=city&q=";
    
    
    public final ConnectionType getType( ){
        return ConnectionType.ZOMATO;
    }
    
    
    @Override
    public final HttpURLConnection connect( int connTimeout, int readTimeout ) throws Exception{
        return null;
    }
    
        
    protected final String getRestaurantsInfo( String cuisineType ){
        
        InputStream instream    = null; 
        StringBuilder builder   = new StringBuilder( );
                
        try{

            String urlString    = BASIC_URL_PREFIX + cuisineType;
            
            HttpClient client   = HttpClients.createDefault( );
            HttpGet httpGet     = new HttpGet( urlString );
            httpGet.addHeader( "user-key", AUTH_KEY );
          
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity   = response.getEntity();
            if( entity == null ){
                return "Entity is NULL";                
            }
            
            BufferedReader rd = new BufferedReader( new InputStreamReader( entity.getContent()) );
            
            String line        = "";
            while( (line = rd.readLine()) != null ){
                builder.append(line);
            }
                        
        }catch( Exception e ){
            System.err.println( "FAILED to execute query: "  );
            e.printStackTrace( );
        
        }finally{
            try{
                if( instream != null ) instream.close();                
            }catch( Exception e ){}
        }
        
        return builder.toString( );

    }
    
    
    
    public final List<Restaurant> parseResponse( String jsonResponse ){
        
        List<Restaurant> restaurants    = new ArrayList<>( );
        JsonObject jsonObject           = JsonObject.readFrom( jsonResponse );
        JsonArray restaurantArray       = jsonObject.get( "restaurants" ).asArray( );
        
        for( JsonValue restaurantValue : restaurantArray ){
                        
            JsonObject vObject          = (JsonObject) restaurantValue;
            Iterator<Member> rIter      = vObject.iterator( );
            
            while( rIter.hasNext( ) ){
             
                Member itemMember       = rIter.next( );
                String name             = itemMember.getName( );
                JsonObject value        = (JsonObject) itemMember.getValue( );
                
                boolean isRestaurant    = "restaurant".equals( name );
                if( !isRestaurant ) continue;
            
                Restaurant restaurant   = new Restaurant( );
                
                String restId           = value.getString( "id", "NotFound" );
                restaurant.setRestaurantId( restId );
                
                String restName         = value.getString( "name", "NotFound" );
                restaurant.setName( restName );
                
                String urlName          = value.getString( "url", "NotFound" );
                restaurant.setUrl( urlName );
                
                String cuisines         = value.getString( "cuisines", "NotFound" );
                restaurant.setCuisines( cuisines );        
                
                parseRating( value, restaurant );
                parseLocation( value, restaurant );
                
                restaurants.add(  restaurant  );
            }
            
        }
        
        return restaurants;
        
    }
    
    
    protected final void parseRating( JsonObject value, Restaurant restaurant ){
        
        JsonObject ratingObject     = (JsonObject) value.get( "user_rating");
        Iterator<Member> ratingIter = ratingObject.iterator( );
        
        while( ratingIter.hasNext( ) ){
            Member ratMember        = ratingIter.next( );
            String ratingName        = ratMember.getName( );
            JsonValue ratingValue    = ratMember.getValue( );
            
            if( ratingName.equals( "rating_text" )){
                restaurant.setRatingText( ratingValue.asString( ) );
            }
            
            if( ratingName.equals( "aggregate_rating" )){
                restaurant.setRatingNumber( ratingValue.asString( ) );
            }
            
        }
        
    }
   
    
    
    protected final void parseLocation( JsonObject value, Restaurant restaurant ){
        
        JsonObject locationObject     = (JsonObject) value.get( "location");
        Iterator<Member> locationIter = locationObject.iterator( );
        
        while( locationIter.hasNext( ) ){
            Member locMember        = locationIter.next( );
            String locName          = locMember.getName( );
            JsonValue locValue      = locMember.getValue( );
            
            if( locName.equals( "city" )){
                restaurant.setCity( locValue.asString( ) );
            }
            
            if( locName.equals( "address" )){
                restaurant.setAddress( locValue.asString( ) );
            }
            
            if( locName.equals( "zipcode" )){
                restaurant.setZipcode( locValue.asString( ) );
            }
                        
        }
        
    }

    
    public static void main( String[] args ) throws Exception{
        ZomatoConnection conn   = new ZomatoConnection( );
        String response         = conn.getRestaurantsInfo( "indian");
        
        List<Restaurant> rests  = conn.parseResponse( response );
        for( Restaurant res : rests ){
            System.out.println( res );    
        }
     
    }

    
}
