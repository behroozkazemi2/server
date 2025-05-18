package com.behrouz.server.component;

import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.balance.BalanceEntity;
import com.behrouz.server.repository.balance.BalanceRepository;
import com.behrouz.server.rest.constant.DataTableRequest;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.constant.RequestResponseList;
import com.behrouz.server.rest.request.ChangeStatusRequest;
import com.behrouz.server.rest.request.CustomerSearchRequest;
import com.behrouz.server.rest.request.EditCustomerRequest;
import com.behrouz.server.rest.response.digestList.CustomerListDigestResponse;
import com.behrouz.server.utils.StringUtil;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.modelOption.CustomerStatusOption;
import com.behrouz.server.repository.AddressRepository;
import com.behrouz.server.repository.CustomerRepository;
import com.behrouz.server.specification.CustomerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.component
 * Project server
 * 11 October 2018 10:46
 **/
@Component
public class CustomerComponent {


    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BalanceRepository balanceRepository;



    public RequestResponseList getList(DataTableRequest request ) throws BehtaShopException {

        Page<CustomerEntity> allCustomers;

        if ( request.getStatus() == CustomerStatusOption.ACTIVE.getId() ){

            allCustomers =
                    customerRepository.findAllByBannedIsFalseAndDeletedIsFalse(
                            PageRequest.of( request.getPage(), request.getLimit() )
                    );

        } else {

            allCustomers =
                    customerRepository.findAllByBannedIsTrueAndDeletedIsFalse(
                            PageRequest.of( request.getPage(), request.getLimit() )
                    );

        }


        List<CustomerListDigestResponse> responseList =
                allCustomers
                        .stream()
                        .map( e ->
                                new CustomerListDigestResponse(
                                        e,
                                        addressRepository
                                                .findFirstByAccount_IdAndDeletedIsFalseOrderByIdDesc(
                                                        e.getId()
                                                )
                                )
                        )
                        .collect( Collectors.toList() );


        return new RequestResponseList<>( responseList, allCustomers.getTotalElements() );
    }

    public RequestResponseList searchCustomer(CustomerSearchRequest searchRequest) throws BehtaShopException {


        // TODO jdbcTemplate

        Page<CustomerEntity> searchResult = customerRepository.findAll(
                CustomerSpecification.searchByFirstNameAndLastNameAndMobile(
                        searchRequest.getFirstName(),
                        searchRequest.getLastName(),
                        searchRequest.getMobile(),
                        searchRequest.getStatus()
                ),
                PageRequest.of( searchRequest.getPage(), searchRequest.getLimit() )
        );


        List< CustomerListDigestResponse > responseList =
                searchResult
                        .stream()
                        .map( e ->
                                new CustomerListDigestResponse(
                                        e,
                                        addressRepository
                                                .findFirstByAccount_IdAndDeletedIsFalseOrderByIdDesc(
                                                        e.getId()
                                                )
                                )
                        )
                        .collect( Collectors.toList() );


        return new RequestResponseList<>( responseList, searchResult.getTotalElements() );
    }

    public EditCustomerRequest getCustomerDetail(int customerId ) throws BehtaShopException {

        CustomerEntity customer = customerRepository.findFirstByIdAndBannedIsFalseAndDeletedIsFalse( customerId );

        if ( customer == null ) {
            throw new BehtaShopException( "کاربر مورد نظر یافت نشد!" );
        }


        BalanceEntity balance = balanceRepository.findFirstByAccount_IdAndDeletedIsFalseOrderByIdDesc( customerId );

        if ( balance == null ) {
            throw new BehtaShopException( "اطلاعات مالی کاربر مورد نظر یافت نشد!" );
        }


        // Maybe add some other information here


        return new EditCustomerRequest(
                customer,
                balance
         );

    }

    public IdRequest editCustomer(EditCustomerRequest editRequest ) throws BehtaShopException {

        if ( StringUtil.isNullOrEmpty( editRequest.getFirstName() ) ||
                StringUtil.isNullOrEmpty( editRequest.getLastName() ) ||
                StringUtil.isNullOrEmpty( editRequest.getMobile() ) ||
                StringUtil.isNullOrEmpty( editRequest.getAccountingNumber() ) ){

            throw new BehtaShopException( "لطفا اطلاعات را کامل وارد کنید" );
        }


        CustomerEntity customer = customerRepository.findFirstByIdAndBannedIsFalseAndDeletedIsFalse( editRequest.getId() );

        if ( customer == null ) {
            throw new BehtaShopException( "کاربر مورد نظر یافت نشد!" );
        }


        BalanceEntity balance = balanceRepository.findFirstByAccount_IdAndDeletedIsFalseOrderByIdDesc( editRequest.getId() );

        if ( balance == null ) {
            throw new BehtaShopException( "اطلاعات مالی کاربر مورد نظر یافت نشد!" );
        }


        customer.setFirstName( editRequest.getFirstName() );
        customer.setLastName( editRequest.getLastName() );
        customer.setAccountingNumber( editRequest.getAccountingNumber() );
        customer.setMobile( editRequest.getMobile() );

        customer.setUpdateDate( new Date(  ) );

        balance.setAmount( editRequest.getAmount() );


        return new IdRequest( editRequest.getId() );
    }



    public IdRequest changeStatus(ChangeStatusRequest statusRequest) throws BehtaShopException {

        CustomerEntity customer =
                customerRepository.findFirstById( statusRequest.getId() );

        if ( customer == null ) {
            throw new BehtaShopException( "مشتری مورد نظر یافت نشد!" );
        }


        customer.setBanned(
                !customer.isBanned()
        );


        customerRepository.save( customer );

        return new IdRequest( customer.getId() );
    }

    public IdRequest delete(IdRequest customerId) throws BehtaShopException {
        return null;
    }
}
