package com.ufrn.camisas.service;

import com.ufrn.camisas.repository.IPedidoRepository;
import org.springframework.stereotype.Service;
import com.ufrn.camisas.domain.Pedido;

@Service
public class PedidoService extends GenericService<Pedido,IPedidoRepository>{

    public PedidoService(IPedidoRepository repository) {
        super(repository);
    }

}
