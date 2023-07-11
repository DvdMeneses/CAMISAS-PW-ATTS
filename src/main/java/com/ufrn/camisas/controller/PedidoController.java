package com.ufrn.camisas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ufrn.camisas.domain.Pedido;
import com.ufrn.camisas.service.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    PedidoService service;

    /**Não tem Dto */

    public PedidoController(PedidoService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido create(@RequestBody Pedido p){
        return service.create(p);
    }

    @GetMapping
    public List<Pedido> list(){
        return service.list();
    }

    @GetMapping("/{id}")
    public Pedido getById(@PathVariable Long id){
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Pedido update(@RequestBody Pedido pedido, @PathVariable Long id){
        return service.update(pedido, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}