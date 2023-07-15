package com.ufrn.camisas.service;


import com.ufrn.camisas.domain.Produto;
import com.ufrn.camisas.repository.IProdutoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService extends GenericService<Produto, IProdutoRepository> {
    public ProdutoService(IProdutoRepository repository) {
        super(repository);


    }


}