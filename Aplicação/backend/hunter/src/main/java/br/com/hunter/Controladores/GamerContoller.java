package br.com.hunter.Controladores;

import br.com.hunter.Modelos.Gamer;
import br.com.hunter.Modelos.GamerInfo;
import br.com.hunter.Repositorios.GamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@Controller("/gamer")
@CrossOrigin(origins = "http://localhost:3000")
public class GamerContoller {

    @Autowired
    public GamerRepository repository;

    private boolean logado = false;

    @GetMapping("/nome/{nome}")
    private ResponseEntity BuscaPorNome(@PathVariable("nome") String nome) {
        List<Gamer> lista = repository.findByNome(nome);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @GetMapping("/usuario/{usuario}")
    private ResponseEntity BuscaPorUsuario(@PathVariable("usuario") String usuario) {
        List<Gamer> lista = repository.findByUsuario(usuario);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @PostMapping("/criar")
    public ResponseEntity createGamer(@RequestBody Gamer newGamer){
        this.repository.save(newGamer);
        return created(null).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(
            @PathVariable("id") int id, @RequestBody Gamer gamerAlterado ) {
        if(repository.existsById(id)) {
            gamerAlterado.setIdGamer(id);
            repository.save(gamerAlterado);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity getUsuario(@PathVariable("email") String email){
        List gamer;
        gamer = repository.findByEmail(email);

        return ResponseEntity.ok(gamer);
    }


    @GetMapping("/{email}/{senha}")
    public ResponseEntity getUsuarioESenha(@PathVariable("email") String email, @PathVariable("senha") String senha ){
        List gamer;
        gamer = repository.findOneByEmailAndSenha(email, senha);
        if(!logado) {
            if (gamer.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                logado = true;
                return ResponseEntity.ok("login realizado com sucesso");
            }
        } else {
            return ResponseEntity.ok("ja está logado");
        }
    }


    @PostMapping("/logoff")
    public ResponseEntity logoff() {
        if(logado) {
            logado = false;
            return ResponseEntity.ok("Você foi deslogado");
        } else {
            return ResponseEntity.ok("Não existe usuario logado");
        }
    }

    @GetMapping
    public ResponseEntity todos() {
        if (this.repository.count() > 0) {
            return ok(this.repository.findAll());
        } else {
            return noContent().build();
        }
    }

}
