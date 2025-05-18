package com.behrouz.server.api.customer;

import com.behrouz.server.api.BaseApi;
import com.behrouz.server.api.customer.response.ImageResponse;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionWrongDataException;
import com.behrouz.server.model.global.ImageEntity;
import com.behrouz.server.repository.ImageRepository;
import com.behrouz.server.rest.constant.IdRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.customer
 * Project server
 * 30 September 2018 13:05
 **/

/**
 *
 *  1 ) Get Image -> get image for list
 *  {@link #getImage(IdRequest)}
 *
 */
public class ImageApi extends BaseApi {


    @Autowired
    private ImageRepository imageRepository;



    @ApiAction( value = "app.customer.image.get", tokenRequired = false )
    public ApiResponseBody getImage( IdRequest request ) throws ApiActionException {

        ImageEntity image = imageRepository.findFirstById( request.getId() );

        if ( image == null || image.getImage() == null ) {
            throw new ApiActionWrongDataException(
                    "عکس یافت نشد!"
            );
        }

        return new ApiResponseBody<>().ok(
                new ImageResponse(
                        image.getImage()
                )
        );
    }




}
