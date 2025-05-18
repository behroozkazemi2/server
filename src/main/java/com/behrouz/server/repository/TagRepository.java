package com.behrouz.server.repository;

import com.behrouz.server.model.product.TagEntity;
import com.behrouz.server.repository.base.BaseNameRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TagRepository extends BaseNameRepository<TagEntity> {

}
