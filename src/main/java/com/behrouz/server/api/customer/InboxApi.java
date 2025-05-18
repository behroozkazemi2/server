package com.behrouz.server.api.customer;

import com.behrouz.server.api.BaseApi;
import com.behrouz.server.api.customer.response.InboxMessageResponse;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.model.MessageEntity;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer
 * Project Name: behta-server
 * 19 January 2019
 **/
public class InboxApi extends BaseApi {


    @Autowired
    private MessageRepository messageRepository;



    @ApiAction( value = "app.customer.inbox.list" )
    public ApiResponseBody list ( int customerId ) throws ApiActionException {

        CustomerEntity customer = getCustomerById( customerId );


        List < MessageEntity > messages =
                messageRepository.findAllByCustomer_IdAndDeletedIsFalse( customerId );


        return new ApiResponseBody<>().ok(
                messages.stream()
                        .map( InboxMessageResponse::new )
                        .collect( Collectors.toList())
        );
    }


}
