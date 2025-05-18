package com.behrouz.server.utils.city;

public class CityLocationUtils {


    public static CityOption city(double lat, double lng){
        return CityOption.getByLatLng(lat, lng);
    }


}
