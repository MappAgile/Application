package com.food_picker.zomato;


public final class Restaurant{
    
    private String restaurantId;
    private String name;
    private String url;
    private String cuisines;
    private String ratingText;
    private String ratingNumber;
    private String address;
    private String city;
    private String zipcode;
    
    
    public final String getRestaurantId( ) {
        return restaurantId;
    }
    
    public final void setRestaurantId( String restaurantId ) {
        this.restaurantId = restaurantId;
    }
    
    public final String getName( ) {
        return name;
    }
    
    public final void setName( String name ) {
        this.name = name;
    }
    
    public final String getUrl( ) {
        return url;
    }
    
    public final void setUrl( String url ) {
        this.url = url;
    }
    
    public final String getCuisines( ) {
        return cuisines;
    }
    
    public final void setCuisines( String cuisines ) {
        this.cuisines = cuisines;
    }
    
    public final String getRatingText( ) {
        return ratingText;
    }
    
    public final void setRatingText( String ratingText ) {
        this.ratingText = ratingText;
    }
    
    public final String getRatingNumber( ) {
        return ratingNumber;
    }
    
    public final void setRatingNumber( String ratingNumber ) {
        this.ratingNumber = ratingNumber;
    }
    
    public final String getAddress( ) {
        return address;
    }
    
    public final void setAddress( String address ) {
        this.address = address;
    }
    
    public final String getCity( ) {
        return city;
    }
    
    public final void setCity( String city ) {
        this.city = city;
    }
    
    public final String getZipcode( ) {
        return zipcode;
    }
    
    public final void setZipcode( String zipcode ) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString( ) {
        StringBuilder builder = new StringBuilder( );
        builder.append( "Restaurant [Id=" );
        builder.append( restaurantId );
        builder.append( ", Name=" );
        builder.append( name );
        builder.append( ", Cuisines=" );
        builder.append( cuisines );
        builder.append( ", ratingText=" );
        builder.append( ratingText );
        builder.append( ", ratingNumber=" );
        builder.append( ratingNumber );
        builder.append( ", address=" );
        builder.append( address );
        builder.append( ", city=" );
        builder.append( city );
        builder.append( ", zipcode=" );
        builder.append( zipcode );
        builder.append( ", url=" );
        builder.append( url );
        builder.append( "]" );
        
        return builder.toString( );
    }
    
    
    
}