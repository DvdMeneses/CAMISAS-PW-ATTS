package com.ufrn.camisas.service;

import com.ufrn.camisas.repository.IPedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.ufrn.camisas.domain.Pedido;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class PedidoService extends GenericService<Pedido, IPedidoRepository> {

    private final ModelMapper mapper;

    public PedidoService(IPedidoRepository repository, ModelMapper mapper) {
        super(repository);
        this.mapper = mapper;
    }

    public List<Pedido.DtoResponse> listPedidos() {
        return repository.findAll().stream()
                .map(pedido -> {
                    Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto(pedido, mapper);
                    response.generateLinks(pedido.getId());
                    return response;
                })
                .collect(Collectors.toList());
    }
}
