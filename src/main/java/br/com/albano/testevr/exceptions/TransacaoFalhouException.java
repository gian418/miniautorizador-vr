package br.com.albano.testevr.exceptions;

public class TransacaoFalhouException extends RuntimeException {

    public TransacaoFalhouException(String message) {
        super(message);
    }
}
