package br.com.hunter.Repositorios;

import br.com.hunter.Modelos.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidaRepository extends  JpaRepository<Partida, Integer> {
}
