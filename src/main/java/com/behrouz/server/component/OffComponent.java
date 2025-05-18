package com.behrouz.server.component;

import com.behrouz.server.model.OffCodeEntity;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.rest.constant.DataTableRequest;
import com.behrouz.server.rest.constant.RequestResponseList;
import com.behrouz.server.rest.request.OffCodeRequest;
import com.behrouz.server.rest.response.OffCodePanelResponse;
import com.behrouz.server.utils.ArraysUtil;
import com.behrouz.server.utils.StringUtil;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.repository.CustomerRepository;
import com.behrouz.server.repository.OffCodeRepository;
import com.behrouz.server.repository.ProductProviderRepository;
import com.behrouz.server.repository.ProviderRepository;
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
 * 26 March 2019
 **/

@Component
public class OffComponent {


    @Autowired
    private OffCodeRepository offCodeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ProductProviderRepository productProviderRepository;


    public RequestResponseList getList (DataTableRequest request ) throws BehtaShopException {

        Page<OffCodeEntity> offCodes;

        if ( request.getStatus() == 1 ) {

            offCodes =
                    offCodeRepository.findAllValidOffCodes(
                            PageRequest.of( request.getPage(), request.getLimit() )
                    );

            if ( offCodes == null || offCodes.getTotalElements() == 0 ) {
                throw new BehtaShopException( "هیچ تخفیفی ثبت نشده." );
            }

        } else {

            offCodes =
                    offCodeRepository.findAllInValidOffCodes(
                            PageRequest.of( request.getPage(), request.getLimit() )
                    );

            if ( offCodes == null || offCodes.getTotalElements() == 0 ) {
                throw new BehtaShopException( "هیچ تخفیفی ثبت نشده." );
            }


        }


        return new RequestResponseList <>(
                offCodes.getContent()
                        .stream()
                        .map( e -> {

                            ProductProviderEntity productProvider = e.getProductProvider();

                            return new OffCodePanelResponse(
                                    e);
                        } )
                        .collect( Collectors.toList() ),

                offCodes.getTotalElements()
        );
    }

    public void deleteOffCode( int id ) throws BehtaShopException {

        OffCodeEntity offCode = offCodeRepository.findFirstByIdAndDeletedIsFalse( id );

        if ( offCode == null ){
            throw new BehtaShopException("کد تخفیف مورد نظر یافت نشد.");
        }


        offCode.setDeleted( true );
        offCodeRepository.save( offCode );

    }

    public void newOffCode( OffCodeRequest request ) throws BehtaShopException {

        if ( request.getCustomerId() != 0 ){

            newOffCodeForCustomer( request );

        } else if ( request.getProviderId() != 0 ){

            newOffCodeForProvider( request );

        } else if ( request.getProductProviderId() != 0 ){

            newOffCodeForProductProvider( request );

        } else {

            throw new BehtaShopException( "اطلاعات وارد شده صحیح نمیباشد." );

        }

    }




    private void newOffCodeForProductProvider ( OffCodeRequest request ) {

        OffCodeEntity offCode = new OffCodeEntity(
                request.getPercent(),
                productProviderRepository.findFirstByIdAndDeletedIsFalse( request.getProductProviderId() )
        );


        offCodeRepository.save( offCode );
    }

    private void newOffCodeForProvider ( OffCodeRequest request ) {

        OffCodeEntity offCode = new OffCodeEntity(
                request.getPercent(),
                providerRepository.findFirstByIdAndDeletedIsFalseAndBannedIsFalse( request.getProviderId() )
        );


        offCodeRepository.save( offCode );
    }

    private void newOffCodeForCustomer ( OffCodeRequest request ) {

        OffCodeEntity offCode = new OffCodeEntity(
                request.getPercent(),
                customerRepository.findFirstByIdAndBannedIsFalseAndDeletedIsFalse( request.getCustomerId() )
        );


        offCodeRepository.save( offCode );
    }

    public void saveByCustomers(OffCodePanelResponse offCode) throws BehtaShopException {

        if(offCode.getStartDate() == null ||
                offCode.getExpireDate() == null ||
                StringUtil.isNullOrEmpty(offCode.getCode()) ||
                StringUtil.isNullOrEmpty(offCode.getDescription()) ||
                offCode.getPercent() <=0 ||
                offCode.getMaxUsageCount() <=0 ||
                offCode.getMaxAmount() <=0){

            throw new BehtaShopException("اطلاعات وارد شده برای ایجاد کد تخفیف نامعتبر می‌باشد!");

        }
        if(offCode.isForAll()){
            OffCodeEntity offCodeEntity = new OffCodeEntity( offCode);
            offCodeEntity.setForAll(true);
            offCodeRepository.save( offCodeEntity );
        }else {

            if (ArraysUtil.isNullOrEmpty(offCode.getCustomerIds())) {
                throw new BehtaShopException("اطلاعات وارد شده برای ایجاد کد تخفیف نامعتبر می‌باشد!");
            }

            for (Integer id : offCode.getCustomerIds()) {

                CustomerEntity customer = customerRepository.findFirstById(id);
                if (customer == null) {
                    throw new BehtaShopException("خطا در جستجو، مشتری یافت نشد!");
                }
                OffCodeEntity offCodeEntity = new OffCodeEntity(customer, offCode);
                offCodeRepository.save(offCodeEntity);

            }
        }

    }

    public void newByProduct(OffCodePanelResponse offCode) throws BehtaShopException {

        if ( offCode.getStartDate() == null ||
                offCode.getExpireDate() == null ||
                StringUtil.isNullOrEmpty( offCode.getCode() ) ||
                StringUtil.isNullOrEmpty( offCode.getDescription() ) ||
                offCode.getPercent() <= 0 ||
                offCode.getMaxUsageCount() <= 0 ||
                offCode.getMaxAmount() <= 0 ) {

            throw new BehtaShopException( "اطلاعات وارد شده برای ایجاد کد تخفیف نامعتبر می‌باشد!" );

        }

        if ( offCode.isForProvider() ) {

            List < OffCodeEntity > offs = providerRepository
                    .findAllByDeletedIsFalseAndBannedIsFalseAndActiveIsTrue()
                    .stream()
                    .map( e -> new OffCodeEntity( e, offCode ) )
                    .collect( Collectors.toList() );

            offCodeRepository.saveAll( offs );

        } else {

            if ( ArraysUtil.isNullOrEmpty( offCode.getProductProviderIds() ) ) {
                throw new BehtaShopException( "اطلاعات وارد شده برای ایجاد کد تخفیف نامعتبر می‌باشد!" );
            }

            for ( Integer id : offCode.getProductProviderIds() ) {

                ProductProviderEntity productProvider = productProviderRepository.findFirstByIdAndDeletedIsFalse( id );
                if ( productProvider == null ) {
                    throw new BehtaShopException( "خطا در جستجو، محصول یافت نشد!" );
                }

                OffCodeEntity offCodeEntity = new OffCodeEntity( productProvider, offCode );
                offCodeRepository.save( offCodeEntity );

            }
        }

    }


}