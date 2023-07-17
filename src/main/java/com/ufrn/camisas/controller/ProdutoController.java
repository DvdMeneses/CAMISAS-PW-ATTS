package com.ufrn.camisas.controller;

import com.ufrn.camisas.domain.Produto;
import com.ufrn.camisas.service.ProdutoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
//coisas que o jottinha adicionou:
/*
  import jakarta.validation.Valid;
  import org.springframework.data.domain.Page;
  import org.springframework.data.domain.Pageable;
  import org.springframework.http.ResponseEntity;
  public ResponseEntity<Page<Produto.DtoResponse>> find(Pageable page) {
*/
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins ="http://127.0.0.1:5173/")
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
    public Produto.DtoResponse create(@RequestBody @Valid Produto.DtoRequest p) {
        Produto produto = this.service.create(Produto.DtoRequest.convertToEntity(p, mapper));
        Produto.DtoResponse response = Produto.DtoResponse.convertToDto(produto, mapper);
        response.generateLinks(produto.getId());
        return response;
    }

    //jotinha adicionou
    @GetMapping
    public ResponseEntity<Page<Produto.DtoResponse>> find(Pageable page) {



        Page<Produto.DtoResponse> dtoResponses = service
                .find(page)
                .map(record -> {
                    Produto.DtoResponse response = Produto.DtoResponse.convertToDto(record, mapper);
                    response.generateLinks(record.getId());
                    return response;
                });


        return new ResponseEntity<>(dtoResponses, HttpStatus.OK);
    }

    /*
     * Listagem de produto
     *
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