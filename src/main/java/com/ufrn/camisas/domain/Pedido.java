package com.ufrn.camisas.domain;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.ufrn.camisas.controller.EnvioController;
import com.ufrn.camisas.domain.Cliente;
import com.ufrn.camisas.domain.Produto;
import com.ufrn.camisas.controller.PedidoController;
import com.ufrn.camisas.service.ClienteService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLSelect;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Pedido extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
     Cliente cliente;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "pedido_produtos",
            joinColumns = @JoinColumn(name = "id_pedido", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_produto", referencedColumnName = "id")
    )
    List<Produto> produtos;

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Pedido pedido){
            if(pedido.cliente.cpf == null || pedido.produtos == null){
                throw new RuntimeException("CAMPO NULL INVALIDO");
            }
            this.cliente = pedido.cliente;
            this.produtos = pedido.produtos;
        }
    }

    @Data
    public static class DtoRequest {
        Cliente cliente;
        ArrayList<Long> id_produtos;
        public static Pedido convertToEntity(DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Pedido.class);
        }
    }
    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse>{
        Cliente cliente;
        List<Produto> produtos;

        public static Pedido.DtoResponse convertToDto(Pedido p, ModelMapper mapper){
            return mapper.map(p, DtoResponse.class);
        }

        public void generateLinks(Long id){
            add(linkTo(PedidoController.class).slash(id).withSelfRel());
            add(linkTo(PedidoController.class).withRel("pedido"));
            add(linkTo(PedidoController.class).slash(id).withRel("delete"));
        }
    }
}