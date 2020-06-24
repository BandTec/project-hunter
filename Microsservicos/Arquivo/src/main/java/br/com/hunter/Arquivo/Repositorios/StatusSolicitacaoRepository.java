package br.com.hunter.Arquivo.Repositorios;

import br.com.hunter.Arquivo.Modelos.StatusSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusSolicitacaoRepository extends JpaRepository<StatusSolicitacao, Integer> {

    StatusSolicitacao findByIdStatus(int id);
}
