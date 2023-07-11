package com.ufrn.camisas.service;
import com.ufrn.camisas.repository.IClienteRepository;
import com.ufrn.camisas.domain.Cliente;
import org.springframework.stereotype.Service;

@Service
public class ClienteService extends GenericService<Cliente, IClienteRepository>{

    public ClienteService (IClienteRepository repository){super(repository);}

}
