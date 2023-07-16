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
    String formaEnvio;
    String endereco;

    @OneToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    Pedido pedido;

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Envio envio){// tratamento para nao nulo
            if(envio.formaEnvio.equals("FORMA DE ENVIO NULO") || envio.endereco == null){
                throw new RuntimeException("CAMPO NULO INVALIDO");
            }
            this.formaEnvio = envio.formaEnvio;
            this.endereco = envio.endereco;
            this.pedido = envio.pedido;
        }
    }

    @Data
    public static class DtoRequest {
        @NotBlank(message = "CAMPO OBRIGATORIO: formaEnvio")
        String formaEnvio;

        @NotBlank(message = "CAMPO OBRIGATORIO: endereco")
        String endereco;

        @NotNull(message = "CAMPO OBRIGATORIO: pedido_id")
        Long pedido_id;

        public static Envio convertToEntity(DtoRequest dto, ModelMapper mapper){
            return mapper.map(dto, Envio.class);
        }
    }

    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
        String formaEnvio;
        String endereco;

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