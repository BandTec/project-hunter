package br.com.hunter.Modelos;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@DynamicUpdate
@Entity
public class Jogo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idJogo;

    private String nomeJogo;

    private Integer qtdePlayers;

    private String fotoJogo;

    public String getFotoJogo() {
        return fotoJogo;
    }

    public void setFotoJogo(String fotoJogo) {
        this.fotoJogo = fotoJogo;
    }

    public Integer getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(Integer idjogo) {
        this.idJogo = idjogo;
    }

    public String getNomeJogo() {
        return nomeJogo;
    }

    public void setNomeJogo(String nomeJogo) {
        this.nomeJogo = nomeJogo;
    }

    public Integer getQtdePlayers() {
        return qtdePlayers;
    }

    public void setQtdePlayers(Integer qtdPlayers) {
        this.qtdePlayers = qtdPlayers;
    }
}
