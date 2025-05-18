package com.behrouz.server.api.provider;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.behrouz.server.api.customer.request.ImageRequest;
import com.behrouz.server.api.customer.request.ImageTypeRequest;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.annotation.ApiActionParam;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionNotFoundException;
import com.behrouz.server.component.ImageComponent;
import com.behrouz.server.model.global.ImageEntity;
import com.behrouz.server.repository.ImageRepository;
import com.behrouz.server.rest.request.IdName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProviderImageApi extends ProviderBaseApi {


    @Autowired
    private ImageComponent imageComponent;

    @Autowired
    private ImageRepository imageRepository;

    @ApiAction("app.image.upload")
    public ApiResponseBody<IdName> upload(@ApiActionParam(nullable = false) ImageRequest imageRequest, int userId){

        ImageEntity image = new ImageEntity(imageRequest.getImage());
        imageRepository.save(image);


        return new ApiResponseBody<>().ok(new IdName(image.getId()));

    }

    @ApiAction("app.provider.image.uploads")
    public ApiResponseBody<List<IdName>> uploads(@ApiActionParam(nullable = false) List<ImageRequest> imageRequests, int userId){


        try {
            ObjectMapper mapper = new ObjectMapper();
            imageRequests = mapper.readValue(mapper.writeValueAsString(imageRequests) , TypeFactory.defaultInstance().constructCollectionLikeType(List.class, ImageRequest.class));
        } catch (IOException e) {
            e.printStackTrace();
        }


        List<ImageEntity> images = imageRequests.stream().map(e -> new ImageEntity(e.getImage())).collect(Collectors.toList());
        imageRepository.saveAll(images);

        List<IdName> response = images.stream().map(m -> new IdName(m.getId())).collect(Collectors.toList());


        return new ApiResponseBody<>().ok(response);

    }

    @ApiAction(value = "app.provider.image.get", tokenRequired = false)
    public ApiResponseBody<ImageRequest> get( ImageTypeRequest imageRequest) throws ApiActionException {

        ApiResponseBody<ImageRequest> responseBody = null;
        byte[] thumbnail = null;

        long start = System.currentTimeMillis();

        ImageEntity image = imageRepository.findFirstById(imageRequest.getId());

        if (image == null) {
            responseBody = notFoundImage();
        }else if(image.getImage() == null){
            try {
                String path = "/opt/java/project/xima_images/original/";
                String filename = String.valueOf( image.getId() ) + ".jpg";
                if(new File(filename).exists()) {
                    BufferedImage mImage = ImageIO.read(new File(path + filename + ".jpg"));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write( mImage, "jpg", baos );
                    baos.flush();
                    thumbnail = baos.toByteArray();
                    baos.close();

                }else {
                    responseBody = notFoundImage();
                }
            } catch ( Exception e ){

                System.out.println( "ERROR " + image.getId() + " ERROR" );
                throw new ApiActionNotFoundException("پیدا نشد.");
            }
        }else {
            thumbnail = image.getImage();
        }
        if(responseBody == null) {
            try {

                switch (imageRequest.getType()) {
                    case 0:
                        thumbnail = imageComponent.scale(thumbnail, 100, 100);
                        break;
                    case 1:
                        thumbnail = imageComponent.scale(thumbnail, 400, 400);
                        break;
                    case 2:
                        thumbnail = imageComponent.scale(thumbnail, 600, 600);
                        break;
                    case 3:
                        thumbnail = imageComponent.scale(thumbnail, 800, 800);
                        break;
                    default:
                        thumbnail = imageComponent.scale(thumbnail, 1000, 1000);
                }


            } catch (Exception e) {
                e.printStackTrace();
                responseBody = notFoundImage();

            }
        }


        long end = System.currentTimeMillis();
        logger.warning("Thumbnail-type: " + imageRequest.getType() + imageRequest.getId() + " -> " + (end - start) + "\n");

        if(responseBody == null){
            responseBody = new ApiResponseBody<>().ok(new ImageRequest(imageRequest.getId() , thumbnail));
        }

        return responseBody;
    }

    private ApiResponseBody<ImageRequest> notFoundImage() throws ApiActionException {
        byte[] img = null;
        String pathNotFound = "/opt/java/project/xima_images/original/not_found.png";
        try {
            if (new File(pathNotFound).exists()) {
                BufferedImage mImage = ImageIO.read(new File(pathNotFound));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(mImage, "png", baos);
                baos.flush();
                img = baos.toByteArray();
                baos.close();

            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiActionNotFoundException();
        }

        return new ApiResponseBody<>().ok(new ImageRequest(0 , img));
    }


    @RequestMapping(
            value = "/files/{type}/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public void images (@PathVariable(name = "type") String type,
                        @PathVariable("id") int id,
                        HttpServletResponse servletResponse){

        if(!"original".equalsIgnoreCase(type) && !"thumbnail".equalsIgnoreCase(type)){
            type = "thumbnail";
        }


        try {
            ApiResponseBody<ImageRequest> resp =
                    get(new ImageTypeRequest(
                            id,
                            "original".equalsIgnoreCase(type) ? 100 : 3
                        )
                    );

            if(resp.successful()) {
                servletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
                StreamUtils.copy(resp.getData().getImage(), servletResponse.getOutputStream());
            }
        } catch (ApiActionException | IOException e) {
            e.printStackTrace();
        }


    }

}
