package com.behrouz.server.rest.request;

import com.behrouz.server.model.account.ProviderEntity;
import com.behrouz.server.model.global.AddressEntity;
import com.behrouz.server.modelOption.ProviderStatusOption;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Abolfazl
 * Package com.behrouz.server.rest.request
 * Project newxima
 * 29 January 2019 10:20 AM
 **/
public class WebProviderRequest {

    private long id; // new or edit

    private String name;
    private String shortDescription;
    private String fullDescription;
    private float rate;

    private List<String> phone;
    private String address;

    private MultipartFile logo;
    private byte[] logoImage;


    private long status;
    private List<Integer> tags;

    private List<WebProductProviderRequest> products;

    public WebProviderRequest() {
    }

    public WebProviderRequest(ProviderEntity provider,
                           AddressEntity address,
                           List<WebProductProviderRequest> productProviders) {

        this.id = provider.getId();
        this.name = provider.getName();
        this.shortDescription = provider.getShortDescription();
        this.fullDescription = provider.getFullDescription();

        this.rate = provider.getRate();


//        this.phone = new ArrayList<String>(  ){{add( provider.getPhone() );}};

        this.address = address.getAddress();

        if ( provider.getLogo() != null ) {

            this.logoImage = provider.getLogo().getImage();

        }

        this.status = provider
                .isActive()
                ? ProviderStatusOption.ACTIVE.getId()
                : ProviderStatusOption.NOT_ACTIVE.getId();

        this.products = productProviders;

    }


    public long getId() {
        return id;
    }
    public void setId( long id ) {
        this.id = id;
    }



    public String getName() {
        return name;
    }
    public void setName( String name ) {
        this.name = name;
    }



    public String getShortDescription() {
        return shortDescription;
    }
    public void setShortDescription( String shortDescription ) {
        this.shortDescription = shortDescription;
    }



    public String getFullDescription() {
        return fullDescription;
    }
    public void setFullDescription( String fullDescription ) {
        this.fullDescription = fullDescription;
    }


    public List< String > getPhone() {
        return phone;
    }
    public void setPhone( List< String > phone ) {
        this.phone = phone;
    }



    public String getAddress() {
        return address;
    }
    public void setAddress( String address ) {
        this.address = address;
    }


    public MultipartFile getLogo() {
        return logo;
    }
    public void setLogo( MultipartFile logo ) {
        this.logo = logo;
    }



    public byte[] getLogoImage() {
        return logoImage;
    }
    public void setLogoImage(byte[] logoImage) {
        this.logoImage = logoImage;
    }



    public long getStatus() {
        return status;
    }
    public void setStatus( long status ) {
        this.status = status;
    }



    public List< Integer > getTags() {
        return tags;
    }
    public void setTags( List< Integer > tags ) {
        this.tags = tags;
    }



    public List<WebProductProviderRequest> getProducts() {
        return products;
    }
    public void setProducts(List<WebProductProviderRequest> products) {
        this.products = products;
    }


    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }
}
