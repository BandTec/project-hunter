package br.com.hunter.Controladores;

import br.com.hunter.Modelos.Jogo;
import br.com.hunter.Repositorios.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/game")
public class JogoController {

    @Autowired
    private JogoRepository repositoryJogo;

    @PostMapping
    public ResponseEntity createJogo(@RequestBody Jogo newJogo){
        this.repositoryJogo.save(newJogo);
        return created(null).build();
    }

    @GetMapping
    public ResponseEntity listJogos() {
        if (this.repositoryJogo.count() > 0) {
            return ok(this.repositoryJogo.findAll());
        } else {
            return noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateJogo(
            @PathVariable Integer id,
            @RequestBody Jogo jogoUpdated) {

        Jogo jojo = this.repositoryJogo.findById(id).get();
        Optional<Jogo> searchJogo = this.repositoryJogo.findById(id);

        if (searchJogo.isPresent()) {
            jojo.setNomeJogo(jogoUpdated.getNomeJogo());
            jojo.setAtdPlayers(jogoUpdated.getAtdPlayers());
            jojo.setTipo(jogoUpdated.getTipo());

            this.repositoryJogo.save(jojo);
            return ok(jojo);
        }
        else {
            return notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteJogo(@PathVariable Integer id){
        if (this.repositoryJogo.existsById(id)) {
            this.repositoryJogo.deleteById(id);
            return ok().build();
        }else {
            return notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getJogo(@PathVariable Integer id){
        Optional<Jogo> searchJogo = this.repositoryJogo.findById(id);

        if (searchJogo.isPresent()) {
            return ok(searchJogo.get());
        } else {
            return notFound().build();
        }
    }
}
