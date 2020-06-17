package br.com.hunter.Controladores;


import br.com.hunter.Modelos.EquipeJogo;
import br.com.hunter.Repositorios.EquipeJogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/equipejogo")
@CrossOrigin(origins = "http://localhost:3000")
public class EquipeJogoController {

    @Autowired
    private EquipeJogoRepository repository;

    @GetMapping
    public ResponseEntity listarTodos() {
        List<EquipeJogo> lista = repository.findAll();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }
    @GetMapping ( "/{id}" )
    public ResponseEntity recuperar( @PathVariable( "id" ) int id ) {
        Optional<EquipeJogo> registro = repository.findById(id);
        return registro.isPresent()? ResponseEntity.ok(registro.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/equipe/{equipe}")
    private ResponseEntity BuscaPorJogo(@PathVariable("equipe") Integer equipe) {
        List<EquipeJogo> lista = repository.findByIdEquipe_IdEquipe(equipe);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @GetMapping("/jogo/{jogo}")
    private ResponseEntity BuscaPorJogo(@PathVariable("jogo") String jogo) {
        List<EquipeJogo> lista = repository.findByIdJogo_NomeJogoContaining(jogo);
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
    public ResponseEntity criar( @RequestBody EquipeJogo EquipeJogo ) {
        this.repository.save(EquipeJogo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping ( "/{id}" )
    public ResponseEntity atualizar(
            @PathVariable("id") int id, @RequestBody EquipeJogo EquipeJogoAlterada ) {
        if(repository.existsById(id)) {
            EquipeJogoAlterada.setIdEquipeJogo(id);
            repository.save(EquipeJogoAlterada);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
