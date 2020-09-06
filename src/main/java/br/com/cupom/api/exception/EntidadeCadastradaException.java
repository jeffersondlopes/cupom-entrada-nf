package br.com.cupom.api.exception;

public class EntidadeCadastradaException extends RuntimeException {

    public static final String MSG = "Entidade de código %d já está cadastrado";

    public EntidadeCadastradaException(Long codigo){
        super(String.format(MSG,codigo));
    }

}
