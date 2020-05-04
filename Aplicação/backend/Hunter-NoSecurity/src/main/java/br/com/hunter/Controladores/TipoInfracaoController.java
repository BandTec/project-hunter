package br.com.hunter.Controladores;

import br.com.hunter.Modelos.TipoInfracao;
import br.com.hunter.Repositorios.TipoInfracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/type")
public class TipoInfracaoController {

    @Autowired
    private TipoInfracaoRepository repositoryTipo;

    @PostMapping
    public ResponseEntity createTipo(@RequestBody TipoInfracao newTipo){
        this.repositoryTipo.save(newTipo);
        return created(null).build();
    }

    @GetMapping
    public ResponseEntity listTipos() {
        if (this.repositoryTipo.count() > 0) {
            return ok(this.repositoryTipo.findAll());
        } else {
            return noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTipos(
            @PathVariable Integer id,
            @RequestBody TipoInfracao tiposUpdated) {

        TipoInfracao tipo = this.repositoryTipo.findById(id).get();
        Optional<TipoInfracao> searchTipo = this.repositoryTipo.findById(id);

        if (searchTipo.isPresent()) {
            tipo.setTipoInfracao(tiposUpdated.getTipoInfracao());

            this.repositoryTipo.save(tipo);
            return ok(tipo);
        }
        else {
            return notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTipo(@PathVariable Integer id){
        if (this.repositoryTipo.existsById(id)) {
            this.repositoryTipo.deleteById(id);
            return ok().build();
        }else {
            return notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getTipo(@PathVariable Integer id){
        Optional<TipoInfracao> searchTipo = this.repositoryTipo.findById(id);

        if (searchTipo.isPresent()) {
            return ok(searchTipo.get());
        } else {
            return notFound().build();
        }
    }
}
