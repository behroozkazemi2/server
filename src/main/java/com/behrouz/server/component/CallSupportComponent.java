package com.behrouz.server.component;

import com.behrouz.server.model.CallSupportEntity;
import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.rest.constant.DataTableRequest;
import com.behrouz.server.rest.constant.RequestResponseList;
import com.behrouz.server.rest.request.BillSearchRequest;
import com.behrouz.server.rest.response.CallSupportResponse;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.repository.CallSupportRepository;
import com.behrouz.server.repository.CustomerRepository;
import com.behrouz.server.specification.CallSupportSpecification;
import com.behrouz.server.specification.CustomerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.component
 * Project Name: behta-server
 * 18 December 2018
 **/

@Component
public class CallSupportComponent {


    @Autowired
    private CallSupportRepository callSupportRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;



    public RequestResponseList getByStatus(DataTableRequest request ) throws BehtaShopException {

        // TODO STATUSE
//        Page<CallSupportEntity > callSupports =
//                callSupportRepository.findAllByStatus_IdAndDeletedISFalse(
//                        request.getStatus(),
//                        PageRequest.of( request.getPage(), request.getLimit() )
//                );
//        return getList( callSupports );
        return  null;
    }


    public RequestResponseList getBySearch( BillSearchRequest searchRequest ) throws BehtaShopException {


        // TODO jdbcTemplate
        List <CustomerEntity> customers = customerRepository.findAll(
                CustomerSpecification.searchByFirstNameAndLastNameAndMobile(
                        searchRequest.getFirstName(),
                        searchRequest.getLastName(),
                        searchRequest.getMobile(),
                        searchRequest.getStatus() )
        );

        List< Long > customerIds =
                customers.stream().map( AccountEntity::getId ).collect( Collectors.toList() );

        // TODO jdbcTemplate

        Page<CallSupportEntity> searchResult = callSupportRepository.findAll(
                CallSupportSpecification.searchByTrackingCodeAndInsertDateBetweenAndCustomerIds(
                        searchRequest.getTrackingCode(),
                        searchRequest.getFromDate(),
                        searchRequest.getToDate(),
                        searchRequest.getStatus(),
                        customerIds
                ),
                PageRequest.of( searchRequest.getPage(), searchRequest.getLimit() )
        );


        return getList( searchResult );


    }


    private RequestResponseList getList ( Page < CallSupportEntity > callSupports ) {

        return new RequestResponseList<>(
                callSupports.getContent()
                        .stream()
                        .map( e -> new CallSupportResponse( e ) )
                        .collect(Collectors.toList()),

                callSupports.getTotalElements()
        );

    }


    private void changeStatus ( int callSupportId , int newStatusId ) throws BehtaShopException {

        CallSupportEntity callSupport = callSupportRepository.findFirstById( callSupportId );

        if ( callSupport == null ) {
            throw new BehtaShopException( "پیام مورد نظر یافت نشد!" );
        }


//        CallSupportStatusEntity status = callSupportStatusRepository.findFirstById( newStatusId );
//
//        if ( status == null ) {
//            throw new HashtagMarketException( "وضعییت مورد نظر یافت نشد!" );
//        }
//
//
//        callSupport.setStatus( status );


        callSupportRepository.save( callSupport );
    }

    public CallSupportEntity getSupportDetail(int supportId) throws BehtaShopException {

        CallSupportEntity callSupport = callSupportRepository.findFirstById(supportId);

        if( callSupport == null ) {
            throw new BehtaShopException( "پیام مورد نظر یافت نشد." );
        }

        return callSupport;

    }

}
