package br.com.hunter.Repositorios;

import br.com.hunter.Modelos.GamerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamerInfoRepository extends JpaRepository<GamerInfo, Integer> {
}
