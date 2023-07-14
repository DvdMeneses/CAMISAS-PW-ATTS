//ERROS:
//@GeneratedValue(strategy = GenerationType.IDENTITY)
//String nome;

//Date DataDeNascimento;
//errado:
//@OneToMany
//@JoinColumn(name = "id_Pedido")
//private List<Pedido> pedidos;

package com.ufrn.camisas.domain;

import java.util.List;

import com.ufrn.camisas.controller.ClienteController;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.hateoas.RepresentationModel;
import jakarta.persistence.*;
import org.modelmapper.ModelMapper;
import lombok.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SQLDelete(sql = "UPDATE Cliente SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
@Where(clause = "deleted_at is null")
public class Cliente extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; // Corrigido: adicionada a definição do ID

    @NotBlank(message = "Nome em Branco")
    String nome;
    @NotBlank
    String cpf; // Corrigido: alterado o tipo para Date

    /*
    * Correção: adicionado @JoinColumn
    * */
    @OneToMany
    @JoinColumn(name = "id_cliente")
    List<Pedido> pedidos;

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Cliente cliente){// tratamento para nao nulo
            if(cliente.nome.equals("Nome em Branco") || cliente.cpf == null){

                throw new NullPointerException ("campo null invalido");
            }
            this.nome = cliente.nome;
            this.cpf = cliente.cpf;
            this.pedidos = cliente.pedidos;
        }
    }

    @Data
    public static class DtoRequest {
        @NotBlank(message = "Nome em Branco")
        private String nome;

        @NotBlank(message = "Data não cadastrada")
        private String cpf;

        public static Cliente convertToEntity(DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Cliente.class);
        }
    }

    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
        private String nome;
        private String cpf;

        public static DtoResponse convertToDto(Cliente c, ModelMapper mapper) {
            return mapper.map(c, DtoResponse.class);

        }
        public void generateLinks(long id){
            add(linkTo(ClienteController.class).slash(id).withSelfRel());
            add(linkTo(ClienteController.class).withRel("cliente"));
            add(linkTo(ClienteController.class).slash(id).withRel("delete"));


        }

    }
}
