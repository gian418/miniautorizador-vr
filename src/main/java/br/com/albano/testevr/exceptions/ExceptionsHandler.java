package br.com.albano.testevr.exceptions;

import br.com.albano.testevr.controllers.dtos.CartaoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e) {
        ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validacao", System.currentTimeMillis());
        for (FieldError x : e.getBindingResult().getFieldErrors()) {
            err.addError(x.getField(), x.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(CartaoNaoEncontradoException.class)
    public ResponseEntity<StandardError> cartaoNaoEncontrado(CartaoNaoEncontradoException e) {
        log.error("Cartão não encontrado", e);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(SalvarCartaoException.class)
    public ResponseEntity<CartaoDTO> erroIntegridade(SalvarCartaoException e) {
        log.error("Não foi possível processar a solicitação", e);
        return ResponseEntity.unprocessableEntity().body(e.getCartaoDTO());
    }
}
