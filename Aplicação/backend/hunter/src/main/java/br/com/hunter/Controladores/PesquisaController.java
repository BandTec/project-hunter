package br.com.hunter.Controladores;

import br.com.hunter.Modelos.Equipe;
import br.com.hunter.Modelos.EquipeJogo;
import br.com.hunter.Modelos.Jogo;
import br.com.hunter.Repositorios.EquipeRepository;
import br.com.hunter.Repositorios.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/pesquisa")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PesquisaController {

    @Autowired
    JogoRepository jogoRepository;
    @Autowired
    EquipeRepository equipeRepository;

    @GetMapping("/busca/{texto}")
    private ResponseEntity Busca(@PathVariable("texto") String texto) {
        List<Jogo> listaJogo = jogoRepository.findByNomeJogo(texto);
        System.out.println("Lista jogo " + listaJogo);
        if (listaJogo.isEmpty()) {
            List<Equipe> listaEquipe = equipeRepository.findByNomeEquipe(texto);
            System.out.println("Lista equipe " + listaEquipe);
            return listaEquipe.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listaEquipe);
        }
        else{
            return listaJogo.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listaJogo);
        }
    }
}
