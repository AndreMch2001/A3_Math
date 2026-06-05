# Sistema de Apoio a Produção de Peças Metálicas

Projeto A3 de Matemática Computacional desenvolvido em Java (aplicação de console).

## Objetivo

O sistema simula um módulo de apoio à tomada de decisão para uma empresa metalúrgica.
Ele recebe dados sobre materiais, pedido, custo, prazo e produção semanal para ajudar a
decidir se uma peça metálica pode ser produzida.

> Para uma explicação **linha a linha** do código (ideal para quem está aprendendo e quer
> replicar), veja o arquivo [`documento-explicativo.md`](documento-explicativo.md).

## Funcionalidades

- Cadastro de materiais disponíveis em estoque.
- Cadastro de materiais necessários para fabricar uma peça.
- Verificação dos materiais faltantes (diferença entre as duas listas).
- Cálculo de custo total do pedido.
- Classificação do pedido usando regras lógicas.
- Simulação simples de risco de atraso.
- Análise de produção semanal usando matriz.

## Conceitos do desafio atendidos

- **(a) Conjuntos e operações:** listas de materiais (`ArrayList`) e cálculo da diferença
  `necessários − disponíveis` usando `contains`, com normalização por `toLowerCase()`.
- **(b) Funções e regras de transformação:** cálculo do custo (`quantidade * custoUnitario`)
  e reuso da mesma lógica de cadastro para listas diferentes.
- **(c) Lógica matemática aplicada à decisão:** classificação do pedido com `if / else if`
  e operadores lógicos (`!`, `>`, `<`).
- **(d) Probabilidade/simulação:** percentual de risco de atraso somando fatores (50% + 30% + 20%).
- **(e) Vetores e matrizes:** matriz `producaoSemanal` percorrida com laços `for` encaixados
  para somar totais e calcular médias.

## Requisitos técnicos atendidos

- Entrada de dados pelo usuário via console (`Scanner`).
- Saída clara e interpretável (mensagens e títulos no terminal).
- Código organizado por um menu (`switch/case`).
- Estruturas de decisão (`if`, `switch`) e repetição (`do-while`, `for`).

## Estrutura

```text
A3_Math/
  src/
    Main.java
  README.md
  documento-explicativo.md
```

## Como executar

Compile o programa:

```bash
javac src/Main.java
```

Execute:

```bash
java -cp src Main
```

## Menu do sistema

```text
1 - Cadastrar materiais disponiveis
2 - Cadastrar materiais necessarios
3 - Verificar materiais faltantes
4 - Calcular custo do pedido
5 - Classificar pedido
6 - Simular risco de atraso
7 - Mostrar producao semanal
0 - Sair
```

## Sugestão para demonstração

1. Opção 1 — cadastre materiais disponíveis: `aco`, `aluminio`, `tinta`.
2. Opção 2 — cadastre materiais necessários: `aco`, `aluminio`, `parafuso`.
3. Opção 3 — verifique os materiais faltantes (deve aparecer `parafuso`).
4. Opção 4 — informe quantidade, custo unitário e orçamento.
5. Opção 5 — informe o prazo e classifique o pedido.
6. Opção 6 — simule o risco de atraso.
7. Opção 7 — veja a produção semanal (totais e médias por máquina).
0. Opção 0 — encerre o sistema.
