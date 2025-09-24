package br.com.fiap.SpringSecurityInitial.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpController {

    @GetMapping("/public")
    public String publicRoute() {
        return "<h1>Rota pública, sinta-se livre para acessar!</h1>";
    }

    @GetMapping("/private")
    public String privateRoute() {
        return "<h1>Rota privada, somente pessoas autorizadas!</h1>";
    }
}
