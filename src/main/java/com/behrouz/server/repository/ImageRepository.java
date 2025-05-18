package com.behrouz.server.repository;

import com.behrouz.server.model.global.ImageEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 30 September 2018 12:35
 **/
@Repository
public interface ImageRepository extends BaseRepository<ImageEntity> {


    ImageEntity findFirstByIdAndDeletedIsFalse( long imageId );

    List<ImageEntity> findAllByIdInAndDeletedIsFalse(List<Long> imageIds);
}
