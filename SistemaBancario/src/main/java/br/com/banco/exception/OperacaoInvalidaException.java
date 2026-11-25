package br.com.banco.exception;

/** Operação bancária recusada (valor inválido, saldo insuficiente etc.). */
public class OperacaoInvalidaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OperacaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
