package br.com.hunter.Repositorios;

import br.com.hunter.Modelos.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipeRepository extends JpaRepository<Equipe, Integer> {

    List<Equipe> findByNomeEquipe(String nomeEquipe);

}
