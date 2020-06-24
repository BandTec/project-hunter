package br.com.hunter.Equipe.Repositorios;

import br.com.hunter.Equipe.Modelos.Posicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosicaoRepository extends JpaRepository<Posicao, Integer> {
}
