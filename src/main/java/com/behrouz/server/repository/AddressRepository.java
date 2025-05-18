package com.behrouz.server.repository;

import com.behrouz.server.model.global.AddressEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 23 September 2018 09:14
 **/
@Repository
public interface AddressRepository extends BaseRepository<AddressEntity> {
    AddressEntity findFirstByAccount_IdAndDeletedIsFalseOrderByIdDesc ( long accountId );
    List<AddressEntity> findAllByAccount_IdAndDeletedIsFalse ( long customerId );
    AddressEntity findFirstByIdAndDeletedIsFalse(long addressId);

    AddressEntity findFirstByAccount_IdAndDeletedIsFalse(long id);

    AddressEntity findFirstByAccount_IdAndIdAndDeletedIsFalse(long addressId, long accountId);

    AddressEntity findFirstByTitleLikeAndDeletedIsFalse(String title);

    AddressEntity findFirstByAccount_IdAndTitleLikeAndDeletedIsFalse(long customerId, String title);
    AddressEntity findFirstByAccount_IdAndTitleAndDeletedIsFalse(long customerId, String title);
}
