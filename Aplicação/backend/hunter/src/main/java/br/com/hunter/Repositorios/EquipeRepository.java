package br.com.hunter.Repositorios;

import br.com.hunter.Modelos.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipeRepository extends JpaRepository<Equipe, Integer> {
}
