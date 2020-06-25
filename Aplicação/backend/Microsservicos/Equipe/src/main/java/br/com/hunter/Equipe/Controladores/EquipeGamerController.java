package br.com.hunter.Equipe.Controladores;

import br.com.hunter.Equipe.Modelos.EquipeGamer;
import br.com.hunter.Equipe.Modelos.StatusSolicitacao;
import br.com.hunter.Equipe.Repositorios.EquipeGamerRepository;
import br.com.hunter.Equipe.Repositorios.StatusSolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equipegamer")
@CrossOrigin(origins = "http://hunterproject.herokuapp.com")
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
        List<EquipeGamer> lista = repository.findByIdGamer_EmailAndIdStatus_IdStatus(gamer, 1);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @GetMapping("/capitao/{idGamer}/{idEquipe}")
    private  ResponseEntity capitao(@PathVariable("idGamer") int idGamer,
                                    @PathVariable("idEquipe") int idEquipe) {

        EquipeGamer capitao = repository.findByIdEquipe_IdEquipeAndIdGamer_IdGamerAndCapitao(idEquipe, idGamer, true);

        return capitao == null ? ResponseEntity.noContent().build() : ResponseEntity.ok().build();
    }
    @GetMapping("/equipe/{nome}")
    private ResponseEntity BuscaPorNomeEquipe(@PathVariable("nome") String nome) {
        List<EquipeGamer> lista = repository.findByIdEquipe_NomeEquipeAndIdStatus_IdStatus(nome, 1);
        if(!lista.isEmpty()) {
            HttpHeaders responseHeaders = new HttpHeaders();
            Integer qtdGamers = repository.countByIdEquipe_IdEquipeAndIdStatus_IdStatus(lista.get(0).getIdEquipe().getIdEquipe(),1 );
            responseHeaders.set("qtdGamers", String.valueOf(qtdGamers));
            return ResponseEntity.ok().headers(responseHeaders).body(lista);
        } else {
           return ResponseEntity.noContent().build();
        }


    }

    @GetMapping("/equipe/qtd/{nome}")
    private ResponseEntity BuscaPorNomeEquipeqtd(@PathVariable("nome") String nome) {
        List<EquipeGamer> lista = repository.findByIdEquipe_NomeEquipeAndIdStatus_IdStatus(nome, 1);
        if (!lista.isEmpty()) {
            Integer qtdGamers = repository.countByIdEquipe_IdEquipeAndIdStatus_IdStatus(lista.get(0).getIdEquipe().getIdEquipe(), 1);
            return ResponseEntity.ok().body(qtdGamers);
        } else {
            return ResponseEntity.noContent().build();
        }


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
    @GetMapping("/existe/{idequipe}/{idgamer}")
    private ResponseEntity gamerNaEquipe(@PathVariable("idequipe")  Integer idEquipe, @PathVariable("idgamer")  Integer idGamer) {
        return repository.existsByIdEquipe_IdEquipeAndIdGamer_IdGamerAndIdStatus_IdStatus(idEquipe,idGamer, 1) ?
                ResponseEntity.ok().build() :  ResponseEntity.noContent().build() ;
    }

    @DeleteMapping( "/{idequipe}/{idgamer}" )
    public ResponseEntity excluir(@PathVariable("idequipe") int idequipe,
                                  @PathVariable("idgamer") int idgamer) {
        if (repository.existsByIdEquipe_IdEquipeAndIdGamer_IdGamer(idequipe, idgamer)) {
            EquipeGamer alvo = repository.findByIdEquipe_IdEquipeAndIdGamer_IdGamer(idequipe, idgamer);
            repository.deleteById(alvo.getIdEquipeGamer());
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
