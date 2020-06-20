package br.com.hunter.Repositorios;

import br.com.hunter.Modelos.GamerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GamerInfoRepository extends JpaRepository<GamerInfo, Integer> {

    List<GamerInfo> findByIdGamer_Email(String email);
    GamerInfo findFirstByIdGamer_IdGamer(Integer id);


}
