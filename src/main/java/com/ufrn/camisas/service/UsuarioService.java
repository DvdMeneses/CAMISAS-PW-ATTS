package com.ufrn.camisas.service;

import com.ufrn.camisas.domain.Usuario;
import com.ufrn.camisas.repository.IUsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends GenericService<Usuario, IUsuarioRepository>{

    public UsuarioService (IUsuarioRepository repository){
        super(repository);
    }
}
