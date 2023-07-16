package com.ufrn.camisas.service;

import com.ufrn.camisas.domain.Envio;
import com.ufrn.camisas.domain.Pedido;
import com.ufrn.camisas.repository.IEnvioRepository;
import com.ufrn.camisas.repository.IPedidoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnvioService extends GenericService<Envio,IEnvioRepository>{

    IPedidoRepository pedidoRepository;
    public EnvioService (IEnvioRepository repository, IPedidoRepository pedidoRepository){
        super(repository);
        this.pedidoRepository = pedidoRepository;
    }

    public Envio preencherEnvio(String formaEnvio, String endereco, Long idPedido){
        Envio envio = new Envio();
        Optional<Pedido> pedido;

        envio.setFormaEnvio(formaEnvio);
        envio.setEndereco(endereco);

        pedido = this.pedidoRepository.findById(idPedido);
        if (pedido.isPresent()){
            envio.setPedido(pedido.get());
        }

        return envio;
    }

}

