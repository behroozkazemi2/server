package com.behrouz.server.repository;

import com.behrouz.server.model.global.BannerEntity;
import com.behrouz.server.repository.base.BaseNameRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BannerRepository extends BaseNameRepository<BannerEntity> {

    List<BannerEntity> findAllByBannerType_IdAndDeletedIsFalse(long id);

    List<BannerEntity> findAllByBannerType_IdAndDeletedIsFalseOrderById(long type);
}
