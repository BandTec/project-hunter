package br.com.hunter.Equipe.Repositorios;

import br.com.hunter.Equipe.Modelos.EquipeJogo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipeJogoRepository extends JpaRepository<EquipeJogo, Integer> {

    List<EquipeJogo> findByIdEquipe_IdEquipe(Integer equipe);
    List<EquipeJogo> findByIdJogo_NomeJogoContaining(String idJogo);
}
