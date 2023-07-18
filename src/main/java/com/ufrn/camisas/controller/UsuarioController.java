package com.ufrn.camisas.controller;

import com.ufrn.camisas.domain.Usuario;
import com.ufrn.camisas.service.UsuarioService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    UsuarioService service;
    ModelMapper mapper;

    public UsuarioController(UsuarioService service, ModelMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario.UsuarioDtoResponse create(@RequestBody @Valid Usuario.UsuarioDtoRequest u){

        Usuario usuario = this.service.create(Usuario.UsuarioDtoRequest.convertToEntity(u, mapper));

        Usuario.UsuarioDtoResponse response = Usuario.UsuarioDtoResponse.convertToDto(usuario, mapper);
        response.generateLinks(usuario.getId());
        return response;
    }

    @GetMapping
    public ResponseEntity<Page<Usuario.UsuarioDtoResponse>> find(Pageable page) {
        Page<Usuario.UsuarioDtoResponse> dtoResponsePage = service
                .find(page)
                .map(record -> {
                    Usuario.UsuarioDtoResponse response = Usuario.UsuarioDtoResponse.convertToDto(record, mapper);
                    response.generateLinks(record.getId());
                    return response;
                });
        return new ResponseEntity<>(dtoResponsePage, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public Usuario.UsuarioDtoResponse getById(@PathVariable Long id){
        Usuario usuario = this.service.getById(id);
        Usuario.UsuarioDtoResponse response = Usuario.UsuarioDtoResponse.convertToDto(usuario, mapper);
        response.generateLinks(usuario.getId());
        return response;
    }

    @PutMapping("{id}")
    public Usuario.UsuarioDtoResponse update(@RequestBody Usuario.UsuarioDtoRequest dtoRequest, @PathVariable Long id){
        Usuario u = Usuario.UsuarioDtoRequest.convertToEntity(dtoRequest,mapper);
        Usuario.UsuarioDtoResponse response = Usuario.UsuarioDtoResponse.convertToDto(this.service.update(u,id),mapper);
        response.generateLinks(id);
        return response;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        this.service.delete(id);
    }

}