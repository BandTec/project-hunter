package br.com.hunter.Repositorios;

import br.com.hunter.Modelos.EquipeJogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EquipeJogoRepository extends JpaRepository<EquipeJogo, Integer> {
}
