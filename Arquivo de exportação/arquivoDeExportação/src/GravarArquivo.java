import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GravarArquivo {

        public static void gravarRegistro(String nomeArq, String registro){
            BufferedWriter saida = null;
            try {
                saida = new BufferedWriter(new FileWriter(nomeArq, true));
            } catch (IOException e){
                System.out.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
            }
            try {
                saida.append(registro + "\n");
                saida.close();
            } catch (IOException e){
                System.out.printf("Erro ao gravar arquivo: %s.\n", e.getMessage());
            }
        }

    public static void main(String[] args) {

            String nomeArq = "Usuário.txt";
            String header = "";
            String corpo = "";
            String trailer = "";
            int contadorDeDados = 0;

        Date dataAtual = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");

        header += "00";
        header += formatter.format(dataAtual);
        header += "01";

        gravarRegistro(nomeArq, header);

        corpo += "02";
        corpo += String.format("%-25s", "Vinicius de Oliveira");
        corpo += String.format("%-15s", "Atacante");
        corpo += String.format("%-18s","EA SPORTS FIFA");

        contadorDeDados++;

        gravarRegistro(nomeArq, corpo);


        corpo = "02";
        corpo += String.format("%-25s", "Alex Barreira");
        corpo += String.format("%-15s", "Zagueiro");
        corpo += String.format("%-18s","EA SPORTS FIFA");

        contadorDeDados++;

        gravarRegistro(nomeArq, corpo);

        trailer += "01";
        trailer += String.format("%010d", contadorDeDados);
        gravarRegistro(nomeArq, trailer);
    }
    }



