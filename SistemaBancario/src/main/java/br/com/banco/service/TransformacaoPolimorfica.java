package br.com.banco.service;

import java.math.BigDecimal;

/**
 * Exemplo de polimorfismo: trabalha com qualquer implementação de
 * {@link TransacoesBancarias}, sem saber qual é a classe concreta.
 */
public class TransformacaoPolimorfica {

    public void realizarSaque(TransacoesBancarias conta, BigDecimal valor) {
        conta.sacar(valor);
    }

    public void realizarDeposito(TransacoesBancarias conta, BigDecimal valor) {
        conta.depositar(valor);
    }
}
