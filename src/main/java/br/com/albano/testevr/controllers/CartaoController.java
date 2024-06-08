package br.com.albano.testevr.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @RequestMapping(method = GET)
    public ResponseEntity<Void> getSaldo() {
        return ResponseEntity.ok().build();
    }
}
