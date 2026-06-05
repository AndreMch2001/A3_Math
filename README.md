# Sistema de Apoio a Produção de Peças Metálicas

Projeto A3 de Matemática Computacional desenvolvido em Java (aplicação de console).

## Objetivo

O sistema simula um módulo de apoio à tomada de decisão para uma empresa metalúrgica.
Ele recebe dados sobre materiais, pedido, custo, prazo e produção semanal para ajudar a
decidir se uma peça metálica pode ser produzida.

> Para uma explicação **parte por parte** do código (ideal para quem está aprendendo e quer
> replicar), veja o arquivo [`documento-explicativo.md`](documento-explicativo.md).

## Funcionalidades

- Cadastro de materiais disponíveis em estoque (conjunto sem duplicados).
- Cadastro de materiais necessários para fabricar uma peça.
- Operações de conjuntos: união, interseção e diferença (materiais faltantes).
- Cálculo de custo total do pedido com verificação de orçamento.
- Classificação do pedido usando regras lógicas (PENDENTE, RECUSADO, ALERTA, APROVADO).
- Simulação de risco de atraso gerando vários cenários de prazo.
- Análise de produção semanal usando vetores e matriz, com total, média e setor crítico.

## Conceitos do desafio atendidos

- **(a) Conjuntos e operações:** dois `LinkedHashSet` de materiais; métodos `calcularUniao`,
  `calcularIntersecao` e `calcularDiferenca`; duplicados eliminados automaticamente pelo `Set`
  e normalização com `trim()` + `toLowerCase()`.
- **(b) Funções e regras de transformação:** lógica separada em métodos que recebem dados e
  retornam resultados (`calcularCustoTotal`, `calcularPercentualRisco`, `cadastrarMateriais`).
- **(c) Lógica matemática aplicada à decisão:** `classificarPedido` avalia múltiplas condições
  e `validarDadosDoPedido` valida a entrada com operadores lógicos.
- **(d) Probabilidade/simulação:** `simularRiscoAtraso` usa um laço para gerar cenários de prazo
  e `calcularPercentualRisco` estima o risco com base na capacidade diária e em fatores de risco.
- **(e) Vetores e matrizes:** vetores `setores` e `diasSemana` e a matriz `producaoSemanal`,
  percorridos com laços `for` aninhados.

## Requisitos técnicos atendidos

- Entrada de dados pelo usuário via console, com leitura validada (`lerInteiro`, `lerDouble`).
- Saída clara e interpretável (títulos, tabela alinhada com `printf`, valores formatados).
- Código organizado em **métodos**, separando responsabilidades e evitando repetição.
- Estruturas de decisão (`if/else if`, `switch`) e repetição (`do-while`, `for`, `while`).

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
1. Cadastrar materiais disponiveis
2. Cadastrar materiais necessarios
3. Verificar materiais faltantes
4. Calcular custo do pedido
5. Classificar pedido
6. Simular risco de atraso
7. Mostrar producao semanal
0. Sair
```

## Sugestão para demonstração

1. Opção 1 — cadastre materiais disponíveis: `aco`, `aluminio`, `tinta` (tente repetir um para ver o conjunto ignorar o duplicado).
2. Opção 2 — cadastre materiais necessários: `aco`, `aluminio`, `parafuso`.
3. Opção 3 — veja união, interseção e diferença (faltante: `parafuso`).
4. Opção 4 — informe quantidade, custo unitário, orçamento e prazo.
5. Opção 5 — classifique o pedido.
6. Opção 6 — simule o risco de atraso em diferentes prazos.
7. Opção 7 — veja a produção semanal (totais, média geral e setor com menor produção).
0. Opção 0 — encerre o sistema.
