package br.com.hunter.Repositorios;

import br.com.hunter.Modelos.StatusSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusSolicitacaoRepository extends JpaRepository<StatusSolicitacao, Integer> {

    StatusSolicitacao findByIdStatus(int id);
}
