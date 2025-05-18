package com.behrouz.server.component;

import com.behrouz.server.api.customer.request.ImageRequest;
import com.behrouz.server.model.global.ImageEntity;
import com.behrouz.server.model.product.ProductImageEntity;
import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.repository.ImageRepository;
import com.behrouz.server.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.component
 * Project Name: behta-server
 * 27 May 2020
 **/

@Component
public class ImageComponent {

    private Logger logger = Logger.getLogger( ImageComponent.class.getSimpleName() );

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductImageRepository productImageRepository;


    public byte[] getByteFromBufferedImage ( BufferedImage thumbnail ) {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try {
            ImageIO.write( thumbnail, "jpg", buffer );
            logger.warning( "jpg successfully sent" );
        } catch ( IOException e ) {
            try {
                ImageIO.write( thumbnail, "png", buffer );
                logger.warning( "png successfully sent" );
            } catch ( IOException e1 ) {
                try {
                    ImageIO.write( thumbnail, "jpeg", buffer );
                    logger.warning( "jpeg successfully sent" );
                } catch ( IOException e2 ) {
                    logger.warning( "Image is not jpeg" );
                    logger.warning( "Shit" );
                }
                logger.warning( "Image is not png" );
            }
            logger.warning( "Image is not jpg" );
        }


        return buffer.toByteArray();
    }

    public byte[] scale(byte[] fileData, int width, int height) throws Exception {

        if(true) {
            return fileData;
        }

        ByteArrayInputStream in = new ByteArrayInputStream( fileData );
        try {
            BufferedImage img = ImageIO.read( in );
            if ( height == 0 ) {
                height = (width * img.getHeight()) / img.getWidth();
            }
            if ( width == 0 ) {
                width = (height * img.getWidth()) / img.getHeight();
            }
            Image scaledImage = img.getScaledInstance( width, height, Image.SCALE_FAST );
            BufferedImage imageBuff = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
            imageBuff.getGraphics().drawImage( scaledImage, 0, 0, new Color( 255, 255, 255, 255 ), null );

            return getByteFromBufferedImage( imageBuff );
        } catch ( Exception e ) {
            logger.warning( "something went wrong" );
            return null;
        }
    }

    public byte[] resize ( byte[] image ) throws Exception {

        return scale( image, 400, 400 );

    }



    @Transactional(rollbackFor = {Exception.class})
    public void save (ImageRequest image, ProductProviderEntity productProvider ) {

        ImageEntity newImage = new ImageEntity( image.getImage() );

        imageRepository.save( newImage );

        saveToHard( image.getImage(), newImage.getId() );

        ProductImageEntity productProviderImage =
                new ProductImageEntity(
                        productProvider.getProduct(),
                        newImage
                );

        productImageRepository.save( productProviderImage );

    }


    @Transactional(rollbackFor = {Exception.class})
    public void saveToHard ( byte[] image, long id ) {
        try {

            String path = "/opt/java/xima_shop/images/original/";

            String filename = String.valueOf( id );

            ByteArrayInputStream bis = new ByteArrayInputStream( image );

            BufferedImage bImage2 = ImageIO.read( bis );

            ImageIO.write( bImage2, "jpg", new File( path + filename + ".jpg" ) );

        } catch ( Exception e ) {

            System.out.println( "ERROR " + id + " ERROR" );

        }


        saveToHardThumbnail( image, id );
    }

    private void saveToHardThumbnail ( byte[] image, long id ) {
        try {

            byte[] resizedImage = resize( image );

            String path = "/opt/java/xima_shop/images/thumbnail/";

            String filename = String.valueOf( id );

            ByteArrayInputStream bis = new ByteArrayInputStream( resizedImage );

            BufferedImage bImage2 = ImageIO.read(bis);

            ImageIO.write(bImage2, "jpg", new File(path + filename + ".jpg") );

            System.out.println( id + " done." );

        } catch ( Exception e ){

            System.out.println( "ERROR " + id + " ERROR" );

        }
    }


}