package br.com.hunter.Equipe.Repositorios;

import br.com.hunter.Equipe.Modelos.StatusSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusSolicitacaoRepository extends JpaRepository<StatusSolicitacao, Integer> {

    StatusSolicitacao findByIdStatus(int id);
}
