package br.com.hunter.Repositorios;

import br.com.hunter.Modelos.EquipeGamer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipeGamerRepository extends JpaRepository<EquipeGamer, Integer> {

    List<EquipeGamer> findByIdGamer_Email(String email);

    List<EquipeGamer> findByIdStatus_IdStatusAndIdEquipe_IdEquipe(Integer idStatus, Integer id);

}
