package br.com.hunter.Repositorios;

import br.com.hunter.Modelos.Gamer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GamerRepository extends JpaRepository<Gamer, Integer> {

    Gamer findById(int id);




    List<Gamer> findByEmail(String email);
    List<Gamer> findOneByEmailAndSenha(String email, String senha);
    List<Gamer> findByNome(String nome);
    Optional<Gamer> findByUsuario(String usuario);
    Gamer findOneByUsuario(String usuario);
    Gamer findOneByEmail(String email);
    Gamer findFistByEmail(String email);

}