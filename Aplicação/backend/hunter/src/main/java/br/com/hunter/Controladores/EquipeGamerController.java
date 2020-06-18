package br.com.hunter.Controladores;

import br.com.hunter.Modelos.EquipeGamer;
import br.com.hunter.Modelos.StatusSolicitacao;
import br.com.hunter.Repositorios.EquipeGamerRepository;
import br.com.hunter.Repositorios.StatusSolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/equipegamer")
@CrossOrigin(origins = "http://localhost:3000")
public class EquipeGamerController {

    @Autowired
    private EquipeGamerRepository repository;

    @Autowired
    private StatusSolicitacaoRepository statusRepository;

    @GetMapping
    public ResponseEntity listarTodos() {
        List<EquipeGamer> lista = repository.findAll();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }


    @GetMapping ( "/{id}" )
    public ResponseEntity recuperar( @PathVariable( "id" ) int id ) {
        Optional<EquipeGamer> registro = repository.findById(id);
        return registro.isPresent()? ResponseEntity.ok(registro.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/gamer/{gamer}")
    private ResponseEntity BuscaPorGamer(@PathVariable("gamer") String gamer) {
        List<EquipeGamer> lista = repository.findByIdGamer_Email(gamer);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @GetMapping("/equipe/{nome}")
    private ResponseEntity BuscaPorNomeEquipe(@PathVariable("nome") String nome) {
        List<EquipeGamer> lista = repository.findByIdEquipe_NomeEquipe(nome);
        HttpHeaders responseHeaders = new HttpHeaders();
        Integer qtdGamers = repository.countByIdEquipe_IdEquipe(lista.get(0).getIdEquipe().getIdEquipe());
        responseHeaders.set("qtdGamers", String.valueOf(qtdGamers));

        return lista.isEmpty() ? ResponseEntity.noContent().build() :
                                 ResponseEntity.ok().headers(responseHeaders).body(lista);
    }

    @GetMapping("/aprovado/{equipe}")
    private ResponseEntity BuscaPorStatusAprovado(@PathVariable("equipe")  Integer equipe) {
        List<EquipeGamer> lista = repository.findByIdStatus_IdStatusAndIdEquipe_IdEquipe(1 , equipe);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @GetMapping("/pendente/{equipe}")
    private ResponseEntity BuscaPorStatusPendente(@PathVariable("equipe")  Integer equipe) {
        List<EquipeGamer> lista = repository.findByIdStatus_IdStatusAndIdEquipe_IdEquipe(3 , equipe);
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
    public ResponseEntity criar( @RequestBody EquipeGamer EquipeGamer ) {
        this.repository.save(EquipeGamer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping ( "/{id}" )
    public ResponseEntity atualizar(
            @PathVariable("id") int id, @RequestBody EquipeGamer EquipeGamerAlterada ) {
        if(repository.existsById(id)) {
            EquipeGamerAlterada.setIdEquipeGamer(id);
            repository.save(EquipeGamerAlterada);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping ( "/aceitar/{id}" )
    public ResponseEntity aceitar(
            @PathVariable("id") int id) {
        if(repository.existsById(id)) {
            EquipeGamer alvo = repository.findByIdEquipeGamer(id);
            StatusSolicitacao status = statusRepository.findById(1).get();
            alvo.setIdStatus(status);
            repository.save(alvo);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping ( "/recusar/{id}" )
    public ResponseEntity recusar(
            @PathVariable("id") int id) {
        if(repository.existsById(id)) {
            EquipeGamer alvo = repository.findByIdEquipeGamer(id);
            StatusSolicitacao status = statusRepository.findById(2).get();
            alvo.setIdStatus(status);
            repository.save(alvo);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
