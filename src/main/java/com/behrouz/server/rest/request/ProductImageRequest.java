package com.behrouz.server.rest.request;

import com.behrouz.server.model.global.ImageEntity;
import com.behrouz.server.utils.thymeleaf.ThymeleafBase64Model;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.request
 * Project Koala Server
 * 10 September 2018 12:32
 **/
public class ProductImageRequest implements ThymeleafBase64Model {

    private long id; // age mikhad Edit kone, sikh nazan

    private long imageOrder;

    private byte[] image;

    private MultipartFile imageFile;

    public ProductImageRequest() {
    }

    public ProductImageRequest (ImageEntity image, long imageOrder ) {

        if ( image != null ) {

            this.id = image.getId();
            this.image = image.getImage();

        }

        this.imageOrder = imageOrder;

    }


    public long getId() {
        return id;
    }
    public void setId( long id ) {
        this.id = id;
    }


    public long getImageOrder() {
        return imageOrder;
    }
    public void setImageOrder( long imageOrder ) {
        this.imageOrder = imageOrder;
    }


    public byte[] getImage() {
        return image;
    }
    public void setImage( byte[] image ) {
        this.image = image;
    }


    public MultipartFile getImageFile() {
        return imageFile;
    }
    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }


    @Override
    public String toString () {
        return "ProductImageRequest{" +
                "id=" + id +
                ", imageOrder=" + imageOrder +
                ", image=" + (image == null ? "Empty" : "Filled") +
                '}';
    }

}
