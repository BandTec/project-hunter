package br.com.hunter.Controladores;

import br.com.hunter.Modelos.Gamer;
import br.com.hunter.Repositorios.GamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class GamerContoller {

    @Autowired
    public GamerRepository repository;

    private boolean logado = false;

    @PostMapping("/usuario/criar")
    public ResponseEntity createGamer(@RequestBody Gamer newGamer){
        this.repository.save(newGamer);
        return created(null).build();
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity getUsuario(@PathVariable("email") String email){
        List gamer;
        gamer = repository.findByEmail(email);

        return ResponseEntity.ok(gamer);
    }


    @GetMapping("/usuario/{email}/{senha}")
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


    @PostMapping("/usuario/logoff")
    public ResponseEntity logoff() {
        if(logado) {
            logado = false;
            return ResponseEntity.ok("Você foi deslogado");
        } else {
            return ResponseEntity.ok("Não existe usuario logado");
        }
    }

    @GetMapping("/usuario")
    public ResponseEntity todos() {
        if (this.repository.count() > 0) {
            return ok(this.repository.findAll());
        } else {
            return noContent().build();
        }
    }

}
