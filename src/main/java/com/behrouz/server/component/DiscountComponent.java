package com.behrouz.server.component;

import com.behrouz.server.model.OffCodeEntity;
import com.behrouz.server.model.account.ProviderEntity;
import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.model.product.ProductProviderPriceEntity;
import com.behrouz.server.repository.OffCodeRepository;
import com.behrouz.server.repository.ProductImageRepository;
import com.behrouz.server.repository.ProductPriceRepository;
import com.behrouz.server.repository.ProviderRepository;
import com.behrouz.server.rest.constant.DataTableRequest;
import com.behrouz.server.rest.constant.RequestResponseList;
import com.behrouz.server.rest.request.DiscountRequest;
import com.behrouz.server.rest.response.DiscountResponse;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.component
 * Project Name: behta-server
 * 25 March 2019
 **/

@Component
public class DiscountComponent {


    @Autowired
    private OffCodeRepository offCodeRepository;

    @Autowired
    private ProductImageRepository productImageRepository;


    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ProductPriceRepository productPriceRepository;


    public void newDiscount ( DiscountRequest discountRequest ) throws BehtaShopException {

        if ( discountRequest.getProductProviderId() != 0 ) {

            newDiscountForProductProvider( discountRequest );

        } else if ( discountRequest.getProviderId() != 0 ) {

            newDiscountForProvider( discountRequest );

        } else {

            throw new BehtaShopException( "اطلاعات وارد شده صحیح نمیباشد." );

        }

    }

    public RequestResponseList getList (DataTableRequest request ) throws BehtaShopException {

        Page <OffCodeEntity> discounts;

        if ( request.getStatus() == 1 ) {

            discounts =
                    offCodeRepository.findAllValidOffCodes(
                            PageRequest.of( request.getPage(), request.getLimit() )
                    );

            if ( discounts == null || discounts.getTotalElements() == 0 ) {
                throw new BehtaShopException( "هیچ تخفیفی ثبت نشده." );
            }

        } else {

            discounts =
                    offCodeRepository.findAllInValidOffCodes(
                            PageRequest.of( request.getPage(), request.getLimit() )
                    );

            if ( discounts == null || discounts.getTotalElements() == 0 ) {
                throw new BehtaShopException( "هیچ تخفیفی ثبت نشده." );
            }


        }


        return new RequestResponseList <>(
                discounts.getContent()
                        .stream()
                        .map( e -> {

                            ProductProviderEntity productProvider = e.getProductProvider();

                            return new DiscountResponse(
                                    e,
                                    productImageRepository
                                            .findAllByProduct_IdAndProduct_DeletedIsFalseAndDeletedIsFalse( productProvider.getId() )
                            );
                        } )
                        .collect( Collectors.toList() ),

                discounts.getTotalElements()
        );
    }

    public void deleteDiscount ( int id ) throws BehtaShopException {

        OffCodeEntity discount = offCodeRepository.findFirstByIdAndDeletedIsFalse( id );

        if ( discount == null ) {
            throw new BehtaShopException( "تخفیف مورد نظر یافت نشد." );
        }

        discount.setDeleted( true );
        offCodeRepository.save( discount );
    }

    private void newDiscountForProductProvider ( DiscountRequest discountRequest ) {

        ProductProviderPriceEntity productPrice =
                productPriceRepository.findFirstByDeletedIsFalseAndProductProvider_IdAndProductProvider_DeletedIsFalseOrderByIdDesc(
                        discountRequest.getProductProviderId()
                );

        OffCodeEntity discount = new OffCodeEntity( discountRequest, productPrice.getProductProvider() );

        offCodeRepository.save( discount );

    }

    private void newDiscountForProvider ( DiscountRequest discountRequest ) {

        List<ProductProviderPriceEntity> productPrices =
                productPriceRepository.findAllByDeletedIsFalseAndProductProvider_Provider_IdAndProductProvider_DeletedIsFalseOrderByIdDesc(
                        discountRequest.getProviderId()
                );

        offCodeRepository.saveAll(
                productPrices
                        .stream()
                        .map( e -> new OffCodeEntity( discountRequest, e.getProductProvider() ) )
                        .collect(Collectors.toList())
        );

    }

    public void addNew(DiscountRequest discountRequest) throws BehtaShopException {

        if(discountRequest == null || discountRequest.getDiscountPercent() <=0 || discountRequest.getStartDate() == null || discountRequest.getExpireDate() == null){
            throw new BehtaShopException("اطلاعات وارد شده نامعتبر می باشد!");
        }

        //todo just for erfaninan
        List<ProviderEntity> providerList = providerRepository.findAll();

        for ( ProviderEntity provider : providerList ){

            discountRequest.setProviderId(provider.getId());

            if(discountRequest.isForProvider()){

                List<ProductProviderPriceEntity> productPrices =
                        productPriceRepository.findAllByDeletedIsFalseAndProductProvider_Provider_IdAndProductProvider_DeletedIsFalseOrderByIdDesc(
                                discountRequest.getProviderId()
                        );

                //delete old discount
                for(ProductProviderPriceEntity pp: productPrices){

                    OffCodeEntity discountOld = offCodeRepository.findFirstByIdAndDeletedIsFalse(pp.getId());
                    if(discountOld != null){
                        discountOld.setDeleted(true);
                        offCodeRepository.save(discountOld);
                    }
                }

                offCodeRepository.saveAll(
                        productPrices
                                .stream()
                                .map( e -> new OffCodeEntity( discountRequest, e.getProductProvider() ) )
                                .collect(Collectors.toList())
                );

            }else{

                if(discountRequest.getProductProviderIds() == null){
                    throw new BehtaShopException("اطلاعات وارد شده نامعتبر می باشد!");
                }

                for(Long ppId : discountRequest.getProductProviderIds()){

                    ProductProviderPriceEntity productPrice =
                            productPriceRepository.findFirstByDeletedIsFalseAndProductProvider_IdAndProductProvider_DeletedIsFalseOrderByIdDesc(
                                    ppId
                            );

                    //delete old discount
                    OffCodeEntity discountOld = offCodeRepository.findFirstByIdAndDeletedIsFalse(productPrice.getId());
                    if(discountOld != null){
                        discountOld.setDeleted(true);
                        offCodeRepository.save(discountOld);
                    }

                    OffCodeEntity discount = new OffCodeEntity( discountRequest, productPrice.getProductProvider() );

                    offCodeRepository.save( discount );
                }
            }

        }

    }

}