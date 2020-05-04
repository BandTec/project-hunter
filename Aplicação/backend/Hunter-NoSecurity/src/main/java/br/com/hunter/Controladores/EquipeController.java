package br.com.hunter.Controladores;

import br.com.hunter.Modelos.Equipe;
import br.com.hunter.Repositorios.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/team")
public class EquipeController {

    @Autowired
    private EquipeRepository repositoryEquipe;

    @PostMapping
    public ResponseEntity createEquipe(@RequestBody Equipe newEquipe){
        this.repositoryEquipe.save(newEquipe);
        return created(null).build();
    }

    @GetMapping
    public ResponseEntity listEquipes() {
        if (this.repositoryEquipe.count() > 0) {
            return ok(this.repositoryEquipe.findAll());
        } else {
            return noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEquipe(
            @PathVariable Integer id,
            @RequestBody Equipe equipeUpdated) {

        Equipe equipe = this.repositoryEquipe.findById(id).get();
        Optional<Equipe> searchEquipe = this.repositoryEquipe.findById(id);

        if (searchEquipe.isPresent()) {
            equipe.setNomeEquipe(equipeUpdated.getNomeEquipe());
            equipe.setPais(equipeUpdated.getPais());
            equipe.setRegiao(equipeUpdated.getRegiao());

            this.repositoryEquipe.save(equipe);
            return ok(equipe);
        }
        else {
            return notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEquipe(@PathVariable Integer id){
        if (this.repositoryEquipe.existsById(id)) {
            this.repositoryEquipe.deleteById(id);
            return ok().build();
        }else {
            return notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getGame(@PathVariable Integer id){
        Optional<Equipe> searchEquipe = this.repositoryEquipe.findById(id);

        if (searchEquipe.isPresent()) {
            return ok(searchEquipe.get());
        } else {
            return notFound().build();
        }
    }
}

