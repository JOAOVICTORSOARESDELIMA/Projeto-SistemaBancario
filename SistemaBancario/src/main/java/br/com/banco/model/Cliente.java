package br.com.banco.model;

import java.util.regex.Pattern;

/** Titular da conta. Os dados são validados na criação. */
public class Cliente {

    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private final String nome;
    private final int idade;
    private final String cpf;
    private final String email;
    private final Conta conta;

    public Cliente(String nome, int idade, String cpf, String email, Conta conta) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if (idade < 18 || idade > 130) {
            throw new IllegalArgumentException("É preciso ter pelo menos 18 anos para abrir uma conta.");
        }
        String digitos = cpf == null ? "" : cpf.replaceAll("\\D", "");
        if (digitos.length() != 11) {
            throw new IllegalArgumentException("CPF deve ter 11 dígitos.");
        }
        if (email == null || !EMAIL.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
        if (conta == null) {
            throw new IllegalArgumentException("Conta é obrigatória.");
        }
        this.nome = nome.trim();
        this.idade = idade;
        this.cpf = digitos;
        this.email = email.trim();
        this.conta = conta;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getCpf() {
        return cpf;
    }

    /** CPF com os 6 dígitos do meio escondidos, para exibir na tela. */
    public String getCpfMascarado() {
        return "***." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-**";
    }

    public String getEmail() {
        return email;
    }

    public Conta getConta() {
        return conta;
    }

    @Override
    public String toString() {
        return "Cliente: " + nome + " | Idade: " + idade + " | CPF: " + getCpfMascarado()
                + " | E-mail: " + email + " | Conta: " + conta.getTipoConta()
                + " | Saldo: R$ " + conta.getSaldo();
    }
}
