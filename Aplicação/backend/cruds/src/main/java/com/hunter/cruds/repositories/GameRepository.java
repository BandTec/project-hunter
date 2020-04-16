package com.hunter.cruds.repositories;

import com.hunter.cruds.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {

}
