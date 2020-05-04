package br.com.hunter.Controladores;

import br.com.hunter.Modelos.DadosPartida;
import br.com.hunter.Repositorios.DadosPartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/matchs")
public class DadosPartidaController {

    @Autowired
    private DadosPartidaRepository repositoryPartida;

    @PostMapping
    public ResponseEntity createPartida(@RequestBody DadosPartida newPartida){
        this.repositoryPartida.save(newPartida);
        return created(null).build();
    }

    @GetMapping
    public ResponseEntity listPartidas() {
        if (this.repositoryPartida.count() > 0) {
            return ok(this.repositoryPartida.findAll());
        } else {
            return noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePartida(
            @PathVariable Integer id,
            @RequestBody DadosPartida partidaUpdated) {

        DadosPartida partida = this.repositoryPartida.findById(id).get();
        Optional<DadosPartida> searchPartida = this.repositoryPartida.findById(id);

        if (searchPartida.isPresent()) {
            partida.setData(partidaUpdated.getData());
            partida.setHorario(partidaUpdated.getHorario());
            partida.setNota(partidaUpdated.getNota());

            this.repositoryPartida.save(partida);
            return ok(partida);
        }
        else {
            return notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePartida(@PathVariable Integer id){
        if (this.repositoryPartida.existsById(id)) {
            this.repositoryPartida.deleteById(id);
            return ok().build();
        }else {
            return notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getGame(@PathVariable Integer id){
        Optional<DadosPartida> searchGame = this.repositoryPartida.findById(id);

        if (searchGame.isPresent()) {
            return ok(searchGame.get());
        } else {
            return notFound().build();
        }
    }
}
