package br.com.albano.testevr.controllers;

import br.com.albano.testevr.controllers.dtos.TransacaoDTO;
import br.com.albano.testevr.services.TransacaoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<String> salvarTransacao(@RequestBody @Valid TransacaoDTO dto) {
        transacaoService.salvar(dto.dtoToDomain());
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }
}
