package com.ufrn.camisas.repository;

import org.springframework.data.repository.ListCrudRepository;
import com.ufrn.camisas.domain.AbstractEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IGenericRepository<E extends AbstractEntity> extends ListCrudRepository<E, Long>, PagingAndSortingRepository<E, Long> {
}