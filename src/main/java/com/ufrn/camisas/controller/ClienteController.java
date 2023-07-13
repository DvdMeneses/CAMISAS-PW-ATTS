package com.ufrn.camisas.controller;
import com.ufrn.camisas.domain.Cliente;
import com.ufrn.camisas.service.ClienteService;
import com.ufrn.camisas.domain.Cliente.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    ClienteService service;
    ModelMapper mapper;

    /* Construtor */
    public ClienteController(ClienteService service, ModelMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    /*
     * Criar cliente
     * */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente.DtoResponse create(@RequestBody Cliente.DtoRequest c){
        Cliente cliente  = this.service.create(Cliente.DtoRequest.convertToEntity(c, mapper));
        Cliente.DtoResponse response  = Cliente.DtoResponse.convertToDto(cliente, mapper);
        response.generateLinks(cliente.getId());

        return response;
    }

    /*
     * Listagem de cliente
     * */
    @GetMapping
    public List<Cliente.DtoResponse> list(){

        return this.service.list().stream().map(
                elementoAtual -> {
                    Cliente.DtoResponse response = Cliente.DtoResponse.convertToDto(elementoAtual, mapper);
                    response.generateLinks(elementoAtual.getId());
                    return response;
                }).toList();
    }

    /*
     * Buscar cliente por id
     * */
    @GetMapping("{id}")
    public Cliente.DtoResponse getByid(@PathVariable Long id){

        Cliente cliente = this.service.getById(id);
        Cliente.DtoResponse response = Cliente.DtoResponse.convertToDto(cliente, mapper);
        response.generateLinks(cliente.getId());
        return response;
    }

    /*
     * Atualizar cliente
     * */
    @PutMapping("{id}")
    public Cliente.DtoResponse update(@RequestBody Cliente.DtoRequest dtoRequest, @PathVariable Long id){
        System.out.println(id);
        Cliente cliente = Cliente.DtoRequest.convertToEntity(dtoRequest, mapper);
        Cliente.DtoResponse response = Cliente.DtoResponse.convertToDto(this.service.update(cliente, id),mapper);
        response.generateLinks(id);
        return response;
    }

    /*
     * Deletar cliente
     * */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id ){
        this.service.delete(id);
    }




}