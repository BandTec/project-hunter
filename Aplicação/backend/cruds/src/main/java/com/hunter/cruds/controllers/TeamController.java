package com.hunter.cruds.controllers;

import com.hunter.cruds.entities.Game;
import com.hunter.cruds.entities.Team;
import com.hunter.cruds.repositories.GameRepository;
import com.hunter.cruds.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamRepository repositoryTeam;

    @PostMapping
    public ResponseEntity createTeam(@RequestBody Team newTeam){
        this.repositoryTeam.save(newTeam);
        return ResponseEntity.created(null).build();
    }

    @GetMapping
    public ResponseEntity listTeams() {
        if (this.repositoryTeam.count() > 0) {
            return ResponseEntity.ok(this.repositoryTeam.findAll());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTeam(
            @PathVariable Integer id,
            @RequestBody Team teamUpdated) {

        Team team = this.repositoryTeam.findById(id).get();
        Optional<Team> searchTeam = this.repositoryTeam.findById(id);

        if (searchTeam.isPresent()) {
            team.setName(teamUpdated.getName());
            team.setCountry(teamUpdated.getCountry());
            team.setRegion(teamUpdated.getRegion());

            this.repositoryTeam.save(team);
            return ResponseEntity.ok(team);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTeam(@PathVariable Integer id){
        if (this.repositoryTeam.existsById(id)) {
            this.repositoryTeam.deleteById(id);
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getTeam(@PathVariable Integer id){
        Optional<Team> searchTeam = this.repositoryTeam.findById(id);

        if (searchTeam.isPresent()) {
            return ResponseEntity.ok(searchTeam.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
