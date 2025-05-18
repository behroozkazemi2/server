package com.behrouz.server.api.customer.response;

import com.behrouz.server.model.product.ProductProviderEntity;

import java.util.List;

public class SuggestionResponse {

    private long id;

    private String title;

    private String detail;

    private long imageId;

    private String activity;

    private List<ExtraDataResponse> extraDataResponse;


    public SuggestionResponse() {
    }


    public SuggestionResponse(long id, String title, String detail, long imageId, String activity, List<ExtraDataResponse> extraDataResponse) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.imageId = imageId;
        this.activity = activity;
        this.extraDataResponse = extraDataResponse;
    }

    public SuggestionResponse(ProductProviderEntity s) {

        this.id = s.getId();
        this.title = s.getProduct().getName();
        this.detail = s.getProduct().getShortDescription();
//        this.imageId = s.getImageId();
//        this.activity = s.getActivity();
//        try {
//            this.extraDataResponse = new ObjectMapper().readValue(s.getData(), TypeFactory.defaultInstance().constructCollectionType(List.class, ExtraDataResponse.class));
//        } catch (IOException e) {
//            System.out.prlongln("FUCK");
//            e.prlongStackTrace();
//        }

    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }


    public long getImageId() {
        return imageId;
    }
    public void setImageId(long imageId) {
        this.imageId = imageId;
    }


    public String getActivity() {
        return activity;
    }
    public void setActivity(String activity) {
        this.activity = activity;
    }


    public List<ExtraDataResponse> getExtraDataResponse() {
        return extraDataResponse;
    }
    public void setExtraDataResponse(List<ExtraDataResponse> extraDataResponse) {
        this.extraDataResponse = extraDataResponse;
    }

}