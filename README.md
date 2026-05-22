# Sistema de Apoio a Producao de Pecas Metalicas

Projeto A3 de Matematica Computacional desenvolvido em Java.

## Objetivo

O sistema simula um modulo de apoio a tomada de decisao para uma empresa metalurgica. Ele recebe dados sobre materiais, pedido, custo, prazo e producao semanal para ajudar a decidir se uma peca metalica pode ser produzida.

## Funcionalidades

- Cadastro de materiais disponiveis em estoque.
- Cadastro de materiais necessarios para fabricar uma peca.
- Comparacao entre conjuntos de materiais.
- Calculo de custo total do pedido.
- Classificacao do pedido usando regras logicas.
- Simulacao simples de risco de atraso.
- Analise de producao semanal usando matriz.

## Conceitos do PDF atendidos

- **Conjuntos:** uso de `LinkedHashSet` para uniao, intersecao, diferenca e remocao de duplicados.
- **Funcoes/metodos:** separacao da logica em metodos reutilizaveis.
- **Logica matematica:** uso de condicoes para aprovar, recusar ou marcar pedido como pendente.
- **Probabilidade/simulacao:** calculo percentual de risco de atraso.
- **Vetores e matrizes:** vetor de setores, vetor de dias e matriz de producao semanal.

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

## Sugestao para demonstracao

1. Cadastre materiais disponiveis: `aco`, `aluminio`, `tinta`.
2. Cadastre materiais necessarios: `aco`, `aluminio`, `parafuso`.
3. Verifique os materiais faltantes.
4. Informe quantidade, custo unitario, orcamento e prazo.
5. Classifique o pedido.
6. Simule o risco de atraso.
7. Mostre a producao semanal.
