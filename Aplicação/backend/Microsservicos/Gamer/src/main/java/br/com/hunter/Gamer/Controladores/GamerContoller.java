package br.com.hunter.Gamer.Controladores;

import br.com.hunter.Gamer.Modelos.Gamer;
import br.com.hunter.Gamer.Repositorios.GamerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/gamer")
@CrossOrigin(origins = "http://hunterproject.herokuapp.com")
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
            Gamer gamercompleto = repository.findById(id);
            copyNonNullProperties(gamerAlterado, gamercompleto);

            repository.save (gamercompleto);
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


    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
