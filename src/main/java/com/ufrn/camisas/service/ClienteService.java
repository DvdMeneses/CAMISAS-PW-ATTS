package com.ufrn.camisas.service;
import com.ufrn.camisas.domain.Produto;
import com.ufrn.camisas.repository.IClienteRepository;
import com.ufrn.camisas.domain.Cliente;
import com.ufrn.camisas.repository.IProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService extends GenericService<Cliente, IClienteRepository>{

    IProdutoRepository produtoRepository;
    public ClienteService (IClienteRepository repository, IProdutoRepository produtoRepository){
        super(repository);
        this.produtoRepository = produtoRepository;
    }
}
