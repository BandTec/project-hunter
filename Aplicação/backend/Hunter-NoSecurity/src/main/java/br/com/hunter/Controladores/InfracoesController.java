package br.com.hunter.Controladores;

import br.com.hunter.Modelos.Infracoes;
import br.com.hunter.Repositorios.InfracoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/infraction")
public class InfracoesController {

    @Autowired
    private InfracoesRepository repositoryInfracoes;

    @PostMapping
    public ResponseEntity createInfracao(@RequestBody Infracoes newInfracao){
        this.repositoryInfracoes.save(newInfracao);
        return created(null).build();
    }

    @GetMapping
    public ResponseEntity listInfracoes() {
        if (this.repositoryInfracoes.count() > 0) {
            return ok(this.repositoryInfracoes.findAll());
        } else {
            return noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateInfracao(
            @PathVariable Integer id,
            @RequestBody Infracoes infracaoUpdated) {

        Infracoes infracao = this.repositoryInfracoes.findById(id).get();
        Optional<Infracoes> searchInfracao = this.repositoryInfracoes.findById(id);

        if (searchInfracao.isPresent()) {
            infracao.setFkGamer(infracaoUpdated.getFkGamer());
            infracao.setFkPartida(infracaoUpdated.getFkPartida());
            infracao.setFkTipo(infracaoUpdated.getFkTipo());

            this.repositoryInfracoes.save(infracao);
            return ok(infracao);
        }
        else {
            return notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteInfracao(@PathVariable Integer id){
        if (this.repositoryInfracoes.existsById(id)) {
            this.repositoryInfracoes.deleteById(id);
            return ok().build();
        }else {
            return notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getInfracao(@PathVariable Integer id){
        Optional<Infracoes> searchInfracao = this.repositoryInfracoes.findById(id);

        if (searchInfracao.isPresent()) {
            return ok(searchInfracao.get());
        } else {
            return notFound().build();
        }
    }
}
