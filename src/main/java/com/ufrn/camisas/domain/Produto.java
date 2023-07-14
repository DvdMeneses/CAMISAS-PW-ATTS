package com.ufrn.camisas.domain;

import java.util.List;

import com.ufrn.camisas.controller.ProdutoController;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

   @NotNull
    private Integer precoProduto;

    /*
    * Mapeamento ManyToMany
    * ## Testar ##
    * */
    @ManyToMany(mappedBy = "produtos")
    private List<Pedido> pedidos;

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Produto produto){

            if(produto.nomeProduto.equals("Nome em Branco") || produto.precoProduto == 0){

                throw new NullPointerException ("campo null invalido");
            }
            this.nomeProduto = produto.nomeProduto;
            this.precoProduto = produto.precoProduto;
            this.pedidos = produto.pedidos;
        }
    }

    @Data
    public static class DtoRequest {
        @NotBlank(message = "Produto com nome em branco")
        String nomeProduto;
        @NotNull(message = "Produto com preco vazio")
        Integer precoProduto;

        public static Produto convertToEntity(DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Produto.class);
        }
    }

    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
        String nomeProduto;
        Integer precoProduto;

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