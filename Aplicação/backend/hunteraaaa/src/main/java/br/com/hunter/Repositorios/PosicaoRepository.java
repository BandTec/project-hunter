package br.com.hunter.Repositorios;

import br.com.hunter.Modelos.Posicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosicaoRepository extends JpaRepository<Posicao, Integer> {
}
