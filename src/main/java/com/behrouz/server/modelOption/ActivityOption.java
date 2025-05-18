package com.behrouz.server.modelOption;

import com.behrouz.server.exception.BehtaShopException;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.modelOption
 * Project Name: behta-server
 * 04 March 2019
 **/
public enum ActivityOption {

    ProductActivity("com.hashtagmarket.android.customer.activity.logic.ProductActivity", 1),
    ProviderActivity("com.hashtagmarket.android.customer.activity.logic.ProviderActivity", 2),
    CartActivity("com.hashtagmarket.android.customer.activity.logic.CartActivity", 3),
    OffCodeActivity("com.hashtagmarket.android.customer.activity.logic.OffCodeActivity", 4),
    ProductDetailActivity("com.hashtagmarket.android.customer.activity.logic.ProductDetailActivity", 5);


    private final String name;
    private final int id;

    ActivityOption(String name, int id ) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static ActivityOption getById(int id) throws BehtaShopException {
        for(ActivityOption option : ActivityOption.values()){
            if(option.getId() == id){
                return option;
            }
        }

        throw new BehtaShopException( "واحد نا معتبر." );
    }

}
