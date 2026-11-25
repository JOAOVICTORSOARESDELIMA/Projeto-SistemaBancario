package br.com.banco.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ClienteTest {

    private static Conta conta() {
        return new Conta(TipoConta.CORRENTE, BigDecimal.TEN);
    }

    @Test
    void criaClienteValido() {
        Cliente c = new Cliente(" Maria ", 25, "123.456.789-09", "maria@email.com", conta());
        assertEquals("Maria", c.getNome());
        assertEquals("12345678909", c.getCpf());
        assertEquals("***.456.789-**", c.getCpfMascarado());
    }

    @Test
    void validaDados() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente("", 25, "12345678909", "a@b.com", conta()));
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente("Ana", 17, "12345678909", "a@b.com", conta()));
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente("Ana", 25, "123", "a@b.com", conta()));
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente("Ana", 25, "12345678909", "email-sem-arroba", conta()));
    }
}
