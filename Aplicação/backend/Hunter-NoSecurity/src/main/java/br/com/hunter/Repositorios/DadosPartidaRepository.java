package br.com.hunter.Repositorios;

import br.com.hunter.Modelos.DadosPartida;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DadosPartidaRepository extends CrudRepository<DadosPartida, Integer> {


}
