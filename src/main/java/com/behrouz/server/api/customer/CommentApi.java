package com.behrouz.server.api.customer;

import com.behrouz.server.api.BaseApi;
import com.behrouz.server.api.customer.request.CommentListRequest;
import com.behrouz.server.api.customer.request.CommentRequest;
import com.behrouz.server.api.customer.response.CommentResponse;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionWrongDataException;
import com.behrouz.server.component.CommentComponent;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.model.account.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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


    @ApiAction(value = "app.customer.comment.add", tokenRequired = false)
    public ApiResponseBody add(CommentRequest request, int customerId) throws ApiActionException {

        CustomerEntity customer =
                getCustomerById(customerId);

        if (request == null) {
            throw new ApiActionWrongDataException("اطلاعات وارد شده صحیح نمیباشد. لطفا دوباره تلاش کنید.");
        }

//        if(request.getProductId() == request.getProviderId()){
//            throw new ApiActionWrongDataException( "اطلاعات وارد شده صحیح نمیباشد. لطفا دوباره تلاش کنید." );
//        }

        try {
            commentComponent.addComment(request, customer);
        } catch (BehtaShopException e) {
            e.printStackTrace();
            return new ApiResponseBody().error(e.getDescription());
        }


        return new ApiResponseBody().ok();
    }

    @ApiAction(value = "app.customer.comment.list", tokenRequired = false)
    public ApiResponseBody list(CommentListRequest request) throws ApiActionException {
        if (request == null || (request.getProviderId() == 0 && request.getProductId() == 0)) {
            throw new ApiActionWrongDataException("اطلاعات وارد شده صحیح نمیباشد. لطفا دوباره تلاش کنید.");
        }

        List<CommentResponse> list =
                commentComponent.getCommentListForWeb(request);

        return new ApiResponseBody().ok(list, list.size());
    }

}