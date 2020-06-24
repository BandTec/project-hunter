package br.com.hunter.Arquivo.Repositorios;

import br.com.hunter.Arquivo.Modelos.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JogoRepository extends JpaRepository<Jogo, Integer> {

    @Query("select j from Jogo j where j.nomeJogo LIKE %?1%")
    List<Jogo> findByNomeJogo(String nomeJogo);
}