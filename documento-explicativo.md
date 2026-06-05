# Documento Explicativo

## Sistema de Apoio a Produção de Peças Metálicas

Este documento explica **todo o código** do arquivo `src/Main.java`, parte por parte,
de forma que mesmo quem está começando em programação consiga **entender e replicar**.
Ao final de cada parte há a indicação de **qual item do desafio** (a, b, c, d, e) aquele
trecho atende.

A aplicação é um programa Java de **console** (terminal) que simula um módulo de apoio à
decisão de uma metalúrgica: cadastra materiais, descobre o que está faltando, calcula
custo, classifica o pedido, simula risco de atraso e analisa a produção semanal.

> Observação importante para quem vai estudar o código: tudo acontece dentro do método
> `main`, controlado por um **menu** em laço. Não há métodos separados nesta versão; a
> organização é feita pelos `case` do `switch`. Isso é proposital para manter o código
> simples e fácil de seguir de cima para baixo.

---

## 1. Importações (linhas 1 a 4)

```java
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
```

- `import` serve para "trazer" classes prontas do Java para o nosso programa.
- `ArrayList` é uma **lista** que cresce conforme adicionamos itens (usamos para guardar materiais).
- `Scanner` é o que permite **ler o que o usuário digita** no teclado.
- `HashSet` e `Set` são estruturas de **conjuntos**. Eles estão importados como base teórica
  do trabalho; nesta versão o conjunto de "faltantes" é montado manualmente com `ArrayList`
  + verificação `contains` (explicado no Caso 3).

---

## 2. Início do programa e variáveis (linhas 6 a 26)

```java
public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<String> materiaisDisponiveis = new ArrayList<>();
        ArrayList<String> materiaisNecessarios = new ArrayList<>();
        ArrayList<String> materiaisFaltantes = new ArrayList<>();
```

- `public class Main` é a "caixa" que contém o programa. Em Java, todo código fica dentro de uma classe.
- `public static void main(String[] args)` é o **ponto de partida**: quando o programa roda, ele começa aqui.
- `Scanner sc = new Scanner(System.in);` cria o leitor de teclado, chamado `sc`.
- As três `ArrayList<String>` são listas de texto:
  - `materiaisDisponiveis`: o que existe no estoque.
  - `materiaisNecessarios`: o que o pedido exige.
  - `materiaisFaltantes`: o que falta (calculado depois).

```java
        int[][] producaoSemanal = {
                {10, 12, 15, 11, 14},
                {20, 18, 22, 19, 25},
                {8, 7, 9, 10, 6}
        };
```

- `int[][]` é uma **matriz** (tabela de números) com 3 linhas e 5 colunas.
- Cada **linha** representa uma máquina; cada **coluna** representa um dia da semana.
- Exemplo: `producaoSemanal[0][2]` vale `15` (máquina 1, dia 3).

```java
        double custoTotal = 0;
        double orcamento = 0;
        int prazo = 0;

        int opcao;
```

- `double` guarda números com vírgula (dinheiro). `int` guarda números inteiros.
- Essas variáveis são declaradas **fora** do menu para "lembrar" os valores entre as opções
  (ex.: o custo calculado no Caso 4 é usado na classificação do Caso 5).

> **Atende ao item (e) Vetores e matrizes**: `producaoSemanal` é a estrutura bidimensional.
> Também prepara o item (a) Conjuntos, criando as listas de materiais.

---

## 3. O menu em laço (linhas 28 a 42)

```java
        do {
            System.out.println("\n========== MENU DO SISTEMA ==========");
            System.out.println("1 - Cadastrar materiais disponiveis");
            // ... demais opções ...
            System.out.print("Escolha uma opcao: ");

            opcao = sc.nextInt();
            sc.nextLine();
```

- `do { ... } while (opcao != 0);` é um **laço de repetição**: mostra o menu de novo e de novo,
  até o usuário digitar `0` para sair.
- `System.out.println` escreve uma linha na tela; `\n` cria uma linha em branco.
- `opcao = sc.nextInt();` lê o número que o usuário escolheu.
- `sc.nextLine();` logo depois "limpa" o resto da linha. Isso evita um bug clássico do Java
  quando se mistura `nextInt()` com `nextLine()`.

> **Atende ao item (c) Lógica e ao Requisito Técnico de repetição**: o menu usa laço e leitura de dados.

---

## 4. O `switch` (escolha da opção) — linha 44

```java
switch (opcao) {
    case 1: ...
    case 2: ...
    ...
    default: ...
}
```

- O `switch` olha o valor de `opcao` e **desvia** para o `case` correspondente.
- `break;` encerra aquele `case` (senão o programa continuaria executando os de baixo).
- `default:` é o que acontece quando o número não existe no menu.

Abaixo, cada `case` em detalhe.

---

### Caso 1 — Cadastrar materiais disponíveis (linhas 46 a 61)

```java
System.out.print("Quantos materiais deseja cadastrar? ");
int qtdDisponiveis = sc.nextInt();
sc.nextLine();

for (int i = 0; i < qtdDisponiveis; i++) {
    System.out.print("Digite o material: ");
    String material = sc.nextLine().toLowerCase();
    materiaisDisponiveis.add(material);
}
```

- Pergunta **quantos** materiais serão cadastrados e guarda em `qtdDisponiveis`.
- O `for` repete a leitura exatamente essa quantidade de vezes.
- `.toLowerCase()` transforma tudo em minúsculas, para que "Aço" e "aço" sejam tratados igual.
- `.add(material)` coloca o texto digitado dentro da lista.

> **Atende ao item (a) Conjuntos** (representação de dados) e **item (b) Funções** (entrada → processamento → guardar).

---

### Caso 2 — Cadastrar materiais necessários (linhas 63 a 78)

É **igual ao Caso 1**, mas grava na lista `materiaisNecessarios`. Repare como a mesma
estrutura (`for` + `add`) é reaproveitada para outro objetivo.

> **Atende ao item (a) Conjuntos** e **item (b) Funções/reutilização de lógica**.

---

### Caso 3 — Verificar materiais faltantes (linhas 80 a 104)

```java
materiaisFaltantes.clear();

for (String material : materiaisNecessarios) {
    if (!materiaisDisponiveis.contains(material)) {
        materiaisFaltantes.add(material);
    }
}
```

- `clear()` esvazia a lista de faltantes antes de recalcular (evita resultado acumulado).
- O `for (String material : materiaisNecessarios)` percorre **cada** material necessário.
- `if (!materiaisDisponiveis.contains(material))` significa: "**se NÃO existe** no estoque".
  O `!` é o operador lógico de negação.
- Se não existe, é adicionado aos faltantes.

Isto é exatamente a **diferença de conjuntos**: `necessários − disponíveis = faltantes`.

```java
if (materiaisFaltantes.isEmpty()) {
    System.out.println("Todos os materiais estao disponiveis.");
} else {
    System.out.println("Materiais faltantes:");
    for (String faltante : materiaisFaltantes) {
        System.out.println("- " + faltante);
    }
}
```

- `isEmpty()` testa se a lista está vazia. Se estiver, está tudo disponível.
- Senão, um `for` imprime cada item que falta.

> **Atende ao item (a) Conjuntos e operações** (diferença, validação de elementos) e ao
> **item (c) Lógica** (condição com `!` e `if/else`).

---

### Caso 4 — Calcular custo do pedido (linhas 106 a 128)

```java
int quantidade = sc.nextInt();
double custoUnitario = sc.nextDouble();
orcamento = sc.nextDouble();

custoTotal = quantidade * custoUnitario;
```

- Lê a quantidade de peças, o custo de cada uma e o orçamento disponível.
- `custoTotal = quantidade * custoUnitario;` é a **função/transformação matemática**:
  entra (quantidade, custo unitário) → processa (multiplicação) → sai (custo total).

```java
if (custoTotal > orcamento) {
    System.out.println("O custo ultrapassou o orcamento!");
} else {
    System.out.println("O custo esta dentro do orcamento.");
}
```

- Compara o custo com o orçamento e dá uma resposta clara.

> **Atende ao item (b) Funções e regras de transformação** (cálculo) e **item (c) Lógica/decisão**.

---

### Caso 5 — Classificar pedido (linhas 130 a 157)

```java
prazo = sc.nextInt();

if (!materiaisFaltantes.isEmpty()) {
    System.out.println("Pedido INVIAVEL.");
} else if (custoTotal > orcamento) {
    System.out.println("Pedido NAO RECOMENDADO.");
} else if (prazo < 5) {
    System.out.println("Pedido de ALTO RISCO.");
} else {
    System.out.println("Pedido APROVADO.");
}
```

- Aqui está o coração da **decisão por múltiplas condições**. O programa testa, **em ordem**:
  1. Falta material? → **INVIÁVEL**.
  2. Custo passou do orçamento? → **NÃO RECOMENDADO**.
  3. Prazo menor que 5 dias? → **ALTO RISCO**.
  4. Nada disso? → **APROVADO**.
- O `else if` faz com que apenas a **primeira** condição verdadeira seja escolhida (prioridade).

> **Atende ao item (c) Lógica matemática aplicada à decisão** (regras de negócio com várias
> condições e classificação de dados).

---

### Caso 6 — Simular risco de atraso (linhas 159 a 186)

```java
double risco = 0;

if (!materiaisFaltantes.isEmpty()) { risco += 50; }
if (prazo < 5)                     { risco += 30; }
if (custoTotal > orcamento)        { risco += 20; }
```

- Começa com risco `0` e **soma pontos** conforme cada problema aparece.
- Diferente do Caso 5 (que escolhe só uma), aqui **todas** as condições podem somar juntas.
  Por isso o risco máximo é `50 + 30 + 20 = 100%`.

```java
if (risco <= 30) {
    System.out.println("Baixo risco de atraso.");
} else if (risco <= 70) {
    System.out.println("Medio risco de atraso.");
} else {
    System.out.println("Alto risco de atraso.");
}
```

- Transforma o número em uma classificação fácil de entender (baixo / médio / alto).

> **Atende ao item (d) Probabilidade/simulação** (estima um percentual de risco a partir dos
> dados) e reforça o **item (c) Lógica**.

---

### Caso 7 — Mostrar produção semanal (linhas 188 a 212)

```java
for (int i = 0; i < producaoSemanal.length; i++) {
    int soma = 0;
    System.out.println("\nMaquina " + (i + 1));

    for (int j = 0; j < producaoSemanal[i].length; j++) {
        System.out.println("Dia " + (j + 1) + ": " + producaoSemanal[i][j] + " pecas");
        soma += producaoSemanal[i][j];
    }

    double media = (double) soma / producaoSemanal[i].length;
    System.out.println("Total produzido: " + soma);
    System.out.println("Media semanal: " + media);
}
```

- São **dois laços encaixados** (um dentro do outro) para percorrer a matriz:
  - O `for` de fora (`i`) anda pelas **linhas** (máquinas).
  - O `for` de dentro (`j`) anda pelas **colunas** (dias).
- `producaoSemanal.length` é o número de linhas; `producaoSemanal[i].length` é o número de colunas.
- `soma += producaoSemanal[i][j];` acumula o total de peças daquela máquina.
- `(double) soma / ...` faz um **cast**: força a divisão a dar resultado com vírgula (média correta).

> **Atende ao item (e) Vetores e matrizes** (percorrer matriz com laços, somar e calcular média).

---

### Caso 0 e default (linhas 214 a 222)

```java
case 0:
    System.out.println("Encerrando sistema...");
    break;
default:
    System.out.println("Opcao invalida!");
```

- `case 0` mostra a mensagem de saída (o laço termina logo depois).
- `default` avisa quando o usuário digita um número que não existe no menu.

---

## 5. Fim do programa (linhas 224 a 228)

```java
        } while (opcao != 0);
        sc.close();
    }
}
```

- `while (opcao != 0)` faz o menu repetir enquanto a opção for diferente de `0`.
- `sc.close();` fecha o leitor de teclado, liberando o recurso quando o programa acaba.

---

## 6. Mapa rápido: cada item do desafio no código

| Item do desafio | Onde está no código |
| --- | --- |
| (a) Conjuntos e operações | Listas de materiais (Casos 1 e 2) e diferença `necessários − disponíveis` (Caso 3) |
| (b) Funções e transformação | Cálculo `custoTotal = quantidade * custoUnitario` (Caso 4); reuso da mesma lógica de cadastro |
| (c) Lógica aplicada à decisão | `if/else if` da classificação (Caso 5) e condições com `!`, `>`, `<` |
| (d) Combinatória/probabilidade | Soma de percentuais de risco (Caso 6) |
| (e) Vetores e matrizes | Matriz `producaoSemanal` e laços encaixados (Caso 7) |

## 7. Requisitos técnicos mínimos atendidos

- **Entrada pelo usuário:** `Scanner` (`nextInt`, `nextDouble`, `nextLine`).
- **Saída clara:** mensagens com `System.out.println` e títulos como `===== RESULTADO =====`.
- **Organização do código:** menu em `switch/case` separando cada funcionalidade.
- **Estruturas de decisão e repetição:** `if/else`, `switch`, `do-while` e `for`.
- **Coerência matemática:** diferença de conjuntos, multiplicação de custo, soma de risco, média da matriz.

## 8. Dica para replicar do zero

1. Crie a classe `Main` com o método `main`.
2. Declare o `Scanner` e as listas/variáveis no topo.
3. Monte o `do-while` com o menu e o `switch`.
4. Implemente um `case` de cada vez, testando antes de seguir para o próximo.
5. Deixe o cálculo da matriz (Caso 7) por último, pois usa dois laços encaixados.
