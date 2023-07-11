package com.ufrn.camisas.domain;

import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Usuario extends AbstractEntity{

    private String login;
    private String senha;

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Usuario usuario){
            this.login = usuario.login;
            this.senha = usuario.senha;
        }
    }
}