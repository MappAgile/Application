package com.food_picker.conn;

import java.net.*;

public interface Connection{
   
    public ConnectionType getType();
    public HttpURLConnection connect( int connTimeout, int readTimeout ) throws Exception;
    
}
