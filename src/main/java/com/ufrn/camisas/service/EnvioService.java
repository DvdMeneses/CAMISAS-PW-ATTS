package com.ufrn.camisas.service;

import com.ufrn.camisas.domain.Envio;
import com.ufrn.camisas.repository.IEnvioRepository;
import org.springframework.stereotype.Service;

@Service
public class EnvioService extends GenericService<Envio,IEnvioRepository>{
    public EnvioService (IEnvioRepository repository){
        super(repository);
    }

}

