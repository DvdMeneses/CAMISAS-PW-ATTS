package com.ufrn.camisas.controller;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ufrn.camisas.config.LoginDTO;
import com.ufrn.camisas.service.TokenService;

@RestController
public class AuthController {


    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/token")
    public String token(@RequestBody LoginDTO loginDTO) {
        System.out.println(loginDTO.password());
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password())
                );
        System.out.println(loginDTO.username());
        return tokenService.generateToken(authentication);
    }



}



