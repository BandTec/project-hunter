package br.com.hunter.Arquivo.Repositorios;

import br.com.hunter.Arquivo.Modelos.EquipeGamer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipeGamerRepository extends JpaRepository<EquipeGamer, Integer> {

    List<EquipeGamer> findByIdEquipe_IdEquipe(int id);

    List<EquipeGamer> findByIdGamer_EmailAndIdStatus_IdStatus(String email, Integer id);

    List<EquipeGamer> findByIdStatus_IdStatusAndIdEquipe_IdEquipe(Integer idStatus, Integer id);

    List<EquipeGamer> findByIdEquipe_NomeEquipeAndIdStatus_IdStatus(String nome, int id);

    List<EquipeGamer> findByIdEquipe_NomeEquipe(String nome);

    List<EquipeGamer> findByIdGamer_IdGamer(int id);

    EquipeGamer findByIdEquipeGamer(int id);

    Integer countByIdEquipe_IdEquipeAndIdStatus_IdStatus(int id, int id2);

    EquipeGamer findByIdEquipe_IdEquipeAndIdGamer_IdGamerAndCapitao(int equipe, int gamer, boolean capitao);

}
