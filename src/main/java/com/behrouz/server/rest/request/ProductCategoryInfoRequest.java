package com.behrouz.server.rest.request;

public class ProductCategoryInfoRequest extends ListRequest {

    private long productId;
    private String  search;
    private long informationCategoryId;

    public ProductCategoryInfoRequest() {
    }


    public ProductCategoryInfoRequest(int page, int length) {
        super(page, length);
    }

    public ProductCategoryInfoRequest(long productId, String search, long informationCategoryId) {
        this.productId = productId;
        this.search = search;
        this.informationCategoryId = informationCategoryId;
    }

    public ProductCategoryInfoRequest(int page, int length, long productId, String search, long informationCategoryId) {
        super(page, length);
        this.productId = productId;
        this.search = search;
        this.informationCategoryId = informationCategoryId;
    }

    public ProductCategoryInfoRequest(int page, int length, long productId, long informationCategoryId) {
        super(page, length);
        this.productId = productId;
        this.informationCategoryId = informationCategoryId;
    }

    public ProductCategoryInfoRequest(long productId, long informationCategoryId) {
        this.productId = productId;
        this.informationCategoryId = informationCategoryId;
    }


    public String getSearch() {
        return search;
    }
    public void setSearch(String search) {
        this.search = search;
    }

    public long getProductId() {
        return productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getInformationCategoryId() {
        return informationCategoryId;
    }
    public void setInformationCategoryId(long informationCategoryId) {
        this.informationCategoryId = informationCategoryId;
    }
}
