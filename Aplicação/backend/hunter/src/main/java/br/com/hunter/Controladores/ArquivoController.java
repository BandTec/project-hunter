package br.com.hunter.Controladores;

import br.com.hunter.MediaTypeUtils;
import br.com.hunter.Modelos.EquipeGamer;
import br.com.hunter.Modelos.Gamer;
import br.com.hunter.Modelos.GamerInfo;
import br.com.hunter.Repositorios.EquipeGamerRepository;
import br.com.hunter.Repositorios.GamerInfoRepository;
import br.com.hunter.Repositorios.GamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/arquivo")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ArquivoController {

    @Autowired
    private GamerInfoRepository gamerInfoRepository;

    @Autowired
    private GamerRepository gamerRepository;

    @Autowired
    private EquipeGamerRepository equipeGamerRepository;

    @Autowired
    private ServletContext servletContext;

    // criar arquivo
    public static void gravarRegistro(String registro) {
        BufferedWriter saida = null;

        try {
            saida = new BufferedWriter(new FileWriter("C:/Users/Public/Downloads/Usuario.txt", true));
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
    public ResponseEntity<InputStreamResource> criarArquivo(@PathVariable("id") Integer id) throws IOException  {

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

        List<EquipeGamer> usuarios = equipeGamerRepository.findByIdStatus_IdStatusAndIdEquipe_IdEquipe(1, id);

        for(int i = 0; i < usuarios.size(); i++) {

            int idGamerAtual = usuarios.get(i).getIdGamer().getIdGamer();
            Gamer atual = gamerRepository.findById(idGamerAtual);
            GamerInfo infos = gamerInfoRepository.findFirstByIdGamer_IdGamer(idGamerAtual);


            String nome = atual.getNome();
            String usuario = atual.getUsuario();
            String email = atual.getEmail();
            String jogo = infos.getIdJogo().getNomeJogo();
            String posicao = infos.getIdPosicao().getPosicao();


            corpo = corpo + "01";
            corpo +=String.format(" %-25s %-25s %-35s %-15s %-15s\n", nome, usuario, email, jogo, posicao);
            contadorDeDados = contadorDeDados + 1;

        }
        gravarRegistro(corpo);
        trailer = trailer + "02";
        trailer = trailer + "11";
        gravarRegistro(trailer);

        //filename: C:/Users/Public/Downloads/Usuario.txt
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, "C:/Users/Public/Downloads/Usuario.txt");
        File file = new File("C:/Users/Public/Downloads/Usuario.txt");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                // Content-Type
                .contentType(mediaType)
                // Contet-Length
                .contentLength(file.length()) //
                .body(resource);
    }

}