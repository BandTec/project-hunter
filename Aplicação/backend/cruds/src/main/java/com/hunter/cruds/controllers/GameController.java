package com.hunter.cruds.controllers;

import com.hunter.cruds.entities.Game;
import com.hunter.cruds.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameRepository repositoryGame;

    @PostMapping
    public ResponseEntity createGame(@RequestBody Game newGame){
        this.repositoryGame.save(newGame);
        return ResponseEntity.created(null).build();
    }

    @GetMapping
    public ResponseEntity listGames() {
        if (this.repositoryGame.count() > 0) {
            return ResponseEntity.ok(this.repositoryGame.findAll());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateGame(
            @PathVariable Integer id,
            @RequestBody Game gameUpdated) {

        Game game = this.repositoryGame.findById(id).get();
        Optional<Game> searchGame = this.repositoryGame.findById(id);

        if (searchGame.isPresent()) {
            game.setName(gameUpdated.getName());
            game.setNumberOfPlayers(gameUpdated.getNumberOfPlayers());
            game.setType(gameUpdated.getType());

            this.repositoryGame.save(game);
            return ResponseEntity.ok(game);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteGame(@PathVariable Integer id){
        if (this.repositoryGame.existsById(id)) {
            this.repositoryGame.deleteById(id);
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getGame(@PathVariable Integer id){
        Optional<Game> searchGame = this.repositoryGame.findById(id);

        if (searchGame.isPresent()) {
            return ResponseEntity.ok(searchGame.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
