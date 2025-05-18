package com.behrouz.server.utils;

import com.behrouz.server.model.bill.BillProductProviderEntity;
import com.behrouz.server.model.order.CustomerOrderEntity;
import com.behrouz.server.modelOption.UnitOption;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.utils
 * Project Name: behta-server
 * 04 March 2019
 **/
public class UnitUtil {


    public static float getSelectedCountBasedOnUnit( CustomerOrderEntity customerOrder ){

        if ( customerOrder.getProductProvider().getProduct().getProductUnit() == null ){
            return customerOrder.getOrderCount();
        }


        if ( customerOrder.getProductProvider().getProduct().getProductUnit().getId() == UnitOption.G_50.getId() ){

            return customerOrder.getOrderCount() / 1000;

        }

        if ( customerOrder.getProductProvider().getProduct().getProductUnit().getId() == UnitOption.G_100.getId() ){

            return customerOrder.getOrderCount() / 1000;

        }

//        if ( customerOrder.getProductProvider().getProductProviderUnitId().getId() == UnitOption.NUMBER.getId() ){
//
//            return customerOrder.getOrderCount();
//
//        }
//
//        if ( customerOrder.getProductProvider().getProductProviderUnitId().getId() == UnitOption.KG.getId() ){
//
//            return customerOrder.getOrderCount();
//
//        }

        return customerOrder.getOrderCount();
    }


    public static float getSelectedCountBasedOnUnit( BillProductProviderEntity billProduct ){


        if ( billProduct.getProductProvider().getProduct().getProductUnit().getId() == UnitOption.G_50.getId() ){

            return billProduct.getOrderCount() / 1000;

        }

        if ( billProduct.getProductProvider().getProduct().getProductUnit().getId() == UnitOption.G_100.getId() ){

            return billProduct.getOrderCount() / 1000;

        }

//        if ( billProduct.getProductProvider().getProductProviderUnitId().getId() == UnitOption.NUMBER.getId() ){
//
//            return billProduct.getFinalCount();
//
//        }
//
//        if ( billProduct.getProductProvider().getProductProviderUnitId().getId() == UnitOption.KG.getId() ){
//
//            return billProduct.getFinalCount();
//
//        }

        return billProduct.getOrderCount();
    }


}
