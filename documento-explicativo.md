# Documento Explicativo

## Sistema de Apoio a Produção de Peças Metálicas

Este documento explica **todo o código** do arquivo `src/Main.java`, parte por parte, de
forma que mesmo quem está começando em programação consiga **entender e replicar**. Ao
final de cada parte há a indicação de **qual item do desafio** (a, b, c, d, e) aquele
trecho atende.

A aplicação é um programa Java de **console** (terminal) que simula um módulo de apoio à
decisão de uma metalúrgica: cadastra materiais, compara conjuntos (união, interseção e
diferença), calcula custo, classifica o pedido, simula o risco de atraso em vários cenários
e analisa a produção semanal em uma matriz.

> Como o código é organizado: o método `main` é curto e só controla o **menu**. Cada
> funcionalidade fica em seu **próprio método** (ex.: `cadastrarMateriais`, `calcularCustoPedido`,
> `simularRiscoAtraso`). Isso separa responsabilidades, evita repetição e é justamente o que
> o item (b) do desafio pede.

---

## 1. Importações (linhas 1 a 3)

```java
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
```

- `import` "traz" classes prontas do Java para o programa.
- `Set` é o tipo **conjunto**: uma coleção que **não aceita itens repetidos**.
- `LinkedHashSet` é uma implementação de `Set` que mantém a **ordem de inserção** (fica mais
  fácil de ler na tela).
- `Scanner` permite **ler o que o usuário digita** no teclado.

---

## 2. Atributos da classe (linhas 5 a 22)

```java
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static final Set<String> materiaisDisponiveis = new LinkedHashSet<>();
    private static final Set<String> materiaisNecessarios = new LinkedHashSet<>();

    private static int quantidadePecas = 0;
    private static int prazoDias = 0;
    private static double custoUnitario = 0.0;
    private static double orcamentoDisponivel = 0.0;

    private static final String[] setores = {"Corte", "Solda", "Pintura"};
    private static final String[] diasSemana = {"Segunda", "Terca", "Quarta", "Quinta", "Sexta"};
    private static final int[][] producaoSemanal = {
            {18, 20, 17, 22, 19},
            {14, 16, 15, 18, 17},
            {11, 13, 12, 14, 15}
    };
}
```

- Esses dados estão **fora do `main`**, no nível da classe, com `static`, para que **todos os
  métodos** possam usá-los e para que os valores sejam "lembrados" entre uma opção e outra do menu.
- `materiaisDisponiveis` e `materiaisNecessarios` são **conjuntos** (sem duplicados).
- `quantidadePecas`, `prazoDias`, `custoUnitario` e `orcamentoDisponivel` guardam os dados do
  pedido informados pelo usuário.
- `setores` e `diasSemana` são **vetores** (estruturas lineares) com rótulos.
- `producaoSemanal` é uma **matriz** 3x5: cada **linha** é um setor, cada **coluna** é um dia.
  Exemplo: `producaoSemanal[0][3]` vale `22` (Corte, Quinta).

> **Atende ao item (a) Conjuntos** (os dois `Set`) e ao **item (e) Vetores e matrizes**
> (`setores`, `diasSemana` e `producaoSemanal`).

---

## 3. O método `main` e o menu (linhas 24 a 63)

```java
public static void main(String[] args) {
    int opcao;
    do {
        mostrarMenu();
        opcao = lerInteiro("Escolha uma opcao: ");
        switch (opcao) {
            case 1: cadastrarMateriais(materiaisDisponiveis, "disponiveis em estoque"); break;
            case 2: cadastrarMateriais(materiaisNecessarios, "necessarios para a peca"); break;
            case 3: verificarMateriais(); break;
            case 4: calcularCustoPedido(); break;
            case 5: classificarPedido(); break;
            case 6: simularRiscoAtraso(); break;
            case 7: mostrarProducaoSemanal(); break;
            case 0: System.out.println("Sistema encerrado."); break;
            default: System.out.println("Opcao invalida. Tente novamente."); break;
        }
    } while (opcao != 0);
    scanner.close();
}
```

- O `do { ... } while (opcao != 0)` é o **laço** que mostra o menu repetidamente até o usuário digitar `0`.
- O `switch` apenas **chama o método** correspondente a cada opção. Repare como o `main` ficou
  limpo: ele não faz a lógica, só decide quem chamar.
- Os Casos 1 e 2 chamam o **mesmo** método `cadastrarMateriais`, mudando só o conjunto e o texto.
  Isso é **reutilização de código**.

> **Atende ao item (c) Lógica** (switch/decisão) e ao **item (b) Funções** (o `main` delega para métodos).

---

## 4. Cadastro de materiais (linhas 78 a 99)

```java
private static void cadastrarMateriais(Set<String> conjunto, String descricao) {
    int quantidade = lerInteiro("Quantos materiais " + descricao + " deseja cadastrar? ");

    for (int i = 1; i <= quantidade; i++) {
        System.out.print("Material " + i + ": ");
        String material = normalizarMaterial(scanner.nextLine());

        if (material.isEmpty()) {
            System.out.println("Material vazio ignorado.");
        } else if (conjunto.add(material)) {
            System.out.println("Material cadastrado.");
        } else {
            System.out.println("Material duplicado ignorado pelo conjunto.");
        }
    }
    System.out.println("Materiais " + descricao + ": " + conjunto);
}

private static String normalizarMaterial(String material) {
    return material.trim().toLowerCase();
}
```

- O método recebe **dois parâmetros**: o `conjunto` onde salvar e uma `descricao` (texto).
  Por isso serve tanto para "disponíveis" quanto para "necessários".
- `normalizarMaterial` aplica `trim()` (tira espaços das pontas) e `toLowerCase()` (minúsculas),
  para "Aço " e "aço" serem tratados como iguais — isso é **validação/padronização de elementos**.
- `conjunto.add(material)` é a parte esperta: o `Set` **retorna `true`** se adicionou e **`false`**
  se o item já existia. Assim, **duplicados são automaticamente ignorados**.
- O `if/else if/else` ainda barra material vazio.

> **Atende ao item (a) Conjuntos** (eliminação de duplicidades e validação) e ao **item (b) Funções**
> (método reutilizável com entrada e processamento).

---

## 5. Operações com conjuntos (linhas 101 a 136)

```java
private static void verificarMateriais() {
    Set<String> uniao = calcularUniao(materiaisDisponiveis, materiaisNecessarios);
    Set<String> intersecao = calcularIntersecao(materiaisDisponiveis, materiaisNecessarios);
    Set<String> faltantes = calcularDiferenca(materiaisNecessarios, materiaisDisponiveis);
    // ... imprime os três resultados ...
}

private static Set<String> calcularUniao(Set<String> primeiro, Set<String> segundo) {
    Set<String> resultado = new LinkedHashSet<>(primeiro);
    resultado.addAll(segundo);     // une os dois
    return resultado;
}

private static Set<String> calcularIntersecao(Set<String> primeiro, Set<String> segundo) {
    Set<String> resultado = new LinkedHashSet<>(primeiro);
    resultado.retainAll(segundo);  // mantém só o que existe nos dois
    return resultado;
}

private static Set<String> calcularDiferenca(Set<String> primeiro, Set<String> segundo) {
    Set<String> resultado = new LinkedHashSet<>(primeiro);
    resultado.removeAll(segundo);  // remove do primeiro o que existe no segundo
    return resultado;
}
```

Aqui estão as **três operações de conjuntos** que o desafio pede, cada uma em seu método que
**recebe dois conjuntos e retorna um novo conjunto**:

- **União** (`addAll`): todos os materiais, sem repetir.
- **Interseção** (`retainAll`): só os materiais que aparecem nos **dois** (já atendidos).
- **Diferença** (`removeAll`): `necessários − disponíveis` = **materiais faltantes**.

Repare que cada método cria uma **cópia** (`new LinkedHashSet<>(primeiro)`) antes de operar,
para **não estragar** os conjuntos originais.

> **Atende ao item (a) Conjuntos e operações** (união, interseção e diferença) e ao
> **item (b) Funções** (métodos que recebem entrada e retornam resultado).

---

## 6. Cálculo de custo (linhas 138 a 156)

```java
private static void calcularCustoPedido() {
    quantidadePecas = lerInteiro("Quantidade de pecas solicitadas: ");
    custoUnitario = lerDouble("Custo unitario estimado da peca: R$ ");
    orcamentoDisponivel = lerDouble("Orcamento disponivel do cliente: R$ ");
    prazoDias = lerInteiro("Prazo de entrega em dias: ");

    double custoTotal = calcularCustoTotal(quantidadePecas, custoUnitario);
    System.out.printf("Custo total estimado: R$ %.2f%n", custoTotal);

    if (custoTotal <= orcamentoDisponivel) {
        System.out.println("O custo esta dentro do orcamento.");
    } else {
        System.out.println("O custo ultrapassa o orcamento disponivel.");
    }
}

private static double calcularCustoTotal(int quantidade, double custoPorPeca) {
    return quantidade * custoPorPeca;
}
```

- Coleta os dados do pedido e os guarda nos atributos da classe (para usar depois).
- `calcularCustoTotal` é uma **função matemática pura**: entra (quantidade, custo) → processa
  (multiplicação) → **retorna** o custo total.
- `printf` com `%.2f` formata o valor com **duas casas decimais**.
- Um `if/else` informa se está dentro do orçamento.

> **Atende ao item (b) Funções e transformação** (cálculo com retorno) e ao **item (c) Lógica**.

---

## 7. Classificação do pedido (linhas 158 a 179)

```java
private static void classificarPedido() {
    validarDadosDoPedido();

    Set<String> faltantes = calcularDiferenca(materiaisNecessarios, materiaisDisponiveis);
    double custoTotal = calcularCustoTotal(quantidadePecas, custoUnitario);
    int risco = calcularPercentualRisco(quantidadePecas, prazoDias, !faltantes.isEmpty(), custoTotal > orcamentoDisponivel);
    // ...
    if (!faltantes.isEmpty()) {
        System.out.println("Status: PENDENTE - faltam materiais: " + faltantes);
    } else if (custoTotal > orcamentoDisponivel) {
        System.out.println("Status: RECUSADO - custo acima do orcamento.");
    } else if (risco >= 70) {
        System.out.println("Status: APROVACAO COM ALERTA - risco alto de atraso.");
    } else {
        System.out.println("Status: APROVADO - pedido viavel para producao.");
    }
}
```

- Esta é a **decisão por múltiplas condições**. O programa avalia, em ordem de prioridade:
  1. Faltam materiais? → **PENDENTE**.
  2. Custo passou do orçamento? → **RECUSADO**.
  3. Risco ≥ 70%? → **APROVAÇÃO COM ALERTA**.
  4. Nada disso? → **APROVADO**.
- O `else if` garante que só a **primeira** condição verdadeira vale.
- Note que ele **reaproveita** três métodos já criados (`calcularDiferenca`, `calcularCustoTotal`,
  `calcularPercentualRisco`).

> **Atende ao item (c) Lógica matemática aplicada à decisão** (múltiplas condições e classificação).

---

## 8. Simulação de risco com cenários (linhas 181 a 239)

```java
private static void simularRiscoAtraso() {
    validarDadosDoPedido();
    // ...
    for (int ajustePrazo = -1; ajustePrazo <= 1; ajustePrazo++) {
        int prazoSimulado = Math.max(1, prazoDias + ajustePrazo);
        int risco = calcularPercentualRisco(quantidadePecas, prazoSimulado, possuiFaltantes, custoAcima);
        System.out.println("Prazo de " + prazoSimulado + " dia(s): " + risco + "% - " + classificarRisco(risco));
    }
}

private static int calcularPercentualRisco(int quantidade, int prazo, boolean possuiFaltantes, boolean custoAcima) {
    int capacidadeDiaria = 25;
    int diasNecessarios = (int) Math.ceil((double) quantidade / capacidadeDiaria);
    int risco = 10;

    if (prazo < diasNecessarios)       { risco += 45; }
    else if (prazo == diasNecessarios) { risco += 25; }
    else                               { risco += 10; }

    if (quantidade > 100)     { risco += 20; }
    else if (quantidade > 50) { risco += 10; }

    if (possuiFaltantes) { risco += 25; }
    if (custoAcima)      { risco += 15; }

    return Math.min(risco, 95);
}
```

- O método `simularRiscoAtraso` usa um **laço `for`** para **gerar 3 cenários**: prazo um dia a
  menos, o prazo informado e um dia a mais. Isso atende ao "gerar cenários a partir dos dados".
- `calcularPercentualRisco` é o algoritmo de **simulação**: primeiro estima quantos dias seriam
  necessários (`Math.ceil` arredonda a divisão para cima, considerando 25 peças/dia) e depois
  **soma fatores de risco** conforme prazo, quantidade, materiais faltantes e custo.
- `Math.min(risco, 95)` impede que o risco passe de 95%.
- `classificarRisco` traduz o número em **baixo / médio / alto**.

> **Atende ao item (d) Análise combinatória/probabilidade** (cálculo de risco e geração de
> cenários com laço de repetição).

---

## 9. Produção semanal com matriz (linhas 241 a 278)

```java
for (int i = 0; i < setores.length; i++) {
    int totalSetor = 0;
    System.out.printf("%-10s", setores[i]);

    for (int j = 0; j < diasSemana.length; j++) {
        System.out.printf("%8d", producaoSemanal[i][j]);
        totalSetor += producaoSemanal[i][j];
    }

    totalGeral += totalSetor;
    System.out.printf("%8d%n", totalSetor);

    if (totalSetor < menorTotal) {
        menorTotal = totalSetor;
        setorComMenorProducao = setores[i];
    }
}

double mediaGeral = (double) totalGeral / (setores.length * diasSemana.length);
```

- **Dois laços encaixados** percorrem a matriz: o de fora (`i`) anda pelos **setores** (linhas),
  o de dentro (`j`) pelos **dias** (colunas).
- Vai somando `totalSetor`, acumula no `totalGeral` e guarda qual setor produziu menos.
- `printf` com `%-10s` e `%8d` alinha a saída em formato de **tabela**.
- `(double) totalGeral / ...` é um **cast** para a média sair com casas decimais.

> **Atende ao item (e) Vetores e matrizes** (percorre matriz com laços e processa: total, média e mínimo).

---

## 10. Métodos de apoio: validação e leitura segura (linhas 280 a 322)

```java
private static void validarDadosDoPedido() {
    if (quantidadePecas <= 0 || custoUnitario <= 0 || orcamentoDisponivel <= 0 || prazoDias <= 0) {
        System.out.println("Dados do pedido ainda nao cadastrados. Informe os dados agora.");
        // pede os dados novamente
    }
}

private static int lerInteiro(String mensagem) {
    while (true) {
        System.out.print(mensagem);
        String entrada = scanner.nextLine();
        try {
            int valor = Integer.parseInt(entrada);
            if (valor >= 0) { return valor; }
            System.out.println("Digite um numero inteiro maior ou igual a zero.");
        } catch (NumberFormatException erro) {
            System.out.println("Entrada invalida. Digite um numero inteiro.");
        }
    }
}
```

- `validarDadosDoPedido` evita classificar/simular sem ter os dados: se algum estiver zerado,
  o programa pede tudo de novo. O `||` é o operador lógico **OU**.
- `lerInteiro` e `lerDouble` leem com segurança: ficam num `while (true)` até o usuário digitar
  um número válido. O `try/catch` evita que o programa quebre se digitarem letras.
- `lerDouble` ainda troca vírgula por ponto (`replace(",", ".")`), aceitando `10,50` ou `10.50`.

> **Atende ao item (c) Lógica/validação** e reforça os **requisitos técnicos** (entrada robusta).

---

## 11. Mapa rápido: cada item do desafio no código

| Item do desafio | Onde está no código |
| --- | --- |
| (a) Conjuntos e operações | `Set` de materiais + `calcularUniao`, `calcularIntersecao`, `calcularDiferenca`; dedup via `Set.add` |
| (b) Funções e transformação | Métodos como `calcularCustoTotal`, `calcularPercentualRisco`, `cadastrarMateriais` (recebem dados e retornam/processam) |
| (c) Lógica aplicada à decisão | `classificarPedido` (múltiplas condições) e `validarDadosDoPedido` |
| (d) Combinatória/probabilidade | `simularRiscoAtraso` (laço gerando cenários) + `calcularPercentualRisco` |
| (e) Vetores e matrizes | `setores`, `diasSemana` e a matriz `producaoSemanal` em `mostrarProducaoSemanal` |

## 12. Requisitos técnicos mínimos atendidos

- **Entrada pelo usuário:** `Scanner` com leitura validada (`lerInteiro`, `lerDouble`).
- **Saída clara:** títulos, tabela alinhada com `printf` e valores formatados.
- **Organização em métodos:** cada funcionalidade em seu próprio método; `main` só coordena.
- **Decisão e repetição:** `switch`, `if/else if`, `do-while`, `for` e `while`.
- **Coerência matemática:** operações de conjuntos, multiplicação de custo, simulação de risco e média da matriz.

## 13. Dica para replicar do zero

1. Crie a classe `Main` e declare os atributos `static` no topo (conjuntos, vetores e a matriz).
2. Faça os métodos de leitura segura (`lerInteiro`, `lerDouble`) primeiro — você vai usá-los em tudo.
3. Escreva o `main` curto, só com o `do-while` + `switch` chamando métodos.
4. Implemente um método por vez e teste antes de seguir (cadastro → conjuntos → custo → classificação → risco → matriz).
5. Deixe a matriz (`mostrarProducaoSemanal`) por último, pois usa dois laços encaixados.
