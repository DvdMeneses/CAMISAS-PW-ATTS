//ERROS:
// verificar erro aqui
//  @ManyToMany
// @JoinTable(name = "pedido_produto",
//         joinColumns = @JoinColumn(name = "id_pedido"),
//       inverseJoinColumns = @JoinColumn(name = "produto_id"))
//private List<Produto> produtos;
package com.ufrn.camisas.domain;

import java.util.ArrayList;
import java.util.List;

import com.ufrn.camisas.controller.EnvioController;
import com.ufrn.camisas.domain.Cliente;
import com.ufrn.camisas.domain.Produto;
import com.ufrn.camisas.controller.PedidoController;
import com.ufrn.camisas.service.ClienteService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Pedido extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Corrigido: adicionada a definição do ID

    /* Um cliente pode ter muitos pedidos e um pedido so é para um cliente*/
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;


    /*
    * Correção: adição do 'referencedColumnName' - referência da coluna 'id_pedido' com o id de Pedido.class
    * ## Testar ##
    * */
    @ManyToMany
    @JoinTable(name = "pedido_produtos",
            joinColumns = {@JoinColumn(name = "id_pedido", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_produto", referencedColumnName = "id")})
    private List<Produto> produtos;


    @Override
    public void partialUpdate(AbstractEntity e) {// tratamento para nao nulo
        if (e instanceof Pedido pedido){
            if(pedido.cliente.cpf == null || pedido.produtos == null){
                throw new RuntimeException("campo null invalido");
            }
            this.cliente = pedido.cliente;
            this.produtos = pedido.produtos;
        }
    }

    /*
    * Correção: DTO Pedido
    * ## Testar ##
    * */
    @Data
    public static class DtoRequest {
        @NotBlank(message = "Cliente inexistente.")
        private Cliente cliente;

        @NotBlank(message = "Produtos inexistentes.")
        private List<Produto> produtos;

        public static Pedido convertToEntity(DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Pedido.class);
        }
    }
    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse>{
        private Cliente cliente;
        private List<Produto> produtos;

        public static Pedido.DtoResponse convertToDto(Pedido p, ModelMapper mapper){
            return mapper.map(p, DtoResponse.class);
        }

        public void generateLinks(Long id){
            add(linkTo(PedidoController.class).slash(id).withSelfRel());
            add(linkTo(PedidoController.class).withRel("pedido"));
            add(linkTo(PedidoController.class).slash(id).withRel("delete"));
        }

        public static Envio.DtoResponse convertToDto(Envio e, ModelMapper mapper){
            return  mapper.map(e, Envio.DtoResponse.class);
        }


    }
}