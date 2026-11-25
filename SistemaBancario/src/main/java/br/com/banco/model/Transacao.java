package br.com.banco.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Uma linha do extrato. */
public record Transacao(LocalDateTime dataHora, String operacao, BigDecimal valor,
                        BigDecimal imposto, BigDecimal saldoApos) {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    public String toString() {
        return String.format("%s | %-8s | Valor: R$ %10.2f | Imposto: R$ %6.2f | Saldo: R$ %10.2f",
                dataHora.format(FORMATO), operacao, valor, imposto, saldoApos);
    }
}
