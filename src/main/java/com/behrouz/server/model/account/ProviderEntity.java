package com.behrouz.server.model.account;

import com.behrouz.server.model.global.ImageEntity;
import com.behrouz.server.model.global.RegionEntity;
import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.rest.request.ProviderRequest;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.behrouz.server.model.OffCodeEntity;
import com.behrouz.server.utils.StringUtil;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 09:40
 **/

@Entity
@Inheritance
public class ProviderEntity extends AccountEntity  {

    private String name;
    private String shortDescription;
    private String fullDescription;
    private float rate;
    private boolean active;

    private ImageEntity logo;


    private long minDay;

    private String address;

    protected String phone;

    protected String instagramId;

    private String telegramId;

    private RegionEntity region;

    private Set<OffCodeEntity> offCodes;
    private Set<ProductProviderEntity> productProviders;



    @OneToMany(mappedBy = "provider")
    private Set<OperatorEntity> operators;



    public ProviderEntity() {
    }


    public ProviderEntity( String name ) {
        this.name = name;
    }



    @Basic
    public String getName() {
        return name;
    }
    public void setName( String name ) {
        this.name = name;
    }


    @Column(columnDefinition = "BigInt default 0")
    public long getMinDay () {
        return minDay;
    }
    public void setMinDay ( long minDay ) {
        this.minDay = minDay;
    }


    @Basic
    public String getShortDescription() {
        return shortDescription;
    }
    public void setShortDescription( String shortDescription ) {
        this.shortDescription = shortDescription;
    }


    @Basic
    public String getFullDescription() {
        return fullDescription;
    }
    public void setFullDescription( String fullDescription ) {
        this.fullDescription = fullDescription;
    }

    @Basic
    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }



    @Basic
    @Column(columnDefinition = "boolean default false")
    public boolean isActive() {
        return active;
    }
    public void setActive( boolean active ) {
        this.active = active;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    public ImageEntity getLogo() {
        return logo;
    }
    public void setLogo( ImageEntity logo ) {
        this.logo = logo;
    }



    @OneToMany(mappedBy = "provider")
    @JsonBackReference
    public Set< ProductProviderEntity > getProductProviders() {
        return productProviders;
    }
    public void setProductProviders( Set< ProductProviderEntity > productProviders ) {
        this.productProviders = productProviders;
    }


    @OneToMany(mappedBy = "provider")
    @JsonBackReference
    public Set < OffCodeEntity > getOffCodes () {
        return offCodes;
    }
    public void setOffCodes ( Set < OffCodeEntity > offCodes ) {
        this.offCodes = offCodes;
    }


    @Basic
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }


    @Basic
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getInstagramId() {
        return instagramId;
    }
    public void setInstagramId(String instagramId) {
        this.instagramId = instagramId;
    }


    public String getTelegramId() {
        return telegramId;
    }
    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }


    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false, columnDefinition = "integer default 2")
    public RegionEntity getRegion() {
        return region;
    }
    public void setRegion(RegionEntity region) {
        this.region = region;
    }



    public void update(ProviderRequest request, RegionEntity regionEntity, ImageEntity logo) {
        this.name = StringUtil.isNullOrEmpty(request.getName()) ? this.name : request.getName();
        this.shortDescription = StringUtil.isNullOrEmpty(request.getShortDescription()) ? this.shortDescription : request.getShortDescription();
        this.fullDescription = StringUtil.isNullOrEmpty(request.getFullDescription()) ? this.fullDescription : request.getFullDescription();
        this.logo = logo == null ? this.logo : logo;
        this.active = true;
        this.minDay = request.getMinDay();
        this.address = StringUtil.isNullOrEmpty(request.getAddress()) ? this.address : request.getAddress();
        this.phone = StringUtil.isNullOrEmpty(request.getPhone()) ? this.phone : request.getPhone();
        this.instagramId = StringUtil.isNullOrEmpty(request.getInstagramId()) ? this.instagramId : request.getInstagramId();
        this.telegramId = StringUtil.isNullOrEmpty(request.getTelegramId()) ? this.telegramId : request.getTelegramId();

        this.region = regionEntity;
    }

    public void updateForProviderEdit(ProviderRequest request, ImageEntity logo) {
        this.name = StringUtil.isNullOrEmpty(request.getName()) ? this.name : request.getName();
//        this.shortDescription = StringUtil.isNullOrEmpty(request.getShortDescription()) ? this.shortDescription : request.getShortDescription();
//        this.fullDescription = StringUtil.isNullOrEmpty(request.getFullDescription()) ? this.fullDescription : request.getFullDescription();
        this.logo = logo == null ? this.logo : logo;
//        this.minDay = request.getMinDay();
        this.address = StringUtil.isNullOrEmpty(request.getAddress()) ? this.address : request.getAddress();
        this.phone = StringUtil.isNullOrEmpty(request.getPhone()) ? this.phone : request.getPhone();
        this.instagramId = StringUtil.isNullOrEmpty(request.getInstagramId()) ? this.instagramId : request.getInstagramId();
        this.telegramId = StringUtil.isNullOrEmpty(request.getTelegramId()) ? this.telegramId : request.getTelegramId();
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj instanceof ProviderEntity && ((ProviderEntity) obj).getId() == this.getId()){
            return true;
        }
        return super.equals(obj);
    }
}
