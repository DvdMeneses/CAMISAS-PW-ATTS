package com.ufrn.camisas.service;

import com.ufrn.camisas.domain.AbstractEntity;
import com.ufrn.camisas.repository.IGenericRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public abstract class GenericService<E extends AbstractEntity, R extends IGenericRepository> implements IGenericService<E>{

    R repository;


    public GenericService(R repository) {
        this.repository = repository;
    }

    @Override
    public E getById(Long id) {
        Optional<E> pessoaBanco = repository.findById(id);
        if (pessoaBanco.isPresent()){
            return (E) pessoaBanco.get();
        }else{
            throw  new EntityNotFoundException();
        }
    }

    @Override
    public E create(E e) {
        return (E) this.repository.save(e);
    }

    @Override
    public E update(E entityParaAtualizar, Long id) {
        Optional<E> pessoaBanco = repository.findById(id);

        if (pessoaBanco.isPresent()){

            E entity = pessoaBanco.get();

            entity.partialUpdate(entityParaAtualizar);

            return (E) this.repository.save(entity);
        }else{
            throw  new EntityNotFoundException();
        }
    }

    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public List<E> list() {
        return (List<E>) this.repository.findAll();
    }
}
