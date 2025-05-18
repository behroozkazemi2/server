package com.behrouz.server.api.customer.request;

import com.behrouz.server.model.global.AddressEntity;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer.request
 * Project Name: behta-server
 * 13 December 2018
 **/
public class AddressRequestResponse {

    private long id;

    private String address;
    private String postalCode;

    private String title;
    private String province;
    private String city;

    private double lat; // Y

    private double lng; // X

    private long order; // olaviat address


    public AddressRequestResponse () {
    }


    public AddressRequestResponse ( AddressEntity e ) {
        this.id = e.getId();
        this.address = e.getAddress();
        this.title = e.getTitle();
        this.title = e.getTitle();
        this.province = e.getProvince();
        this.city = e.getCity();
        this.postalCode = e.getPostalCode();

        if ( e.getLocation() != null ){
            this.lat =  e.getLocation().getCoordinate().y;
            this.lng =  e.getLocation().getCoordinate().x;
        }


        this.order = (long) e.getAddressOrder();
    }


    public long getId () {
        return id;
    }
    public void setId ( long id ) {
        this.id = id;
    }


    public String getAddress () {
        return address;
    }
    public void setAddress ( String address ) {
        this.address = address;
    }


    public double getLat () {
        return lat;
    }
    public void setLat ( double lat ) {
        this.lat = lat;
    }


    public double getLng () {
        return lng;
    }
    public void setLng ( double lng ) {
        this.lng = lng;
    }


    public long getOrder () {
        return order;
    }
    public void setOrder ( long order ) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
