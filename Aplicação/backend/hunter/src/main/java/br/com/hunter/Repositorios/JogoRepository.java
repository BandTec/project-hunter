package br.com.hunter.Repositorios;

import br.com.hunter.Modelos.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JogoRepository extends JpaRepository<Jogo, Integer> {

    List<Jogo> findByNomeJogo(String nomeJogo);
}
