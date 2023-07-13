package com.ufrn.camisas.controller;

import com.ufrn.camisas.domain.Produto;
import com.ufrn.camisas.service.ProdutoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    ProdutoService service;
    ModelMapper mapper;

    /* Construtor */
    public ProdutoController(ProdutoService service, ModelMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    /*
     * Criar produto
     * */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto.DtoResponse create(@RequestBody Produto.DtoRequest p) {
        Produto produto = this.service.create(Produto.DtoRequest.convertToEntity(p, mapper));
        Produto.DtoResponse response = Produto.DtoResponse.convertToDto(produto, mapper);
        response.generateLinks(produto.getId());
        return response;
    }

    /*
     * Listagem de produto
     * */
    @GetMapping
    public List<Produto.DtoResponse> list(){
        return this.service.list().stream().map(
                elementoAtual -> {
                    Produto.DtoResponse response = Produto.DtoResponse.convertToDto(elementoAtual, mapper);
                    response.generateLinks(elementoAtual.getId());
                    return response;
                }).toList();
    }

    /*
     * Buscar produto por id
     * */
    @GetMapping("{id}")
    public Produto.DtoResponse getById(@PathVariable Long id) {
        Produto produto = service.getById(id);
        Produto.DtoResponse response = Produto.DtoResponse.convertToDto(produto, mapper);
        response.generateLinks(produto.getId());
        return response;
    }

    /*
     * Atualizar produto
     * */
    @PutMapping("{id}")
    public Produto.DtoResponse update(@RequestBody Produto.DtoRequest dtoRequest, @PathVariable Long id){
        Produto p = Produto.DtoRequest.convertToEntity(dtoRequest, mapper);
        Produto.DtoResponse response = Produto.DtoResponse.convertToDto(this.service.update(p, id), mapper);
        response.generateLinks(id);
        return response;
    }

    /*
     * Deletar produto
     * */
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        this.service.delete(id);
    }




}