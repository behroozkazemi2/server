package com.behrouz.server.component;

import com.behrouz.server.api.customer.response.SuggestionResponse;
import com.behrouz.server.model.global.ImageEntity;
import com.behrouz.server.repository.ImageRepository;
import com.behrouz.server.repository.PromoteProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by: HapiKZM
 * Project Name: behta-server
 * 09 July 2020
 **/
@Component
@Transactional
public class SuggestionComponent {

    @Autowired
    private PromoteProductRepository promoteProductRepository;

    @Autowired
    private ImageComponent imageComponent;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    public void addNewSuggestionProduct(){
        // TODO: 7/9/20
    }

    public void deleteSuggestionProduct(){
        // TODO: 7/9/20
    }


    public List<SuggestionResponse> getListOfSuggestionProduct(){
        // TODO: 7/9/20 redis for view

        // TODO HASSAN :: Jdbc -> promoteProduct_Product_Image

//        List<SuggestionResponse> list = promoteProductRepository.findAllByPromote_IdAndProductProvider_DeletedIsFalse(
//                PromoteOption.Special.getId()
//        ).stream().map( m -> m.getProductProvider()).collect(Collectors.toList());
//       return list.stream();

return null;
    }


    private ImageEntity saveImage(MultipartFile imageFile ) {

        ImageEntity image = null;

        try {
            image = new ImageEntity(imageFile.getBytes());

            imageRepository.save( image );

            imageComponent.saveToHard( imageFile.getBytes(), image.getId() );

        } catch (IOException e) {

            e.printStackTrace();
        }


        return image;
    }

}
