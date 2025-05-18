package com.behrouz.server.repository.bill;

import com.behrouz.server.model.bill.BillStatusEntity;
import com.behrouz.server.repository.base.BaseNameRepository;

import java.util.List;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.repository
 * Project Name: behta-server
 * 04 December 2018
 **/
public interface BillStatusRepository extends BaseNameRepository<BillStatusEntity> {

    BillStatusEntity findFirstByIdAndDeletedIsFalse ( long id );

    List<BillStatusEntity> findAllByIdInAndDeletedIsFalseOrderById(List<Long> asList);
}
