package br.com.hunter.Controladores;

import br.com.hunter.Modelos.Infracao;
import br.com.hunter.Repositorios.InfracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/infracao")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InfracaoController {

    @Autowired
    private InfracaoRepository repository;

    @GetMapping
    public ResponseEntity listarTodos() {
        List<Infracao> lista = repository.findAll();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity recuperar(@PathVariable("id") int id) {
        Optional<Infracao> registro = repository.findById(id);
        return registro.isPresent() ? ResponseEntity.ok(registro.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity criar(@RequestBody Infracao Infracao) {
        this.repository.save(Infracao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(
            @PathVariable("id") int id, @RequestBody Infracao InfracaoAlterada) {
        if (repository.existsById(id)) {
            InfracaoAlterada.setIdInfracao(id);
            repository.save(InfracaoAlterada);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
