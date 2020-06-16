import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LerArquivo {

    public static void lerArquivo(String nomeArq) {
        BufferedReader entrada = null;
        String dados;
        String tipoDados;
        String nome, nivel, posicao, nomeJogo;
        int contDados = 0;

        try {
            entrada = new BufferedReader(new FileReader(nomeArq));
        } catch (IOException e) {
            System.out.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        try {
            dados = entrada.readLine();

            while (dados != null) {
                tipoDados = dados.substring(0, 2);

                if (tipoDados.equals("00")) {
                    System.out.println("Header");
                    System.out.println("Modelo de dados: " + dados.substring(2, 6));
                   // int data = Integer.parseInt(dados.substring(2, 21));
                    //System.out.println("Data e hora da geração do arquivo: " + dados.substring(11, 30));
                } else if (tipoDados.equals("01")) {
                    System.out.println("\nTrailer");
                    int quantidadeDeDados = Integer.parseInt(dados.substring(2, 12));
                    if (quantidadeDeDados == contDados) {
                        System.out.println("Quantidade de dados gravados compátivel com a quantidade lida");
                    } else {
                        System.out.println("Quantidade de dados gravados não estão compátiveis com a quantidade lida");
                    }
                }
                    else if (tipoDados.equals("02")){
                        if (contDados == 0){
                            System.out.println();
                            System.out.printf("%-25s %-15s %-15s %-25s\n", "nome", "nivel", "posicao", "nomeJogo");
                        }

                        nome = dados.substring(2, 27);
                        nivel = dados.substring(27, 42);
                        posicao = dados.substring(42, 57);
                        nomeJogo = dados.substring(57, 82);

                    System.out.printf("%-25s %-15s %-15s %-25s\n", nome, nivel, posicao, nomeJogo);
                    contDados++;
                }
                    else {
                    System.out.println("Modelo de dados inválido");
                }

                    dados = entrada.readLine();
            }

            entrada.close();
        }catch (IOException e){
            System.err.printf("Erroa ao ler os dados: %s.\n", e.getMessage());
        }
        }

    public static void main(String[] args) {
        String nomeArq = "Usuário.txt";
        lerArquivo(nomeArq);
    }
    }