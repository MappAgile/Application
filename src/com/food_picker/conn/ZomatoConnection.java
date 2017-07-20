package com.food_picker.conn;

import java.net.*;


public final class ZomatoConnection implements Connection{

   
    public ZomatoConnection( ){
        
    }
    
    
    public final ConnectionType getType( ){
        return ConnectionType.ZOMATO;
    }
    
    
    @Override
    public final HttpURLConnection connect( int connTimeout, int readTimeout ) throws Exception{
        throw new UnsupportedOperationException( );
    }
    
    
}
