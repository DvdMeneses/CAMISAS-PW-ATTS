package com.ufrn.camisas.controller;

import com.ufrn.camisas.domain.Envio;
import com.ufrn.camisas.domain.Produto;
import com.ufrn.camisas.service.EnvioService;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/envio")
public class EnvioController {
    EnvioService service;
    ModelMapper mapper;

    /* Construtor */
    public EnvioController(EnvioService service, ModelMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }


    /*
    * Criar envio
    * */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Envio.DtoResponse create(@RequestBody Envio.DtoRequest e){
        Envio envio = this.service.create(Envio.DtoRequest.convertToEntity( e, mapper));
        Envio.DtoResponse response = Envio.DtoResponse.convertToDto(envio,mapper);
        response.generateLinks(envio.getId());
        return response;
    }

    /*
    * Listagem de envio
    * */
    @GetMapping
    public List<Envio.DtoResponse> list(){
        return this.service.list().stream().map(
                elementoAtual ->{
                    Envio.DtoResponse response = Envio.DtoResponse.convertToDto(elementoAtual, mapper);
                    response.generateLinks(elementoAtual.getId());
                    return response;

                }).toList();
    }

    /*
    * Buscar envio por id
    * */
    @GetMapping("{id}")
    public Envio.DtoResponse getById(@PathVariable Long id) {
        Envio envio = this.service.getById(id);
        Envio.DtoResponse response = Envio.DtoResponse.convertToDto(envio, mapper);
        response.generateLinks(envio.getId());
        return response;
    }

    /*
    * Atualizar envio
    * */
    @PutMapping("{id}")
    public Envio.DtoResponse update(@RequestBody Envio.DtoRequest e,@PathVariable Long id){
        Envio envio = Envio.DtoRequest.convertToEntity(e, mapper);
        Envio.DtoResponse response = Envio.DtoResponse.convertToDto(this.service.update(envio,id), mapper);
        return response;
    }

    /*
     * Deletar envio
     * */
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        this.service.delete(id);
    }



}