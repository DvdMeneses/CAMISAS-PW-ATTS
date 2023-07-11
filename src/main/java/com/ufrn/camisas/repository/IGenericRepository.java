package com.ufrn.camisas.repository;

import com.ufrn.camisas.domain.AbstractEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface IGenericRepository<E extends AbstractEntity> extends ListCrudRepository<E, Long> {

} 