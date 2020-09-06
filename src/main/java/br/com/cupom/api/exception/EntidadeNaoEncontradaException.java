package br.com.cupom.api.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

    public static final String MSG = "Entidade de código %d não encontrado";

    public EntidadeNaoEncontradaException(Long codigo){
        super(String.format(MSG,codigo));
    }

}
