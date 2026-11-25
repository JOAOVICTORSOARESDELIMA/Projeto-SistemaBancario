package br.com.banco.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.banco.exception.OperacaoInvalidaException;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContaTest {

    private Conta conta;

    @BeforeEach
    void setUp() {
        conta = new Conta(TipoConta.CORRENTE, new BigDecimal("100.00"));
    }

    @Test
    void depositoAumentaSaldo() {
        conta.depositar(new BigDecimal("50.25"));
        assertEquals(new BigDecimal("150.25"), conta.getSaldo());
    }

    @Test
    void saqueDiminuiSaldo() {
        conta.sacar(new BigDecimal("30"));
        assertEquals(new BigDecimal("70.00"), conta.getSaldo());
    }

    @Test
    void podeSacarTodoOSaldo() {
        conta.sacar(new BigDecimal("100.00"));
        assertEquals(new BigDecimal("0.00"), conta.getSaldo());
    }

    @Test
    void saqueAcimaDoSaldoERecusadoESemExtrato() {
        assertThrows(OperacaoInvalidaException.class, () -> conta.sacar(new BigDecimal("100.01")));
        assertEquals(new BigDecimal("100.00"), conta.getSaldo());
        assertTrue(conta.getExtrato().getTransacoes().isEmpty());
    }

    @Test
    void valoresInvalidosSaoRecusados() {
        assertThrows(OperacaoInvalidaException.class, () -> conta.depositar(BigDecimal.ZERO));
        assertThrows(OperacaoInvalidaException.class, () -> conta.depositar(new BigDecimal("-5")));
        assertThrows(OperacaoInvalidaException.class, () -> conta.sacar(null));
        assertThrows(OperacaoInvalidaException.class, () -> conta.depositar(new BigDecimal("1.001")));
    }

    @Test
    void semErroDeArredondamento() {
        Conta c = new Conta(TipoConta.POUPANCA, BigDecimal.ZERO);
        c.depositar(new BigDecimal("0.1"));
        c.depositar(new BigDecimal("0.2"));
        assertEquals(new BigDecimal("0.30"), c.getSaldo());
    }

    @Test
    void extratoRegistraOperacoesComImpostoDeUmPorCento() {
        conta.depositar(new BigDecimal("200"));
        conta.sacar(new BigDecimal("50"));
        var transacoes = conta.getExtrato().getTransacoes();
        assertEquals(2, transacoes.size());
        assertEquals("Depósito", transacoes.get(0).operacao());
        assertEquals(new BigDecimal("2.00"), transacoes.get(0).imposto());
        assertEquals(new BigDecimal("300.00"), transacoes.get(0).saldoApos());
        assertEquals(new BigDecimal("250.00"), transacoes.get(1).saldoApos());
    }

    @Test
    void saldoInicialNegativoERecusado() {
        assertThrows(OperacaoInvalidaException.class,
                () -> new Conta(TipoConta.CORRENTE, new BigDecimal("-1")));
    }
}
