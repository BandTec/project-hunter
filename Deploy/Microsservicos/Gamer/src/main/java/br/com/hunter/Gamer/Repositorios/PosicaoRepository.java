package br.com.hunter.Gamer.Repositorios;

import br.com.hunter.Gamer.Modelos.Posicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosicaoRepository extends JpaRepository<Posicao, Integer> {
}
