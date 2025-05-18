package com.behrouz.server.repository;

import com.behrouz.server.model.global.CategoryEntity;
import com.behrouz.server.repository.base.BaseNameRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 23 September 2018 14:51
 **/
@Repository
public interface CategoryRepository extends BaseNameRepository<CategoryEntity> {

    CategoryEntity findFirstByIdAndDeletedIsFalse( long categoryId );
//    List<CategoryEntity> findAllByParentIsNullAndDeletedIsFalse();
//    List<CategoryEntity> findAllByParentIsNotNullAndDeletedIsFalse();
//    List<CategoryEntity> findAllByParent_IdAndDeletedIsFalseAndDeletedIsFalse( int parentId );

    List<CategoryEntity> findAllByIdInAndDeletedIsFalse( List<Long> categoryIds );
    List<CategoryEntity> findAllByIdInAndDeletedIsFalse( long categoryIds );

    List<CategoryEntity> findAllByDeletedIsFalse();

    List<CategoryEntity> findFirstByIdInAndDeletedIsFalse(List<Long> ids);

    List<CategoryEntity> findAllByParent_IdAndDeletedIsFalse(long id);

}

