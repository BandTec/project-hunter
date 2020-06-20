package br.com.hunter.Partida.Repositorios;

import br.com.hunter.Partida.Modelos.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PartidaRepository extends  JpaRepository<Partida, Integer> {

    List<Partida> findByIdGamer_IdGamer(int idGamer);
    List<Partida> findByIdGamer_IdGamerAndDataBefore(int idGamer, LocalDate data);
    List<Partida> findByIdGamer_IdGamerAndDataAfter(int idGamer, LocalDate data);
    List<Partida> findByIdEquipe_IdEquipe(int IdEquipe);
    List<Partida> findByIdPartida(int idPartida);
    Partida findFirstByOrderByIdPartidaDesc();
    long deleteByIdPartida(int id);
    boolean existsByIdPartida(int id);
    List<Partida> findFirstByIdEquipe_IdEquipeAndIdPartida(int idEquipe, int Idpartida);
    Partida findFirstByIdEquipe_IdEquipeOrderByIdPartidaDesc(int idEquipe);

}
