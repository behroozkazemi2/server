package com.behrouz.server.api.customer;

import com.behrouz.server.api.BaseApi;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.HttpCode;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionWrongDataException;
import com.behrouz.server.component.TicketComponent;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.rest.constant.DataTableResponse;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.request.IdLong;
import com.behrouz.server.rest.request.SaveTicketMessageRestRequest;
import com.behrouz.server.rest.request.SaveTicketRestRequest;
import com.behrouz.server.rest.request.TicketMessageRequest;
import com.behrouz.server.rest.response.TicketDetailRestResponse;
import com.behrouz.server.rest.response.TicketMessageRestResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class TicketApi extends BaseApi {


    @Autowired
    private TicketComponent ticketComponent;


    @ApiAction(value = "app.customer.ticket.list")
    public ApiResponseBody<List<TicketDetailRestResponse>> ticketList(String search, int customerId) throws ApiActionException {

        DataTableResponse<TicketDetailRestResponse> ticketList =
                ticketComponent.getTicketList(
                        1,
                        10000,
                        1,
                        0,
                        search,
                        "",
                        "",
                        0,
                        0,
                        0,
                        0,
                        customerId,
                        false
                );

        return new ApiResponseBody<>().ok(ticketList.getData());
    }

    @ApiAction(value = "app.customer.ticket.add")
    public ApiResponseBody<IdLong> saveOrEditTicket(SaveTicketRestRequest saveTicketRestRequest, int customerId) throws ApiActionException {
        String item = "";
        try {
            CustomerEntity customer =
                    getCustomerById(customerId);

            item = ticketComponent.saveOrEditTicket(saveTicketRestRequest, customer);
        } catch (BehtaShopException e) {
            throw new ApiActionWrongDataException(e.getDescription());
        }
        return new ApiResponseBody<>().ok(item);
    }

    @ApiAction(value = "app.customer.ticket.detail")
    public ApiResponseBody<TicketDetailRestResponse> ticketDetail(String trackingCode, int customerId) throws ApiActionException {
        CustomerEntity customer = getCustomerById(customerId);

        TicketDetailRestResponse item =
                ticketComponent.getTicketDetail(trackingCode);

        if (customer.getId() != item.getAccountId())
            throw new ApiActionException(
                    HttpCode.INTERNAL_SERVER_ERROR,
                    "خطا در یافتن تیکت"
            );

        return new ApiResponseBody<>().ok(item);
    }

    @ApiAction(value = "app.customer.ticket.messages")
    public ApiResponseBody<List<TicketDetailRestResponse>> ticketMessageList(TicketMessageRequest ticketMessageRequest, int customerId) throws ApiActionException {

        CustomerEntity customer =
                getCustomerById(customerId);

        List<TicketMessageRestResponse> item =
                ticketComponent.getTicketMessages(ticketMessageRequest.getTicketId(), ticketMessageRequest.isLastMessage(), customer);

        return new ApiResponseBody<>().ok(item);
    }

    @ApiAction(value = "app.customer.ticket.close")
    public ApiResponseBody<Void> closeTicket(IdRequest ticketId, int customerId) throws ApiActionException {
        CustomerEntity customer =
                getCustomerById(customerId);
        try {
            ticketComponent.closeTicket(ticketId.getId(), customer);
        } catch (BehtaShopException e) {
            throw new RuntimeException(e.getDescription());
        }
        return new ApiResponseBody<>().ok();
    }

    @ApiAction(value = "app.customer.ticket.add.message")
    public ApiResponseBody<Void> addTicketMessage(SaveTicketMessageRestRequest request, int customerId) throws ApiActionException {
        CustomerEntity customer =
                getCustomerById(customerId);
        try {
            ticketComponent.addNewTicketMessage(request, customer);
        } catch (BehtaShopException e) {
            throw new RuntimeException(e.getDescription());
        }
        return new ApiResponseBody<>().ok();
    }

}
