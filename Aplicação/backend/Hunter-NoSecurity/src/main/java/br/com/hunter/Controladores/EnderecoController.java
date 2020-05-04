package br.com.hunter.Controladores;

import br.com.hunter.Modelos.Endereco;
import br.com.hunter.Repositorios.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/address")
public class EnderecoController {

    @Autowired
    private EnderecoRepository repositoryEndereco;

    @PostMapping
    public ResponseEntity createEndereco(@RequestBody Endereco newEndereco){
        this.repositoryEndereco.save(newEndereco);
        return created(null).build();
    }

    @GetMapping
    public ResponseEntity listEnderecos() {
        if (this.repositoryEndereco.count() > 0) {
            return ok(this.repositoryEndereco.findAll());
        } else {
            return noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEndereco(
            @PathVariable Integer id,
            @RequestBody Endereco enderecoUpdated) {

        Endereco endereco = this.repositoryEndereco.findById(id).get();
        Optional<Endereco> searchEndereco = this.repositoryEndereco.findById(id);

        if (searchEndereco.isPresent()) {
            endereco.setBairro(enderecoUpdated.getBairro());
            endereco.setCep(enderecoUpdated.getCep());
            endereco.setCidade(enderecoUpdated.getCidade());
            endereco.setComplemento(enderecoUpdated.getComplemento());
            endereco.setFkGamer(enderecoUpdated.getFkGamer());
            endereco.setLogradouro(enderecoUpdated.getLogradouro());
            endereco.setNumero(enderecoUpdated.getNumero());
            endereco.setUf(enderecoUpdated.getBairro());

            this.repositoryEndereco.save(endereco);
            return ok(endereco);
        }
        else {
            return notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEndereco(@PathVariable Integer id){
        if (this.repositoryEndereco.existsById(id)) {
            this.repositoryEndereco.deleteById(id);
            return ok().build();
        }else {
            return notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getGame(@PathVariable Integer id){
        Optional<Endereco> searchGame = this.repositoryEndereco.findById(id);

        if (searchGame.isPresent()) {
            return ok(searchGame.get());
        } else {
            return notFound().build();
        }
    }
}
