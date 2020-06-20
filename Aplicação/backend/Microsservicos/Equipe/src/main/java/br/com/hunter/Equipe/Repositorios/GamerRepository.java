package br.com.hunter.Equipe.Repositorios;

import br.com.hunter.Equipe.Modelos.Gamer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GamerRepository extends JpaRepository<Gamer, Integer> {

    Gamer findById(int id);




    List<Gamer> findByEmail(String email);
    List<Gamer> findOneByEmailAndSenha(String email, String senha);
    List<Gamer> findByNome(String nome);
    List<Gamer> findByUsuario(String usuario);
    Gamer findOneByUsuario(String usuario);
    Gamer findOneByEmail(String email);


}
