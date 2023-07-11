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

    public EnvioController(EnvioService service, ModelMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }


    // CRIAR
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Envio.DtoResponse create(@RequestBody Envio.DtoRequest e){
        Envio envio = this.service.create(Envio.DtoRequest.convertToEntity( e, mapper));
        Envio.DtoResponse response = Envio.DtoResponse.convertToDto(envio,mapper);
        response.generateLinks(envio.getId());
        return response;
    }


    //Listar
    @GetMapping
    public List<Envio.DtoResponse> list(){
        return this.service.list().stream().map(
                elementoAtual ->{
                    Envio.DtoResponse response = Envio.DtoResponse.convertToDto(elementoAtual, mapper);
                    response.generateLinks(elementoAtual.getId());
                    return response;

                }).toList();
    }

    @GetMapping("{id}")
    public Envio.DtoResponse getById(@PathVariable Long id) {
        Envio envio = this.service.getById(id);
        Envio.DtoResponse response = Envio.DtoResponse.convertToDto(envio, mapper);
        response.generateLinks(envio.getId());
        return response;
    }

    @PutMapping("{id}")
    public Envio.DtoResponse update(@RequestBody Envio.DtoRequest dtoRequest,@PathVariable Long id){

        Envio e = Envio.DtoRequest.convertToEntity(dtoRequest, mapper);
        Envio.DtoResponse response = Envio.DtoResponse.convertToDto(this.service.update(e,id), mapper);

        return response;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        this.service.delete(id);

    }



}