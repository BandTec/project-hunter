package br.com.hunter.Controladores;

import br.com.hunter.Modelos.Partida;
import br.com.hunter.Repositorios.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        List<Partida> lista = repository.findByIdEquipe_IdEquipe(id);
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
    @PostMapping
    public ResponseEntity criar(@RequestBody Partida partida) {
        if (partida == null) {
            return ResponseEntity.badRequest().build();
        } else {
            Partida partidaLastId = repository.findFirstByOrderByIdPartidaDesc();
            Integer lastId = partidaLastId.getIdPartida();
            partida.setIdPartida(lastId + 1);
            this.repository.save(partida);
            return ResponseEntity.status(HttpStatus.CREATED).body(partida.getIdPartida());
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity criarPorId(@RequestBody Partida partida, @PathVariable("id") Integer id) {
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
