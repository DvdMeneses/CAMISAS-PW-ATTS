package com.ufrn.camisas.domain;

import com.ufrn.camisas.controller.ProdutoController;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import com.ufrn.camisas.controller.UsuarioController;
import org.hibernate.annotations.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SQLDelete(sql = "UPDATE usuario SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
@Where(clause = "deleted_at is null")
public class Usuario extends AbstractEntity implements UserDetails {

    @NotBlank(message = "Login em branco")
    @Column(nullable = false, unique = true)
     String username;

    @NotBlank(message = "Senha em branco")
    @Column(nullable = false)
     String password;

     Boolean admin = false;

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Usuario usuario) {
            this.username = usuario.username;
            this.password = usuario.password;
        }
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (admin) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorities;
    }

    @Data
    public static class UsuarioDtoRequest {
        @NotBlank(message = "Login em branco")
         String username;

        @NotBlank(message = "Senha em branco")
         String password;

         boolean admin;

        public static Usuario convertToEntity(UsuarioDtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Usuario.class);
        }
    }

    @Data
    public static class UsuarioDtoResponse extends RepresentationModel<UsuarioDtoResponse> {
         String username;
         boolean admin;

        public static UsuarioDtoResponse convertToDto(Usuario usuario, ModelMapper mapper) {
            UsuarioDtoResponse dto = mapper.map(usuario, UsuarioDtoResponse.class);
            dto.generateLinks(usuario.getId());
            return dto;
        }

        public void generateLinks(Long id){
            add(linkTo(UsuarioController.class).withRel("Usuario"));
            add(linkTo(UsuarioController.class).slash(id).withRel("delete"));
        }
    }

}