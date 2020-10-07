package br.com.hunter.Controladores;

import br.com.hunter.Modelos.GamerInfo;
import br.com.hunter.Modelos.Posicao;
import br.com.hunter.Repositorios.PosicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/posicao")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PosicaoController {

    @Autowired
    private PosicaoRepository repository;

    @GetMapping
    public ResponseEntity listarTodos() {
        List<Posicao> lista = repository.findAll();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }
    @GetMapping ( "/{id}" )
    public ResponseEntity recuperar( @PathVariable( "id" ) int id ) {
        Optional<Posicao> registro = repository.findById(id);
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
    public ResponseEntity criar( @RequestBody Posicao Posicao ) {
        this.repository.save(Posicao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity atualizar(
            @PathVariable("id") int id, @RequestBody Posicao PosicaoAlterada ) {
        if(repository.existsById(id)) {
            PosicaoAlterada.setIdPosicao(id);
            repository.save(PosicaoAlterada);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
