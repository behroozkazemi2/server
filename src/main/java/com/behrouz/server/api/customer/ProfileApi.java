package com.behrouz.server.api.customer;

import com.behrouz.server.api.BaseApi;
import com.behrouz.server.api.customer.response.ProfileRequestResponse;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionWrongDataException;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.global.AddressEntity;
import com.behrouz.server.model.order.CustomerOrderEntity;
import com.behrouz.server.repository.AddressRepository;
import com.behrouz.server.repository.CustomerOrderRepository;
import com.behrouz.server.repository.CustomerRepository;
import com.behrouz.server.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer
 * Project Name: behta-server
 * 10 February 2019
 **/
public class ProfileApi extends BaseApi {


    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;


    @ApiAction( value = "app.customer.profile.get" )
    public ApiResponseBody get( int customerId ) throws ApiActionException {

        CustomerEntity customer = getCustomerById( customerId );


        List<CustomerOrderEntity> customerOrders = customerOrderRepository.findAllByCustomer_IdAndDeletedIsFalse( customerId );

        AddressEntity addresses =
                addressRepository.findFirstByAccount_IdAndDeletedIsFalse(customerId);

        return new ApiResponseBody <>().ok(
                new ProfileRequestResponse(
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getMobile(),
                        customer.getAvatar() != null ? customer.getAvatar().getImage() : null,
                        customerOrders != null ? customerOrders.size() : 0,
                        addresses == null ? 0 : addresses.getId(),
                        customer.getNationalCode()
                ) );
    }


    @ApiAction( value = "app.customer.profile.edit" )
    public ApiResponseBody edit( ProfileRequestResponse request, int customerId ) throws ApiActionException {

        CustomerEntity customer = getCustomerById( customerId );

        if ( StringUtil.isNullOrEmpty( request.getFirstName() ) ||
                StringUtil.isNullOrEmpty( request.getLastName() ) ){

            throw new ApiActionWrongDataException( "اطلاعات وارد شده صحیح نمیباشد." );

        }


        if ( isNewInformation(customer.getFirstName(), request.getFirstName()) ||
                isNewInformation(customer.getLastName(), request.getLastName()) ){

            customer.update(request);
            customerRepository.save( customer );

        }


        return new ApiResponseBody().ok();
    }



    private boolean isNewInformation ( String s1, String s2 ) {
        return s1 != null && !s1.equalsIgnoreCase( s2 );
    }
}
