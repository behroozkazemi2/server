package com.behrouz.server.modelOption;

public enum ProductOrderOption {


    PRICE_UP(1),//گرانترین
    PRICE_DOWN (2),//ارزانترین
    VIEW (3),//پربازدیدترین
    OFF (4),//"بیشترین تخفیف"
    NEW(5),//جدیدترین
    SAIL(6),//"پرفروش ترین"
    PROMOTE(7);//"حالت اندرویدی محصولات برتر هفته"

    private final long id;

    ProductOrderOption(long id) {
        this.id = id;
    }


    public long getId() {
        return id;
    }



    public static ProductOrderOption getById(long id){
        for(ProductOrderOption option : ProductOrderOption.values()){
            if(option.getId() == id){
                return option;
            }
        }

        return NEW;
    }
}
