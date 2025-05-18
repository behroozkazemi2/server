package com.behrouz.server.repository.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;


@NoRepositoryBean
public interface BaseNameRepository<T> extends BaseRepository<T> {

    Page<T> findAllByNameContainingIgnoreCaseAndDeletedIsFalse(String name, Pageable pageable);

    List<T> findAllByNameContainingIgnoreCaseAndDeletedIsFalse(String name);

}
