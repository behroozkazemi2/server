package com.behrouz.server.api.provider;

import com.behrouz.server.api.BaseApi;
import com.behrouz.server.api.provider.response.ListResponse;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.HttpCode;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionWrongDataException;
import com.behrouz.server.component.TicketComponent;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.model.account.OperatorEntity;
import com.behrouz.server.model.account.ProviderEntity;
import com.behrouz.server.rest.constant.DataTableResponse;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.request.*;
import com.behrouz.server.rest.request.*;
import com.behrouz.server.rest.response.TicketDetailRestResponse;
import com.behrouz.server.rest.response.TicketMessageRestResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TicketApi extends BaseApi {


    @Autowired
    private TicketComponent ticketComponent;


    @ApiAction(value = "app.provider.ticket.list")
    public ApiResponseBody<ListResponse<TicketDetailRestResponse>> ticketList(TicketListRestRequest search, int providerId) throws ApiActionException {

        DataTableResponse<TicketDetailRestResponse> ticketList =
                ticketComponent.getTicketList(
                        search,
                        providerId
                );
        return new ApiResponseBody<>().ok(new ListResponse (ticketList.getData(), ticketList.getRecordsFiltered()));
    }

    @ApiAction(value = "app.provider.ticket.add")
    public ApiResponseBody<IdLong> saveOrEditTicket(SaveTicketRestRequest saveTicketRestRequest, int providerId) throws ApiActionException {
        String item = "";
        try {
            AccountEntity provider =
                    getAccountById(providerId);
            item = ticketComponent.saveOrEditTicket(saveTicketRestRequest, provider);
        } catch (BehtaShopException e) {
            throw new ApiActionWrongDataException(e.getDescription());
        }
        return new ApiResponseBody<>().ok(item);
    }

    @ApiAction(value = "app.provider.ticket.detail")
    public ApiResponseBody<TicketDetailRestResponse> ticketDetail(String trackingCode, int providerId) throws ApiActionException {
        AccountEntity provider =
                getAccountById(providerId);

        TicketDetailRestResponse item =
                ticketComponent.getTicketDetail(trackingCode);

        if (!(provider instanceof OperatorEntity) && !(provider instanceof ProviderEntity) )
            throw new ApiActionException(
                    HttpCode.INTERNAL_SERVER_ERROR,
                    "خطا در یافتن تیکت"
            );

        return new ApiResponseBody<>().ok(item);
    }

    @ApiAction(value = "app.provider.ticket.messages")
    public ApiResponseBody<List<TicketDetailRestResponse>> ticketMessageList(TicketMessageRequest ticketMessageRequest, int providerId) throws ApiActionException {

        AccountEntity provider =
                getAccountById(providerId);

        List<TicketMessageRestResponse> item =
                ticketComponent.getTicketMessages(ticketMessageRequest.getTicketId(), ticketMessageRequest.isLastMessage(), provider);

        return new ApiResponseBody<>().ok(item);
    }

    @ApiAction(value = "app.provider.ticket.close")
    public ApiResponseBody<Void> closeTicket(IdRequest ticketId, int providerId) throws ApiActionException {
        AccountEntity provider =
                getAccountById(providerId);
        try {
            ticketComponent.closeTicket(ticketId.getId(), provider);
        } catch (BehtaShopException e) {
            throw new RuntimeException(e.getDescription());
        }
        return new ApiResponseBody<>().ok();
    }

    @ApiAction(value = "app.provider.ticket.add.message")
    public ApiResponseBody<Void> addTicketMessage(SaveTicketMessageRestRequest request, int providerId) throws ApiActionException {
        AccountEntity provider =
                getAccountById(providerId);
        try {
            ticketComponent.addNewTicketMessage(request, provider);
        } catch (BehtaShopException e) {
            throw new RuntimeException(e.getDescription());
        }
        return new ApiResponseBody<>().ok();
    }

}
