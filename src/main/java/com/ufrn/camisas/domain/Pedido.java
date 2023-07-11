//ERROS:
// verificar erro aqui
//  @ManyToMany
// @JoinTable(name = "pedido_produto",
//         joinColumns = @JoinColumn(name = "id_pedido"),
//       inverseJoinColumns = @JoinColumn(name = "produto_id"))
//private List<Produto> produtos;
package com.ufrn.camisas.domain;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.modelmapper.ModelMapper;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Pedido extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Corrigido: adicionada a definição do ID

    @ManyToOne // Corrigido: alterado para ManyToOne para representar a associação correta
    @JoinColumn(name = "idCliente") // Corrigido: definido o nome da coluna corretamente
    private Cliente cliente; // Corrigido: adicionado o atributo cliente para representar a associação correta

    @ManyToMany
    @JoinTable(name = "pedido_produto",
            joinColumns = @JoinColumn(name = "id_pedido"),
            inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<Produto> produtos;


    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Pedido pedido){
            this.cliente = pedido.cliente;
            this.produtos = pedido.produtos;
        }
    }

    @Data
    public static class DtoRequest {
        @NotBlank(message = "Nome em Branco")
        private String nome;

        @NotBlank(message = "Data não cadastrada")
        private Integer dataDeNascimento;

        public static Cliente convertToEntity(Cliente.DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Cliente.class);
        }
    }
}