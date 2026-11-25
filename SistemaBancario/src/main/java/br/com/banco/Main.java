package br.com.banco;

import br.com.banco.exception.OperacaoInvalidaException;
import br.com.banco.model.Agencia;
import br.com.banco.model.Atendente;
import br.com.banco.model.Cliente;
import br.com.banco.model.Conta;
import br.com.banco.model.TipoConta;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Scanner;

/** Atendimento bancário pelo terminal. */
public class Main {

    private final Scanner entrada;
    private final PrintStream saida;
    private final Atendente atendente = new Atendente("João Emanuel Silva");
    private final Agencia agencia = new Agencia("Cabo Branco");

    public Main(Scanner entrada, PrintStream saida) {
        this.entrada = entrada;
        this.saida = saida;
    }

    public static void main(String[] args) {
        new Main(new Scanner(System.in, "UTF-8"), System.out).executar();
    }

    public void executar() {
        saida.println("Bem-vindo ao sistema de atendimento bancário!");
        saida.println("Atendente: " + atendente.nome() + " | Agência: " + agencia.nome());

        Cliente cliente = cadastrarCliente();
        saida.println("\nCliente cadastrado com sucesso:");
        saida.println(cliente);

        while (true) {
            saida.println("\nSelecione uma operação:");
            saida.println("1 - Sacar");
            saida.println("2 - Depositar");
            saida.println("3 - Ver saldo");
            saida.println("4 - Ver extrato");
            saida.println("5 - Sair");
            int opcao = lerInteiro("Opção: ");
            if (opcao == 5) {
                saida.println("Saindo do sistema...");
                return;
            }
            executarOperacao(opcao, cliente);
        }
    }

    private void executarOperacao(int opcao, Cliente cliente) {
        Conta conta = cliente.getConta();
        try {
            switch (opcao) {
                case 1 -> {
                    conta.sacar(lerValor("Valor para saque: R$ "));
                    saida.println("Saque realizado. Saldo atual: R$ " + conta.getSaldo());
                }
                case 2 -> {
                    conta.depositar(lerValor("Valor para depósito: R$ "));
                    saida.println("Depósito realizado. Saldo atual: R$ " + conta.getSaldo());
                }
                case 3 -> saida.println("Saldo atual: R$ " + conta.getSaldo());
                case 4 -> saida.println(conta.getExtrato().formatar(cliente, atendente, agencia));
                default -> saida.println("Opção inválida! Tente novamente.");
            }
        } catch (OperacaoInvalidaException e) {
            saida.println("Erro: " + e.getMessage());
        }
    }

    private Cliente cadastrarCliente() {
        saida.println("\nCadastro de cliente");
        while (true) {
            String nome = lerTexto("Nome completo: ");
            int idade = lerInteiro("Idade: ");
            String cpf = lerTexto("CPF: ");
            String email = lerTexto("E-mail: ");
            TipoConta tipo = lerTipoConta();
            BigDecimal saldoInicial = lerValorOuZero("Saldo inicial: R$ ");
            try {
                return new Cliente(nome, idade, cpf, email, new Conta(tipo, saldoInicial));
            } catch (IllegalArgumentException | OperacaoInvalidaException e) {
                saida.println("Erro: " + e.getMessage() + " Vamos tentar de novo.\n");
            }
        }
    }

    private TipoConta lerTipoConta() {
        while (true) {
            saida.println("Tipo de conta: 1 - Corrente | 2 - Poupança");
            switch (lerInteiro("Opção: ")) {
                case 1: return TipoConta.CORRENTE;
                case 2: return TipoConta.POUPANCA;
                default: saida.println("Opção inválida!");
            }
        }
    }

    // ---- Leitura segura: entradas inválidas não derrubam o programa ----

    private String lerTexto(String pergunta) {
        saida.print(pergunta);
        if (!entrada.hasNextLine()) {
            throw new IllegalStateException("Entrada encerrada.");
        }
        return entrada.nextLine().trim();
    }

    private int lerInteiro(String pergunta) {
        while (true) {
            String texto = lerTexto(pergunta);
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                saida.println("Digite um número inteiro.");
            }
        }
    }

    private BigDecimal lerValor(String pergunta) {
        while (true) {
            String texto = lerTexto(pergunta).replace(",", ".");
            try {
                return new BigDecimal(texto);
            } catch (NumberFormatException e) {
                saida.println("Valor inválido. Exemplo: 150,50");
            }
        }
    }

    private BigDecimal lerValorOuZero(String pergunta) {
        while (true) {
            String texto = lerTexto(pergunta).replace(",", ".");
            if (texto.isEmpty()) {
                return BigDecimal.ZERO;
            }
            try {
                return new BigDecimal(texto);
            } catch (NumberFormatException e) {
                saida.println("Valor inválido. Exemplo: 150,50");
            }
        }
    }
}
