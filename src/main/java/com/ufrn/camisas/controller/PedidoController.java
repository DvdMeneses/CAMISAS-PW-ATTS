package com.ufrn.camisas.controller;


import com.ufrn.camisas.domain.Cliente;
import com.ufrn.camisas.domain.Produto;
import com.ufrn.camisas.repository.IClienteRepository;
import com.ufrn.camisas.repository.IProdutoRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufrn.camisas.domain.Pedido;
import com.ufrn.camisas.service.PedidoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos/")
public class PedidoController {

    PedidoService service;
    ModelMapper mapper;
    IProdutoRepository produtoRepository;
    IClienteRepository clienteRepository;
    public PedidoController(PedidoService service, ModelMapper mapper, IProdutoRepository produtoRepository,IClienteRepository clienteRepository){
        this.service = service;
        this.mapper = mapper;
        this.produtoRepository = produtoRepository;
        this.clienteRepository = clienteRepository;
    }

    /*
    * Criar pedido
    * */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido.DtoResponse create(@RequestBody Pedido.DtoRequest p){
        Pedido pedido = Pedido.DtoRequest.convertToEntity(p, mapper);
        List<Produto> listaProdutos = new ArrayList<>();
        Optional<Cliente> cliente = clienteRepository.findById(p.getCliente().getId());

        if(p.getProdutos_id()!=null){
            for (Long i : p.getProdutos_id()){
                listaProdutos.add(produtoRepository.findById(i).get());
            }
        }else{
            System.out.println("LISTAPRODUTOS NULA");
        }
        if(cliente.isPresent()){
            pedido.setCliente(cliente.get());
        }
        pedido.setProdutos(listaProdutos);
        Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto((Pedido) this.service.create(pedido),mapper);
        response.generateLinks(pedido.getId());

        return response;
    }

    /*
     * Listar pedidos
     * */
    @GetMapping
    public List<Pedido.DtoResponse> list() {
        return service.listPedidos();
    }
    /*
    @GetMapping
    public List<Pedido.DtoResponse> list(){
        return this.service.list().stream().map(
                elementoAtual -> {
                    Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto(elementoAtual, mapper);
                    response.generateLinks(elementoAtual.getId());
                    return response;
                }).toList();
    }
    */

    /*
     * Buscar pedido por id
     * */
    @GetMapping("/{id}")
    public ResponseEntity<Pedido.DtoResponse> getPedidoById(@PathVariable Long id) {
        Pedido pedido = (Pedido) service.getById(id);
        if (pedido != null) {
            Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto(pedido, mapper);
            response.generateLinks(pedido.getId());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /*@GetMapping("/{id}")
    public Pedido.DtoResponse getById(@PathVariable Long id){
        Pedido pedido = this.service.getById(id);
        Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto(pedido, mapper);
        response.generateLinks(pedido.getId());
        return response;
    }*/

    /*
     * Atualizar pedido
     * */
    @PutMapping("/{id}")
    public ResponseEntity<Pedido.DtoResponse> updatePedido(@PathVariable Long id, @Valid @RequestBody Pedido.DtoRequest dtoRequest) {
        Pedido existingPedido = (Pedido) service.getById(id);

        if (existingPedido != null) {
            mapper.map(dtoRequest, existingPedido);
            Pedido updatedPedido = (Pedido) service.update(existingPedido, id);
            Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto(updatedPedido, mapper);
            response.generateLinks(updatedPedido.getId());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /*@PutMapping("/{id}")
    public Pedido.DtoResponse update(@RequestBody Pedido.DtoRequest p, @PathVariable Long id){
        Pedido pedido = Pedido.DtoRequest.convertToEntity(p, mapper);
        Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto(this.service.update(pedido, id), mapper);
        response.generateLinks(id);
        return response;
    }*/

    /*
    * Deletar pedido
    * */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}