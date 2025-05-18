package com.behrouz.server.repository.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;


@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long> {

    T findFirstById(long id);

    T findFirstByIdAndDeletedIsFalse(long id);

    List<T> findAllByDeletedIsFalse();

    Page<T> findAllByDeletedIsFalse(Pageable pageable);

    List<T> findAllByIdInAndDeletedIsFalse(List<Long> ids);

}
