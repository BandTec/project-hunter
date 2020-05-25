package br.com.hunter.Repositorios;

import br.com.hunter.Modelos.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogoRepository extends JpaRepository<Jogo, Integer> {
}
