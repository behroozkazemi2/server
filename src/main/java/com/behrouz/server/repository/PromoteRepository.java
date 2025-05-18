package com.behrouz.server.repository;

import com.behrouz.server.model.product.PromoteEntity;
import com.behrouz.server.repository.base.BaseNameRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PromoteRepository extends BaseNameRepository<PromoteEntity> {

    PromoteEntity findFirstByDeletedIsFalse();

    List<PromoteEntity> findAllByDeletedIsFalseOrderByIdDesc();

    PromoteEntity findFirstByDeletedIsFalseOrderById();
}
