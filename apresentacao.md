# Roteiro de Apresentação — Sistema de Apoio à Produção de Peças Metálicas

> Projeto A3 de Matemática Computacional (Java, aplicação de console).
> Arquivo principal: `src/Main.java`.

## Como o sistema funciona (visão geral — 1 min)

O programa simula um módulo de apoio à decisão de uma metalúrgica. Pelo menu, o usuário pode:
cadastrar materiais, comparar conjuntos de materiais, calcular o custo do pedido, classificar
o pedido, simular o risco de atraso e analisar a produção semanal.

A estrutura segue boas práticas: o método `main` é curto e só controla o **menu** (laço
`do-while` + `switch`), e **cada funcionalidade fica em seu próprio método**. Isso atende
diretamente ao requisito do PDF de "utilizar funções/métodos para organizar o código".

Estruturas de dados principais (linhas 6 a 22 de `Main.java`):
- Dois conjuntos `LinkedHashSet` para materiais (sem duplicados).
- Vetores `setores` e `diasSemana`.
- Matriz `producaoSemanal` (3 setores x 5 dias).

---

## Ordem sugerida de fala

1. **Vitor** — abre, faz a introdução geral acima + Conjuntos (a) + Funções (b).
2. **André** — Lógica aplicada à decisão (c).
3. **Willian** — Análise combinatória / probabilidade (d).
4. **Jonas** — Vetores e matrizes (e) e encerra com a demonstração ao vivo.

---

## VITOR — Conjuntos e Operações (a) + Funções e Transformação (b)

### Parte 1: Conjuntos e Operações — Critério 1 (8 pts)

**O que mostrar no código:**
- Declaração dos conjuntos (linhas 8–9): uso de `Set`/`LinkedHashSet`.
- `cadastrarMateriais` (linhas 78–95) e `normalizarMaterial` (linhas 97–99).
- `verificarMateriais` (linhas 101–118).
- `calcularUniao`, `calcularIntersecao`, `calcularDiferenca` (linhas 120–136).

**O que falar:**
- "Representamos os dados como **conjuntos** (`Set`), que por natureza **não aceitam
  duplicados**. No cadastro, `conjunto.add(material)` retorna `false` quando o item já existe,
  então duplicidades são ignoradas automaticamente." → atende **C1.2 (eliminação de duplicidades)**.
- "Antes de salvar, `normalizarMaterial` aplica `trim()` e `toLowerCase()`, então 'Aço ' e
  'aço' são tratados como o mesmo elemento." → atende **C1.2 (validação de elementos)**.
- "Implementamos as três operações de conjuntos pedidas: **união** (`addAll`), **interseção**
  (`retainAll`) e **diferença** (`removeAll`). A diferença `necessários − disponíveis` nos dá
  os **materiais faltantes**." → atende **C1.1 (operações de conjunto a partir da entrada)**.

**Frase de ligação para a parte 2:** "Repare que essas operações já são, na prática, **funções**
— o que me leva ao próximo ponto."

### Parte 2: Funções e Regras de Transformação — Critério 2 (8 pts)

**O que mostrar no código:**
- `calcularCustoTotal` (linhas 154–156) — função pura que recebe e retorna valor.
- `calcularCustoPedido` (linhas 138–152) — coleta dados e usa a função acima.
- O `main`/`switch` (linhas 24–63) chamando métodos; Casos 1 e 2 chamando o **mesmo**
  `cadastrarMateriais`.

**O que falar:**
- "Cada responsabilidade está em um método separado, e o `main` apenas coordena. Isso evita
  repetição e organiza o código." → atende **C2.2 (organização e separação de responsabilidades)**.
- "`calcularCustoTotal(quantidade, custoPorPeca)` é o exemplo clássico de **entrada →
  processamento → saída**: recebe dados, faz a regra matemática (multiplicação) e **retorna**
  o resultado." → atende **C2.1 (função que processa e retorna resultado)**.
- "Reaproveitamos código: os Casos 1 e 2 chamam o **mesmo** método `cadastrarMateriais`,
  mudando só o conjunto e o texto." → reforça **C2.2**.

**Mencionar de passagem (infraestrutura):** "Para a entrada ser segura, criamos `lerInteiro` e
`lerDouble` (linhas 290–322), que repetem a pergunta até receber um número válido, usando
`try/catch` para não quebrar o programa."

---

## ANDRÉ — Lógica Matemática Aplicada à Decisão (c) — Critério 3 (8 pts)

**O que mostrar no código:**
- `classificarPedido` (linhas 158–179).
- `validarDadosDoPedido` (linhas 280–288).

**O que falar:**
- "A decisão central está em `classificarPedido`. Ele avalia **múltiplas condições em ordem
  de prioridade** com `if / else if`:"
  1. Faltam materiais? → **PENDENTE**
  2. Custo acima do orçamento? → **RECUSADO**
  3. Risco ≥ 70%? → **APROVAÇÃO COM ALERTA**
  4. Caso contrário → **APROVADO**
  → atende **C3.1 (múltiplas condições para decisão)**.
- "O resultado é uma **classificação** clara do pedido, que é exatamente o que o critério pede."
  → atende **C3.2 (classificar/validar dados com base em regras)**.
- "Em `validarDadosDoPedido` usamos o operador lógico **OU** (`||`): se qualquer dado essencial
  estiver zerado, o sistema pede tudo de novo antes de decidir." → reforça **C3.1/C3.2 (validação)**.
- Destacar os operadores: `!faltantes.isEmpty()` (negação), `custoTotal > orcamentoDisponivel`
  (comparação), `risco >= 70`.

**Observação honesta para os professores:** "Essa classificação **reutiliza** métodos do Vitor
e do Willian (`calcularDiferenca`, `calcularCustoTotal`, `calcularPercentualRisco`), mostrando
como as partes do sistema se integram."

---

## WILLIAN — Análise Combinatória / Probabilidade (d) — Critério 4 (8 pts)

**O que mostrar no código:**
- `simularRiscoAtraso` (linhas 181–200) — o laço que gera cenários.
- `calcularPercentualRisco` (linhas 202–230) — o algoritmo de simulação.
- `classificarRisco` (linhas 232–239).

**O que falar:**
- "Em `calcularPercentualRisco` estimamos um **percentual de risco de atraso**. Primeiro
  calculamos quantos dias seriam necessários: `Math.ceil(quantidade / 25.0)` (consideramos
  capacidade de 25 peças por dia, arredondando para cima). Depois **somamos fatores de risco**
  conforme prazo, quantidade, materiais faltantes e custo. No fim, `Math.min(risco, 95)` limita
  o teto a 95%." → atende **C4.1 (algoritmo que calcula/simula possibilidades)**.
- "Em `simularRiscoAtraso` usamos um **laço `for`** para **gerar 3 cenários**: o prazo informado,
  um dia a menos e um dia a mais. Assim mostramos como o risco muda conforme o prazo, apoiando a
  decisão." → atende **C4.2 (estrutura de repetição para gerar cenários/simulações)**.
- "`classificarRisco` traduz o número em **baixo / médio / alto**, deixando o resultado
  interpretável para o usuário."

**Exemplo numérico para falar:** "Se forem 60 peças com capacidade de 25/dia, precisamos de 3
dias. Se o prazo for menor que isso, o risco já sobe bastante; nosso laço mostra o risco para
o prazo−1, o prazo e o prazo+1."

---

## JONAS — Vetores e Matrizes (e) — Critério 5 (8 pts)

**O que mostrar no código:**
- Declaração de `setores`, `diasSemana` e `producaoSemanal` (linhas 16–22).
- `mostrarProducaoSemanal` (linhas 241–278).

**O que falar:**
- "Organizamos os dados em **vetores** (`setores`, `diasSemana`) e em uma **matriz**
  bidimensional `producaoSemanal`, onde cada linha é um setor e cada coluna é um dia da semana."
  → atende **C5.1 (dados em vetores/matrizes)**.
- "Usamos **dois laços `for` aninhados** para percorrer a matriz: o externo passa pelos setores
  (linhas) e o interno pelos dias (colunas). Enquanto percorremos, somamos o total por setor,
  acumulamos o total geral e guardamos qual setor produziu menos." → atende **C5.2 (laços
  percorrendo e processando os dados)**.
- "Ao final, calculamos a **média diária geral** com um *cast* `(double)` para ter casas
  decimais, e usamos `printf` com larguras fixas (`%-10s`, `%8d`) para imprimir uma **tabela
  alinhada**."

**Encerramento:** "Vou demonstrar o programa rodando."

---

## Demonstração ao vivo (roteiro — Jonas conduz)

1. **Compilar:** `javac src/Main.java`
2. **Executar:** `java -cp src Main`
3. **Opção 1** — cadastrar disponíveis: `aco`, `aluminio`, `tinta` (repita `aco` para mostrar o
   conjunto ignorando o duplicado).
4. **Opção 2** — cadastrar necessários: `aco`, `aluminio`, `parafuso`.
5. **Opção 3** — mostrar união, interseção e diferença (faltante: `parafuso`).
6. **Opção 4** — informar quantidade, custo, orçamento e prazo.
7. **Opção 5** — classificar o pedido (deve dar PENDENTE por causa do parafuso faltante).
8. **Opção 6** — simular o risco em vários prazos.
9. **Opção 7** — mostrar a tabela de produção semanal (total, média, setor crítico).
10. **Opção 0** — encerrar.

---

## Possíveis perguntas dos professores (preparem-se)

- **Por que usaram `Set` e não uma lista?** Porque conjunto não aceita duplicados, o que atende
  diretamente à "eliminação de duplicidades" do PDF e dá as operações de união/interseção/diferença.
- **Por que `LinkedHashSet` e não `HashSet`?** Para manter a ordem de inserção e a saída ficar
  mais legível na tela.
- **O que acontece se o usuário digitar letra em vez de número?** `lerInteiro`/`lerDouble` capturam
  o erro com `try/catch` e pedem de novo, sem quebrar o programa.
- **Por que separar tudo em métodos?** Organização, reutilização e facilidade de manutenção —
  exatamente o Critério 2.
- **Como o risco é calculado?** Estimativa de dias necessários + soma de fatores, com teto de 95%.

---

## Mapa rápido: critério do PDF → quem fala → onde no código

| Critério (pts) | Responsável | Métodos / linhas |
| --- | --- | --- |
| C1 Conjuntos (8) | Vitor | `cadastrarMateriais` (78–95), `calcularUniao/Intersecao/Diferenca` (120–136) |
| C2 Funções (8) | Vitor | `calcularCustoTotal` (154–156), `calcularCustoPedido` (138–152), `main` (24–63) |
| C3 Lógica/decisão (8) | André | `classificarPedido` (158–179), `validarDadosDoPedido` (280–288) |
| C4 Combinatória/probabilidade (8) | Willian | `simularRiscoAtraso` (181–200), `calcularPercentualRisco` (202–230) |
| C5 Vetores e matrizes (8) | Jonas | `setores`/`diasSemana`/`producaoSemanal` (16–22), `mostrarProducaoSemanal` (241–278) |