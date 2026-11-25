package br.com.banco.model;

import java.util.ArrayList;
import java.util.List;

/** Histórico de transações de uma conta. */
public class Extrato {

    private final List<Transacao> transacoes = new ArrayList<>();

    void registrar(Transacao transacao) {
        transacoes.add(transacao);
    }

    public List<Transacao> getTransacoes() {
        return List.copyOf(transacoes);
    }

    public String formatar(Cliente cliente, Atendente atendente, Agencia agencia) {
        if (transacoes.isEmpty()) {
            return "Nenhuma transação registrada até o momento.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Extrato de transações\n");
        sb.append("Cliente: ").append(cliente.getNome())
          .append(" | Conta: ").append(cliente.getConta().getTipoConta())
          .append(" | Atendente: ").append(atendente.nome())
          .append(" | Agência: ").append(agencia.nome()).append('\n');
        sb.append("-".repeat(95)).append('\n');
        transacoes.forEach(t -> sb.append(t).append('\n'));
        sb.append("-".repeat(95));
        return sb.toString();
    }
}
