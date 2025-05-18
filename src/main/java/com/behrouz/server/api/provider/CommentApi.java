package com.behrouz.server.api.provider;

import com.behrouz.server.api.BaseApi;
import com.behrouz.server.api.customer.request.CommentListRequest;
import com.behrouz.server.api.customer.response.CommentResponseList;
import com.behrouz.server.api.provider.request.CommentStatusRequest;
import com.behrouz.server.api.provider.response.ListResponse;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionWrongDataException;
import com.behrouz.server.component.CommentComponent;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.model.account.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer
 * Project Name: behta-server
 * 13 December 2018
 **/
public class CommentApi extends BaseApi {


    @Autowired
    private CommentComponent commentComponent;

    @ApiAction(value = "app.provider.comment.list")
    public ApiResponseBody getList(CommentListRequest request) throws ApiActionException {

        if (request == null) {
            throw new ApiActionWrongDataException("اطلاعات وارد شده صحیح نمیباشد. لطفا دوباره تلاش کنید.");
        }

        ListResponse<CommentResponseList> list =
                commentComponent.getCommentListForPanel(request);

        return new ApiResponseBody().ok(list);
    }

    @ApiAction(value = "app.provider.comment.changeStatus")
    public ApiResponseBody add(CommentStatusRequest request, int customerId) throws ApiActionException {

        AccountEntity customer =
                getAccountById(customerId);

        if (request == null) {
            throw new ApiActionWrongDataException("اطلاعات وارد شده صحیح نمیباشد. لطفا دوباره تلاش کنید.");
        }

        try {
            commentComponent.changeCommentStatus(request, customer);
        } catch (BehtaShopException e) {
            e.printStackTrace();
            return new ApiResponseBody().error(e.getDescription());
        }


        return new ApiResponseBody().ok();
    }

}