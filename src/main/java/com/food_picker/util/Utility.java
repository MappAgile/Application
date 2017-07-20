package com.food_picker.util;

import com.eclipsesource.json.*;

public final class Utility{
    
    
    private Utility(){}
    
    public final static JsonArray create( String[] fields ){
        
        JsonArray array = new JsonArray( );
        for( String field : fields ){
            array.add( field );
        }
     
        return array;
    }

}
