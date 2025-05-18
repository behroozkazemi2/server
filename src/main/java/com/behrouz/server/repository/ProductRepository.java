package com.behrouz.server.repository;

import com.behrouz.server.model.product.ProductEntity;
import com.behrouz.server.repository.base.BaseNameRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends BaseNameRepository<ProductEntity> {

}
