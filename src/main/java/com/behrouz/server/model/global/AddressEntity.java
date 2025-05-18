package com.behrouz.server.model.global;

import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.model.bill.BillEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.behrouz.server.api.customer.request.AddressRequestResponse;
import com.behrouz.server.model.account.ProviderEntity;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 14:49
 **/

@Entity
@Table(name = "address", schema = "public")
public class AddressEntity extends BaseEntity {

    private String address;
    private String title;
    private String city;
    private String postalCode;
    private String province;
    private Geometry location;
    private float addressOrder;

    private AccountEntity account;


    @OneToMany(mappedBy = "address")
    @JsonBackReference
    private Set<BillEntity> bills;


    public AddressEntity() {
    }

    public AddressEntity( String address, ProviderEntity newProvider ) {
        this.address = address;
        this.account = newProvider;

    }

    public AddressEntity (CustomerEntity customer, AddressRequestResponse request ) {
        this.address = request.getAddress();
        this.title = request.getTitle();
        this.addressOrder = request.getOrder();
        this.city = request.getCity();
        this.province = request.getProvince();
        this.postalCode = request.getPostalCode();

        this.location =
                new GeometryFactory()
                        .createPoint(
                                new Coordinate(
                                        request.getLng(),
                                        request.getLat()
                                )
                        );

        this.account = customer;

    }

    @Basic
    @Column(nullable = false)
    public String getAddress() {
        return address;
    }
    public void setAddress( String address ) {
        this.address = address;
    }

    @Column(name = "location", columnDefinition = "Geometry")
    public Geometry getLocation() {
        return location;
    }
    public void setLocation(Geometry location) {
        location.setSRID(4326);
        this.location = location;
    }


    @Basic
    public float getAddressOrder() {
        return addressOrder;
    }
    public void setAddressOrder( float addressOrder ) {
        this.addressOrder = addressOrder;
    }

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    public AccountEntity getAccount() {
        return account;
    }
    public void setAccount( AccountEntity account ) {
        this.account = account;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void update (AddressRequestResponse request, final CustomerEntity customer ) {

        this.address = request.getAddress();
        this.addressOrder = request.getOrder();
//
//        this.location =
//                new GeometryFactory()
//                        .createPoint(
//                                new Coordinate(
//                                        request.getLng(),
//                                        request.getLat()
//                                )
//                        );

        this.account = customer;

    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
