package br.com.banco.service;

import java.math.BigDecimal;

/** Operações que qualquer conta bancária deve oferecer. */
public interface TransacoesBancarias {

    void sacar(BigDecimal valor);

    void depositar(BigDecimal valor);

    BigDecimal getSaldo();
}
