package br.com.hunter.Controladores;

import br.com.hunter.Repositorios.EquipeGamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/equipegamer")
public class EquipeGamerController {

    @Autowired
    private EquipeGamerRepository repository;

    @GetMapping
    public ResponseEntity listEquipeGamers() {
        if (this.repository.count() > 0) {
            return ok(this.repository.findAll());
        } else {
            return noContent().build();
        }
    }

}
