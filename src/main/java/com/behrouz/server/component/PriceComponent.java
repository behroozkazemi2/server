package com.behrouz.server.component;

import com.behrouz.server.model.OffCodeEntity;
import com.behrouz.server.model.bill.BillProductProviderEntity;
import com.behrouz.server.model.global.AddressEntity;
import com.behrouz.server.model.order.CustomerOrderEntity;
import com.behrouz.server.model.product.ProductProviderPriceEntity;
import com.behrouz.server.utils.UnitUtil;
import com.behrouz.server.repository.OffCodeRepository;
import com.behrouz.server.repository.ProductPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer
 * Project Name: behta-server
 * 01 January 2019
 **/

@Component
public class PriceComponent {


    @Autowired
    private OffCodeRepository offCodeRepository;

    @Autowired
    private ProductPriceRepository productPriceRepository;



    public long oneProductWithDiscount( CustomerOrderEntity customerOrder ){

        OffCodeEntity discount =
                offCodeRepository.findFirstByProductProvider_IdAndDeletedIsFalseAndProductProvider_DeletedIsFalseOrderByIdDesc(
                        customerOrder.getProductProvider().getId()
                );

        ProductProviderPriceEntity productPrice =
                productPriceRepository.findFirstByDeletedIsFalseAndProductProvider_IdAndProductProvider_DeletedIsFalseOrderByIdDesc(
                        customerOrder.getProductProvider().getId()
                );

        return  (long)
                customerOrder.getOrderCount() * calculatePriceAfterDiscount(
                UnitUtil.getSelectedCountBasedOnUnit( customerOrder ),
                productPrice != null ? productPrice.getPrimitiveAmount() : 0,
                discount != null ? discount.getPercent() : 0
        );
    }


    public long calculateTheTotalPrice ( float count, long productPrice ) {
        return (long) (count * productPrice);
    }


    public long calculateTheTotalPrice ( float count, ProductProviderPriceEntity productPrice ) {
        return calculateTheTotalPrice( count, productPrice != null ? productPrice.getPrimitiveAmount() : 0 );
    }


    public long calculatePriceAfterDiscount ( float orderCount, long primitiveAmount, float discountPercent ) {
//        return orderCount * (primitiveAmount * (discountPercent / 100));
        return (long) (primitiveAmount * (discountPercent / 100));
    }

    public long calculatePriceAfterOffCode ( float orderCount, long primitiveAmount, OffCodeEntity offCode ) {
//        return orderCount * (primitiveAmount * (discountPercent / 100));
        return (long) Math.min(
                primitiveAmount * (offCode.getPercent() / 100),
                offCode.getMaxAmount()
        );

    }



    public long calculateTotalPriceAfterDiscount ( long totalPrice, long totalDiscount ) {
        return totalPrice - totalDiscount;
    }







    /*
    Calculating "Discount" And "OffCode" Effects of Products
     */

    public long realPriceForCustomerOrders ( List<CustomerOrderEntity> customerOrders ) {

        return (long)
                customerOrders
                        .stream()
                        .mapToDouble( e ->
                                calculateTheTotalPrice(
                                        UnitUtil.getSelectedCountBasedOnUnit( e ),
                                        productPriceRepository.findFirstByDeletedIsFalseAndProductProvider_IdAndProductProvider_DeletedIsFalseOrderByIdDesc(
                                                e.getProductProvider().getId()
                                        )
                                )
                        )
                        .sum();

    }


    public long discountForCustomerOrders ( List < CustomerOrderEntity > customerOrders ) {
        return (long)
                customerOrders
                        .stream()
                        .mapToDouble( e -> {

                            OffCodeEntity discount = offCodeRepository.findFirstByProductProvider_IdAndDeletedIsFalseAndProductProvider_DeletedIsFalse(
                                    e.getProductProvider().getId()
                            );

                            ProductProviderPriceEntity price = productPriceRepository.findFirstByDeletedIsFalseAndProductProvider_IdAndProductProvider_DeletedIsFalseOrderByIdDesc(
                                    e.getProductProvider().getId()
                            );

//                            long totalPrice = calculateTheTotalPrice(
//                                    UnitUtil.getSelectedCountBasedOnUnit( e ),
//                                    price
//                            );

                            return calculatePriceAfterDiscount(
                                    UnitUtil.getSelectedCountBasedOnUnit( e ),
                                    price != null ? price.getPrimitiveAmount() : 0,
                                    discount != null ? discount.getPercent() : 0
                            )
                                    * e.getOrderCount();

                        } )
                        .sum();
    }


    public long offCodeForCustomerOrders ( OffCodeEntity offCode, List<CustomerOrderEntity> selectedProducts ) {

        if ( offCode == null ){
            return 0;
        }

        long offCodeAmount = 0;

        if ( offCode.getCustomer() != null || offCode.isForAll() ) {

            long offCodeEffect = (long) selectedProducts
                    .stream()
                    .mapToDouble( e ->
                            e.getOrderCount() * calculatePriceAfterOffCode(
                                    UnitUtil.getSelectedCountBasedOnUnit( e ),
                                    getProductProviderPrice( e.getProductProvider().getId() ),
                                    offCode
                            )
                    )
                    .sum();


            offCodeAmount = Math.min( offCodeEffect, getOffCodeMaxAmount(offCode) );


        } else if ( offCode.getProvider() != null ){

            long offCodeEffect =
                    (long) selectedProducts
                            .stream()
                            .filter( e -> e.getProductProvider().getProvider().getId() == offCode.getProvider().getId() )
                            .mapToDouble( e ->
                                    e.getOrderCount() * calculatePriceAfterOffCode(
                                            UnitUtil.getSelectedCountBasedOnUnit( e ),
                                            getProductProviderPrice( e.getProductProvider().getId() ),
                                            offCode
                                    )
                            )
                            .sum();

            offCodeAmount = Math.min( offCodeEffect, getOffCodeMaxAmount(offCode) );


        } else if ( offCode.getProductProvider() != null ){

            long offCodeEffect =
                    (long) selectedProducts
                            .stream()
                            .filter( e -> e.getProductProvider().getId() == offCode.getProductProvider().getId() )
                            .mapToDouble( e ->
                                    e.getOrderCount() * calculatePriceAfterOffCode(
                                            UnitUtil.getSelectedCountBasedOnUnit( e ),
                                            getProductProviderPrice( e.getProductProvider().getId() ),
                                            offCode
                                    )
                            )
                            .sum();

            offCodeAmount = Math.min( offCodeEffect, getOffCodeMaxAmount(offCode) );


        }

        return offCodeAmount;
    }


    public long realPriceForBillProducts ( List<BillProductProviderEntity> billProducts ) {

        return (long)
                billProducts
                        .stream()
                        .mapToDouble( e ->
                                calculateTheTotalPrice(
                                        UnitUtil.getSelectedCountBasedOnUnit( e ),
                                        productPriceRepository.findFirstByDeletedIsFalseAndProductProvider_IdAndProductProvider_DeletedIsFalseOrderByIdDesc(
                                                e.getProductProvider().getId()
                                        )
                                )
                        )
                        .sum();

    }


    public long discountForBillProducts ( List <BillProductProviderEntity> billProducts ) {
        return (long)
                billProducts
                        .stream()
                        .mapToDouble( e -> {

                            OffCodeEntity discount = offCodeRepository.findFirstByProductProvider_IdAndDeletedIsFalseAndProductProvider_DeletedIsFalse(
                                    e.getProductProvider().getId()
                            );

                            ProductProviderPriceEntity price = productPriceRepository.findFirstByDeletedIsFalseAndProductProvider_IdOrderByIdDesc(
                                    e.getProductProvider().getId()
                            );

                            long totalPrice = calculateTheTotalPrice(
                                    UnitUtil.getSelectedCountBasedOnUnit( e ),
                                    price
                            );

                            return totalPrice - calculatePriceAfterDiscount(
                                    UnitUtil.getSelectedCountBasedOnUnit( e ),
                                    price.getPrimitiveAmount(),
                                    discount != null ? discount.getPercent() : 0
                            );

                        } )
                        .sum();
    }


//    public long offCodeForBillProducts ( OffCodeEntity offCode, List<BillCustomerOrderEntity> selectedProducts ) {
//
//        if ( offCode == null ){
//            return 0;
//        }
//
//        long offCodeAmount = 0;
//
//        if ( offCode.getCustomer() != null || offCode.isForAll() ) {
//
//            long offCodeEffect = (long) selectedProducts
//                    .stream()
//                    .mapToDouble( e ->
//                            e.getOrder().getOrderCount() * calculatePriceAfterOffCode(
//                                    UnitUtil.getSelectedCountBasedOnUnit( e ),
//                                    getProductProviderPrice( e.getProductProvider().getId() ),
//                                    offCode
//                            )
//                    )
//                    .sum();
//
//
//            offCodeAmount = Math.min( offCodeEffect, getOffCodeMaxAmount(offCode) );
//
//
//        } else if ( offCode.getProvider() != null ){
//
//            long offCodeEffect =
//                    (long) selectedProducts
//                            .stream()
//                            .filter( e -> e.getProductProvider().getProvider().getId() == offCode.getProvider().getId() )
//                            .mapToDouble( e ->
//                                    e.getFinalCount() * calculatePriceAfterOffCode(
//                                            UnitUtil.getSelectedCountBasedOnUnit( e ),
//                                            getProductProviderPrice( e.getProductProvider().getId() ),
//                                            offCode
//                                    )
//                            )
//                            .sum();
//
//            offCodeAmount = Math.min( offCodeEffect, getOffCodeMaxAmount(offCode) );
//
//
//        } else if ( offCode.getProductProvider() != null ){
//
//            long offCodeEffect =
//                    (long) selectedProducts
//                            .stream()
//                            .filter( e -> e.getProductProvider().getId() == offCode.getProductProvider().getId() )
//                            .mapToDouble( e ->
//                                    e.getFinalCount() * calculatePriceAfterOffCode(
//                                            UnitUtil.getSelectedCountBasedOnUnit( e ),
//                                            getProductProviderPrice( e.getProductProvider().getId() ),
//                                            offCode
//                                    )
//                            )
//                            .sum();
//
//            offCodeAmount = Math.min( offCodeEffect, getOffCodeMaxAmount(offCode) );
//
//
//        }
//
//        return offCodeAmount;
//    }




    /*
    Helper Functions
     */

    public long getProductProviderPrice( long productProviderId ) {

        ProductProviderPriceEntity lastPrice =
                productPriceRepository.findFirstByDeletedIsFalseAndProductProvider_IdAndProductProvider_DeletedIsFalseOrderByIdDesc( productProviderId );


        return lastPrice != null ? lastPrice.getFinalAmount() : 0;

    }


    private long getOffCodeMaxAmount ( OffCodeEntity offCode ) {
        return (long) offCode.getMaxAmount();
    }


    public long calculateDistanceAmount ( AddressEntity address ) {
        return 0; // TODO: 7/2/20
    }

}
