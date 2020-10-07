package br.com.hunter.Controladores;


import br.com.hunter.Modelos.Jogo;
import br.com.hunter.Repositorios.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/jogo")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JogoController {

    @Autowired
    private JogoRepository repository;

    @GetMapping
    public ResponseEntity listarTodos() {
        List<Jogo> lista = repository.findAll();
        return lista.isEmpty() ? ResponseEntity.noContent ().build() : ResponseEntity.ok(lista);
    }

    @GetMapping("/nome/{nome}")
    private ResponseEntity BuscaPorNome(@PathVariable("nome") String nome) {
        List<Jogo> lista = repository.findByNomeJogo(nome);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @GetMapping ("/{id}")
    public ResponseEntity recuperar( @PathVariable( "id" ) int id ) {
        Optional<Jogo> registro = repository.findById( id );
        return registro.isPresent()? ResponseEntity.ok(registro.get()) : ResponseEntity.notFound().build();
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
    public ResponseEntity criar( @RequestBody Jogo jogo ) {
        this.repository.save(jogo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping ( "/{id}" )
    public ResponseEntity atualizar(
            @PathVariable("id") int id, @RequestBody Jogo JogoAlterado ) {
        if(repository.existsById(id)) {
            JogoAlterado.setIdJogo(id);
            repository.save(JogoAlterado);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
