package com.behrouz.server.repository;

import com.behrouz.server.model.SmsHistoryEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 17 September 2018 10:31
 **/
@Repository
public interface SmsHistoryRepository extends BaseRepository<SmsHistoryEntity> {

    @Query(
            nativeQuery = true ,
            value = "SELECT * FROM sms_history ssh " +
                    "WHERE ssh.mobile = :mobileNumber " +
                    "AND ssh.sent_date > now() - interval '1 day' "
    )
    List< SmsHistoryEntity > findAllByMobileNumberAndInsertDateIsToday(
            @Param(("mobileNumber")) String mobileNumber
    );


    @Query(
            nativeQuery = true ,
            value = "SELECT COUNT (*) > 0 FROM sms_history ssh " +
                    "WHERE ssh.mobile = :mobile " +
                    "AND ssh.sent_date > now() - interval '2 minutes' "
    )
    boolean findAllRecentRequestForMobile(
            @Param( "mobile" ) String mobile
    );


}
