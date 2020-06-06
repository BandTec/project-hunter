package br.com.hunter.Repositorios;

import br.com.hunter.Modelos.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartidaRepository extends  JpaRepository<Partida, Integer> {

    List<Partida> findByIdGamer_IdGamer(int idGamer);
    List<Partida> findByIdEquipe_IdEquipe(int IdEquipe);
   // List<Partida> orderByIdPartidaDescFindFirst10ByIdGamer_Email(String email);

}
