package br.com.hunter.Partida.Repositorios;

import br.com.hunter.Partida.Modelos.Posicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosicaoRepository extends JpaRepository<Posicao, Integer> {
}
