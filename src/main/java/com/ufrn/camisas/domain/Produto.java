package com.ufrn.camisas.domain;


import java.util.List;

import com.ufrn.camisas.controller.ProdutoController;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Produto extends AbstractEntity {

    private String nomeProduto;
    private int precoProduto;

    @ManyToMany(mappedBy = "produtos") // Correção: alterado o valor de 'mappedBy' para 'produtos'
    private List<Pedido> pedidos;

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Produto produto){
            this.nomeProduto = produto.nomeProduto;
            this.precoProduto = produto.precoProduto;
            this.pedidos = produto.pedidos;
        }
    }

    @Data
    public static class DtoRequest {
        @NotBlank(message = "Produto com nome em branco")
        String nomeProduto;
        @NotBlank(message = "Produto com preco vazio")
        int precoProduto;

        public static Produto convertToEntity(DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Produto.class);
        }
    }

    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
        String nomeProduto;
        int precoProduto;

        public static DtoResponse convertToDto(Produto p, ModelMapper mapper) {
            return mapper.map(p, DtoResponse.class);
        }

        public void generateLinks(Long id){
            add(linkTo(ProdutoController.class).slash(id).withSelfRel());
            add(linkTo(ProdutoController.class).withRel("produtos"));
            add(linkTo(ProdutoController.class).slash(id).withRel("delete"));
        }
    }

}