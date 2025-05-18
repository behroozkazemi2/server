package com.behrouz.server.api.customer.request;

import com.behrouz.server.rest.request.ListRequest;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.customer.request
 * Project server
 * 30 September 2018 10:43
 **/
public class CustomerOrderListRequest extends ListRequest {

    private long providerId; // for next step, when clicked on some provider

    public CustomerOrderListRequest() {
    }

    public long getProviderId() {
        return providerId;
    }
    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }
}
