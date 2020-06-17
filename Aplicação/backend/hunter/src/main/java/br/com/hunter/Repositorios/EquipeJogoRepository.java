package br.com.hunter.Repositorios;

import br.com.hunter.Modelos.EquipeJogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EquipeJogoRepository extends JpaRepository<EquipeJogo, Integer> {

    List<EquipeJogo> findByIdEquipe_IdEquipe(Integer equipe);
    List<EquipeJogo> findByIdJogo_NomeJogoContaining(String idJogo);
}
