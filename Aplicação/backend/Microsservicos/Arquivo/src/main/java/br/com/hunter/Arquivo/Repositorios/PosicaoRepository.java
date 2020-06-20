package br.com.hunter.Arquivo.Repositorios;

import br.com.hunter.Arquivo.Modelos.Posicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosicaoRepository extends JpaRepository<Posicao, Integer> {
}
