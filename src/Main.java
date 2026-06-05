import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<String> materiaisDisponiveis = new ArrayList<>();
        ArrayList<String> materiaisNecessarios = new ArrayList<>();
        ArrayList<String> materiaisFaltantes = new ArrayList<>();

        int[][] producaoSemanal = {
                {10, 12, 15, 11, 14},
                {20, 18, 22, 19, 25},
                {8, 7, 9, 10, 6}
        };

        double custoTotal = 0;
        double orcamento = 0;
        int prazo = 0;

        int opcao;

        do {

            System.out.println("\n========== MENU DO SISTEMA ==========");
            System.out.println("1 - Cadastrar materiais disponiveis");
            System.out.println("2 - Cadastrar materiais necessarios");
            System.out.println("3 - Verificar materiais faltantes");
            System.out.println("4 - Calcular custo do pedido");
            System.out.println("5 - Classificar pedido");
            System.out.println("6 - Simular risco de atraso");
            System.out.println("7 - Mostrar producao semanal");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {

                case 1:

                    System.out.print("Quantos materiais deseja cadastrar? ");
                    int qtdDisponiveis = sc.nextInt();
                    sc.nextLine();

                    for (int i = 0; i < qtdDisponiveis; i++) {

                        System.out.print("Digite o material: ");
                        String material = sc.nextLine().toLowerCase();

                        materiaisDisponiveis.add(material);
                    }

                    System.out.println("Materiais cadastrados com sucesso!");
                    break;

                case 2:

                    System.out.print("Quantos materiais sao necessarios? ");
                    int qtdNecessarios = sc.nextInt();
                    sc.nextLine();

                    for (int i = 0; i < qtdNecessarios; i++) {

                        System.out.print("Digite o material necessario: ");
                        String material = sc.nextLine().toLowerCase();

                        materiaisNecessarios.add(material);
                    }

                    System.out.println("Materiais necessarios cadastrados!");
                    break;

                case 3:

                    materiaisFaltantes.clear();

                    for (String material : materiaisNecessarios) {

                        if (!materiaisDisponiveis.contains(material)) {
                            materiaisFaltantes.add(material);
                        }
                    }

                    System.out.println("\n===== RESULTADO =====");

                    if (materiaisFaltantes.isEmpty()) {
                        System.out.println("Todos os materiais estao disponiveis.");
                    } else {

                        System.out.println("Materiais faltantes:");

                        for (String faltante : materiaisFaltantes) {
                            System.out.println("- " + faltante);
                        }
                    }

                    break;

                case 4:

                    System.out.print("Digite a quantidade de pecas: ");
                    int quantidade = sc.nextInt();

                    System.out.print("Digite o custo unitario: ");
                    double custoUnitario = sc.nextDouble();

                    System.out.print("Digite o orcamento disponivel: ");
                    orcamento = sc.nextDouble();

                    custoTotal = quantidade * custoUnitario;

                    System.out.println("\n===== CUSTO TOTAL =====");
                    System.out.println("Custo total do pedido: R$ " + custoTotal);

                    if (custoTotal > orcamento) {
                        System.out.println("O custo ultrapassou o orcamento!");
                    } else {
                        System.out.println("O custo esta dentro do orcamento.");
                    }

                    break;

                case 5:

                    System.out.print("Digite o prazo em dias: ");
                    prazo = sc.nextInt();

                    System.out.println("\n===== CLASSIFICACAO =====");

                    if (!materiaisFaltantes.isEmpty()) {

                        System.out.println("Pedido INVIAVEL.");
                        System.out.println("Motivo: falta de materiais.");

                    } else if (custoTotal > orcamento) {

                        System.out.println("Pedido NAO RECOMENDADO.");
                        System.out.println("Motivo: custo acima do orcamento.");

                    } else if (prazo < 5) {

                        System.out.println("Pedido de ALTO RISCO.");
                        System.out.println("Motivo: prazo muito curto.");

                    } else {

                        System.out.println("Pedido APROVADO.");
                    }

                    break;

                case 6:

                    double risco = 0;

                    if (!materiaisFaltantes.isEmpty()) {
                        risco += 50;
                    }

                    if (prazo < 5) {
                        risco += 30;
                    }

                    if (custoTotal > orcamento) {
                        risco += 20;
                    }

                    System.out.println("\n===== ANALISE DE RISCO =====");
                    System.out.println("Risco calculado: " + risco + "%");

                    if (risco <= 30) {
                        System.out.println("Baixo risco de atraso.");
                    } else if (risco <= 70) {
                        System.out.println("Medio risco de atraso.");
                    } else {
                        System.out.println("Alto risco de atraso.");
                    }

                    break;

                case 7:

                    System.out.println("\n===== PRODUCAO SEMANAL =====");

                    for (int i = 0; i < producaoSemanal.length; i++) {

                        int soma = 0;

                        System.out.println("\nMaquina " + (i + 1));

                        for (int j = 0; j < producaoSemanal[i].length; j++) {

                            System.out.println("Dia " + (j + 1) + ": "
                                    + producaoSemanal[i][j] + " pecas");

                            soma += producaoSemanal[i][j];
                        }

                        double media = (double) soma / producaoSemanal[i].length;

                        System.out.println("Total produzido: " + soma);
                        System.out.println("Media semanal: " + media);
                    }

                    break;

                case 0:

                    System.out.println("Encerrando sistema...");
                    break;

                default:

                    System.out.println("Opcao invalida!");
            }

        } while (opcao != 0);

        sc.close();
    }
}