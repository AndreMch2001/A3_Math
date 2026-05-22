# Documento Explicativo

## Sistema de Apoio a Producao de Pecas Metalicas

O projeto desenvolvido e uma aplicacao em Java, executada pelo console, que simula um modulo de apoio a decisao para uma empresa metalurgica. O sistema permite cadastrar materiais disponiveis no estoque, informar os materiais necessarios para produzir uma peca, calcular o custo estimado de um pedido, classificar a viabilidade da producao, simular o risco de atraso e analisar a producao semanal por setor.

O objetivo principal e demonstrar como conceitos de Matematica Computacional podem ser aplicados em um problema real de Desenvolvimento de Sistemas. A aplicacao nao busca ser um sistema empresarial completo, mas sim um prototipo funcional, simples e organizado, capaz de mostrar regras matematicas transformadas em algoritmos.

## Aplicacao dos Conceitos Matematicos

O conceito de conjuntos aparece no cadastro de materiais. O programa usa conjuntos para armazenar os materiais disponiveis e os materiais necessarios, evitando duplicidades automaticamente. A partir desses conjuntos, o sistema calcula uniao, intersecao e diferenca. A diferenca e usada para identificar quais materiais estao faltando para iniciar a producao.

As funcoes e regras de transformacao sao representadas pelos metodos criados no codigo. Cada metodo possui uma responsabilidade especifica, como cadastrar materiais, calcular custo, verificar materiais faltantes, classificar o pedido, calcular risco de atraso e exibir a producao semanal. Essa divisao facilita a leitura, evita repeticao de codigo e deixa o sistema mais organizado.

A logica matematica aplicada a decisao aparece na classificacao do pedido. O sistema avalia varias condicoes, como existencia de materiais faltantes, custo acima do orcamento e risco de atraso. Com base nessas condicoes, o pedido pode ser aprovado, recusado, ficar pendente ou ser aprovado com alerta.

A parte de probabilidade e simulacao e trabalhada no calculo do risco de atraso. O sistema estima um percentual de risco considerando quantidade de pecas, prazo disponivel, materiais faltantes e custo acima do orcamento. Alem disso, gera cenarios com prazos diferentes para apoiar a decisao.

Os vetores e matrizes aparecem na analise da producao semanal. O programa usa vetores para armazenar os nomes dos setores e dos dias da semana. A matriz armazena a quantidade de pecas produzidas por setor em cada dia. Com lacos de repeticao, o sistema percorre essa matriz para calcular o total produzido por setor, o total geral, a media diaria e o setor com menor producao.

## Conclusao

O sistema atende aos requisitos da atividade ao integrar conjuntos, metodos, decisoes logicas, simulacao de risco e processamento com vetores e matrizes. A proposta tambem possui aplicacao pratica, pois representa uma situacao comum em uma metalurgica: decidir se um pedido pode ser produzido considerando recursos, custo, prazo e capacidade produtiva.
