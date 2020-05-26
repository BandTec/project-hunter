package br.com.hunter.Controladores;

import br.com.hunter.Modelos.Equipe;
import br.com.hunter.Modelos.GamerInfo;
import br.com.hunter.Repositorios.GamerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gamerinfo")
@CrossOrigin(origins = "http://localhost:3000")
public class GamerInfoController {

    @Autowired
    private GamerInfoRepository repository;

    @GetMapping
    public ResponseEntity listarTodos() {
        List<GamerInfo> lista = repository.findAll();
        return lista.isEmpty() ? ResponseEntity.noContent ().build() : ResponseEntity.ok ( lista );
    }
    @GetMapping ( "/{id}" )
    public ResponseEntity recuperar( @PathVariable( "id" ) int id ) {
        Optional<GamerInfo> registro = repository .findById( id );
        return registro.isPresent()? ResponseEntity.ok(registro.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/gamer/{gamer}")
    private ResponseEntity BuscaPorGamer(@PathVariable("gamer") String gamer) {
        List<GamerInfo> lista = repository.findByIdGamer_Email(gamer);
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
    public ResponseEntity criar( @RequestBody GamerInfo gamerinfo ) {
        this.repository.save(gamerinfo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping ( "/{id}" )
    public ResponseEntity atualizar(
            @PathVariable("id") int id, @RequestBody GamerInfo gamerInfoAlterado ) {
        if(repository.existsById(id)) {
            gamerInfoAlterado.setIdGamerInfo(id);
            repository.save(gamerInfoAlterado);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
