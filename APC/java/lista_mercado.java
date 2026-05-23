import java.util.Scanner;

public class lista_mercado {

    public static void main(String[] args){

        Scanner entrada = new Scanner(System.in);

        String nomedocliente;
        int quantidadedeprodutos;

        double preco;
        double total = 0;
        double media;

        System.out.println("Digite o nome do cliente:");
        nomedocliente = entrada.nextLine();

        System.out.println("Quantos produtos deseja comprar?");
        quantidadedeprodutos = entrada.nextInt();

        // LOOP
        for (int i = 1; i <= quantidadedeprodutos; i++) {

            System.out.println("Digite o preço do produto " + i + ":");

            preco = entrada.nextDouble();

            total = total + preco;
        }

        // MÉDIA
        media = total / quantidadedeprodutos;

        // RESUMO
        System.out.println("\n============== RESUMO DA COMPRA ==============");

        System.out.println("Cliente: " + nomedocliente);

        System.out.println("Quantidade de produtos: " + quantidadedeprodutos);

        System.out.println("Valor total: R$ " + total);

        System.out.println("Valor médio dos preços: R$ " + media);

        // DESCONTO
        if (total > 100){

            double desconto = total * 0.10;

            double valorfinal = total - desconto;

            System.out.println("VOCÊ GANHOU UM DESCONTO DE 10%!!!");

            System.out.println("Valor com desconto: R$ " + valorfinal);

        } else {

            System.out.println("Sem desconto disponível");

        }

        entrada.close();

    }
}