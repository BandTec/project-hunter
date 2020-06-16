package br.com.hunter.Controladores;

import br.com.hunter.Modelos.EquipeGamer;
import br.com.hunter.Modelos.GamerInfo;
import br.com.hunter.Repositorios.EquipeGamerRepository;
import br.com.hunter.Repositorios.GamerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/arquivo")
@CrossOrigin(origins = "http://localhost:3000")
public class ArquivoController {

    @Autowired
    private GamerInfoRepository repository;

    @Autowired
    private EquipeGamerRepository equipeGamerRepository;
    // criar arquivo
    public static void gravarRegistro(String registro) {
        BufferedWriter saida = null;

        try {
            saida = new BufferedWriter(new FileWriter("Usuario.txt", true));
        } catch (IOException var5) {
            System.out.printf("Erro na abertura do arquivo: %s.\n", var5.getMessage());
        }

        try {
            saida.append(registro + "\n");
            saida.close();
        } catch (IOException var4) {
            System.out.printf("Erro ao gravar arquivo: %s.\n", var4.getMessage());
        }

    }

    // ler arquivo
    public static void lerArquivo(String nomeArq) {
        BufferedReader entrada = null;
        int contDados = 0;

        try {
            entrada = new BufferedReader(new FileReader(nomeArq));
        } catch (IOException var10) {
            System.out.printf("Erro na abertura do arquivo: %s.\n", var10.getMessage());
        }

        try {
            for(String dados = entrada.readLine(); dados != null; dados = entrada.readLine()) {
                String tipoDados = dados.substring(0, 2);
                if (tipoDados.equals("00")) {
                    System.out.println("Header");
                    System.out.println("Modelo de dados: " + dados.substring(2, 6));
                } else if (tipoDados.equals("01")) {
                    System.out.println("\nTrailer");
                    int quantidadeDeDados = Integer.parseInt(dados.substring(2, 12));
                    if (quantidadeDeDados == contDados) {
                        System.out.println("Quantidade de dados gravados compátivel com a quantidade lida");
                    } else {
                        System.out.println("Quantidade de dados gravados não estão compátiveis com a quantidade lida");
                    }
                } else if (tipoDados.equals("02")) {
                    if (contDados == 0) {
                        System.out.println();
                        System.out.printf("%-25s %-15s %-15s %-25s\n", "nome", "nivel", "posicao", "nomeJogo");
                    }

                    String id = dados.substring(3, 7);
                    String nome = dados.substring(7, 32);
                    String nomeEquipe = dados.substring(32, 57);
                    String posicao = dados.substring(57, 72);
                    String nomeJogo = dados.substring(72, 97);
                    System.out.printf("%-4s %-25s %-25s %-15s %-25s\n", id, nome, nomeEquipe, posicao, nomeJogo);
                    ++contDados;
                } else {
                    System.out.println("Modelo de dados inválido");
                }
            }

            entrada.close();
        } catch (IOException var11) {
            System.err.printf("Erroa ao ler os dados: %s.\n", var11.getMessage());
        }

    }

    @PostMapping("/{id}")
    public void criarArquivo(@PathVariable("id") Integer id) {

        String header = "";
        String corpo = "";
        String trailer = "";
        int contadorDeDados = 0;
        Date dataAtual = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");
        header = header + "00";
        header = header + formatter.format(dataAtual);
        header = header + "01";
        gravarRegistro(header);

        for(int i = id; i < 11; i++) {
            GamerInfo usuario = repository.findOneByIdGamer_IdGamer(i);

            List<EquipeGamer> equipe = equipeGamerRepository.findByIdGamer_IdGamer(i);
            String nomeEquipe = "";
            String nome = usuario.getIdGamer().getNome();
            if (equipe.isEmpty()) {
                nomeEquipe = "";
            } else {
                EquipeGamer atual = equipe.get(0);
                nomeEquipe = atual.getIdEquipe().getNomeEquipe();
            }
            String posicao = usuario.getIdPosicao().getPosicao();
            String nomeJogo = usuario.getIdJogo().getNomeJogo();

            corpo = corpo + "01";
            corpo +=String.format(" %-4s %-25s %-25s %-15s %-25s\n",i, nome, nomeEquipe, posicao, nomeJogo);
            contadorDeDados = contadorDeDados + 1;

        }
        gravarRegistro(corpo);
        trailer = trailer + "02";
        trailer = trailer + String.format("%010d", contadorDeDados);
        gravarRegistro(trailer);
    }
}
