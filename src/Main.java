import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    // [VITOR] Conjuntos (a) - estruturas de dados sem duplicados (Criterio 1)
    private static final Set<String> materiaisDisponiveis = new LinkedHashSet<>();
    private static final Set<String> materiaisNecessarios = new LinkedHashSet<>();

    // Dados do pedido (usados por ANDRÉ na decisao e por WILLIAN na simulacão)
    private static int quantidadePecas = 0;
    private static int prazoDias = 0;
    private static double custoUnitario = 0.0;
    private static double orcamentoDisponivel = 0.0;

    // [JONAS] Vetores e matriz (e) - estruturas linear e bidimensional (Criterio 5)
    private static final String[] setores = {"Corte", "Solda", "Pintura"};
    private static final String[] diasSemana = {"Segunda", "Terca", "Quarta", "Quinta", "Sexta"};
    private static final int[][] producaoSemanal = {
            {18, 20, 17, 22, 19},
            {14, 16, 15, 18, 17},
            {11, 13, 12, 14, 15}
    };

    // ============================================================================
    // INTRODUCÃO (VITOR abre) - main curto
    // ============================================================================
    public static void main(String[] args) {
        int opcao;

        do {
            mostrarMenu();
            opcao = lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    cadastrarMateriais(materiaisDisponiveis, "disponiveis em estoque");
                    break;
                case 2:
                    cadastrarMateriais(materiaisNecessarios, "necessarios para a peca");
                    break;
                case 3:
                    verificarMateriais();
                    break;
                case 4:
                    calcularCustoPedido();
                    break;
                case 5:
                    classificarPedido();
                    break;
                case 6:
                    simularRiscoAtraso();
                    break;
                case 7:
                    mostrarProducaoSemanal();
                    break;
                case 0:
                    System.out.println("Sistema encerrado.");
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
                    break;
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("=== Sistema de Apoio a Producao de Pecas Metalicas ===");
        System.out.println("1. Cadastrar materiais disponiveis");
        System.out.println("2. Cadastrar materiais necessarios");
        System.out.println("3. Verificar materiais faltantes");
        System.out.println("4. Calcular custo do pedido");
        System.out.println("5. Classificar pedido");
        System.out.println("6. Simular risco de atraso");
        System.out.println("7. Mostrar producao semanal");
        System.out.println("0. Sair");
    }

    // ############################################################################
    // # VITOR - CONJUNTOS E OPERACOES (item a) - CRITERIO 1
    // # Cadastro com remoção de duplicados + união, interseção e diferença.
    // ############################################################################

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

    private static void verificarMateriais() {
        Set<String> uniao = calcularUniao(materiaisDisponiveis, materiaisNecessarios);
        Set<String> intersecao = calcularIntersecao(materiaisDisponiveis, materiaisNecessarios);
        Set<String> faltantes = calcularDiferenca(materiaisNecessarios, materiaisDisponiveis);

        System.out.println();
        System.out.println("Materiais disponiveis: " + materiaisDisponiveis);
        System.out.println("Materiais necessarios: " + materiaisNecessarios);
        System.out.println("Uniao dos conjuntos: " + uniao);
        System.out.println("Intersecao (materiais atendidos): " + intersecao);
        System.out.println("Diferenca (materiais faltantes): " + faltantes);

        if (faltantes.isEmpty()) {
            System.out.println("Todos os materiais necessarios estao disponiveis.");
        } else {
            System.out.println("Existem materiais faltantes para iniciar a producao.");
        }
    }

    // VITOR (a): UNIAO de conjuntos (addAll) - C1.1
    private static Set<String> calcularUniao(Set<String> primeiro, Set<String> segundo) {
        Set<String> resultado = new LinkedHashSet<>(primeiro);
        resultado.addAll(segundo);
        return resultado;
    }

    // VITOR (a): INTERSECAO de conjuntos (retainAll) - C1.1
    private static Set<String> calcularIntersecao(Set<String> primeiro, Set<String> segundo) {
        Set<String> resultado = new LinkedHashSet<>(primeiro);
        resultado.retainAll(segundo);
        return resultado;
    }

    // VITOR (a): DIFERENCA de conjuntos (removeAll) -> materiais faltantes - C1.1
    private static Set<String> calcularDiferenca(Set<String> primeiro, Set<String> segundo) {
        Set<String> resultado = new LinkedHashSet<>(primeiro);
        resultado.removeAll(segundo);
        return resultado;
    }

    // ############################################################################
    // # VITOR - FUNÇÕES E REGRAS DE TRANSFORMAÇÃO (item b) - CRITERIO 2 (8 pts)
    // # Método que recebe dados, processa a regra matemática e retorna resultado.
    // ############################################################################

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

    // VITOR (b): funcao pura entrada -> processamento -> saida (retorno) - C2.1
    private static double calcularCustoTotal(int quantidade, double custoPorPeca) {
        return quantidade * custoPorPeca;
    }

    // ############################################################################
    // # ANDRE - LÓGICA MATEMÁTICA APLICADA A DECISÃO (c) - CRITERIO 3
    // # Múltiplas condições (if/else if) para classificar o pedido.
    // ############################################################################

    private static void classificarPedido() {
        validarDadosDoPedido();

        Set<String> faltantes = calcularDiferenca(materiaisNecessarios, materiaisDisponiveis);
        double custoTotal = calcularCustoTotal(quantidadePecas, custoUnitario);
        int risco = calcularPercentualRisco(quantidadePecas, prazoDias, !faltantes.isEmpty(), custoTotal > orcamentoDisponivel);

        System.out.println();
        System.out.println("=== Classificacao do Pedido ===");
        System.out.printf("Custo total: R$ %.2f%n", custoTotal);
        System.out.println("Risco de atraso: " + risco + "% - " + classificarRisco(risco));

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

    // ############################################################################
    // # WILLIAN - ANÁLISE COMBINATÓRIA / PROBABILIDADE (d) - CRITERIO 4
    // # Laço gera cenários de prazo + algoritmo que calcula o risco de atraso.
    // ############################################################################

    private static void simularRiscoAtraso() {
        validarDadosDoPedido();

        Set<String> faltantes = calcularDiferenca(materiaisNecessarios, materiaisDisponiveis);
        double custoTotal = calcularCustoTotal(quantidadePecas, custoUnitario);
        boolean possuiFaltantes = !faltantes.isEmpty();
        boolean custoAcima = custoTotal > orcamentoDisponivel;

        System.out.println();
        System.out.println("=== Simulacao de Risco de Atraso ===");
        System.out.println("Capacidade estimada: 25 pecas por dia.");

        for (int ajustePrazo = -1; ajustePrazo <= 1; ajustePrazo++) {
            int prazoSimulado = Math.max(1, prazoDias + ajustePrazo);
            int risco = calcularPercentualRisco(quantidadePecas, prazoSimulado, possuiFaltantes, custoAcima);
            System.out.println("Prazo de " + prazoSimulado + " dia(s): " + risco + "% - " + classificarRisco(risco));
        }

        System.out.println("Cenarios gerados com repeticao para apoiar a decisao do pedido.");
    }

    // WILLIAN (d): algoritmo que calcula/simula o percentual de risco - C4.1
    private static int calcularPercentualRisco(int quantidade, int prazo, boolean possuiFaltantes, boolean custoAcima) {
        int capacidadeDiaria = 25;
        int diasNecessarios = (int) Math.ceil((double) quantidade / capacidadeDiaria);
        int risco = 10;

        if (prazo < diasNecessarios) {
            risco += 45;
        } else if (prazo == diasNecessarios) {
            risco += 25;
        } else {
            risco += 10;
        }

        if (quantidade > 100) {
            risco += 20;
        } else if (quantidade > 50) {
            risco += 10;
        }

        if (possuiFaltantes) {
            risco += 25;
        }

        if (custoAcima) {
            risco += 15;
        }

        return Math.min(risco, 95);
    }

    // WILLIAN (d): traduz o percentual em baixo/medio/alto
    private static String classificarRisco(int risco) {
        if (risco >= 70) {
            return "alto";
        } else if (risco >= 40) {
            return "medio";
        }
        return "baixo";
    }

    // ############################################################################
    // # JONAS - VETORES E MATRIZES (e) - CRITERIO 5
    // # Dois laços for aninhados percorrem a matriz: total, média e setor crítico.
    // ############################################################################

    private static void mostrarProducaoSemanal() {
        System.out.println();
        System.out.println("=== Producao Semanal por Setor ===");
        System.out.print("Setor      ");

        for (String dia : diasSemana) {
            System.out.printf("%8s", dia);
        }

        System.out.println("   Total");

        int totalGeral = 0;
        int menorTotal = Integer.MAX_VALUE;
        String setorComMenorProducao = "";

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
        System.out.println("Total geral produzido: " + totalGeral + " pecas");
        System.out.printf("Media diaria geral: %.2f pecas%n", mediaGeral);
        System.out.println("Setor com menor producao: " + setorComMenorProducao + " (" + menorTotal + " pecas)");
    }

    // ############################################################################
    // # MÉTODOS DE APOIO (infraestrutura usada por todos)
    // # ANDRE pode citar a validação (C3) e VITOR a leitura segura de dados.
    // ############################################################################

    // ANDRE (c): validação com operador lógico OU (||) antes de decidir
    private static void validarDadosDoPedido() {
        if (quantidadePecas <= 0 || custoUnitario <= 0 || orcamentoDisponivel <= 0 || prazoDias <= 0) {
            System.out.println("Dados do pedido ainda nao cadastrados. Informe os dados agora.");
            quantidadePecas = lerInteiro("Quantidade de pecas solicitadas: ");
            custoUnitario = lerDouble("Custo unitario estimado da peca: R$ ");
            orcamentoDisponivel = lerDouble("Orcamento disponivel do cliente: R$ ");
            prazoDias = lerInteiro("Prazo de entrega em dias: ");
        }
    }

    // VITOR: leitura segura de inteiro (repete até ser válido; try/catch não quebra)
    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();

            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= 0) {
                    return valor;
                }
                System.out.println("Digite um numero inteiro maior ou igual a zero.");
            } catch (NumberFormatException erro) {
                System.out.println("Entrada invalida. Digite um numero inteiro.");
            }
        }
    }

    // VITOR: leitura segura de decimal (aceita vírgula ou ponto)
    private static double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().replace(",", ".");

            try {
                double valor = Double.parseDouble(entrada);
                if (valor >= 0) {
                    return valor;
                }
                System.out.println("Digite um numero maior ou igual a zero.");
            } catch (NumberFormatException erro) {
                System.out.println("Entrada invalida. Digite um numero decimal.");
            }
        }
    }
}