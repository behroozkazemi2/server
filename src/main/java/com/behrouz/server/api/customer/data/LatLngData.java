package com.behrouz.server.api.customer.data;


import com.behrouz.server.model.global.AddressEntity;

/*
 * api-action: app.customer.region.find
 * response -> IdName
 */
public class LatLngData{


    protected double lat;

    protected double lng;


    public LatLngData() {
    }

    public LatLngData(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public LatLngData(AddressEntity address) {
        if ( address.getLocation() != null ){
            this.lat =  address.getLocation().getCoordinate().y;
            this.lng =  address.getLocation().getCoordinate().x;
        }
    }

    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }



    public double getLng() {
        return lng;
    }
    public void setLng(double lng) {
        this.lng = lng;
    }


    @Override
    public boolean equals(Object obj) {
        return obj != null &&
                obj instanceof LatLngData &&
                ((LatLngData)obj).getLat() == this.getLat() &&
                ((LatLngData)obj).getLng() == this.getLng();
    }
}
