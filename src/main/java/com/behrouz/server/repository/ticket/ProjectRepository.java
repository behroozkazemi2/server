package com.behrouz.server.repository.ticket;



import com.behrouz.server.model.ticket.project.ProjectEntity;
import com.behrouz.server.repository.base.BaseNameRepository;

import java.util.List;

public interface ProjectRepository extends BaseNameRepository<ProjectEntity> {

    ProjectEntity findFirstByNameAndAndDeletedIsFalse(String name);
    ProjectEntity findByNameAndIdIsNotAndDeletedIsFalse(String name, long Id);

    List<ProjectEntity> findAllByNameLikeAndDeletedIsFalse(String name);

}


