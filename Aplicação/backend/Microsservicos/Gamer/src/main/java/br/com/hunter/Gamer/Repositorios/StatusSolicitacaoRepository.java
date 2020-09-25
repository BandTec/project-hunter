package br.com.hunter.Gamer.Repositorios;

import br.com.hunter.Gamer.Modelos.StatusSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusSolicitacaoRepository extends JpaRepository<StatusSolicitacao, Integer> {

    StatusSolicitacao findByIdStatus(int id);
}