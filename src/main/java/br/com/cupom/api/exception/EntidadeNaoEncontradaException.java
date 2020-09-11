package br.com.cupom.api.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

    public static final String MSG = "Entidade de código %s não encontrado";

    public EntidadeNaoEncontradaException(String codigo){
        super(String.format(MSG,codigo));
    }

}
