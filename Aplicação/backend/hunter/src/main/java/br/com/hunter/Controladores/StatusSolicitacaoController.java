package br.com.hunter.Controladores;


import br.com.hunter.Modelos.StatusSolicitacao;
import br.com.hunter.Repositorios.StatusSolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/statussolicitacao")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StatusSolicitacaoController {

    @Autowired
    private StatusSolicitacaoRepository repository;

    @GetMapping
    public ResponseEntity listarTodos() {
        List<StatusSolicitacao> lista = repository.findAll();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }
    @GetMapping ( "/{id}" )
    public ResponseEntity recuperar( @PathVariable( "id" ) int id ) {
        Optional<StatusSolicitacao> registro = repository.findById(id);
        return registro.isPresent()? ResponseEntity.ok(registro.get()) : ResponseEntity.notFound().build();
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
    public ResponseEntity criar( @RequestBody StatusSolicitacao StatusSolicitacao ) {
        this.repository.save(StatusSolicitacao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping ( "/{id}" )
    public ResponseEntity atualizar(
            @PathVariable("id") int id, @RequestBody StatusSolicitacao StatusSolicitacaoAlterada ) {
        if(repository.existsById(id)) {
            StatusSolicitacaoAlterada.setIdStatus(id);
            repository.save(StatusSolicitacaoAlterada);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
