package com.behrouz.server.utils.city;

public enum CityOption {

    Mashhad(1, "مشهد", 36.28691, 59.54392),
    Tehran(2, "تهران",35.75, 51.375),
    Karaj(10, "کرج",35.84272, 50.97715);

    private final int id;

    private final String name;

    private final double lat;

    private final double lng;


    CityOption(int id, String name, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }


    public static CityOption getByLatLng(double lat , double lng){

        CityOption city = Tehran;
        double diff = -1;

        for (CityOption value : CityOption.values()) {
            double cDiff = (value.getLat() - lat) * (value.getLat() - lat) + (value.getLng() - lng) * (value.getLng() - lng);
            if(diff  == -1 || diff > cDiff){
                diff = cDiff;
                city = value;
            }
        }

        return city;

    }
}
