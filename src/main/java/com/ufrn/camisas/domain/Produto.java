package com.ufrn.camisas.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    String nomeProduto;
    Integer precoProduto;

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Produto produto){
            if(produto.nomeProduto.equals("NOME EM BRANCO") || produto.precoProduto == 0){
                throw new NullPointerException ("CAMPO NULL INVALIDO");
            }
            this.nomeProduto = produto.nomeProduto;
            this.precoProduto = produto.precoProduto;
        }
    }
    @Data
    public static class DtoRequest {
        @NotBlank(message = "PRODUTO COM NOME EM BRANCO")
        String nomeProduto;
        @NotNull(message = "PRODUTO COM PRECO NULO")
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