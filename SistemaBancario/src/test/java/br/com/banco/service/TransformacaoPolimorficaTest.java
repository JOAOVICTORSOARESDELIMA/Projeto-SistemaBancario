package br.com.banco.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.banco.model.Conta;
import br.com.banco.model.TipoConta;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class TransformacaoPolimorficaTest {

    @Test
    void funcionaComQualquerImplementacao() {
        TransacoesBancarias conta = new Conta(TipoConta.POUPANCA, new BigDecimal("10"));
        TransformacaoPolimorfica operacoes = new TransformacaoPolimorfica();
        operacoes.realizarDeposito(conta, new BigDecimal("5"));
        operacoes.realizarSaque(conta, new BigDecimal("3"));
        assertEquals(new BigDecimal("12.00"), conta.getSaldo());
    }
}
