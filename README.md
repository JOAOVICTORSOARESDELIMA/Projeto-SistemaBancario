[![CI](https://github.com/JOAOVICTORSOARESDELIMA/Projeto-SistemaBancario/actions/workflows/ci.yml/badge.svg)](https://github.com/JOAOVICTORSOARESDELIMA/Projeto-SistemaBancario/actions/workflows/ci.yml)

# Sistema Bancário em Java

Projeto em equipe da disciplina de **Linguagem de Programação I** do curso de Sistemas para Internet (UNIESP), para praticar **orientação a objetos**: encapsulamento, interfaces, enums e polimorfismo.

É um atendimento bancário pelo terminal: o cliente se cadastra, escolhe o tipo de conta (corrente ou poupança) e faz saques e depósitos. Toda operação fica registrada num extrato.

## Funcionalidades

- Cadastro do cliente com validação de nome, idade (18+), CPF e e-mail
- Conta **corrente** ou **poupança**
- **Saque** (bloqueia valor maior que o saldo) e **depósito**
- Consulta de **saldo** e **extrato** com data, hora, valor, imposto e saldo após cada operação
- Imposto informativo de 1% registrado no extrato
- Entradas inválidas (letras, valores negativos, opções fora do menu) não derrubam o programa
- CPF mascarado na tela (`***.456.789-**`)

## Conceitos de orientação a objetos

| Conceito | Onde aparece |
|---|---|
| Encapsulamento | `Conta` só altera o saldo pelos métodos `sacar` e `depositar`, que validam tudo antes |
| Interface | `TransacoesBancarias` define o que toda conta precisa oferecer |
| Polimorfismo | `TransformacaoPolimorfica` opera qualquer `TransacoesBancarias` sem saber a classe concreta |
| Enum | `TipoConta` (Corrente, Poupança) |
| Records | `Agencia`, `Atendente` e `Transacao`: classes de dados imutáveis |
| Exceções próprias | `OperacaoInvalidaException` para saldo insuficiente e valores inválidos |

## Como executar

Requisitos: **Java 17** e **Maven**.

```bash
cd SistemaBancario
mvn package                              # compila e roda os testes
java -jar target/sistema-bancario.jar
```

Também abre direto no IntelliJ IDEA pelo `SistemaBancario.iml` ou pelo `pom.xml`.

### Exemplo

```
Bem-vindo ao sistema de atendimento bancário!
Atendente: João Emanuel Silva | Agência: Cabo Branco
...
Extrato de transações
Cliente: Maria Silva | Conta: Corrente | Atendente: João Emanuel Silva | Agência: Cabo Branco
------------------------------------------------------------------------------------
25/11/2026 17:14:10 | Depósito | Valor: R$     100.50 | Imposto: R$   1.01 | Saldo: R$     100.50
25/11/2026 17:14:12 | Saque    | Valor: R$      20.00 | Imposto: R$   0.20 | Saldo: R$      80.50
```

## Testes

`mvn test` roda 11 testes JUnit 5: saque e depósito, saldo insuficiente, valores inválidos, arredondamento de dinheiro, extrato, validação do cliente e polimorfismo. O GitHub Actions roda os testes a cada push.

## Estrutura

```
SistemaBancario/src/main/java/br/com/banco/
├── Main.java                        # menu e leitura do terminal
├── model/      Cliente, Conta, TipoConta, Extrato, Transacao, Agencia, Atendente
├── service/    TransacoesBancarias (interface), TransformacaoPolimorfica
└── exception/  OperacaoInvalidaException
```

## O que mudou em relação à versão entregue

- **Corrigido:** o código antigo não compilava (pacotes `model` e `enums` declarados mas inexistentes, chave faltando no `Extrato`).
- **Corrigido:** faltava `break` no `switch` do menu. Escolher "Sacar" executava também depósito, saldo, extrato e **saía do sistema**.
- **Corrigido:** operações recusadas (ex.: saque sem saldo) eram registradas no extrato mesmo assim.
- Dinheiro passou de `double` para `BigDecimal`, sem erros de arredondamento.
- Nomes com espaço agora funcionam no cadastro (antes só lia a primeira palavra).
- Adicionados validações, testes automatizados, Maven e CI.

## Equipe

- **João Victor Soares de Lima**: [GitHub](https://github.com/JOAOVICTORSOARESDELIMA)
- Maria Eduarda Maia Santos: [GitHub](https://github.com/mariaeduardams)
- Lucas Accioly
- Eli

## Licença

[MIT](LICENSE)
