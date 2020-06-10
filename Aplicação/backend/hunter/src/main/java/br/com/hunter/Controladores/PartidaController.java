package br.com.hunter.Controladores;

import br.com.hunter.Modelos.Gamer;
import br.com.hunter.Modelos.Partida;
import br.com.hunter.Modelos.Posicao;
import br.com.hunter.Repositorios.GamerRepository;
import br.com.hunter.Repositorios.PartidaRepository;
import br.com.hunter.Repositorios.PosicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/partida")
@CrossOrigin(origins = "http://localhost:3000")
public class PartidaController {

    @Autowired
    private PartidaRepository repository;

    @Autowired
    private GamerRepository gamerRepository;

    @Autowired
    private PosicaoRepository posicaoRepository;

    @GetMapping
    public ResponseEntity listarTodos() {
        List<Partida> lista = repository.findAll();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }
    @GetMapping ( "/{id}" )
    public ResponseEntity recuperar( @PathVariable("id") int id ) {
        Optional<Partida> registro = repository.findById( id );
        return registro.isPresent()? ResponseEntity.ok(registro.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/gamer/{id}")
    public  ResponseEntity listarPorGamer(@PathVariable("id") Integer id) {
        List<Partida> lista = repository.findByIdGamer_IdGamer(id);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @GetMapping("/equipe/{id}")
    public  ResponseEntity listarPorEquipe(@PathVariable("id") Integer id) {
        Partida ultima = repository.findFirstByIdEquipe_IdEquipeOrderByIdPartidaDesc(id);
        int max = ultima.getIdPartida();
        List<Partida> lista = repository.findFirstByIdEquipe_IdEquipeAndIdPartida(id,0);
        for (int i = 0; i <= max ; i++) {
            List<Partida> atual = repository.findFirstByIdEquipe_IdEquipeAndIdPartida(id,i);
            if (atual.isEmpty()) {

            } else{
                lista.add(repository.findFirstByIdEquipe_IdEquipeAndIdPartida(id,i).get(0));
            }
        }

        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @GetMapping("/idpartida/{id}")
    public  ResponseEntity listarIdPartida(@PathVariable("id") Integer id) {
        List<Partida> lista = repository.findByIdPartida(id);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @DeleteMapping( "/{id}" )
    public ResponseEntity excluir(@PathVariable("id") int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Transactional
    @DeleteMapping( "/partida/{id}" )
    public ResponseEntity excluirIdPartida(@PathVariable("id") int id) {
        if (repository.existsByIdPartida(id)) {
            repository.deleteByIdPartida(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity criar(@RequestBody Partida partida) {
        if (partida == null) {
            return ResponseEntity.badRequest().build();
        } else {
            Partida partidaLastId = repository.findFirstByOrderByIdPartidaDesc();
            Integer lastId = partidaLastId.getIdPartida();
            partida.setIdPartida(lastId + 1);
            this.repository.save(partida);
            return ResponseEntity.status(HttpStatus.CREATED).body(partida);
        }
    }

    @PostMapping("/{usuario}/{posicao}/{id}")
    public ResponseEntity criarPorId(@RequestBody Partida partida,
                                     @PathVariable("id") Integer id,
                                     @PathVariable("usuario") String usuario,
                                     @PathVariable("posicao") Integer posicao) {


        Gamer gamerPorNome = gamerRepository.findOneByUsuario(usuario);
        partida.setIdGamer(gamerPorNome);

        Posicao posicaoPorId = posicaoRepository.getOne(posicao);
        partida.setIdPosicao(posicaoPorId);

        partida.setIdPartida(id);

        this.repository.save(partida);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping ( "/{id}" )
    public ResponseEntity atualizar(
            @PathVariable("id") int id, @RequestBody Partida PartidaAlterado ) {
        if(repository.existsById(id)) {
            PartidaAlterado.setIdPartida(id);
            repository.save(PartidaAlterado);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
