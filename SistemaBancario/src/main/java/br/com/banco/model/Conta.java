package br.com.banco.model;

import br.com.banco.exception.OperacaoInvalidaException;
import br.com.banco.service.TransacoesBancarias;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * Conta bancária. Valores em {@link BigDecimal} para não ter erro de
 * arredondamento com dinheiro (ex.: 0.1 + 0.2 em double dá 0.30000000000000004).
 *
 * <p>Toda operação registra um imposto informativo de 1% sobre o valor no
 * extrato. O imposto não é descontado do saldo.</p>
 */
public class Conta implements TransacoesBancarias {

    public static final BigDecimal TAXA_IMPOSTO = new BigDecimal("0.01");

    private final TipoConta tipoConta;
    private final Extrato extrato = new Extrato();
    private BigDecimal saldo;

    public Conta(TipoConta tipoConta, BigDecimal saldoInicial) {
        if (tipoConta == null) {
            throw new IllegalArgumentException("Tipo de conta é obrigatório.");
        }
        if (saldoInicial == null || saldoInicial.signum() < 0) {
            throw new OperacaoInvalidaException("O saldo inicial não pode ser negativo.");
        }
        this.tipoConta = tipoConta;
        this.saldo = arredondar(saldoInicial);
    }

    @Override
    public void sacar(BigDecimal valor) {
        BigDecimal v = validarValor(valor);
        if (v.compareTo(saldo) > 0) {
            throw new OperacaoInvalidaException(
                    "Saldo insuficiente. Saldo atual: R$ " + saldo + ".");
        }
        saldo = saldo.subtract(v);
        registrar("Saque", v);
    }

    @Override
    public void depositar(BigDecimal valor) {
        BigDecimal v = validarValor(valor);
        saldo = saldo.add(v);
        registrar("Depósito", v);
    }

    @Override
    public BigDecimal getSaldo() {
        return saldo;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public Extrato getExtrato() {
        return extrato;
    }

    private void registrar(String operacao, BigDecimal valor) {
        BigDecimal imposto = arredondar(valor.multiply(TAXA_IMPOSTO));
        extrato.registrar(new Transacao(LocalDateTime.now(), operacao, valor, imposto, saldo));
    }

    private static BigDecimal validarValor(BigDecimal valor) {
        if (valor == null || valor.signum() <= 0) {
            throw new OperacaoInvalidaException("O valor deve ser maior que zero.");
        }
        if (valor.stripTrailingZeros().scale() > 2) {
            throw new OperacaoInvalidaException("Use no máximo 2 casas decimais.");
        }
        return arredondar(valor);
    }

    private static BigDecimal arredondar(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_EVEN);
    }
}
