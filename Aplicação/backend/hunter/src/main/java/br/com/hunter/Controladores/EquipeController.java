package br.com.hunter.Controladores;

import br.com.hunter.Modelos.Equipe;
import br.com.hunter.Modelos.Jogo;
import br.com.hunter.Repositorios.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/equipe")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EquipeController {

    @Autowired
    private EquipeRepository repository;

    @PostMapping
    public ResponseEntity createEquipe(@RequestBody Equipe newEquipe){
        this.repository.save(newEquipe);
        return created(null).body(newEquipe);
    }

    @GetMapping
    public ResponseEntity listEquipes() {
        if (this.repository.count() > 0) {
            return ok(this.repository.findAll());
        } else {
            return noContent().build();
        }
    }

    @GetMapping("/nome/{nome}")
    private ResponseEntity BuscaPorNome(@PathVariable("nome") String nome) {
        List<Equipe> lista = repository.findByNomeEquipe(nome);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEquipe(
            @PathVariable Integer id,
            @RequestBody Equipe equipeUpdated) {

        Equipe equipe = this.repository.findById(id).get();
        Optional<Equipe> searchEquipe = this.repository.findById(id);

        if (searchEquipe.isPresent()) {
            equipe.setNomeEquipe(equipeUpdated.getNomeEquipe());

            this.repository.save(equipe);
            return ok(equipe);
        }
        else {
            return notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEquipe(@PathVariable Integer id){
        if (this.repository.existsById(id)) {
            this.repository.deleteById(id);
            return ok().build();
        }else {
            return notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getGame(@PathVariable Integer id){
        Optional<Equipe> searchEquipe = this.repository.findById(id);

        if (searchEquipe.isPresent()) {
            return ok(searchEquipe.get());
        } else {
            return notFound().build();
        }
    }
}

