package br.com.albano.testevr.controllers;

import br.com.albano.testevr.controllers.dtos.CartaoDTO;
import br.com.albano.testevr.services.CartaoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<CartaoDTO> inserir(@RequestBody @Valid CartaoDTO dto) {
        try {
            var cartaoSalvo = cartaoService.salvar(dto.dtoToDomain());
            return ResponseEntity.status(HttpStatus.CREATED).body(CartaoDTO.domainToDto(cartaoSalvo));
        } catch (DataIntegrityViolationException ex) {
            log.error("Não foi possível processar a solicitação", ex);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(dto);
        }

    }
}
