package br.com.hunter.Partida.Repositorios;

import br.com.hunter.Partida.Modelos.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipeRepository extends JpaRepository<Equipe, Integer> {

    @Query("select e from Equipe e where e.nomeEquipe LIKE %?1%")
    List<Equipe> findByNomeEquipe(String nomeEquipe);

}
