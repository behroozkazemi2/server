package com.behrouz.server.api.provider.response.geo;

import com.behrouz.server.api.customer.data.LatLngData;

import java.util.List;

public class ProviderPolygonResponse {


    private long provider;

    private List<LatLngData> points;


    public ProviderPolygonResponse() {
    }

    public ProviderPolygonResponse(long provider, List<LatLngData> points) {
        this.provider = provider;
        this.points = points;
    }

    public long getProvider() {
        return provider;
    }
    public void setProvider(long provider) {
        this.provider = provider;
    }



    public List<LatLngData> getPoints() {
        return points;
    }
    public void setPoints(List<LatLngData> points) {
        this.points = points;
    }
}
