package br.com.hunter.Gamer.Controladores;

import br.com.hunter.Gamer.Modelos.Gamer;
import br.com.hunter.Gamer.Modelos.GamerInfo;
import br.com.hunter.Gamer.Repositorios.GamerInfoRepository;
import br.com.hunter.Gamer.Repositorios.GamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gamerinfo")
@CrossOrigin(origins = "http://hunterproject.herokuapp.com")
public class GamerInfoController {

    @Autowired
    private GamerInfoRepository repository;

    @Autowired
    private GamerRepository gamerRepository;

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

    @PostMapping("/{email}")
    public ResponseEntity criarPorEmail( @RequestBody GamerInfo gamerinfo, @PathVariable("email") String email ) {
        Gamer gamer = gamerRepository.findOneByEmail(email);
        gamerinfo.setIdGamer(gamer);
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
