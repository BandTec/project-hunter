

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.FileHandler;

public class GravarDados {


    public static void gravaLista(ListaObj<Gamer>lista, boolean isCSV) {
        FileWriter arq =null;
        Formatter saida= null;
        boolean erroNoSistema = false;
        String ArquivoNome;

        if (isCSV){
            ArquivoNome = "Gamer.csv";
        } else {
            ArquivoNome = "Gamer.txt";
        }

        try{
            arq = new FileWriter(ArquivoNome, true);
            saida = new Formatter(arq);
        }
        catch (IOException erro){
            System.err.println("Erro na abertura do arquivo");
            System.exit(1);
        }
        try {
            for (int i=0; i < lista.getTamanho(); i++){
                Gamer g = lista.getElemento(i);

                if (isCSV){
                    saida.format("%s;%s;%s;%s%n", g.getApelido(),
                            g.getNome(), g.getEmail(), g.getJogo());
                } else{
                    saida.format("%s %s %s %s %n", g.getApelido(),
                            g.getNome(), g.getEmail(),  g.getJogo());
                }
            }
        }
        catch (FormatterClosedException erro){
            System.err.println("Erro na gravação do arquivo");
            erroNoSistema =true;
        }
        finally {
            saida.close();
            try {
                arq.close();
            }
            catch (IOException erro){
                System.err.println("Falha ao fechar o arquivo");
                erroNoSistema = true;
            }
            if (erroNoSistema){
                System.exit(1);
            }
        }
    }


    public static void leEexibeArquivo(boolean isCSV) {
        FileReader arq =null;
        Scanner entrada= null;
        String ArquivoNome;
        boolean erroNoSistema = false;


        if (isCSV){
            ArquivoNome = "Gamer.csv";
        } else {
            ArquivoNome = "Gamer.txt";
        }
        try {
            arq = new FileReader(ArquivoNome);
            if (isCSV) {
                // se o arquivo for CSV, usa como delimitador de campo o ';' e o fim de registro
                entrada = new Scanner(arq).useDelimiter(";|\\r\\n");
            }
            else {
                // se o arquivo for TXT, usa como delimitador de campo o ' ' e o fim de registro
                entrada = new Scanner(arq);
            }
        }
        catch (FileNotFoundException erro){
            System.err.println("Arquivo não encotrado");
            System.exit(1);
        }

        try {
            System.out.printf("%-50s %-20s %-50s %20s\n","NOME","APELIDO","EMAIL","JOGO");

            while (entrada.hasNext()){
                String nome = entrada.next();
                String apelido = entrada.next();
                String email = entrada.next();
                String jogo = entrada.next();

                System.out.printf("%-50s %-20s %-50s %20s\n", nome, apelido, email, jogo);
            }
        }
        catch (NoSuchElementException erro){
            System.err.println("Arquivo com problemas");
            erroNoSistema = true;
        }
        catch (IllegalStateException erro){
            System.err.println("Erro na leitura do arquivo");
        }
        finally {
            entrada.close();
            try {
                arq.close();
            }
            catch (IOException erro){
                System.err.println("Erro ao encerrar o arquivo");
            }
            if (erroNoSistema){
                System.exit(1);
            }
        }
    }

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        boolean fim = false;
        String nome;
        String apelido;
        String email;
        String jogo;
        int selecao;

        ListaObj<Gamer> lista = new ListaObj(5);

        while (!fim) {
            System.out.println("\nDefina umas das opções :");
            System.out.println("1- Cadastrar um Gamer");
            System.out.println("2- Exibir cadastros");
            System.out.println("3- Gravar o cadastro em um arquivo txt");
            System.out.println("4- Gravar o cadastro em um arquivo csv");
            System.out.println("5- Ler e exibir arquivo txt");
            System.out.println("6- Ler e exibir arquivo csv");
            System.out.println("7- Fim");

            selecao = leitor.nextInt();

            switch (selecao) {
                case 1:

                    System.out.println("Digite seu nome");
                    nome = leitor.next();

                    System.out.println("Digite seu apelido");
                    apelido = leitor.next();

                    System.out.println("Digite seu email");
                    email = leitor.next();

                    System.out.println("Digite seu jogo");
                    jogo = leitor.next();

                    Gamer gamer = new Gamer(nome, email, jogo, apelido);

                    lista.adiciona(gamer);
                    break;


                case 2:
                    if (lista.getTamanho() == 0) {
                        System.out.println("Lista vazia");
                    } else {
                        lista.exibe();
                    }
                    break;


                case 3:
                    if (lista.getTamanho() == 0) {
                        System.out.println("Lista vazia. Não há o que gravar");
                    } else {
                        gravaLista(lista, false);
                        lista.limpa();
                    }
                    break;


                case 4:
                    if (lista.getTamanho() == 0) {
                        System.out.println("Lista vazia. Não há o que gravar");
                    } else {
                        gravaLista(lista, true);
                        lista.limpa();
                    }
                    break;



                case 5:
                    leEexibeArquivo(false);
                    break;



                case 6:
                    leEexibeArquivo(true);
                    break;




                case 7:
                    fim = true;
                    break;


                default:
                    System.out.println("Defina uma opção válida!");
                    break;
            }
        }
    }}
