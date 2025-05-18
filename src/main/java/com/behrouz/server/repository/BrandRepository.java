package com.behrouz.server.repository;

import com.behrouz.server.model.brand.BrandEntity;
import com.behrouz.server.repository.base.BaseNameRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BrandRepository extends BaseNameRepository<BrandEntity> {

}
