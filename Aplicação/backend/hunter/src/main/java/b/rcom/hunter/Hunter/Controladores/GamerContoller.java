package b.rcom.hunter.Hunter.Controladores;

import b.rcom.hunter.Hunter.Modelos.Gamer;
import b.rcom.hunter.Hunter.Repositorios.GamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class GamerContoller {

    @Autowired
    public GamerRepository repository;

    @GetMapping("/usuario/{email}/{senha}")
    public ResponseEntity getUsuarioESenha(@PathVariable("email") String email, @PathVariable("senha") String senha ){
        List gamer;
        gamer = repository.findByEmailAndSenha(email, senha);
        if(gamer.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {

            return ResponseEntity.ok(gamer);
        }
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity getUsuario(@PathVariable("email") String email){
        List gamer;
        gamer = repository.findByEmail(email);

        return ResponseEntity.ok(gamer);
    }

    @PostMapping("/usuario/salvar")
    public ResponseEntity salvarUsuario() {
        Gamer gamer = new Gamer();
        gamer.setNome("jose");
        gamer.setCpf("123456789");
        gamer.setEmail("jose@gmail.com");
        gamer.setSenha("1234");
        gamer.setTelefone("945632156");
        //"jose","123549","jose@gmail.com", "senha", "944445"
        this.repository.save(gamer);
        return ResponseEntity.ok(gamer);
    }
//    @GetMapping("/usuario-teste/{email}")
//    public ResponseEntity<Gamer> getUsuarioTeste(@PathVariable("email") String email){
//        Gamer gamer = repository.findByEmail(email);
//        System.out.println(gamer.getUsername());
//        System.out.println(gamer.getNome());
//        return new ResponseEntity<>(HttpStatus.OK);
//    }



}
