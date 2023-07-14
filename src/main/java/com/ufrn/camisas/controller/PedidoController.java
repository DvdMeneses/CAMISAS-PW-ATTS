package com.ufrn.camisas.controller;

import com.ufrn.camisas.domain.Cliente;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ufrn.camisas.domain.Pedido;
import com.ufrn.camisas.service.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    PedidoService service;
    ModelMapper mapper;

    /*
    * Correção: adicionado DTO + Geracao de links (HATEOAS)
    * */

    /* Construtor */
    public PedidoController(PedidoService service, ModelMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    /*
    * Criar pedido
    * */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido.DtoResponse create(@RequestBody Pedido.DtoRequest p){

        Pedido pedido = this.service.create(Pedido.DtoRequest.convertToEntity(p, mapper));
        System.out.println(pedido.getCliente().getId());

        Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto(pedido, mapper);
        response.generateLinks(pedido.getId());
        return response;
    }

    /*
    * Listagem de pedidos
    * */
    @GetMapping
    public List<Pedido.DtoResponse> list(){
        return this.service.list().stream().map(
                elementoAtual -> {
                    Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto(elementoAtual, mapper);
                    response.generateLinks(elementoAtual.getId());
                    return response;
                }).toList();
    }

    /*
    * Buscar pedido por id
    * */
    @GetMapping("/{id}")
    public Pedido.DtoResponse getById(@PathVariable Long id){
        Pedido pedido = this.service.getById(id);
        Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto(pedido, mapper);
        response.generateLinks(pedido.getId());
        return response;
    }

    /*
    * Atualizar pedido
    * */
    @PutMapping("/{id}")
    public Pedido.DtoResponse update(@RequestBody Pedido.DtoRequest p, @PathVariable Long id){
        Pedido pedido = Pedido.DtoRequest.convertToEntity(p, mapper);
        Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto(this.service.update(pedido, id), mapper);
        response.generateLinks(id);
        return response;
    }

    /*
    * Deletar pedido
    * */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}