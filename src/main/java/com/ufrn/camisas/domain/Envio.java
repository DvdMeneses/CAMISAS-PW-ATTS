package com.ufrn.camisas.domain;

import com.ufrn.camisas.controller.ClienteController;
import com.ufrn.camisas.controller.EnvioController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Envio extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Corrigido: adicionada a definição do ID
    @NotNull(message = "Forma de Envio vazia")
    private String formaEnvio;
    @NotNull
    private String endereco;

    @OneToOne
    @JoinColumn(name = "codigo_pedido", referencedColumnName = "id")
    private Pedido pedido;

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Envio envio){// tratamento para nao nulo
            if(envio.formaEnvio.equals("Forma de Envio vazia") || envio.endereco == null){
                throw new RuntimeException("campo null invalido");
            }
            this.formaEnvio = envio.formaEnvio;
            this.endereco = envio.endereco;
            this.pedido = envio.pedido;
        }
    }

    @Data
    public static class DtoRequest {
        @NotBlank(message = "CAMPO OBRIGATORIO - ENVIO")
        private String formaEnvio;

        @NotBlank(message = "CAMPO OBRIGATORIO - ENDERECO")
        private String endereco;

        public static Envio convertToEntity(DtoRequest dto, ModelMapper mapper){
            return mapper.map(dto, Envio.class);
        }

    }

    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
        private String formaEnvio;
        private String endereco;

        public static  DtoResponse convertToDto(Envio e, ModelMapper mapper){
            return  mapper.map(e, DtoResponse.class);
        }

        public void generateLinks(Long id) {
            add(linkTo(EnvioController.class).slash(id).withSelfRel());
            add(linkTo(EnvioController.class).withRel("envio"));
            add(linkTo(EnvioController.class).slash(id).withRel("delete"));
        }


    }
}