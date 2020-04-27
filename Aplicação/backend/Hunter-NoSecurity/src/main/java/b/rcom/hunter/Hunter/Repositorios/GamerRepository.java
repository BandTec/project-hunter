package b.rcom.hunter.Hunter.Repositorios;

import b.rcom.hunter.Hunter.Modelos.Gamer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface GamerRepository extends JpaRepository<Gamer, Integer> {

    Gamer findById(int id);




    List<Gamer> findByEmail(String email);
    List<Gamer> findByEmailAndSenha(String email, String senha);


}
