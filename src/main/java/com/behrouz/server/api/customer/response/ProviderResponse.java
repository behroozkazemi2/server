package com.behrouz.server.api.customer.response;

//import com.behrouz.server.model.ProviderCategoryEntity;

import com.behrouz.server.model.account.ProviderEntity;
import com.behrouz.server.model.global.AddressEntity;
import com.behrouz.server.model.product.ProductImageEntity;
import com.behrouz.server.rest.constant.RequestDetailResponse;
import com.behrouz.server.modelOption.ProviderStatusOption;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.customer.response
 * Project server
 * 30 September 2018 14:13
 **/
public class ProviderResponse {

    private long id;

    private String name;

    private float rate;
    
    private long commentNumber;

    private long imageId;

    private float discount;


    private String shortDescription;

    private String fullDescription;

//    private List<RequestDetailResponse> categories;

    private List<String> phone;

    private String address;

    private long status; // is provider active or not ?

    private List<RequestDetailResponse> tags; // not using now


    public ProviderResponse(long id, String name, float rate, long commentNumber, long imageId, float discount, String shortDescription, String fullDescription, List<String> phone, String address, long status) {
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.commentNumber = commentNumber;
        this.imageId = imageId;
        this.discount = discount;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
//        this.categories = categories;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }

    public ProviderResponse (long id, String name, float rate, long imageId ) {
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.imageId = imageId;
    }

    public ProviderResponse(long id, String name, float rate, List<ProductImageEntity> images) {
        this(id, name, rate,
                images != null && !images.isEmpty() ? images.get(0).getId() : 0
        );
    }

    public ProviderResponse ( ProviderEntity provider,
//                              List <ProviderCategoryEntity> providerCategories,
                              AddressEntity address,
                              float providerDiscount ) {


        this.id = provider.getId();

        this.name = provider.getName();

        this.rate = provider.getRate();

        if ( provider.getLogo() != null ){

            this.imageId = provider.getLogo().getId();

        }

        this.discount = providerDiscount;

        this.shortDescription = provider.getShortDescription();

        this.fullDescription = provider.getFullDescription();

//        this.categories =
//                providerCategories
//                        .stream()
//                        .map( e ->
//                                new RequestDetailResponse(
//                                        e.getCategory().getId(),
//                                        e.getCategory().getName()
//                                )
//                        )
//                        .collect(Collectors.toList());

//        this.phone = new ArrayList<String>(  ){{add( provider.getPhone() );}};

        this.address = address == null ? null :address.getAddress();

        this.status = provider
                .isActive()
                ? ProviderStatusOption.ACTIVE.getId()
                : ProviderStatusOption.NOT_ACTIVE.getId();

    }

    public ProviderResponse(ProviderEntity provider) {
        this.id = provider.getId();

        this.name = provider.getName();

        this.rate = provider.getRate();

        this.shortDescription = provider.getShortDescription();

        if ( provider.getLogo() != null ){

            this.imageId = provider.getLogo().getId();

        }

        this.status = provider
                .isActive()
                ? ProviderStatusOption.ACTIVE.getId()
                : ProviderStatusOption.NOT_ACTIVE.getId();
    }


    public long getId() {
        return id;
    }
    public void setId( long id ) {
        this.id = id;
    }


    public long getCommentNumber() {
        return commentNumber;
    }
    public void setCommentNumber(long commentNumber) {
        this.commentNumber = commentNumber;
    }

    
    public String getName() {
        return name;
    }
    public void setName( String name ) {
        this.name = name;
    }


    public float getRate() {
        return rate;
    }
    public void setRate( float rate ) {
        this.rate = rate;
    }


    public long getImageId() {
        return imageId;
    }
    public void setImageId( long imageId ) {
        this.imageId = imageId;
    }


    public float getDiscount() {
        return discount;
    }
    public void setDiscount( float offPercent ) {
        this.discount = offPercent;
    }


    public String getShortDescription () {
        return shortDescription;
    }
    public void setShortDescription ( String shortDescription ) {
        this.shortDescription = shortDescription;
    }


    public String getFullDescription () {
        return fullDescription;
    }
    public void setFullDescription ( String fullDescription ) {
        this.fullDescription = fullDescription;
    }


//    public List < RequestDetailResponse > getCategories () {
//        return categories;
//    }
//    public void setCategories ( List < RequestDetailResponse > categories ) {
//        this.categories = categories;
//    }


    public List < String > getPhone () {
        return phone;
    }
    public void setPhone ( List < String > phone ) {
        this.phone = phone;
    }


    public String getAddress () {
        return address;
    }
    public void setAddress ( String address ) {
        this.address = address;
    }


    public long getStatus () {
        return status;
    }
    public void setStatus ( long status ) {
        this.status = status;
    }


    public List < RequestDetailResponse > getTags () {
        return tags;
    }
    public void setTags ( List < RequestDetailResponse > tags ) {
        this.tags = tags;
    }
}
