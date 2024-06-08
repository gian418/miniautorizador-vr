package br.com.albano.testevr.controllers;

import br.com.albano.testevr.controllers.dtos.CartaoDTO;
import br.com.albano.testevr.services.CartaoSaldoService;
import br.com.albano.testevr.services.CartaoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private CartaoSaldoService cartaoSaldoService;

    @PostMapping
    public ResponseEntity<CartaoDTO> inserir(@RequestBody @Valid CartaoDTO dto) {
        var cartaoSalvo = cartaoService.salvar(dto.dtoToDomain());
        return ResponseEntity.status(HttpStatus.CREATED).body(CartaoDTO.domainToDto(cartaoSalvo));
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> getSaldo(@PathVariable String numeroCartao) {
        var saldo = cartaoSaldoService.getSaldo(numeroCartao);
        return ResponseEntity.ok().body(saldo);
    }

}
